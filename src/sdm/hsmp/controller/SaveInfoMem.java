package sdm.hsmp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import sdm.hsmp.jdbc.DriverConnection;

@WebServlet("/svinfomem")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 100, // 10 MB
maxFileSize = 1024 * 1024 * 500, // 50 MB
maxRequestSize = 1024 * 1024 * 1000 // 100 MB
)
public class SaveInfoMem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "memphotos";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ResultSet rs = DriverConnection.selectOperation("select * from TBLFLATMEMBERS");
			int maxRow = 1;
			 while(rs.next()) {
				maxRow++;
			}
			String flatno = request.getParameter("flatno");
			String mName = request.getParameter("mname");
			String sex = request.getParameter("sex");
			String dob = request.getParameter("dob");
			String address = request.getParameter("address");
			String pin = request.getParameter("pin");
			String mobile = request.getParameter("mob");
			String eMail = request.getParameter("email");
			String photo = uploadFile(request, (maxRow +"") /* Max ID */, flatno);
			String education = request.getParameter("edu");
			String idType = request.getParameter("idp");
			String idNumber = request.getParameter("idnum");
			
			PreparedStatement pst=(PreparedStatement) (DriverConnection.get_connection()).prepareStatement("insert into TBLFLATMEMBERS values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pst.setInt(1, maxRow);
			pst.setString(2, "member");
			pst.setString(3, mName);
			pst.setString(4, flatno);
			pst.setString(5, sex);
			pst.setString(6, dob);
			pst.setString(7, address);
			pst.setString(8, pin);
			pst.setString(9, mobile);
			pst.setString(10, eMail);
			pst.setString(11, photo);
			pst.setString(12, education);
			pst.setString(13, idType);
			pst.setString(14, idNumber);
			pst.setInt(15,maxRow );
			int result = pst.executeUpdate();
			String query = "insert into tbllogin values ('"+AES.encrypt(mName)+"','"+AES.encrypt(mName)+"','member',"+maxRow+",1)";
			System.out.println(query);
		    int r = DriverConnection.updateOperation(query);
		    
		    String msg = "Flat is allowed !!!";
			request.setAttribute("err", msg);
			//request.setAttribute("err", msg);
			RequestDispatcher rd = request.getRequestDispatcher("pmembersentry.jsp");
			rd.forward(request, response);
			
		} catch (Exception e) {
System.out.println(e);
		}
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private String uploadFile(HttpServletRequest request, String iN, String fN) {
		String fileName = "", fileName1 = "";
		try {
			Part filePart = request.getPart("photo");
			fileName = getFileName(filePart);
			fileName1 = iN + "_" + fN + fileName.substring(fileName.indexOf("."));
			String applicationPath = request.getServletContext().getRealPath("/");
			// System.out.println(applicationPath);
			String basePath = applicationPath + UPLOAD_DIR + File.separator;
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				File outputFilePath = new File(basePath + fileName1);
				inputStream = filePart.getInputStream();
				outputStream = new FileOutputStream(outputFilePath);
				int read = 0;
				final byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				fileName1 = "";
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}

		} catch (Exception e) {
			fileName1 = "";
		}
		return fileName1;
	}

	private String getFileName(Part part) {
		for (String content : part.getHeader("Content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return "abc";
			}
		}
		return null;
	}


}
