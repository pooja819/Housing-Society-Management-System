package sdm.hsmp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sdm.hsmp.jdbc.DriverConnection;

/**
 * Servlet implementation class MChngPass
 */
@WebServlet("/mchngpass")
public class MChngPass extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		String usr =(String) ses.getAttribute("uname");
		String ps = request.getParameter("pwd1");
		int i = DriverConnection.updateOperation("update tbllogin set passwd='"+AES.encrypt(ps)+"' where usrname ='"+AES.encrypt(usr)+"'");
		if(i >= 1){
			request.setAttribute("msg", "Password is updated");
			request.getRequestDispatcher("mchngpass.jsp").forward(request, response);
		}
		else{
			request.setAttribute("msg", "Password is updated failed !!!");
			request.getRequestDispatcher("mchngpass.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
