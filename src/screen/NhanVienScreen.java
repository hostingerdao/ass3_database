package screen;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class NhanVienScreen extends JFrame {

	private JPanel contentPane;
	private JTextField tfName;
	private JTextField tfNgaySinh;
	private JTextField tfCMND;
	private JTextField tfLuong;
	private JComboBox cbPhuong;
	private JComboBox cbDuong;
	private JComboBox cbThanhPho;
	private JComboBox cbQuan;
	private JComboBox cbChiNhanh;
	private JLabel errMa;
	private JLabel errName;
	private JLabel errCMND;
	private JLabel errNgaySinh;
	List<String> listDuong;
	List<String> listPhuong;
	List<String> listQuan;
	List<String> listThanhPho;
	List<String> listChiNhanh;
	private JLabel tvResult;
	private JTextField tfEmail;
	private JTextField tfPhone;
	private JTextField tfNamVao;
	private JLabel ldb;
	private JTextField tfNhanVien;
	private JLabel errEmail;
	private JLabel errPhone;
	private JLabel errNamVao;
	private JLabel errLuong;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NhanVienScreen frame = new NhanVienScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public int checkData(){
		int result = 0;
		ResultSet rs;
		try {
			//check for MaCN
			rs = ManagerDatabase.getTableDuong();
//			INSERT INTO SYS.NHANVIEN values (sys.seq_MaNhanVien.nextval,'Nguyen Van A', '11-11-1990', '113496780','nguyenvananh@gmai.com', '0922345678', '2015', 'Truong Chi Nhanh',700000, 'D0001', 'P0001', '', 1 );
			String Hoten = "'" +  tfName.getText() + "'";//not empty
			
			String NgaySinh = tfNgaySinh.getText();
			NgaySinh = NgaySinh.equals("")?"null":"'" + NgaySinh + "'";
			
			String CMND = tfCMND.getText();
			
			String sqlCheckCMND = "SELECT COUNT(*) AS NUM FROM SYS.Nhanvien where sys.nhanvien.cmnd ="+ CMND;
			rs = ManagerDatabase.getResultFromSQL(sqlCheckCMND);
			rs.next();
			if(rs.getInt(1) != 0){
				errCMND.setText("\u0110\u00E3 t\u1ED3n t\u1EA1i");
				rs.close();
				return 0;
			}else{
				errCMND.setText("H\u1EE3p l\u1EC7");
			}
						
			
			String Phone = tfPhone.getText();
			Phone = Phone.equals("")?"null":"'" + Phone + "'";
			
			String NamVao = tfNamVao.getText();
			NamVao = NamVao.equals("")?"null":"'" + NamVao + "'";
			
			String Chucvu = tfNhanVien.getText();
			Chucvu = Chucvu.equals("")?"null":"'" + Chucvu + "'";
			
			String Email = tfEmail.getText();
			Email = Email.equals("")?"null":"'" + Email + "'";
			
			String Luong = tfLuong.getText();
			Luong = Luong.equals("")?"null":Luong ;
			
			String ChiNhanh = listChiNhanh.get(cbChiNhanh.getSelectedIndex());
			ChiNhanh = ChiNhanh.equals("")?"null":ChiNhanh;
						
			String Phuong = "'" + listPhuong.get(cbPhuong.getSelectedIndex()) + "'";
			
			String Duong =  "'" + listDuong.get(cbDuong.getSelectedIndex())+ "'";
			
			String sqlInsert =
					"INSERT INTO SYS.NHANVIEN values (sys.seq_MaNhanVien.nextval,"+ Hoten + ", " + NgaySinh+ ", " + CMND  + ", " +Email+ ", " + Phone+  ", "+ NamVao + ", " + Chucvu +", " + Luong + "," +  Duong + ", " + Phuong + ",''," +  ChiNhanh + ")";
			String returnMess = ManagerDatabase.executeSql(sqlInsert);
			tvResult.setText(returnMess);
			if(returnMess.equals("success")){
				ManagerDatabase.UpdateDiaChiNhanVien();
				System.out.println(sqlInsert);
				ManagerDatabase.getResultFromSQL("commit");
				System.out.println("Insert successful");
			}
			
			
			rs.close();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			tvResult.setText(e.getMessage());
			return 0;
		}
		
	};
	
	void createDataForComboBox(){
		ResultSet rs;
		try {
			//list city
			rs = ManagerDatabase.getTableTP();
			listThanhPho = new ArrayList<String>();
			while(rs.next()){
				listThanhPho.add(new String(rs.getString(1)));
				cbThanhPho.addItem(new String(rs.getString(2)));
			}
			
			ChangeTP();
			
			//list street
			rs = ManagerDatabase.getTableDuong();
			listDuong = new ArrayList<String>();
			while(rs.next()){
				listDuong.add(new String(rs.getString(1)));
				cbDuong.addItem(new String(rs.getString(2)));
			}
			
			//list ChiNhanh
			String sqlgetEmployee = "SELECT macn, tencn  FROM sys.chinhanh";
			rs = ManagerDatabase.getResultFromSQL(sqlgetEmployee);
			listChiNhanh = new ArrayList<String>();
		while(rs.next()){
			listChiNhanh.add(new String(rs.getString(1)));
			cbChiNhanh.addItem(new String(rs.getString(2)));
		}
			
		
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void ChangeTP(){
		String maTP = listThanhPho.get(cbThanhPho.getSelectedIndex());
		ChangeQuan(maTP);
	};
	
	void ChangeQuan(String maTP){
		ResultSet rs;			
		if(maTP != null){
			try {
				rs = ManagerDatabase.getTableQuan(maTP);
				listQuan = new ArrayList<String>();
				cbQuan.removeAllItems();
				while(rs.next()){
					listQuan.add(new String(rs.getString(1)));
					cbQuan.addItem(new String(rs.getString(2)));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(cbQuan.getSelectedIndex() != -1){
			String maQuan = listQuan.get(cbQuan.getSelectedIndex());
			ChangePhuong(maQuan);
		}
	}
	
	void ChangePhuong(String maQuan){
		try {
			ResultSet rs = ManagerDatabase.getTablePhuong(maQuan);
			listPhuong = new ArrayList<String>();
			cbPhuong.removeAllItems();
			while(rs.next()){
				listPhuong.add(new String(rs.getString(1)));
				cbPhuong.addItem(new String(rs.getString(2)));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * Create the frame.
	 */
	public NhanVienScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 458, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHVTn = new JLabel("H\u1ECD v\u00E0 t\u00EAn*");
		lblHVTn.setBounds(10, 57, 57, 14);
		contentPane.add(lblHVTn);
		
		tfName = new JTextField();
		tfName.setText("nguyen van a");
		tfName.setBounds(129, 54, 129, 20);
		contentPane.add(tfName);
		tfName.setColumns(10);
		
		tfNgaySinh = new JTextField();
		tfNgaySinh.setText("06-12-1993");
		tfNgaySinh.setBounds(129, 82, 129, 20);
		contentPane.add(tfNgaySinh);
		tfNgaySinh.setColumns(10);
		
		JLabel lblNgySinh = new JLabel("Ng\u00E0y sinh");
		lblNgySinh.setBounds(10, 85, 68, 14);
		contentPane.add(lblNgySinh);
		
		JLabel lblCmnd = new JLabel("CMND*");
		lblCmnd.setBounds(10, 113, 46, 14);
		contentPane.add(lblCmnd);
		
		JLabel lblLng = new JLabel("L\u01B0\u01A1ng");
		lblLng.setBounds(10, 228, 46, 14);
		contentPane.add(lblLng);
		
		JLabel lblChiNhnh = new JLabel("Chi nh\u00E1nh");
		lblChiNhnh.setBounds(10, 294, 68, 14);
		contentPane.add(lblChiNhnh);
		
		JLabel lblThnhPh = new JLabel("Th\u00E0nh ph\u1ED1");
		lblThnhPh.setBounds(10, 325, 80, 14);
		contentPane.add(lblThnhPh);
		
		JLabel lblQun = new JLabel("Qu\u1EADn");
		lblQun.setBounds(10, 353, 46, 14);
		contentPane.add(lblQun);
		
		JLabel lblPhng = new JLabel("Ph\u01B0\u1EDDng");
		lblPhng.setBounds(10, 381, 68, 14);
		contentPane.add(lblPhng);
		
		JLabel lblng = new JLabel("\u0110\u01B0\u1EDDng");
		lblng.setBounds(10, 409, 68, 14);
		contentPane.add(lblng);
		
		cbDuong = new JComboBox();
		cbDuong.setBounds(129, 406, 129, 20);
		contentPane.add(cbDuong);
		
		cbQuan = new JComboBox();
		cbQuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeQuan(null);
			}
		});


		
		cbQuan.setBounds(129, 350, 129, 20);
		contentPane.add(cbQuan);
		
		cbPhuong = new JComboBox();
		cbPhuong.setBounds(129, 378, 129, 20);
		contentPane.add(cbPhuong);
		
		cbChiNhanh = new JComboBox();
		cbChiNhanh.setBounds(129, 291, 129, 20);
		contentPane.add(cbChiNhanh);
		
		cbThanhPho = new JComboBox();
		cbThanhPho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeTP();
			}
		});
		cbThanhPho.setBounds(129, 322, 129, 20);
		contentPane.add(cbThanhPho);
		
		tfLuong = new JTextField();
		tfLuong.setText("7000000");
		tfLuong.setBounds(129, 225, 129, 20);
		contentPane.add(tfLuong);
		tfLuong.setColumns(10);
		
		tfCMND = new JTextField();
		tfCMND.setText("123456789");
		tfCMND.setBounds(129, 110, 129, 20);
		contentPane.add(tfCMND);
		tfCMND.setColumns(10);
		
		JButton btnSave = new JButton("SAVE");
		btnSave.setBounds(69, 517, 89, 23);
		contentPane.add(btnSave);
		
		JButton btnQuit = new JButton("QUIT");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen3 ret = new screen3();
				ret.setVisible(true);
				setVisible(false); //you can't see me!
				dispose();
			}
		});
		btnQuit.setBounds(201, 517, 89, 23);
		contentPane.add(btnQuit);
		
		errMa = new JLabel("");
		errMa.setBounds(292, 26, 113, 14);
		contentPane.add(errMa);
		
		errName = new JLabel("");
		errName.setBounds(292, 57, 113, 14);
		contentPane.add(errName);
		
		errCMND = new JLabel("");
		errCMND.setBounds(292, 113, 113, 14);
		contentPane.add(errCMND);
		
		tvResult = new JLabel("");
		tvResult.setBounds(10, 434, 341, 72);
		contentPane.add(tvResult);
		
		JLabel label = new JLabel("Email");
		label.setBounds(10, 141, 46, 14);
		contentPane.add(label);
		
		tfEmail = new JTextField();
		tfEmail.setText("vannguyen@gmail.com");
		tfEmail.setColumns(10);
		tfEmail.setBounds(129, 138, 129, 20);
		contentPane.add(tfEmail);
		
		JLabel label_1 = new JLabel("Phone");
		label_1.setBounds(10, 169, 46, 14);
		contentPane.add(label_1);
		
		tfPhone = new JTextField();
		tfPhone.setText("0922345677");
		tfPhone.setColumns(10);
		tfPhone.setBounds(129, 166, 129, 20);
		contentPane.add(tfPhone);
		
		JLabel label_2 = new JLabel("N\u0103m v\u00E0o");
		label_2.setBounds(10, 197, 68, 14);
		contentPane.add(label_2);
		
		tfNamVao = new JTextField();
		tfNamVao.setText("2015");
		tfNamVao.setColumns(10);
		tfNamVao.setBounds(129, 194, 129, 20);
		contentPane.add(tfNamVao);
		
		ldb = new JLabel("Ch\u1EE9c v\u1EE5");
		ldb.setBounds(10, 256, 46, 14);
		contentPane.add(ldb);
		
		tfNhanVien = new JTextField();
		tfNhanVien.setText("Nhan Vien");
		tfNhanVien.setColumns(10);
		tfNhanVien.setBounds(129, 253, 129, 20);
		contentPane.add(tfNhanVien);
		
		errNgaySinh = new JLabel("");
		errNgaySinh.setBounds(292, 85, 113, 14);
		contentPane.add(errNgaySinh);
		
		errEmail = new JLabel("");
		errEmail.setBounds(292, 141, 113, 14);
		contentPane.add(errEmail);
		
		errPhone = new JLabel("");
		errPhone.setBounds(292, 169, 113, 14);
		contentPane.add(errPhone);
		
		errNamVao = new JLabel("");
		errNamVao.setBounds(292, 197, 113, 14);
		contentPane.add(errNamVao);
		
		errLuong = new JLabel("");
		errLuong.setBounds(292, 228, 113, 14);
		contentPane.add(errLuong);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int isContinue = 1;
				if(tfName.getText().equals("") ){
					errName.setText("Not empty");
					isContinue = 0;
				}else 
					errName.setText("");

				if(tfCMND.getText().equals("") ){
					errCMND.setText("Not empty");
					isContinue = 0;
				}else if(!tfCMND.getText().matches("^\\d{9}|\\d{11}")){
					errCMND.setText("Invalid");
						isContinue = 0;
				}else					
					errCMND.setText("");
					
				
				if(tfNgaySinh.getText().equals("") ){
					errNgaySinh.setText("");
				}else if(!tfNgaySinh.getText().matches("^(0[1-9]|[12][0-9]|3[01])[- \\/.](0[1-9]|1[012])[- \\/.](19|20)\\d\\d$")){
					errNgaySinh.setText("Invalid");
						isContinue = 0;
				}else 
					errNgaySinh.setText("");
				
				if(tfEmail.getText().equals("") ){
					errEmail.setText("");
				}else if(!tfEmail.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
				{
					errEmail.setText("Invalid");
						isContinue = 0;
				}else 
					errEmail.setText("");
				
				if(tfPhone.getText().equals("") ){
					errPhone.setText("");
				}else if(!tfPhone.getText().matches("^\\d{10,11}$"))
				{
					errPhone.setText("Invalid");
						isContinue = 0;
				}else 
					errPhone.setText("");
				
				
				if(tfNamVao.getText().equals("") ){
					errNamVao.setText("");
				}else if(!tfNamVao.getText().matches("^(19|20)\\d\\d$")){
					errNamVao.setText("Invalid");
					isContinue = 0;
				}else 
					errNamVao.setText("");
				
				if(tfLuong.getText().equals("") ){
					errLuong.setText("");
				}else if(!tfLuong.getText().matches("\\d+$")){
					errLuong.setText("Invalid");
					isContinue = 0;
				}else 
					errLuong.setText("");
				
				if(isContinue == 1){
					if(checkData() == 1){
//						screen3 ret = new screen3();
//						ret.setVisible(true);
//						setVisible(false); //you can't see me!
//						dispose();
					}
				}			
				
				return;
				
			}
		});
		
		
		createDataForComboBox();
	}
}
