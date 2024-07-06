package sdm.hsmp.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sdm.hsmp.jdbc.DriverConnection;

@WebServlet("/flch")
public class FlatCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs=null;
		String flat = request.getParameter("flatno");
		String query = "select * from tblflatmembers where flatno = '"+flat+"'";
		rs = DriverConnection.selectOperation(query);
		
		try {
			if(rs.next()){
				String msg = "The Flat is already alloted !!!";
				request.setAttribute("err", msg);
				RequestDispatcher rd = request.getRequestDispatcher("pownerentry.jsp");
				rd.forward(request, response);
				
			}
			else{
				request.setAttribute("fno", flat);
				request.getRequestDispatcher("ownerentry.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		// TODO Auto-generated method stub
	     String []op = new String[10];
		String nextpath="";
		String UPLOAD_DIRECTORY = "C:\\books";
		String action=request.getParameter("action");
		System.out.println("Action="+action);
		HttpSession session = request.getSession(true);
		
		/*	if("download".equals(action))
			{	ArrayList<FileBean> filelist = null;
				 session = request.getSession(true);
				File folder = new File("C://"+session.getAttribute("emailid")+"//Encrypt");
				if(folder != null){
				File[] listOfFiles = folder.listFiles();
				 filelist=new ArrayList<FileBean>();
				    for (int i = 0; listOfFiles !=null && i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				    	  FileBean bean= new FileBean();
				    	  System.out.println(listOfFiles[i].getName());
				    	  bean.setFilename(listOfFiles[i].getName());
				    	  bean.setFilesize(listOfFiles[i].length()/(1024L ));
				    	  filelist.add(bean);
				        
				      } 
				    }
				}
				request.setAttribute("filelist", filelist);				
				nextpath="/Download.jsp";	
			}
		else{*/
		{ double startTime = System.currentTimeMillis();
		String email =(String) session.getAttribute("emailid");
		System.out.println("Path="+session.getAttribute("emailid"));
		File file = new File(UPLOAD_DIRECTORY);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		 String name=null;
		int i =0;
		if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
           
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                         name = new File(item.getName()).getName();
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                    }else
                    {
                    	//if(!("fileupload".equalsIgnoreCase(op) || "upload".equalsIgnoreCase(op))){
                    		op[i++] = item.getString();
                    	System.out.println(op[i-1]+"------");
                    	//}
                    }
                }
            	Connection con = (Connection) DriverConnection.get_connection();
            	PreparedStatement pst = (PreparedStatement) con
            			.prepareStatement("insert into file values(?,?)");
            	pst.setString(1,op[2]);
            	pst.setString(2,name);
            	pst.executeUpdate();
              
        		 }catch (Exception e){
       		      
            		 System.out.println("Sorry, No Internet Connection" +e);     
            		                                                             
            		 } 
        	   
                
		}
                
                
                
         
               request.setAttribute("message", "File Uploaded Successfully");
         
		nextpath="/uploadbook.jsp";
		
		}
        request.getRequestDispatcher(nextpath).forward(request, response);
     


		    
	}

}
