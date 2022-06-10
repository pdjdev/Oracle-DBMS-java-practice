import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class JDBC_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	static Connection conn = null;
	static PreparedStatement stmt = null;
	
	String PCModel = ""; 		int PCSupply = 0; 		int PCPrice = 0;
	String LaptopModel = ""; 	int LaptopSupply = 0; 		int LaptopPrice = 0;
	String PrinterModel = ""; 	int PrinterSupply = 0; 	int PrinterPrice = 0;
	
	boolean purchaseAdded = false;
	
	JPanel MainPanel;
	JTabbedPane TabbedInfoPanel;
	
	// product panel
	JLabel TabCheckLabel;
	JComboBox<String> TabCheckProductCombobox;
	static JTextArea TabProductTextArea;		
	static JTextArea TabBuyTextArea;
	private JLabel PCLabel;
	private JLabel LaptopLabel;
	private JLabel PrinterLabel;
	private JTextField PCTextField;
	private JTextField LaptopTextField;
	private JTextField PrinterTextField;
	private JComboBox PCComboBox;
	private JComboBox LaptopComboBox;
	private JComboBox PrinterComboBox;
	private JButton BuyButton;
	private JButton CloseButton;
	private JTextArea TabLogTextArea;
	private JButton TabLogGetButton;
	private JLabel TotalRevLabel;
	private JPanel TopPanel1;
	private JLabel TopPanel1Label;
	private JPanel TopPanel2;
	private JLabel TopPanel2Label;
	private JButton TabBuyResetButton;
	private JButton TabBuyProceedButton;
	private JLabel ModelNumLabel;
	private JLabel SupplyLabel;
	// --
	
	public JDBC_GUI() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		setTitle("JDBC와 자바 GUI 실습");
		setBounds(100, 20, 845, 394);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MainPanel = new JPanel();
		MainPanel.setLayout(null);
		
		makeComponent();
		
		getContentPane().add(MainPanel, BorderLayout.CENTER);
		
		TopPanel1 = new JPanel();
		TopPanel1.setBorder(new LineBorder(new Color(0, 0, 0)));
		TopPanel1.setBounds(12, 10, 345, 58);
		TopPanel1.setLayout(null);
		MainPanel.add(TopPanel1);
		
		TopPanel1Label = new JLabel("DB \uCEF4\uD4E8\uD130\uAC00\uAC8C");
		TopPanel1Label.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		TopPanel1Label.setHorizontalAlignment(SwingConstants.CENTER);
		TopPanel1Label.setBounds(12, 10, 321, 38);
		TopPanel1.add(TopPanel1Label);
		
		TopPanel2 = new JPanel();
		TopPanel2.setLayout(null);
		TopPanel2.setBorder(new LineBorder(new Color(0, 0, 0)));
		TopPanel2.setBounds(369, 10, 452, 58);
		MainPanel.add(TopPanel2);
		
		TopPanel2Label = new JLabel("\uC88B\uC740 \uC2DC\uAC04 \uB418\uC138\uC694. " 
									+ LocalDate.now().format(DateTimeFormatter.ofPattern("[yyyy년 / MM월 / dd일]")));
		TopPanel2Label.setHorizontalAlignment(SwingConstants.CENTER);
		TopPanel2Label.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		TopPanel2Label.setBounds(12, 10, 428, 38);
		TopPanel2.add(TopPanel2Label);
		
		// 처음 시작시 자동으로 PC 리스트 보여주기
		JDBC_Connect_Product JCP = new JDBC_Connect_Product();
		TabProductTextArea.setText(JCP.getProductList("PC"));
	}
	
	public void makeComponent() {
		// left panel
		JPanel leftpanel = new JPanel();
		leftpanel.setLayout(null);
		leftpanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,
											 new Color(255, 255, 255),
											 new Color(160, 160, 160)),
											 "\uAD6C\uB9E4",
											 TitledBorder.LEADING,
											 TitledBorder.TOP,
											 null, new Color(0, 0, 0)));
		leftpanel.setToolTipText("");
		leftpanel.setBounds(12,78,345,269);
		MainPanel.add(leftpanel);
		
		PCLabel = new JLabel("PC");
		PCLabel.setBounds(37, 48, 67, 37);
		leftpanel.add(PCLabel);
		
		LaptopLabel = new JLabel("Laptop");
		LaptopLabel.setBounds(37, 93, 67, 37);
		leftpanel.add(LaptopLabel);
		
		PrinterLabel = new JLabel("Printer");
		PrinterLabel.setBounds(37, 140, 67, 37);
		leftpanel.add(PrinterLabel);
		
		PCTextField = new JTextField();
		PCTextField.setColumns(10);
		PCTextField.setBounds(116, 52, 107, 29);
		leftpanel.add(PCTextField);
		
		LaptopTextField = new JTextField();
		LaptopTextField.setColumns(10);
		LaptopTextField.setBounds(116, 97, 107, 29);
		leftpanel.add(LaptopTextField);
		
		PrinterTextField = new JTextField();
		PrinterTextField.setColumns(10);
		PrinterTextField.setBounds(116, 144, 107, 29);
		leftpanel.add(PrinterTextField);
		
		PCComboBox = new JComboBox();
		PCComboBox.setBounds(237, 52, 58, 29);
		leftpanel.add(PCComboBox);
		
		LaptopComboBox = new JComboBox();
		LaptopComboBox.setBounds(237, 97, 58, 29);
		leftpanel.add(LaptopComboBox);
		
		PrinterComboBox = new JComboBox();
		PrinterComboBox.setBounds(237, 144, 58, 29);
		leftpanel.add(PrinterComboBox);
		
		BuyButton = new JButton("\uAD6C\uB9E4");
		BuyButton.setBounds(58, 201, 100, 37);
		BuyButton.addActionListener(this);
		leftpanel.add(BuyButton);
		
		
		CloseButton = new JButton("\uB2EB\uAE30");
		CloseButton.setBounds(170, 201, 100, 37);
		CloseButton.addActionListener(this);
		leftpanel.add(CloseButton);
		
		ModelNumLabel = new JLabel();
		ModelNumLabel.setFont(new Font("굴림", Font.PLAIN, 11));
		ModelNumLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		ModelNumLabel.setText("\uBAA8\uB378 \uBC88\uD638");
		ModelNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ModelNumLabel.setBounds(116, 25, 107, 23);
		leftpanel.add(ModelNumLabel);
		
		SupplyLabel = new JLabel();
		SupplyLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		SupplyLabel.setText("\uAC1C\uC218");
		SupplyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		SupplyLabel.setFont(new Font("굴림", Font.PLAIN, 11));
		SupplyLabel.setBounds(237, 25, 58, 23);
		leftpanel.add(SupplyLabel);
		
		
		for (int i=1; i<11; i++) {
			PCComboBox.addItem(i);
			LaptopComboBox.addItem(i);
			PrinterComboBox.addItem(i);
		}
		
		// --
		
		// right panel
		TabbedInfoPanel = new JTabbedPane();
		JPanel TabbedInfoPanel_Product = new JPanel();
		TabbedInfoPanel_Product.setLayout(null);
		TabbedInfoPanel.addTab("조회", TabbedInfoPanel_Product);
		TabbedInfoPanel.setBounds(369, 78, 452, 269);
		// --
		
		// product panel
		TabCheckLabel = new JLabel();
		TabCheckLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TabCheckLabel.setText("\uC870\uD68C\uD560 \uBB3C\uD488 \uC120\uD0DD");
		TabCheckLabel.setIcon(new ImageIcon(""));
		TabCheckLabel.setBounds(12, 10, 105, 33);
		TabbedInfoPanel_Product.add(TabCheckLabel);
	
		TabCheckProductCombobox = new JComboBox<String>();
		// --
		
		// add combobox contents
		TabCheckProductCombobox.addItem("PC");
		TabCheckProductCombobox.addItem("Laptop");
		TabCheckProductCombobox.addItem("Printer");
		
		TabCheckProductCombobox.setBounds(129, 10, 130, 33);
		TabbedInfoPanel_Product.add(TabCheckProductCombobox);
		TabCheckProductCombobox.addActionListener(this);
		
		TabProductTextArea = new JTextArea();
		TabProductTextArea.setFont(new Font("굴림", 0, 12));
		TabProductTextArea.setForeground(Color.black);
		TabProductTextArea.setOpaque(true);
		TabProductTextArea.setBackground(Color.white);
		//TabProductTextArea.setBounds(12, 53, 423, 177);
		TabProductTextArea.setBorder(null);
		TabProductTextArea.setEditable(false);
		
		JScrollPane TabProductTextScroll = new JScrollPane();
		TabProductTextScroll.setViewportView(TabProductTextArea);
		TabProductTextScroll.setBounds(12, 53, 423, 177);
		TabbedInfoPanel_Product.add(TabProductTextScroll);
		
		
		
		// Buy panel
		JPanel TabbedInfoPanel_Buy = new JPanel();
		TabbedInfoPanel_Buy.setLayout(null);
		
		TabBuyTextArea = new JTextArea();
		TabBuyTextArea.setFont(new Font("굴림", 0, 12));
		TabBuyTextArea.setForeground(Color.black);
		TabBuyTextArea.setOpaque(true);
		TabBuyTextArea.setBackground(Color.white);
		TabBuyTextArea.setBorder(null);
		TabBuyTextArea.setEditable(false);
		
		JScrollPane TabBuyTextScroll = new JScrollPane();
		TabBuyTextScroll.setViewportView(TabBuyTextArea);
		TabBuyTextScroll.setBounds(12, 10, 423, 173);
		TabbedInfoPanel_Buy.add(TabBuyTextScroll);
		
		
		TabBuyResetButton = new JButton("\uB9AC\uC14B");
		TabBuyResetButton.setBounds(335, 193, 100, 37);
		TabBuyResetButton.addActionListener(this);
		TabbedInfoPanel_Buy.add(TabBuyResetButton);
		
		TabBuyProceedButton = new JButton("\uCD5C\uC885 \uAD6C\uB9E4");
		TabBuyProceedButton.setBounds(223, 193, 100, 37);
		TabBuyProceedButton.addActionListener(this);
		TabbedInfoPanel_Buy.add(TabBuyProceedButton);
		
		TabbedInfoPanel.addTab("최종구매", TabbedInfoPanel_Buy);
		MainPanel.add(TabbedInfoPanel);
		// --
		
		
		// Log panel
		JPanel TabbedInfoPanel_Log = new JPanel();
		TabbedInfoPanel_Log.setLayout(null);
				
		TabbedInfoPanel.addTab("구매내역", TabbedInfoPanel_Log);
		
		TabLogTextArea = new JTextArea();
		TabLogTextArea.setFont(new Font("굴림", 0, 12));
		TabLogTextArea.setForeground(Color.black);
		TabLogTextArea.setOpaque(true);
		TabLogTextArea.setBackground(Color.white);
		TabLogTextArea.setBorder(null);
		TabLogTextArea.setEditable(false);
		
		JScrollPane TabLogTextScroll = new JScrollPane();
		TabLogTextScroll.setViewportView(TabLogTextArea);
		TabLogTextScroll.setBounds(12, 10, 423, 173);
		TabbedInfoPanel_Log.add(TabLogTextScroll);

		TabLogGetButton = new JButton("\uC870\uD68C");
		TabLogGetButton.setBounds(335, 193, 100, 37);
		TabLogGetButton.addActionListener(this);
		TabbedInfoPanel_Log.add(TabLogGetButton);
		
		TotalRevLabel = new JLabel();
		TotalRevLabel.setText("KDE \uCEF4\uD4E8\uD130 \uAC00\uAC8C \uCD1D \uC218\uC785:");
		TotalRevLabel.setBounds(12, 193, 311, 33);
		TabbedInfoPanel_Log.add(TotalRevLabel);
		MainPanel.add(TabbedInfoPanel);
		// --
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object action = e.getSource();
		JDBC_Connect_Product JCP = new JDBC_Connect_Product();
		
		if (action == TabCheckProductCombobox) {
			String productType = (String) TabCheckProductCombobox.getSelectedItem();
			TabProductTextArea.setText(JCP.getProductList(productType));
			
		} else if (action == CloseButton) {
			System.exit(0);
			
		} else if (action == BuyButton) {
			if (PCTextField.getText().strip() == "" && 
				LaptopTextField.getText().strip() == "" &&
				PrinterTextField.getText().strip() == "" ) {
				JOptionPane.showMessageDialog(null, "세개 중에 한개는 입력해 주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			} else addPurchase(JCP);
			
		} else if (action == TabBuyResetButton) {
			JOptionPane.showMessageDialog(null, "리셋 되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			resetPurchase();
			
		} else if (action == TabBuyProceedButton) {
			if (!purchaseAdded) {
				JOptionPane.showMessageDialog(null, "먼저 구매를 해주세요.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			} else {
				int sum = PCPrice + LaptopPrice + PrinterPrice;
				if (doPurchase(JCP)) {
					JOptionPane.showMessageDialog(null, "최종구매하여 [총금액 : $" + sum + "] 가 결제되었습니다",
							  "메시지", JOptionPane.INFORMATION_MESSAGE);
					
					if (JOptionPane.showConfirmDialog(null, "계속 구매를 하겠습니까?",
												      "확인창", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						resetPurchase();
					} else {
						System.exit(0);
					};
					
					
				} else {
					JOptionPane.showMessageDialog(null, "최종구매 도중 문제가 발생했습니다",
							  "메시지", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (action == TabLogGetButton) {
			String[] result = JCP.getTransaction();
			
			TabLogTextArea.setText(result[0]);
			TotalRevLabel.setText("KDE 컴퓨터 가게 총 수입: $" + result[1]);
			
		}
	}
	
	private void addPurchase(JDBC_Connect_Product JCP) {
		resetPurchase();
		
		int pcPrice = 0;
		int laptopPrice = 0;
		int printerPrice = 0;
		
		// 가격 & 물량 가져오기
		if (PCTextField.getText().strip() != "") {
			PCModel = PCTextField.getText().strip();
			pcPrice = JCP.getProductPrice("PC", PCTextField.getText().strip());
			PCSupply = (int) PCComboBox.getSelectedItem();
		}
		if (LaptopTextField.getText().strip() != "") {
			LaptopModel = LaptopTextField.getText().strip();
			laptopPrice = JCP.getProductPrice("Laptop", LaptopTextField.getText().strip());
			LaptopSupply = (int) LaptopComboBox.getSelectedItem();
		}
		if (PrinterTextField.getText().strip() != "") {
			PrinterModel = PrinterTextField.getText().strip();
			printerPrice = JCP.getProductPrice("Printer", PrinterTextField.getText().strip());
			PrinterSupply = (int) PrinterComboBox.getSelectedItem();
		}
		if (pcPrice == -1 || laptopPrice == -1 || printerPrice == -1) {
			JOptionPane.showMessageDialog(null, "입력하신 모델이 존재하지 않습니다", "메시지", JOptionPane.INFORMATION_MESSAGE);
			
		} else {
			
			// 곱한 값 구하기
			PCPrice = pcPrice * PCSupply;
			LaptopPrice = laptopPrice * LaptopSupply;
			PrinterPrice = printerPrice * PrinterSupply;
			
			printPurchase();
			purchaseAdded = true;
			
			JOptionPane.showMessageDialog(null, "정상적으로 추가 되었습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
			TabbedInfoPanel.setSelectedIndex(1);
		}
	}
	
	private void resetPurchase() {
		purchaseAdded = false;
		TabBuyTextArea.setText("");
		PCModel = ""; PCSupply = 0; PCPrice = 0;
		LaptopModel = ""; LaptopSupply = 0; LaptopPrice = 0;
		PrinterModel = ""; PrinterSupply = 0; PrinterPrice = 0;
	}
	
	private void printPurchase() {
		String log = "";
		
		log += "-PC-\nPCmodel : ";
		if (PCModel == "") log += "없음";
		else log += PCModel;
		log += "\t개수 : " + PCSupply + "\t가격 : $" + PCPrice + "\n\n";
		
		log += "-Laptop-\nLaptopmodel : ";
		if (LaptopModel == "") log += "없음";
		else log += LaptopModel;
		log += "\t개수 : " + LaptopSupply + "\t가격 : $" + LaptopPrice + "\n\n";
		
		log += "-Printer-\nPrintermodel : ";
		if (PrinterModel == "") log += "없음";
		else log += PrinterModel;
		log += "\t개수 : " + PrinterSupply + "\t가격 : $" + PrinterPrice;
		
		TabBuyTextArea.setText(log);
	}
	
	private boolean doPurchase(JDBC_Connect_Product JCP) {
		if (PCModel != "" && PCPrice > 0) {
			if (!JCP.sendPurchase(PCModel, PCSupply, PCPrice)) return false; }
		if (LaptopModel != "" && LaptopPrice > 0) {
			if (!JCP.sendPurchase(LaptopModel, LaptopSupply, LaptopPrice)) return false; }
		if (PrinterModel != "" && PrinterPrice > 0) {
			if (!JCP.sendPurchase(PrinterModel, PrinterSupply, PrinterPrice)) return false; }
		
		return true;
	}
	
	public static void main(String[] args) {
	}
}

