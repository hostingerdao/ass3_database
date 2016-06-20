package screen;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public class screen3 extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JTable table;
	private JTable table_1;
	private int tag = 0;
	private JTextField tfSearch;
	JComboBox cbChiNhanhFilter;
	JComboBox cbChucVuFilter;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private List<String> listChiNhanh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					screen3 frame = new screen3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void refreshDataChiNhanh(){
			table.setModel(ManagerDatabase.setDataTableChiNhanh());
			setSizeChiNhanh();
	}; 
	
	public void setSizeChiNhanh(){
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(25);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(300);
		table.getColumnModel().getColumn(4).setPreferredWidth(130);
	};
	
	public void refreshDataNhanVien(){
			table_1.setModel(ManagerDatabase.setDataTableNhanVien());
			setSizeNhanVien();
	}; 
	
	public void setSizeNhanVien(){
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(25);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(130);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(300);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(130);
	};
	
	public void createDataFilter(){
		ResultSet rs;
		try {
			//list ChiNhanh
			String sqlgetCN = "SELECT macn, tencn  FROM sys.chinhanh";
			rs = ManagerDatabase.getResultFromSQL(sqlgetCN);
			listChiNhanh = new ArrayList<String>();
			listChiNhanh.add(new String());
			cbChiNhanhFilter.addItem("All");
			while(rs.next()){
				listChiNhanh.add(new String(rs.getString(1)));
				cbChiNhanhFilter.addItem(new String(rs.getString(2)));
			}
			
			//list ChiNhanh
			String sqlgetChucVu = "SELECT distinct chucvu   FROM sys.nhanvien";
			rs = ManagerDatabase.getResultFromSQL(sqlgetChucVu);
			cbChucVuFilter.addItem("All");
			while(rs.next()){
				cbChucVuFilter.addItem(new String(rs.getString(1)));
			}
		
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	/**
	 * Create the frame.
	 */
	public screen3() {
		setTitle("NHAN SU");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1073, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(25, 26, 1022, 410);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Chi Nh\u00E1nh", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(119, 35, 673, 308);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton btnAddCN = new JButton("ADD");
		btnAddCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChiNhanhInfo intent = new ChiNhanhInfo();
				intent.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAddCN.setBounds(10, 38, 89, 23);
		panel.add(btnAddCN);
		
		JButton btnEditCN = new JButton("EDIT");
		btnEditCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1){
					int row = table.getSelectedRow();
					ChiNhanh chinhanh = ManagerDatabase.getChiNhanh(table.getValueAt(row, 0).toString());
					screenEditChiNhanh intent = new screenEditChiNhanh(chinhanh);
					intent.setVisible(true);
					setVisible(false); 
					dispose();
				}
			}
		});
		btnEditCN.setBounds(10, 76, 89, 23);
		panel.add(btnEditCN);
		
		JButton btnDeleteCN = new JButton("DELETE");
		btnDeleteCN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1){
					int row = table.getSelectedRow();
					int result = ManagerDatabase.deleteChiNhanh(table.getValueAt(row, 0).toString());
					if(result == 1)
						refreshDataChiNhanh();
				}
			}
		});
		btnDeleteCN.setBounds(10, 110, 89, 23);
		panel.add(btnDeleteCN);
		
		JButton btnLogout = new JButton("LOGOUT");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenLogIn login = new screenLogIn();
				login.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnLogout.setBounds(10, 144, 89, 23);
		panel.add(btnLogout);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Nh\u00E2n vi\u00EAn", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(0, 0, 819, 382);
		panel_1.add(panel_2);
		
		JButton btnAddNV = new JButton("ADD");
		btnAddNV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NhanVienScreen intent = new NhanVienScreen();
				intent.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnAddNV.setBounds(10, 42, 89, 23);
		panel_2.add(btnAddNV);
		
		JButton btnEditNV = new JButton("EDIT");
		btnEditNV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if(row != -1){
					NhanVien nhanvien = ManagerDatabase.getNhanVien(table_1.getValueAt(row, 0).toString());
					NhanVienEdit intent = new NhanVienEdit(nhanvien);
					intent.setVisible(true);
					setVisible(false);
					dispose();
				}
				
			}
		});
		btnEditNV.setBounds(10, 76, 89, 23);
		panel_2.add(btnEditNV);
		
		JButton btnDeleteNV = new JButton("DELETE");
		btnDeleteNV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_1.getSelectedRow() != -1){
					int row = table_1.getSelectedRow();
					int result = ManagerDatabase.deleteNhanVien(table_1.getValueAt(row, 0).toString());
					if(result == 1)
						refreshDataNhanVien();
				}
			}
		});
		btnDeleteNV.setBounds(10, 110, 89, 23);
		panel_2.add(btnDeleteNV);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(121, 35, 688, 333);
		panel_2.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table_1);
		
		JButton btnDoiTactp = new JButton("DOI TAC ");
		btnDoiTactp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				if(row != -1){
					NhanVienLienLac intent = new NhanVienLienLac(table_1.getValueAt(row, 0).toString());
					intent.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
		});
		btnDoiTactp.setBounds(10, 144, 89, 23);
		panel_2.add(btnDoiTactp);
		
		JButton btnQuit = new JButton("QUIT");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screenLogIn login = new screenLogIn();
				login.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnQuit.setBounds(10, 212, 89, 23);
		panel_2.add(btnQuit);
		
		JButton btnReload = new JButton("RELOAD");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshDataNhanVien();
			}
		});
		btnReload.setBounds(10, 178, 89, 23);
		panel_2.add(btnReload);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "FILTER", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(829, 33, 178, 132);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		cbChiNhanhFilter = new JComboBox();
		cbChiNhanhFilter.setBounds(10, 25, 158, 20);
		panel_3.add(cbChiNhanhFilter);
		
		cbChucVuFilter = new JComboBox();
		cbChucVuFilter.setBounds(10, 56, 158, 20);
		panel_3.add(cbChucVuFilter);
		
		JButton btnLc = new JButton("L\u1ECCC");
		btnLc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String MaCN = listChiNhanh.get(cbChiNhanhFilter.getSelectedIndex());
				String ChucVu = cbChucVuFilter.getSelectedItem().toString();
				System.out.println(MaCN + ChucVu);
				table_1.setModel(ManagerDatabase.setDataTableNhanVien(MaCN, ChucVu));
				setSizeNhanVien();
			}
		});
		btnLc.setBounds(46, 89, 89, 23);
		panel_3.add(btnLc);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "SEARCH", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(829, 194, 178, 177);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		tfSearch = new JTextField();
		tfSearch.setBounds(10, 29, 158, 20);
		panel_4.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("SEARCH");
		
		btnSearch.setBounds(46, 131, 89, 23);
		panel_4.add(btnSearch);
		
		JRadioButton rdbtnTen = new JRadioButton("T\u00ECm theo t\u00EAn");
		rdbtnTen.setSelected(true);
		buttonGroup.add(rdbtnTen);
		rdbtnTen.setBounds(6, 56, 109, 23);
		panel_4.add(rdbtnTen);
		rdbtnTen.setActionCommand("ten");
		
		JRadioButton rdbtnSDT = new JRadioButton("T\u00ECm theo s\u1ED1 \u0111i\u1EC7n tho\u1EA1i");
		buttonGroup.add(rdbtnSDT);
		rdbtnSDT.setBounds(6, 82, 162, 23);
		panel_4.add(rdbtnSDT);
		rdbtnSDT.setActionCommand("sdt");
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = tfSearch.getText();
				ButtonModel selectedModel = buttonGroup.getSelection();
	            if (selectedModel != null) {
	               if(selectedModel.getActionCommand().equals("ten")){
	            	   table_1.setModel(ManagerDatabase.setDataTableNhanVienTheoTen(data));
	               }else{
	            	   table_1.setModel(ManagerDatabase.setDataTableNhanVienTheoSDT(data));
	               };
	               setSizeNhanVien();
	            }
			}
		});
		
		
		refreshDataChiNhanh();
		refreshDataNhanVien();
		createDataFilter();
		tabbedPane.setSelectedIndex(1);
		
	}
}
