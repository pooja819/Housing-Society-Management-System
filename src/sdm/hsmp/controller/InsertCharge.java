package sdm.hsmp.controller;

import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdm.hsmp.jdbc.DriverConnection;

/**
 * Servlet implementation class InsertCharge
 */
@WebServlet("/inschrg")
public class InsertCharge extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int maintenance=Integer.parseInt(request.getParameter("mcharge"));
		int water=Integer.parseInt(request.getParameter("wcharge"));
		int parking=Integer.parseInt(request.getParameter("pcharge"));
		int event=Integer.parseInt(request.getParameter("echarge"));
		int t=DriverConnection.updateOperation("update  TBLBILLCHARGE set MAINTENANCE="+maintenance+",PARKING="+parking+",water="+water+",event="+event+" where id=1");
		if(t>0)
		{
			request.setAttribute("msg", "Updated Successfully");
		    request.getRequestDispatcher("showcharges.jsp").forward(request,response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
