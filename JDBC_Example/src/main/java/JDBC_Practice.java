import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class JDBC_Practice implements ActionListener {
	private JFrame frame = new JFrame();
	private JFrame frame2 = new JFrame();
	
	private JLabel idLabel = new JLabel("아이디");
	private JLabel pwdLabel = new JLabel("비밀번호");
	private JTextField idInput = new JTextField();
	private JPasswordField pwdInput = new JPasswordField();
	private JButton loginButton = new JButton("로그인");
	
	private JTextArea check_area = new JTextArea();
	private JComboBox<String> check_box = new JComboBox<String>();
	
	private JButton buyButton = new JButton("구매");
	private JComboBox<Integer> num = new JComboBox<Integer>();
	private JTextField modelInput = new JTextField();
	String model = "";
	int price = 0;
	int quantity = 0;
	
	private JPanel panel = new JPanel();
	private String username = "";
	private String password = "";
	private Connection dbTest;
	
	private void connectDB() {
		try {
			// JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.OracleDriver");
			dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", username, password);
			System.out.println("박동준님이 데이터베이스에 연결되었습니다");
			PCstore();
			showTable();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연결에 실패하였습니다");
			System.out.println("SQLException:" + e);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
	
	private void showTable() throws SQLException {
		String specification = "";
		
		String sqlStr = "select count(column_name) num from cols where table_name = '" +
		((String) check_box.getSelectedItem()).toUpperCase() + "'";
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		int number = rs.getInt("num");
		String[] tables = new String[number];
		
		sqlStr = "select column_name from cols where table_name = '" +
		((String) check_box.getSelectedItem()).toUpperCase() + "'";
		stmt = dbTest.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		for (number = 0; rs.next(); number++) {
			tables[number] = rs.getString("column_name");
			specification += tables[number] + '\t';
		}
		
		for (specification += "\n"; number > 0; number--) {
			specification += "--------------------";
		}
		specification += "\n";
		
		sqlStr = "select * from " + (String) check_box.getSelectedItem();
		stmt = dbTest.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		
		for (number = 0; rs.next(); number++) {
			for (int i=0; i < tables.length; i++) {
				specification += rs.getString(tables[i]) + '\t';
			}
			specification += "\n";
		}
		
		check_area.setText(specification);
		
		rs.close();
		stmt.close();
	}
	
	private void insertItem(String model, int quantity) throws SQLException {
		String sqlStr = "select price from " + (String) check_box.getSelectedItem() + " where model=" + model;
		PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			price = rs.getInt("price");
			
			sqlStr = "insert into transaction (tnumber, tmodel, tcount, tprice) values (tnum_seq.NEXTVAL," 
			+ model + "," + quantity + "," + price + ")";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "모델 " + model + " 을(를) " + quantity + "개 구매하였습니다.", "메세지", JOptionPane.INFORMATION_MESSAGE);
			
			rs.close();
			stmt.close();
		} else {
			JOptionPane.showMessageDialog(null, "박동준님, 입력한 모델명 " + model + "은(는) 존재하지 않기 때문에 구매할 수 없습니다.",
										  "메세지", JOptionPane.INFORMATION_MESSAGE);
			rs.close();
			stmt.close();
		}
	}
	
	private void PCstore() {
		frame2.setVisible(false);
		
		frame2 = new JFrame();
		panel = new JPanel();
		
		panel.setFont(new Font("맑은 고딕", 1, 12));
		panel.setBorder(new TitledBorder("조회"));
		panel.setBounds(380, 80, 490, 280);
		panel.setLayout(null);
		
		check_box.addItem("PC");
		check_box.addItem("Laptop");
		check_box.addItem("Printer");
		check_box.addItem("Product");

		for (int i = 1; i < 11; i++) {
			num.addItem(i);
		}
		num.setBounds(170, 40, 60, 30);
		
		buyButton.setBounds(300, 40, 60, 35);
		buyButton.addActionListener(this);
		
		check_area.setBorder(new LineBorder(Color.gray));
		check_area.setEditable(false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(check_area);
		
		check_box.setBounds(20, 40, 70, 30);
		scroll.setBounds(10, 80, 360, 170);
	
		check_box.addActionListener(this);
		
		modelInput.setBounds(100,40,70,30);
		
		panel.add(check_box);
		panel.add(scroll);
		panel.add(modelInput);
		panel.add(num);
		panel.add(buyButton);
		
		frame2.add(panel);
		
		frame2.setTitle("PC Store");
		frame2.setSize(400, 300);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
	}
	
	public JDBC_Practice() {
		panel.setLayout(null);
		
		idLabel.setBounds(20, 10, 60, 30);
		pwdLabel.setBounds(20, 50, 60, 30);
		idInput.setBounds(100, 10, 80, 30);
		pwdInput.setBounds(100, 50, 80, 30);
		loginButton.setBounds(200, 25, 80, 35);
		
		loginButton.addActionListener(this);
		
		panel.add(idLabel);
		panel.add(pwdLabel);
		panel.add(idInput);
		panel.add(pwdInput);
		panel.add(loginButton);
		
		frame.add(panel);
		
		frame.setTitle("JDBC Practice 1");
		frame.setSize(320, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			username = idInput.getText();
			password = new String(pwdInput.getPassword());
			
			connectDB();
		} else if (e.getSource() == check_box) {
			try {
				showTable();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} else if (e.getSource() == buyButton) {
			model = modelInput.getText();
			quantity = (int) num.getSelectedIndex() + 1;
			
			try {
				insertItem(model, quantity);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "구매 요청 중 문제가 발생했습니다", "메세지", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new JDBC_Practice();
	}

}
