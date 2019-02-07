package biz.ekspert.dao;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Repository.CommentsToken;


public class DbOperations {
	  private static final int BUFFER_SIZE = 4096;
    DbConn dbcon = new DbConn();
    String sql = "";


    public DbOperations()  {
    }

   
    public void  updateContent (CommentsToken commentsToken) throws SQLException  {
        Connection conn = null;
        PreparedStatement pstmt = null;
       String  result = "";
        try {
        	conn = this.dbcon.getConnection();
			pstmt = conn.prepareStatement("Update XXX_ALERT x set x.COMMENTS = ? where x.HASH  = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
	}
        pstmt.setString(1 ,commentsToken.getContent());
        pstmt.setString(2 ,commentsToken.getToken());
        System.out.println(commentsToken.getContent());
        pstmt.executeUpdate();
        //conn.commit();
        pstmt.close();
        
    }

   
    public void  updateStatus (String token) throws SQLException  {
        Connection conn = null;
        PreparedStatement pstmt = null;
       String  result = "";
        try {
        	conn = this.dbcon.getConnection();
			pstmt = conn.prepareStatement("Update XXX_ALERT x set x.status = 1 where x.HASH  = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
	}
        pstmt.setString(1 ,token);;
       
        pstmt.executeUpdate();
        //conn.commit();
        pstmt.close();
 

       
    }
    
    

    public Integer  getStatus (String token) throws SQLException  {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer  result = null; 
      //  this.sql = "select u.ID_UZYTKOWNIK,u.OZNNRWYDRUZYT,u.NAZWISKOIMIE, u.NAZWISKOIMIE,u.ID_GRUPAUZYTKOWNIK, from UZYTKOWNIK u WHERE P.NAZWISKOIMIE=? ";
        this.sql = "select x.status from XXX_ALERT x where x.HASH  = ?";
       
        try {
            conn = this.dbcon.getConnection();
            pstmt = conn.prepareStatement(this.sql);
            pstmt.setString(1 ,token);
            rs = pstmt.executeQuery();
            while ( rs.next() ) {
            	result= rs.getInt("Status");
               
            }
         
        } catch (SQLException var15) {new RuntimeException("Cannot loaddb,propierties files");} 
        finally {
            rs.close();
            pstmt.close();
            conn.close();
        }
        return result;
    }

 
    public String saveFileOnDiski( Integer iddok) throws IOException, SQLException {
    	
    	
    	
    	
    	 Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         String  Filename = null; 
         String filePath = "C:\\Temp\\";
    
       //  this.sql = "select u.ID_UZYTKOWNIK,u.OZNNRWYDRUZYT,u.NAZWISKOIMIE, u.NAZWISKOIMIE,u.ID_GRUPAUZYTKOWNIK, from UZYTKOWNIK u WHERE P.NAZWISKOIMIE=? ";
         this.sql = "SELECT NAZWAPLIKU, DOKUMENT FROM DODDOKUMZEW WHERE ID_DODDOKUMZEW  = ?";
        
         try {
             conn = this.dbcon.getConnection_DD();
            
            
             //System.out.println(  conn.getCatalog());
             pstmt = conn.prepareStatement(this.sql);
             pstmt.setInt(1, iddok);
             rs = pstmt.executeQuery();
             while ( rs.next() ) {
         
           	     
           	  Filename = rs.getString("NAZWAPLIKU") ; 
           	  Blob blob = rs.getBlob("DOKUMENT");
           	System.out.println(Filename);
            InputStream inputStream = blob.getBinaryStream();  
           	OutputStream outputStream = new FileOutputStream(filePath+Filename);

              int bytesRead = -1;
              byte[] buffer = new byte[BUFFER_SIZE];
        	
			while ((bytesRead = inputStream.read(buffer)) != -1) {
                  outputStream.write(buffer, 0, bytesRead);
              }

              inputStream.close();
              outputStream.close();
              System.out.println("File saved");
           	    
             }
          
         } catch (SQLException var15) {new RuntimeException("Cannot loaddb,propierties files");} 
         finally {
             rs.close();
             pstmt.close();
             conn.close();
         }
         return filePath+Filename;

     }
    
    
    
    
}
