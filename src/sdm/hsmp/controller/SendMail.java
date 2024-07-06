package sdm.hsmp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet("/sendmail")
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private static String USER_NAME = "housing.society16";  // GMail user name (just the part before "@gmail.com")
	    private static String PASSWORD = "housing@12345"; // GMail password
	    private static String RECIPIENT = "housing.society16@gmail.com";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String name = request.getParameter("author");
		String gmail1 = request.getParameter("email");
		String body1 = request.getParameter("text");
		
		
		String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { "housing.society16@gmail.com" }; // list of recipient email addresses
        String subject = "Feed Back";
        String body = "Name is : "+name+"\n Mail From : "+gmail1+"\n Message :\n"+body1;

        sendFromGMail(from, pass, to, subject, body);
        
        String msg = "Feedback Sending Sucess";
        
        request.setAttribute("emsg", msg);
        RequestDispatcher rd = request.getRequestDispatcher("contact.jsp");
        rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
		
		
		
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		/*props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");*/

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}
