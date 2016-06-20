package screen;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class screenEditChiNhanh extends JFrame {

	private JPanel contentPane;
	private JTextField tfMa;
	private JTextField tfTen;
	private JTextField tfFax;
	private JComboBox cbPhuong;
	private JComboBox cbDuong;
	private JComboBox cbTruongCN;
	private JLabel tvTenCNStatus;
	private JLabel tvMaStatus;
	private JLabel tvFaxStatus;
	List<String> listDuong;
	List<String> listPhuong;
	List<String> listQuan;
	List<String> listThanhPho;
	List<String> listNhanVien;
	private JLabel lblThnhPh;
	private JComboBox cbThanhpho;
	private JLabel lblQun;
	private JComboBox cbQuan;
	ChiNhanh chinhanh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChiNhanhInfo frame = new ChiNhanhInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public int checkData(){
		int result = 0;
		Connection connection = ManagerDatabase.getConnection("NhanSuAdmin", "NhanSuAdmin");
		Statement st;
		try {
			int check = 1;
			st = connection.createStatement();
			String sql = "SELECT sys.chinhanh.MACN as \"M\u00E3\", TENCN as \"T\u00EAn Chi Nh\u00E1nh\", SOFAX as \"S\u1ED1 Fax\", sys.chinhanh.DIACHI as \"Chi Nh\u00E1nh\", SYS.NHANVIEN.HOTEN as \"Tr\u01B0\u1EDFng Chi Nh\u00E1nh\" FROM SYS.CHINHANH, SYS.NHANVIEN WHERE SYS.CHINHANH.MANVTRUONG = SYS.NHANVIEN.MANV";
			ResultSet rs;
			
			//check for Ten Chi Nhanh
			String sqlCheckTen = "SELECT COUNT(*) AS NUM FROM SYS.CHINHANH where sys.chinhanh.TENCN ='"+ tfTen.getText()+ "'";
			rs = st.executeQuery(sqlCheckTen);rs.next();
			if(rs.getInt(1) != 0 && rs.getString(1).equals(chinhanh.getTenCN())){
				tvTenCNStatus.setText("\u0110\u00E3 t\u1ED3n t\u1EA1i");
				check = -1;
			}
			else
				tvTenCNStatus.setText("H\u1EE3p l\u1EC7");
			System.out.println(rs.getInt(1));
			
//			while(rs.next()){
//				System.out.println(rs.getString(1) + " " + rs.getString(2));
//				//co the get data theo ten cua truong getInt("ID"); va no bat dau tu vi tri 1
//			};
			String truongCN = listNhanVien.get(cbTruongCN.getSelectedIndex());
			truongCN = truongCN.equals("")?"null":"'" + truongCN+"'";
			if(check == 1){
				String sqlUpdate = "UPDATE SYS.CHINHANH SET TENCN = '"+tfTen.getText()+"', SOFAX = '" + tfFax.getText()+ "' , MADUONG = '"+listDuong.get(cbDuong.getSelectedIndex())+"' , MAPHUONG = '"+listPhuong.get(cbPhuong.getSelectedIndex()) +"' , MANVTRUONG= "+truongCN+" WHERE MACN =" + tfMa.getText();
				System.out.println(sqlUpdate);
				rs = st.executeQuery(sqlUpdate);
				String sqlUpdateDiaChi = "update sys.chinhanh set diachi = ( SELECT TENDUONG || ', ' ||TENPHUONG || ', ' ||TENQUAN || ', ' || TENTP FROM sys.DUONG D, sys.PHUONG P, sys.QUAN Q, sys.THANHPHO TP where chinhanh.maduong  =  d.maduong and p.maphuong = chinhanh.maphuong and p.maquan = q.maquan and q.matp = tp.matp)";
				rs = st.executeQuery(sqlUpdateDiaChi);
				System.out.println("Insert successful");
				result = 1;
			}	
			
			rs.close();
			st.close();
			connection.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	};
	
	void showData(ChiNhanh chinhanh){
		Connection connection = ManagerDatabase.getConnection("NhanSuAdmin", "NhanSuAdmin");
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs;

			tfMa.setText(chinhanh.getMaCN());
			tfTen.setText(chinhanh.getTenCN());
			tfFax.setText(chinhanh.getSoFax());			
			
			//list city
			String sqlgetTp = "SELECT *  FROM SYS.thanhpho";
			rs = st.executeQuery(sqlgetTp);
			listThanhPho = new ArrayList<String>();
			while(rs.next()){
				listThanhPho.add(new String(rs.getString(1)));
				cbThanhpho.addItem(new String(rs.getString(2)));
				if(rs.getString(1).equals(chinhanh.getMaTP()))
					cbThanhpho.setSelectedIndex(listThanhPho.size() - 1);
			}
			
			//list quan
			String sqlgetQuan = "SELECT distinct sys.quan.maquan , sys.quan.tenquan FROM SYS.quan, sys.thanhpho where sys.quan.matp = sys.thanhpho.matp and sys.thanhpho.matp = '" + chinhanh.getMaTP() + "'";
			rs = st.executeQuery(sqlgetQuan);
			listQuan = new ArrayList<String>();
			cbQuan.removeAllItems();
			while(rs.next()){
				listQuan.add(new String(rs.getString(1)));
				cbQuan.addItem(new String(rs.getString(2)));
				if(rs.getString(1).equals(chinhanh.getMaQuan()))
					cbQuan.setSelectedIndex(listQuan.size() - 1);
			}
			
			//list Phuong
			String sqlgetPhuong = "SELECT sys.phuong.maphuong, sys.phuong.tenphuong  FROM SYS.phuong, sys.quan where sys.phuong.maquan = sys.quan.maquan and sys.quan.maquan = '" + chinhanh.getMaQuan() + "'";
			rs = st.executeQuery(sqlgetPhuong);
			listPhuong = new ArrayList<String>();
			cbPhuong.removeAllItems();
			while(rs.next()){
				listPhuong.add(new String(rs.getString(1)));				
				cbPhuong.addItem(new String(rs.getString(2)));
				if(rs.getString(1).equals(chinhanh.getMaPhuong()))
					cbPhuong.setSelectedIndex(listPhuong.size() - 1);
			}
			
			
			//list street
			String sqlgetStreet = "SELECT *  FROM sys.duong";
			rs = st.executeQuery(sqlgetStreet);
			listDuong = new ArrayList<String>();
			while(rs.next()){
				listDuong.add(new String(rs.getString(1)));
				cbDuong.addItem(new String(rs.getString(2)));
				if(rs.getString(1).equals(chinhanh.getMaDuong()))
					cbDuong.setSelectedIndex(listDuong.size() - 1);
			}
			
			//list employee
			String sqlgetEmployee = "SELECT manv, HoTen  FROM sys.Nhanvien";
			rs = st.executeQuery(sqlgetEmployee);
			listNhanVien = new ArrayList<String>();
			listNhanVien.add(new String());
			cbTruongCN.addItem(new String("No Choice"));
			while(rs.next()){
				listNhanVien.add(new String(rs.getString(1)));
				cbTruongCN.addItem(new String(rs.getString(1) + " | " + rs.getString(2)));
				if(rs.getString(1).equals(chinhanh.getMaTruongCN()))
					cbTruongCN.setSelectedIndex(listNhanVien.size() - 1);				
			}
//			cbTruongCN.setSelectedIndex(cbTruongCN.getItemCount() - 1);
//			System.out.println(cbTruongCN.getSelectedItem().toString());
//			System.out.println(cbTruongCN.getItemAt(cbTruongCN.getItemCount()-1).toString());
			
			rs.close();
			st.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	void ChangeTP(){
		String maTP = listThanhPho.get(cbThanhpho.getSelectedIndex());
		ChangeQuan(maTP);
	};
	void ChangeQuan(String maTP){
		if(maTP != null){
			Connection connection = ManagerDatabase.getConnection("NhanSuAdmin", "NhanSuAdmin");
			Statement st;
			try {
				st = connection.createStatement();
				ResultSet rs;				
				//list quan
				String sqlgetQuan = "SELECT distinct sys.quan.maquan , sys.quan.tenquan FROM SYS.quan, sys.thanhpho where sys.quan.matp = sys.thanhpho.matp and sys.thanhpho.matp = '" + maTP + "'";
				rs = st.executeQuery(sqlgetQuan);
				listQuan = new ArrayList<String>();
				cbQuan.removeAllItems();
				while(rs.next()){
					listQuan.add(new String(rs.getString(1)));
					cbQuan.addItem(new String(rs.getString(2)));
				}
				rs.close();
				st.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(cbQuan.getSelectedIndex() != -1){
			String maQuan = listQuan.get(cbQuan.getSelectedIndex());
			ChangePhuong(maQuan);
		}
	}
	void ChangePhuong(String maQuan){
		Connection connection = ManagerDatabase.getConnection("NhanSuAdmin", "NhanSuAdmin");
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs;				
			//list Phuong
			String sqlgetPhuong = "SELECT sys.phuong.maphuong, sys.phuong.tenphuong  FROM SYS.phuong, sys.quan where sys.phuong.maquan = sys.quan.maquan and sys.quan.maquan = '" + maQuan + "'";
			rs = st.executeQuery(sqlgetPhuong);
			listPhuong = new ArrayList<String>();
			cbPhuong.removeAllItems();
			while(rs.next()){
				listPhuong.add(new String(rs.getString(1)));
				cbPhuong.addItem(new String(rs.getString(2)));
			}
			rs.close();
			st.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public screenEditChiNhanh(ChiNhanh chinhanh) {
		this.chinhanh = chinhanh;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMChiNhnh = new JLabel("M\u00E3 Chi Nh\u00E1nh");
		lblMChiNhnh.setBounds(10, 11, 106, 14);
		contentPane.add(lblMChiNhnh);
		
		tfMa = new JTextField();
		tfMa.setEditable(false);
		tfMa.setText("7");
		tfMa.setBounds(126, 8, 174, 20);
		contentPane.add(tfMa);
		tfMa.setColumns(10);
		
		JLabel lblTnChiNhnh = new JLabel("T\u00EAn Chi nh\u00E1nh");
		lblTnChiNhnh.setBounds(10, 36, 106, 14);
		contentPane.add(lblTnChiNhnh);
		
		JLabel lblNewLabel = new JLabel("S\u1ED1 Fax");
		lblNewLabel.setBounds(10, 61, 106, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblng = new JLabel("\u0110\u01B0\u1EDDng");
		lblng.setBounds(10, 171, 106, 14);
		contentPane.add(lblng);
		
		JLabel lblPhng = new JLabel("Ph\u01B0\u1EDDng");
		lblPhng.setBounds(10, 143, 106, 14);
		contentPane.add(lblPhng);
		
		tfTen = new JTextField();
		tfTen.setText("Van hau");
		tfTen.setColumns(10);
		tfTen.setBounds(126, 33, 174, 20);
		contentPane.add(tfTen);
		
		tfFax = new JTextField();
		tfFax.setText("0123456789");
		tfFax.setColumns(10);
		tfFax.setBounds(126, 58, 174, 20);
		contentPane.add(tfFax);
		
		JLabel lblTrngChiNhnh = new JLabel("Tr\u01B0\u1EDFng Chi nh\u00E1nh");
		lblTrngChiNhnh.setBounds(10, 199, 106, 14);
		contentPane.add(lblTrngChiNhnh);
		
		tvMaStatus = new JLabel("");
		tvMaStatus.setBounds(308, 11, 116, 14);
		contentPane.add(tvMaStatus);
		
		cbPhuong = new JComboBox();
		cbPhuong.setBounds(126, 140, 174, 20);
		contentPane.add(cbPhuong);
		
		cbDuong = new JComboBox();
		cbDuong.setBounds(126, 168, 174, 20);
		contentPane.add(cbDuong);
		
		tvTenCNStatus = new JLabel("");
		tvTenCNStatus.setBounds(308, 36, 116, 14);
		contentPane.add(tvTenCNStatus);
		
		tvFaxStatus = new JLabel("");
		tvFaxStatus.setBounds(310, 61, 114, 14);
		contentPane.add(tvFaxStatus);
		
		cbTruongCN = new JComboBox();
		cbTruongCN.setBounds(126, 196, 174, 20);
		contentPane.add(cbTruongCN);
		
		JButton btnThm = new JButton("C\u1EACP NH\u1EACT");
		btnThm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int isContinue = 1;
				if(tfMa.getText().equals("") ){
					tvMaStatus.setText("Not empty");
					isContinue = 0;
					}
				else if(!tfMa.getText().matches("^\\d$")){
					tvMaStatus.setText("Invalid");
					isContinue = 0;
					}
				else 
					tvMaStatus.setText("");
				if(tfTen.getText().equals("") ){
					tvTenCNStatus.setText("Not empty");
					isContinue = 0;
					}
				else 
					tvTenCNStatus.setText("");
				if(!tfFax.getText().matches("^\\d{10,11}$")){
					tvFaxStatus.setText("Invalid");
					isContinue = 0;
					}
				else 
					tvFaxStatus.setText("");
				if(isContinue == 1){
					if(checkData() == 1){
						screen3 ret = new screen3();
						ret.setVisible(true);
						setVisible(false); //you can't see me!
						dispose();
					}
				}	
				return;
			}
		});
		btnThm.setBounds(126, 227, 89, 23);
		contentPane.add(btnThm);
		
		JButton btnHy = new JButton("H\u1EE6Y");
		btnHy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen3 ret = new screen3();
				ret.setVisible(true);
				setVisible(false); //you can't see me!
				dispose();
			}
		});
		btnHy.setBounds(226, 227, 89, 23);
		contentPane.add(btnHy);
		
		lblThnhPh = new JLabel("Th\u00E0nh ph\u1ED1");
		lblThnhPh.setBounds(10, 89, 106, 14);
		contentPane.add(lblThnhPh);
		
		cbThanhpho = new JComboBox();
		
		cbThanhpho.setBounds(126, 86, 174, 20);
		contentPane.add(cbThanhpho);
		
		lblQun = new JLabel("Qu\u1EADn");
		lblQun.setBounds(10, 117, 106, 14);
		contentPane.add(lblQun);
		
		cbQuan = new JComboBox();
		cbQuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeQuan(null);
			}
		});
		cbQuan.setBounds(126, 114, 174, 20);
		contentPane.add(cbQuan);
		
		showData( chinhanh);
		
		
		cbThanhpho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 ChangeTP();
			}
		});
	}

}
