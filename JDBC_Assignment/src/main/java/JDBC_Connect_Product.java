import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Connect_Product {
	
	public boolean initiateConnection(String user, String pw) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", user, pw);
		} catch (Exception e) {
			System.out.println("DB connection failed");
			return false;
		}
		return true;
	}

	public String getProductList(String productType) {
		
		String printTxt = "";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", JDBC_Main.user , JDBC_Main.pw);
			
			switch (productType) {
				case "PC":
					printTxt = "model" + '\t' + "speed" + '\t' + "ram" + '\t' + "HD" + '\t' + "CD" + '\t' + "price";
					break;
				case "Laptop":
					printTxt = "model" + '\t' + "speed" + '\t' + "ram" + '\t' + "HD" + '\t' + "screen" + '\t' + "price";
					break;
				case "Printer":
					printTxt = "model" + '\t' + "color" + '\t' + "type" + '\t' + "price";
					break;
				default:
					System.out.println("Invaild Product Type");
					return "";
			}
			
			printTxt += "\n--------------------------------------------------------------\n";
			
			String sqlStr = "SELECT * FROM " + productType;
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while (rs.next()) {
				switch (productType) {
					case "PC":
						printTxt += rs.getString("model").strip() + '\t' 
						         + rs.getString("speed").strip() + '\t' 
						         + rs.getString("ram").strip() + '\t' 
						         + rs.getString("HD").strip() + '\t' 
						         + rs.getString("CD").strip() + '\t' 
						         + rs.getString("price").strip() + '\n';
						break;
					case "Laptop":
						printTxt += rs.getString("model").strip() + '\t' 
						         + rs.getString("speed").strip() + '\t' 
						         + rs.getString("ram").strip() + '\t' 
						         + rs.getString("HD").strip() + '\t' 
						         + rs.getString("screen").strip() + '\t' 
						         + rs.getString("price").strip() + '\n';
						break;
					case "Printer":
						printTxt += rs.getString("model").strip() + '\t' 
						         + rs.getString("color").strip() + '\t' 
						         + rs.getString("type").strip() + '\t'
						         + rs.getString("price").strip() + '\n';
						break;
				}
			}
			
			rs.close();
			JDBC_GUI.stmt.close();
			
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			
		} finally {
			try {
				if (JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if (JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch (SQLException e) {
				return "";
			}
		}
		
		return printTxt;
	}

	public int getProductPrice(String productType, String model) {
		int price = -1;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", JDBC_Main.user , JDBC_Main.pw);
			
			String sqlStr = "SELECT * FROM " + productType + " WHERE model = '" + model + "'";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while (rs.next()) {
				price = rs.getInt("price");
			}
			
			rs.close();
			JDBC_GUI.stmt.close();
			
		} catch (Exception e) {
			System.out.println("error occured: " + e.getMessage());
			return -1;
			
		} finally {
			try {
				if (JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if (JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch (SQLException e) {
				return -1;
			}
		}
		
		return price;
	}
	
	public boolean sendPurchase(String model, int quantity, int price) {
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", JDBC_Main.user , JDBC_Main.pw);
			
			String sqlStr = "INSERT INTO transaction (tnumber, tmodel, tcount, tprice) "
					+ "VALUES (tnum_seq.NEXTVAL, " + model + ", " + quantity + ", " + price + ")";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			JDBC_GUI.stmt.executeQuery(sqlStr);
			JDBC_GUI.stmt.close();
			
		} catch (Exception e) {
			System.out.println("purchase error occured: " + e.getMessage());
			return false;
			
		} finally {
			try {
				if (JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if (JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch (SQLException e) {
				return false;
			}
		}
		
		return true;
	}
	
	public String[] getTransaction() {

		String printTxt = "tnumber" + '\t' + "tmodel" + '\t' + "tcount" + '\t' + "tprice";
		int sum = 0;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", JDBC_Main.user , JDBC_Main.pw);
			
			printTxt += "\n--------------------------------------------------------------\n";
			
			String sqlStr = "SELECT * FROM transaction ORDER BY tnumber DESC";
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while (rs.next()) {
				printTxt += rs.getString("tnumber").strip() + '\t'
						 + rs.getString("tmodel").strip() + '\t' 
						 + rs.getString("tcount").strip() + '\t' 
						 + '$' + rs.getString("tprice").strip() + '\n';
				sum += rs.getInt("tprice");
			}
			
			rs.close();
			JDBC_GUI.stmt.close();
			
		} catch (Exception e) {
			System.out.println("Error occured: " + e.getMessage());
			return null;
			
		} finally {
			try {
				if (JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if (JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch (SQLException e) {
				return null;
			}
		}
		
		String[] result = {printTxt, Integer.toString(sum)};
		
		return result;
	
	}

}


