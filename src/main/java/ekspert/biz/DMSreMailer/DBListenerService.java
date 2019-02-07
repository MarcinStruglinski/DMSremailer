package ekspert.biz.DMSreMailer;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import biz.ekspert.dao.DbConn;
import biz.ekspert.dao.DbOperations;
import service.MD5;
import service.MailSender;

public class DBListenerService extends Thread {
	public static final  String URL = "http:/localhost:8082/Confirmation/";
	 
	private List<Map<String, Object>> recordTaskList =  new ArrayList<Map<String, Object>>();  ;
    DbConn dbcon = new DbConn();
    MailSender mailsender = new MailSender();
    MD5 md5 = new MD5();
		
     
    private List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()){
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }
     
     
     private void queryTasks() throws SQLException {
    	 Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String   sql = "SELECT a.ID_XXX_ALERT, a.ID_SEKDOK, a.ID_UZYTKOWNIK, a.REQUESTDATE, a.MAILTO, a.TEXT, a.STATUS, a.DATEOFREPLY, a.HASH, a.ID_DODDOKUMZEW, a.ID_ETAPAKCEPT \r\n" + 
         		"FROM XXX_ALERT a  WHERE STATUS = 0 and  ID_ETAPAKCEPT IS NOT NULL ";
         
    	 try {
    			
			 conn = this.dbcon.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         recordTaskList = resultSetToList(rs);
			 pstmt.close();
			 rs.close();
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	 
     }  
     
     
    
     
	public void run() {
		
		while (true) {
		
		  try {
			  
			recordTaskList.clear();
			this.queryTasks();
			this.exequteTask();
			
		  } catch (SQLException e) {
			e.printStackTrace();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
		 	
			
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}


	private void exequteTask() throws SQLException, IOException {
		
		 Connection conn = null;
         PreparedStatement pstmt = null;
				  
		 Iterator<Map<String, Object>> iterator = 
				 recordTaskList.iterator();
	 
	        // iterate AL using while-loop
	        while(iterator.hasNext()) {
	        	 Map<String, Object> sqlrecord = iterator.next(); 
	        	 String Md5HashCode ="";   	 
	        	 Integer  id_alert	=   (int)  sqlrecord.get("ID_XXX_ALERT");
	        	 String head =  (String)  sqlrecord.get("TEXT") ;	
	        	 String reciver = (String)  sqlrecord.get("MAILTO") ;
	         	 Integer  iddokzew = (Integer)  sqlrecord.get("ID_DODDOKUMZEW") ;
	             Md5HashCode=MD5.generateMd5HashCode( id_alert.toString() );
	             String mesageText ="Akceptacja dokunetu  " +   URL  + Md5HashCode +"/true" + "\n" + "odrzucenie dokunetu  " +   URL + "/" + Md5HashCode +"/false" ;
			if( mailsender.sendAttachmentEmail(head, mesageText, reciver,iddokzew)) {
				conn = this.dbcon.getConnection();
		     
		         try {
					pstmt = conn.prepareStatement("update   XXX_ALERT set status =1, HASH = ? where ID_XXX_ALERT = ?");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        pstmt.setInt(2, id_alert);
		        pstmt.setString(1 ,Md5HashCode);
		        pstmt.executeUpdate();
		        //conn.commit();
		        pstmt.close();
		 
			}


	        }
	
	}
	

}
