package screen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GiaoDichThanhCong extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JComboBox cbPhuong;
	private JComboBox cbDuong;
	private JComboBox cbThanhPho;
	private JComboBox cbQuan;
	private JComboBox cbLoai;
	List<String> listDuong;
	List<String> listPhuong;
	List<String> listQuan;
	List<String> listThanhPho;
	List<String> listLoai;
	private JTable table_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GiaoDichThanhCong frame = new GiaoDichThanhCong();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void updataData(){
		table.setModel(ManagerDatabase.setDataTableBDSChuaThanhCong());
	};
	
	
	public void updataDataGDTC(){
		table_1.setModel(ManagerDatabase.setDataTableBDSThanhCong());
	};
	
	void createDataForComboBox(){
		ResultSet rs;
		try {
			//list city
			rs = ManagerDatabase.getTableTP();
			listThanhPho = new ArrayList<String>();
			listThanhPho.add(new String());
			cbThanhPho.addItem("All");
			while(rs.next()){
				listThanhPho.add(new String(rs.getString(1)));
				cbThanhPho.addItem(rs.getString(2));
			}
		
			//list quan
			listQuan = new ArrayList<String>();
			cbQuan.removeAllItems();
			listQuan.add(new String());
			cbQuan.addItem("All");
					
			//list Phuong
			listPhuong = new ArrayList<String>();
			cbPhuong.removeAllItems();
			listPhuong.add(new String());
			cbPhuong.addItem(new String("All"));
			
			
			//list street
			rs = ManagerDatabase.getTableDuong();
			listDuong = new ArrayList<String>();
			listDuong.add(new String());
			cbDuong.addItem("All");
			while(rs.next()){
				listDuong.add(new String(rs.getString(1)));
				cbDuong.addItem(new String(rs.getString(2)));
			}
			
			//list ChiNhanh
			listLoai = new ArrayList<String>();
			listLoai.add(new String("CH"));
			cbLoai.addItem(new String("Can Ho"));
			listLoai.add(new String("NP"));
			cbLoai.addItem(new String("Nha Pho"));
			listLoai.add(new String("DN"));
			cbLoai.addItem(new String("Dat Nen"));
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
				listQuan.add(new String());
				cbQuan.addItem("All");
				while(rs.next()){
					listQuan.add(new String(rs.getString(1)));
					cbQuan.addItem(new String(rs.getString(2)));
				}
				
				listPhuong = new ArrayList<String>();
				cbPhuong.removeAllItems();
				listPhuong.add(new String());
				cbPhuong.addItem(new String("All"));
				
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	void ChangePhuong(String maQuan){
		try {
			ResultSet rs = ManagerDatabase.getTablePhuong(maQuan);
			listPhuong = new ArrayList<String>();
			cbPhuong.removeAllItems();
			listPhuong.add(new String());
			cbPhuong.addItem("All");
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
	public GiaoDichThanhCong() {
		setTitle("KINH DOANH");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 757, 417);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("B\u1EA5t \u0111\u1ED9ng s\u1EA3n", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(164, 49, 272, 287);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton btnThnhCng = new JButton("Giao D\u1ECBch");
		btnThnhCng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row != -1){
					//igao din giao dich
					GiaoDich giaodich = new GiaoDich(table.getValueAt(row,0).toString());
					System.out.println(table.getValueAt(row,0).toString() + "blaba");
					giaodich.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
		});
		btnThnhCng.setBounds(10, 56, 89, 23);
		panel.add(btnThnhCng);
		
		JButton btnReload = new JButton("Reload");
		btnReload.setBounds(10, 124, 89, 23);
		panel.add(btnReload);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenLogIn login = new screenLogIn();
				login.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnLogout.setBounds(10, 158, 89, 23);
		panel.add(btnLogout);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(575, 31, 167, 206);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		cbThanhPho = new JComboBox();
		cbThanhPho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index =cbThanhPho.getSelectedIndex(); 
				if(index > 0){
					ChangeQuan(listThanhPho.get(index));
				}
				
			}
		});
		cbThanhPho.setBounds(10, 26, 147, 20);
		panel_2.add(cbThanhPho);
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(44, 172, 89, 23);
		panel_2.add(btnFilter);
		
		cbQuan = new JComboBox();
		cbQuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index =cbQuan.getSelectedIndex(); 
				if(index > 0){
					ChangePhuong(listQuan.get(index));	
				}
				
			}
		});
		cbQuan.setBounds(10, 57, 147, 20);
		panel_2.add(cbQuan);
		
		cbPhuong = new JComboBox();
		cbPhuong.setBounds(10, 88, 147, 20);
		panel_2.add(cbPhuong);
		
		cbDuong = new JComboBox();
		cbDuong.setBounds(10, 119, 147, 20);
		panel_2.add(cbDuong);
		
		cbLoai = new JComboBox();
		cbLoai.setBounds(10, 150, 147, 20);
		panel_2.add(cbLoai);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(575, 248, 167, 89);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 23, 147, 20);
		panel_3.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setBounds(46, 54, 89, 23);
		panel_3.add(btnSearch);
		
		JButton btnInfor = new JButton("Infor");
		btnInfor.setBounds(10, 90, 89, 23);
		panel.add(btnInfor);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Giao d\u1ECBch th\u00E0nh c\u00F4ng", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if(row != -1){
					//igao din giao dich
					EditGiaoDich giaodich = new EditGiaoDich(table_1.getValueAt(row,0).toString());
					System.out.println(table_1.getValueAt(row,0).toString() + "blaba");
					giaodich.setVisible(true);
					setVisible(false);
					dispose();
				}
				
			}
		});
		btnEdit.setBounds(37, 76, 89, 23);
		panel_1.add(btnEdit);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if(row != -1){
					//igao din giao dich
					ManagerDatabase.deleteGiaoDich(table_1.getValueAt(row,0).toString());
					updataDataGDTC();	
					updataData();	
				}
			}
		});
		btnDelete.setBounds(37, 110, 89, 23);
		panel_1.add(btnDelete);
		
		JButton button_3 = new JButton("Logout");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenLogIn login = new screenLogIn();
				login.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		button_3.setBounds(37, 144, 89, 23);
		panel_1.add(button_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(261, 63, 311, 222);
		panel_1.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		updataData();
		updataDataGDTC();
		createDataForComboBox();
		tabbedPane.setSelectedIndex(1);
	}
}
