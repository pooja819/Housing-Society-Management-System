package sdm.hsmp.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdm.hsmp.jdbc.DriverConnection;

@WebServlet("/updtemem")
public class UpdateMemberDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int mid = Integer.parseInt(request.getParameter("mid"));
		String oName = request.getParameter("name");
		String sex = request.getParameter("gender");
		String dob = request.getParameter("dob");
		String address = request.getParameter("address");
		String pin = request.getParameter("pin");
		String mobile = request.getParameter("mobile");
		String eMail = request.getParameter("email");
		String education = request.getParameter("qualification");
		

		try {
			PreparedStatement pst = (PreparedStatement) (DriverConnection.get_connection()).prepareStatement(
					"update TBLFLATMEMBERS set mname = ? , address = ? , pin = ? ,sex = ? , dob = ? , mobile = ? , email = ? , qualification = ? where id = ?");

			pst.setString(1, oName);
			pst.setString(2, address);
			pst.setString(3, pin);
			pst.setString(4, sex);
			pst.setString(5, dob);
			pst.setString(6, mobile);
			pst.setString(7, eMail);
			pst.setString(8, education);
			pst.setInt(9, mid);

			int result = pst.executeUpdate();
			
			request.setAttribute("fno", "Update Sucessful ......");
			
			request.getRequestDispatcher("dashboard.jsp").forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
