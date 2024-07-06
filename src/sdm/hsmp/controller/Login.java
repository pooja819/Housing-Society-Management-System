package sdm.hsmp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sdm.hsmp.jdbc.DriverConnection;

import java.sql.*;

@WebServlet("/log")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String uname = request.getParameter("usrname");
			String passwd = request.getParameter("passwd");
			//System.out.println(uname+" "+passwd);
			Connection con = (Connection) DriverConnection.get_connection();
			PreparedStatement pst = (PreparedStatement) con
					.prepareStatement("select * from tbllogin where usrname = ? and passwd = ?");
			pst.setString(1,uname);
			pst.setString(2, passwd);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				if(rs.getInt("status") == 1){
					String utype = rs.getString("utype");
					int id = rs.getInt("id");
					HttpSession session = request.getSession();
					session.setAttribute("uname",(uname));
					session.setAttribute("utype",(utype));
					
					if(utype.equalsIgnoreCase("admin")){
						response.sendRedirect("dashboard.jsp");
					}
					else if(utype.equalsIgnoreCase("member")){
						session.setAttribute("uid", id);
						response.sendRedirect("memhome.jsp");
						
					}
					else if(utype.equalsIgnoreCase("guard")){
						session.setAttribute("uid", id);
						response.sendRedirect("guardhome.jsp");
						
					}
			
				}
				else{
					String msg = "Incorrect User Name or Password";
	                request.setAttribute("emsg", msg);
	                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
	                rd.forward(request, response);
				}
				
			}
			else{
				String msg = "Incorrect User Name or Password";
                request.setAttribute("emsg", msg);
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
