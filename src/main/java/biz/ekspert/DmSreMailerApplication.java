
package biz.ekspert;
import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biz.ekspert.dao.DbOperations;
import ekspert.biz.DMSreMailer.DBListenerService;
import service.MailSender;

@SpringBootApplication
@EnableAutoConfiguration
public class DmSreMailerApplication {

	
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		
		DBListenerService bBListenerService = new  DBListenerService();
		bBListenerService.start();		
        
		SpringApplication.run(DmSreMailerApplication.class, args);
	
	}

}

