import javax.swing.JOptionPane;

public class JDBC_Main {
	
	static String user = "";
	static String pw = "";
	
	public static void main(String[] args) {
		
		JDBC_Connect_Product JCP = new JDBC_Connect_Product();
		
		String dbid = JOptionPane.showInputDialog(null, "Oracle 아이디를 입력해 주세요", "입력", JOptionPane.QUESTION_MESSAGE);
		if (dbid == null) System.exit(0);
		String dbpw = JOptionPane.showInputDialog(null, "Oracle 비밀번호를 입력해 주세요", "입력", JOptionPane.QUESTION_MESSAGE);
		if (dbpw == null) System.exit(0);
		
		while (!JCP.initiateConnection(dbid, dbpw)) {
			JOptionPane.showMessageDialog(null, "로그인 할 수가 없습니다. 아이디와 비밀번호를 다시 입력해 주세요", "메시지", JOptionPane.INFORMATION_MESSAGE);
			dbid = JOptionPane.showInputDialog(null, "Oracle 아이디를 입력해 주세요", "입력", JOptionPane.QUESTION_MESSAGE);
			if (dbid == null) System.exit(0);
			dbpw = JOptionPane.showInputDialog(null, "Oracle 비밀번호를 입력해 주세요", "입력", JOptionPane.QUESTION_MESSAGE);
			if (dbpw == null) System.exit(0);
		}
		
		user = dbid;
		pw = dbpw;
		
		JOptionPane.showMessageDialog(null, "로그인 되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
		
		JDBC_GUI gui = new JDBC_GUI();
		gui.setVisible(true);
	}

}
