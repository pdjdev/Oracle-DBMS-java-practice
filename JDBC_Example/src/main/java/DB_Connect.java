import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Connect {
	/*
	private String username = "";
	private String password = "";
	private static Connection dbTest;
	
	DB_Connect() {
		connectDB();
	}
	
	private void connectDB() {
		try {
			// JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.OracleDriver");
			dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", username, password);
			System.out.println("DB에 연결되었습니다");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연결에 실패하였습니다");
			System.out.println("SQLException:" + e);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
	
	public void execute_query() throws SQLException {
		String sqlStr = "SELECT maker, AVG(price) as Avgprice"
				+ " FROM product NATURAL JOIN PC"
				+ " WHERE ram = 16"
				+ " GROUP BY maker"
				+ " ORDER BY Avgprice ASC";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			System.out.println("MAKER: " + rs.getString("maker"));
			System.out.println("Avgprice: " + rs.getString("Avgprice"));
		}
		
		rs.close();
		stmt.close();
	}
	
	public static void main(String[] args) {	
		DB_Connect c1 = new DB_Connect();
		
		try {
			c1.execute_query();
			dbTest.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException:" + e);
		}
	}
*/
}

