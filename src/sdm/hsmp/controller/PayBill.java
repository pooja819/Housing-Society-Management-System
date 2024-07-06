package sdm.hsmp.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sdm.hsmp.jdbc.DriverConnection;

/**
 * Servlet implementation class PayBill
 */
@WebServlet("/Pb")
public class PayBill extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     String year=request.getParameter("year");
     String month=request.getParameter("month");
     String flatno=request.getParameter("flatno");
     Connection con=DriverConnection.get_connection();
     PreparedStatement pst;
	try {
		pst = con.prepareStatement("select * from TBLFLATMEMBERS where flatno=?");
		
		  pst.setString(1,flatno);
		     ResultSet rs=pst.executeQuery();
		     if(rs.next())
		     {
		    	 int t=0;
		    	 pst=con.prepareStatement("select max(id) from BILLPAYMENT");
		    	 ResultSet rs1=pst.executeQuery();
		    	 if(rs1.next())
		    	 {
		    		 t=rs1.getInt(1);
		    	 }
		    	 
		    	 
		    	 pst=con.prepareStatement("select * from BILLPAYMENT where flatno=? and month=? and year=?");
		    	 pst.setString(1,flatno);
		    	 pst.setString(2, month);
		    	 pst.setString(3,year);
		    	 rs1=pst.executeQuery();
		    	 if(!rs1.next()){
		    	   pst=con.prepareStatement("insert into BILLPAYMENT values(?,?,?,?,?)");
		    	   pst.setInt(1, t+1);
		    	   pst.setString(2, flatno);
		    	   pst.setString(3, month);
		    	   pst.setString(4, year);		    	 
		    	   pst.setInt(5, 1);
		    	   int f=pst.executeUpdate();
		    	   if(f>0)
		    	   {
		    		   request.setAttribute("msg", "Payment Successfull");
		    		   request.getRequestDispatcher("generatebill.jsp").forward(request,response);
		    	   }
		    	   
		    	 }else{
		    		 request.setAttribute("msg", "Paid already");
		    		   request.getRequestDispatcher("generatebill.jsp").forward(request,response);
		    	 }
		     }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
