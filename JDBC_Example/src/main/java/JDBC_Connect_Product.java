import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Connect_Product {
	
	public void productSearch(String modelnumber, boolean ispc) {
		String maker = "";
		String model = "";
		String type = "";
		
		String speed = "";
		String ram = "";
		String HD = "";
		String CD = "";
		String price = "";
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			JDBC_GUI.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "0101");
			
			String sqlStr;
			
			if (ispc) {
				sqlStr = "SELECT * FROM PC WHERE model = \'" + modelnumber + "\'";
			} else {
				sqlStr = "SELECT * FROM product WHERE model = \'" + modelnumber + "\'";
			}
			
			JDBC_GUI.stmt = JDBC_GUI.conn.prepareStatement(sqlStr);
			ResultSet rs = JDBC_GUI.stmt.executeQuery(sqlStr);
			
			while (rs.next()) {
				model = rs.getString("model");
							
				if (ispc) {
					speed = rs.getString("speed");
					ram = rs.getString("ram");
					HD = rs.getString("HD");
					CD = rs.getString("CD");
					price = rs.getString("price");
				} else {
					maker = rs.getString("maker");
					type = rs.getString("type");
				}
				
				System.out.println(model);
			}
			String printTxt = '\n' + "model : " + model + '\n';
			
			if (ispc) {
				printTxt += '\n' + "speed : " + speed 
				          + '\n' + "ram : " + ram 
				          + '\n' + "HD : " + HD 
				          + '\n' + "CD :" + CD 
				          + '\n' + "price : " + price + '\n';
				JDBC_GUI.TabPCTextArea.setText(printTxt);
			} else {
				printTxt += '\n' + "maker : " + maker 
						  + '\n' + "type : " + type + '\n';
				JDBC_GUI.TabProductTextArea.setText(printTxt);
			}

			rs.close();
			JDBC_GUI.stmt.close();
		} catch (Exception e) {
		} finally {
			try {
				if (JDBC_GUI.stmt != null)
					JDBC_GUI.stmt.close();
				if (JDBC_GUI.conn != null)
					JDBC_GUI.conn.close();
			} catch (SQLException e) {
				
			}
		}
	}
}


