import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class JDBC_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	static Connection conn = null;
	static PreparedStatement stmt = null;
	
	JPanel MainPanel;
	JTabbedPane TabbedInfoPanel;
	
	// product panel
	JLabel TabProductLabel;
	JComboBox<String> TabProductmodelNumberCombobox;
	static JTextArea TabProductTextArea;		
	// --
	
	// product panel
	JLabel TabPCLabel;
	JComboBox<String> TabPCmodelNumberCombobox;
	static JTextArea TabPCTextArea;		
	// --
	
	public JDBC_GUI() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		setTitle("JDBC와 자바 GUI 실습");
		setBounds(100, 20, 540, 380);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MainPanel = new JPanel();
		MainPanel.setLayout(null);
		
		makeComponent();
		
		getContentPane().add(MainPanel, BorderLayout.CENTER);
	}
	
	public void makeComponent() {
		// panel
		TabbedInfoPanel = new JTabbedPane();
		JPanel TabbedInfoPanel_Product = new JPanel();
		TabbedInfoPanel_Product.setLayout(null);
		
		TabbedInfoPanel.addTab("Product", TabbedInfoPanel_Product);
		
		TabbedInfoPanel.setBounds(10, 20, 500, 300);
		MainPanel.add(TabbedInfoPanel);
		// --
		
		// product panel
		TabProductLabel = new JLabel();
		TabProductLabel.setText("model");
		TabProductLabel.setIcon(new ImageIcon(""));
		TabProductLabel.setBounds(20, 0, 80, 80);
		TabbedInfoPanel_Product.add(TabProductLabel);
	
		TabProductmodelNumberCombobox = new JComboBox<String>();
		// --
		
		// for문으로 콤보박스 넣기
		for (int i=1; i<10; i++) {
			TabProductmodelNumberCombobox.addItem("100"+i);
		}
		TabProductmodelNumberCombobox.addItem("1010");
		
		for (int i=1; i<9; i++) {
			TabProductmodelNumberCombobox.addItem("200"+i);
		}
		
		for (int i=1; i<7; i++) {
			TabProductmodelNumberCombobox.addItem("300"+i);
		}
		
		TabProductmodelNumberCombobox.setBounds(80, 20, 130, 40);
		TabbedInfoPanel_Product.add(TabProductmodelNumberCombobox);
		TabProductmodelNumberCombobox.addActionListener(this);
		
		TabProductTextArea = new JTextArea();
		TabProductTextArea.setFont(new Font("굴림", 0, 12));
		TabProductTextArea.setForeground(Color.black);
		TabProductTextArea.setOpaque(true);
		TabProductTextArea.setBackground(Color.white);
		TabProductTextArea.setBounds(20, 80, 450, 180);
		TabProductTextArea.setBorder(null);
		TabProductTextArea.setLineWrap(true);
		TabProductTextArea.setEditable(false);
		TabbedInfoPanel_Product.add(TabProductTextArea);
		
		
		
		// PC panel
		JPanel TabbedInfoPanel_PC = new JPanel();
		TabbedInfoPanel_PC.setLayout(null);
		
		TabbedInfoPanel.addTab("PC", TabbedInfoPanel_PC);
		MainPanel.add(TabbedInfoPanel);
		// --
		
		// PC panel
		TabPCLabel = new JLabel();
		TabPCLabel.setText("model");
		TabPCLabel.setIcon(new ImageIcon(""));
		TabPCLabel.setBounds(20, 0, 80, 80);
		TabbedInfoPanel_PC.add(TabPCLabel);
	
		TabPCmodelNumberCombobox = new JComboBox<String>();
		// --
		
		// for문으로 콤보박스 넣기
		for (int i=1; i<10; i++) { TabPCmodelNumberCombobox.addItem("100"+i); }
		TabPCmodelNumberCombobox.addItem("1001");
		
		TabPCmodelNumberCombobox.setBounds(80, 20, 130, 40);
		TabbedInfoPanel_PC.add(TabPCmodelNumberCombobox);
		TabPCmodelNumberCombobox.addActionListener(this);
		
		TabPCTextArea = new JTextArea();
		TabPCTextArea.setFont(new Font("굴림", 0, 12));
		TabPCTextArea.setForeground(Color.black);
		TabPCTextArea.setOpaque(true);
		TabPCTextArea.setBackground(Color.white);
		TabPCTextArea.setBounds(20, 80, 450, 180);
		TabPCTextArea.setBorder(null);
		TabPCTextArea.setLineWrap(true);
		TabPCTextArea.setEditable(false);
		TabbedInfoPanel_PC.add(TabPCTextArea);		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object buttonAction = e.getSource();
		
		if (buttonAction == TabProductmodelNumberCombobox) {
			String modelnumber = (String) TabProductmodelNumberCombobox.getSelectedItem();
			
			JDBC_Connect_Product JCP = new JDBC_Connect_Product();
			JCP.productSearch(modelnumber, false);
			System.out.println(modelnumber);
		} else if (buttonAction == TabPCmodelNumberCombobox) {
			String modelnumber = (String) TabPCmodelNumberCombobox.getSelectedItem();
			
			JDBC_Connect_Product JCP = new JDBC_Connect_Product();
			JCP.productSearch(modelnumber, true);
			System.out.println(modelnumber);
		}
	}
	
	public static void main(String[] args) {
	}

}

