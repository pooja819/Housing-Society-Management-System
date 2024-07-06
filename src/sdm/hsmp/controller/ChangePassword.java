package sdm.hsmp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdm.hsmp.jdbc.DriverConnection;


@WebServlet("/chng")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ps = request.getParameter("pwd1");
		int i = DriverConnection.updateOperation("update tbllogin set passwd='"+AES.encrypt(ps)+"' where passwd='"+AES.encrypt(ps)+"'");
		if(i == 1){
			request.setAttribute("msg", "Password is updated");
			request.getRequestDispatcher("changepassword.jsp").forward(request, response);
		}
		else{
			request.setAttribute("msg", "Password is updated failed !!!");
			request.getRequestDispatcher("changepassword.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
