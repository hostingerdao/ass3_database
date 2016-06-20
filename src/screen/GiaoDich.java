package screen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GiaoDich extends JFrame {

	private JPanel contentPane;
	private JTextField tfGiaRao;
	private JTextField tfGia;
	private JTextField tfMa;
	private JTextField tfLoai;
	private JTextField tfDienTich;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String maBDS;
	JComboBox cbBanMua;
	JComboBox cbNhanvien;
	JComboBox cbNam;
	JComboBox cbThang;
	JComboBox cbNgay;
	List<String> listNhanVien;
	JLabel tvError;
	JButton btnThucHien;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					GiaoDich frame = new GiaoDich();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void showdata(){
		
		try {
			tfMa.setText(maBDS);
			if(maBDS.substring(0,2).equals("CH")){
				tfLoai.setText("Can Ho");
			}else if(maBDS.substring(0,2).equals("DN")){
				tfLoai.setText("Dat Nen");
			}else if(maBDS.substring(0,2).equals("NP")){
				tfLoai.setText("Nha Pho");
			}
			String sqlBan = "select count(*) from sys.BDSDANGBAN where mabds = '" + maBDS+"'";
			ResultSet rs = ManagerDatabase.getResultFromSQLKinhDoanh(sqlBan);
			rs.next();
			if(rs.getInt(1) > 0){
				cbBanMua.addItem("Ban");
				 btnThucHien.setEnabled(true);
				 tfGia.setEditable(true);
			}
			
			String sqlThue = "select count(*) from sys.BDSDANGTHUE where mabds = '" + maBDS+"'";
			rs = ManagerDatabase.getResultFromSQLKinhDoanh(sqlThue);
			rs.next();
			if(rs.getInt(1) > 0){
				cbBanMua.addItem("Thue");
				btnThucHien.setEnabled(true);
				 tfGia.setEditable(true);
			}
			
			tfGiaRao.setText(ManagerDatabase.getGiaBan(maBDS));
			
			rs = ManagerDatabase.getNhanVienBH();
			listNhanVien = new ArrayList<String>();
			while(rs.next()){
				listNhanVien.add(new String(rs.getString(1)));
				cbNhanvien.addItem(new String(rs.getString(2)));
			}
			
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
			int day = now.get(Calendar.DAY_OF_MONTH);
			
			for(int i= -5; i < 1; i++ ){
				cbNam.addItem(year + i + "");
			}
			cbNam.setSelectedItem(year + "");
			
			for(int i= 1; i < 13; i++ ){
				cbThang.addItem(i<10?("0"+i):i + "");
			}
			cbThang.setSelectedIndex(month-1);
			
			updateDate(year, month, day);
			
//			System.out.printf("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month, day, hour, minute, second, millis);
			
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateDate(int year, int month, int ngay){
		int numngay = 0;
		if(month != 1){
			if( month == 0 ||month == 2 ||month == 4 ||month == 6 ||month == 7 ||month == 9 || month == 11 )
				numngay = 31;
			else
				numngay = 30;
		}else if(year%4 == 0 && month == 1){
			numngay = 29;
		}else if(year%4 != 0 && month == 1){
			numngay = 28;
		}
		cbNgay.removeAllItems();
		for(int i = 1; i <= numngay; i++){
			cbNgay.addItem(i<10?("0" + i):i+"");
		}
		
		if(ngay != 0){
			cbNgay.setSelectedIndex(ngay - 1);
		}
	};
	
	
	
	/**
	 * Create the frame.
	 */
	public GiaoDich(String maBDS) {
		this.maBDS = maBDS;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Giao d\u1ECBch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(363, 25, 247, 326);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblMuaHayBn = new JLabel("Mua hay B\u00E1n");
		lblMuaHayBn.setBounds(34, 130, 86, 14);
		panel.add(lblMuaHayBn);
		
		tfGiaRao = new JTextField();
		tfGiaRao.setEditable(false);
		tfGiaRao.setBounds(114, 158, 123, 20);
		panel.add(tfGiaRao);
		tfGiaRao.setColumns(10);
		
		JLabel lblGiRao = new JLabel("Gi\u00E1 Rao");
		lblGiRao.setBounds(34, 164, 86, 14);
		panel.add(lblGiRao);
		
		JLabel lblGiGiaoDch = new JLabel("Gi\u00E1 Giao d\u1ECBch");
		lblGiGiaoDch.setBounds(34, 192, 86, 14);
		panel.add(lblGiGiaoDch);
		
		tfGia = new JTextField();
		tfGia.setEditable(false);
		tfGia.setBounds(114, 189, 123, 20);
		panel.add(tfGia);
		tfGia.setColumns(10);
		
		btnThucHien = new JButton("Th\u1EF1c hi\u1EC7n");
		btnThucHien.setEnabled(false);
		
		btnThucHien.setBounds(93, 262, 110, 23);
		panel.add(btnThucHien);
		
		
		final String BDS = maBDS;
		cbBanMua = new JComboBox();
		cbBanMua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = cbBanMua.getSelectedIndex();
				if(index != -1){
					if(cbBanMua.getSelectedItem().equals("Ban"))
						tfGiaRao.setText(ManagerDatabase.getGiaBan(BDS).toString());
					else if(cbBanMua.getSelectedItem().equals("Thue"))
						tfGiaRao.setText(ManagerDatabase.getGiaThue(BDS).toString());
				}
			}
		});
		cbBanMua.setBounds(115, 127, 122, 20);
		panel.add(cbBanMua);
		
		tvError = new JLabel("Status");
		tvError.setBounds(34, 230, 169, 14);
		panel.add(tvError);
		
		JLabel lblNgy = new JLabel("Ng\u00E0y");
		lblNgy.setBounds(34, 105, 46, 14);
		panel.add(lblNgy);
		
		cbNgay = new JComboBox();
		cbNgay.setBounds(114, 99, 123, 20);
		panel.add(cbNgay);
		
		JLabel lblThng = new JLabel("Th\u00E1ng");
		lblThng.setBounds(34, 80, 46, 14);
		panel.add(lblThng);
		
		cbThang = new JComboBox();
		cbThang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDate(Integer.parseInt(cbNam.getSelectedItem().toString()), cbThang.getSelectedIndex(), 0 );
			}
		});
		cbThang.setBounds(114, 74, 123, 20);
		panel.add(cbThang);
		
		JLabel lblNm = new JLabel("N\u0103m");
		lblNm.setBounds(34, 55, 46, 14);
		panel.add(lblNm);
		
		cbNam = new JComboBox();
		cbNam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDate(Integer.parseInt(cbNam.getSelectedItem().toString()), cbThang.getSelectedIndex(), 0 );
			}
		});
		cbNam.setBounds(114, 49, 123, 20);
		panel.add(cbNam);
		
		JLabel lblNhnVin = new JLabel("Nh\u00E2n vi\u00EAn");
		lblNhnVin.setBounds(34, 30, 70, 14);
		panel.add(lblNhnVin);
		
		cbNhanvien = new JComboBox();
		cbNhanvien.setBounds(114, 24, 123, 20);
		panel.add(cbNhanvien);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "B\u1EA5t \u0111\u1ED9ng s\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(35, 25, 264, 214);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("M\u00E3");
		label.setBounds(43, 24, 68, 14);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Lo\u1EA1i");
		label_1.setBounds(43, 49, 68, 14);
		panel_1.add(label_1);
		
		tfMa = new JTextField();
		tfMa.setEditable(false);
		tfMa.setColumns(10);
		tfMa.setBounds(121, 21, 86, 20);
		panel_1.add(tfMa);
		
		tfLoai = new JTextField();
		tfLoai.setEditable(false);
		tfLoai.setColumns(10);
		tfLoai.setBounds(121, 46, 86, 20);
		panel_1.add(tfLoai);
		
		JLabel label_3 = new JLabel("Di\u1EC7n t\u00EDch");
		label_3.setBounds(43, 77, 68, 14);
		panel_1.add(label_3);
		
		tfDienTich = new JTextField();
		tfDienTich.setEditable(false);
		tfDienTich.setColumns(10);
		tfDienTich.setBounds(121, 74, 86, 20);
		panel_1.add(tfDienTich);
		
		JButton btnQuit = new JButton("QUIT");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GiaoDichThanhCong gdtc = new GiaoDichThanhCong();
				gdtc.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		
		btnThucHien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tfGiaRao.getText().equals("") ){
					tvError.setText("Not empty");
					
				}else if(!tfGiaRao.getText().matches("^\\d{1,12}")){
					tvError.setText("Invalid");
					
				}else if(Long.parseLong(tfGiaRao.getText()) <= Long.parseLong(tfGia.getText())){
						tvError.setText("Gia Khong hop le");
				}else{
						tvError.setText("");
				}
				
				String result = ManagerDatabase.ThucHienGiaoDich(tfMa.getText(),listNhanVien.get(cbNhanvien.getSelectedIndex()),
						cbNam.getSelectedItem().toString(),cbThang.getSelectedItem().toString(), cbNgay.getSelectedItem().toString(),
						tfGia.getText(), cbBanMua.getSelectedItem().toString());
				
				if(result.equals("success")){					
					ManagerDatabase.executeSqlKinhDoanh("commit");
					System.out.println("Insert successful");
				}
				
			}
		});
		
		
		btnQuit.setBounds(123, 271, 89, 23);
		contentPane.add(btnQuit);
		
		
		showdata();
	}
}
