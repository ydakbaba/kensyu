import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Myservlet
 */
@WebServlet("/Myservlet")
public class Myservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Myservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String text1 = "";	// テキスト1格納用変数
		String text2 = "";	// テキスト1格納用変数

		Properties prop = new Properties();
		Connection conn = null;
		ResultSet rs = null;
		String sql = null;
		PreparedStatement pstmt = null;

		String driver = null;
		String url = null;
		String userName = null;
		String password = null;

		text1 = request.getParameter("id");
		text2 = request.getParameter("pass");

	    String msg = "";
	    try {
	    	System.out.println("bbb");
	    	prop.load(new FileInputStream("C:/Users/baba1/Desktop/pleiades/workspace/LOGIN/WebContent/Connection.properties"));
	    	System.out.println("ccc");
	    	driver = prop.getProperty("driver");
	    	url = prop.getProperty("host");
	    	userName = prop.getProperty("user");
	    	password = prop.getProperty("password");
	    	System.out.println("aaa");
	    	Class.forName(driver);
	    	System.out.println("success");
	    }catch (ClassNotFoundException e){
	    	System.out.println("error1");
	    }catch (Exception e){
	    	System.out.println("error2");
	    }
		response.getWriter().append(msg);


    	String w_hosyu = null;

	    try{
	    	Class.forName(driver).newInstance();
	    	conn = DriverManager.getConnection(url, userName, password);
	    	Statement stmt = conn.createStatement();
/**        	String sql = "select name from usertable where  1 = 1" ;
 *
 */
	    	sql = "select name from usertable where  name = " + text1 + " and pass = " + text2 ;

	    	System.out.println(sql);

	    	rs = stmt.executeQuery(sql);


			while (rs.next()) {
		    	w_hosyu   = rs.getString("name");
			}
	    	rs.close();
	    	stmt.close();
	    }catch(ClassNotFoundException e){
		      msg = "bbb";
			response.getWriter().append(msg);
	    }catch (SQLException e){
	          msg = "xxx";
	  	    response.getWriter().append(msg);
	    }catch (Exception e){
		      msg = "ccc";
			response.getWriter().append(msg);
	    }

	    if (w_hosyu == null){
	    		getServletConfig().getServletContext().
	    		getRequestDispatcher("/error.jsp" ).
				forward( request, response );
	    } else {
	    		getServletConfig().getServletContext().
	    		getRequestDispatcher("/result.jsp" ).
	    		forward( request, response );
	    }
	}

}

