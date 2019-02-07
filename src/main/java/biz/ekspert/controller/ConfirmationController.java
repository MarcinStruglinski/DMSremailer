package biz.ekspert.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import Repository.CommentsToken;
import biz.ekspert.dao.DbOperations;




@Controller
public class ConfirmationController {

	  CommentsToken commentsToken = new CommentsToken();  
	  DbOperations dbOperations = new DbOperations();
	 

	  
	  @PostMapping("/save")
	    public String saveTime(@ModelAttribute CommentsToken  result) throws SQLException {
		  System.out.println(result.getToken());
		  dbOperations.updateContent(result);
	        return "index";
	    }
	  
	  
	  
	  @GetMapping( value = "/Confirmation/{token}/{akcept}" )
    public String  Confirmation(Model model,@PathVariable String token,@PathVariable String akcept) throws SQLException {
		  System.out.println("test");
		  Integer status = null;
		  try {
			status = dbOperations.getStatus(token);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
		  Boolean boolenakcept = true ;
	  if (akcept.equalsIgnoreCase("true") || akcept.equalsIgnoreCase("false")) {
			  boolenakcept.valueOf(akcept);			    // do something   
			} else {
			    // throw some exception
			}
		 if (status == 0 && boolenakcept ) {
			 dbOperations.updateStatus(token);
		 }
		  
         this.commentsToken.setAkcept(boolenakcept);
         this.commentsToken.setToken(token);
        this.commentsToken.setStatusDok(status);
       this.commentsToken.setContent("test");
		  model.addAttribute("CommentsToken", commentsToken);
		    return "Confirmation";
		  }
    
	
}
