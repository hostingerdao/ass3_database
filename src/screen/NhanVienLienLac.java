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
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

public class NhanVienLienLac extends JFrame {

	private JPanel contentPane;
	private JTable table;
	JComboBox cbDTChuaThem;
	List<String> listChuaThem;
	String maNv;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
////					NhanVienLienLac frame = new NhanVienLienLac();
//					NhanVienScreen frame = new NhanVienScreen();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	
	void DataForShow(){
		ResultSet rs;
		try {
			//list city
			rs = ManagerDatabase.getDSDoiTacKhongPhuTrach(maNv);
			listChuaThem = new ArrayList<String>();
			cbDTChuaThem.removeAllItems();
			while(rs.next()){
				listChuaThem.add(new String(rs.getString(1)));
				cbDTChuaThem.addItem(new String(rs.getString(2)));
			}
			
			table.setModel(ManagerDatabase.setDataTableNhanVien(maNv));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(25);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public NhanVienLienLac(String maNv) {
		setTitle("\u0110\u1ED0I T\u00C1C PH\u1EE4 TR\u00C1CH");
		this.maNv = maNv;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 277);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbliTc = new JLabel("Kh\u00F4ng ph\u1EE5 tr\u00E1ch");
		lbliTc.setBounds(23, 11, 179, 14);
		contentPane.add(lbliTc);
		final String manhanvien = maNv;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(213, 36, 228, 195);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblDanhSchi = new JLabel("Ph\u1EE5 tr\u00E1ch");
		lblDanhSchi.setBounds(213, 11, 159, 14);
		contentPane.add(lblDanhSchi);
		
		JButton btnRemove = new JButton("REMOVE");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow(); 
				if( row != -1){
					ManagerDatabase.updateNhanvienDoitac(null,table.getValueAt(row, 0).toString());
					DataForShow();
				}
			}
		});
		btnRemove.setBounds(101, 111, 96, 23);
		contentPane.add(btnRemove);
		
		JButton btnNewButton = new JButton("QUIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen3 intent = new screen3();
				intent.setVisible(true);
				setVisible(false); //you can't see me!
				dispose();
			}
		});
		btnNewButton.setBounds(101, 156, 96, 23);
		contentPane.add(btnNewButton);
		
		cbDTChuaThem = new JComboBox();
		cbDTChuaThem.setBounds(23, 36, 174, 20);
		contentPane.add(cbDTChuaThem);
		JButton btnAdd = new JButton("ADD");
		btnAdd.setBounds(100, 67, 97, 23);
		contentPane.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerDatabase.updateNhanvienDoitac(manhanvien,listChuaThem.get(cbDTChuaThem.getSelectedIndex()));
				DataForShow();
			}
		});
		DataForShow();
	}
}
