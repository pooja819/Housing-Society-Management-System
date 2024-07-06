package sdm.hsmp.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdm.hsmp.jdbc.DriverConnection;

@WebServlet("/flli")
public class FlatList extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ResultSet rs = null;
			String flat = request.getParameter("fl");
			String query = "select * from tblflatmembers where flatno = '" + flat+"'";
			rs = DriverConnection.selectOperation(query);

			if (rs.next()) {
				//String msg = "The Flat is Not alloted !!!";
				request.setAttribute("fno", flat);
				RequestDispatcher rd = request.getRequestDispatcher("listmembers.jsp");
				rd.forward(request, response);

			}
			else{
				String msg = "The Flat is Not alloted !!!";
				request.setAttribute("err", msg);
				RequestDispatcher rd = request.getRequestDispatcher("plistmembers.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
