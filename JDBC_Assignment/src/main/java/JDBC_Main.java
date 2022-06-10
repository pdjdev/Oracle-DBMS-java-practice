import javax.swing.JOptionPane;

public class JDBC_Main {
	
	static String user = "";
	static String pw = "";
	
	public static void main(String[] args) {
		
		JDBC_Connect_Product JCP = new JDBC_Connect_Product();
		
		String dbid = JOptionPane.showInputDialog(null, "Oracle ���̵� �Է��� �ּ���", "�Է�", JOptionPane.QUESTION_MESSAGE);
		if (dbid == null) System.exit(0);
		String dbpw = JOptionPane.showInputDialog(null, "Oracle ��й�ȣ�� �Է��� �ּ���", "�Է�", JOptionPane.QUESTION_MESSAGE);
		if (dbpw == null) System.exit(0);
		
		while (!JCP.initiateConnection(dbid, dbpw)) {
			JOptionPane.showMessageDialog(null, "�α��� �� ���� �����ϴ�. ���̵�� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�޽���", JOptionPane.INFORMATION_MESSAGE);
			dbid = JOptionPane.showInputDialog(null, "Oracle ���̵� �Է��� �ּ���", "�Է�", JOptionPane.QUESTION_MESSAGE);
			if (dbid == null) System.exit(0);
			dbpw = JOptionPane.showInputDialog(null, "Oracle ��й�ȣ�� �Է��� �ּ���", "�Է�", JOptionPane.QUESTION_MESSAGE);
			if (dbpw == null) System.exit(0);
		}
		
		user = dbid;
		pw = dbpw;
		
		JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�.", "�޽���", JOptionPane.INFORMATION_MESSAGE);
		
		JDBC_GUI gui = new JDBC_GUI();
		gui.setVisible(true);
	}

}
