package screen;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ManagerDatabase {
	
	public static Connection getConnection(String user, String password){
		try {
			Class.forName ("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", user,password);
			return con;
		} catch (ClassNotFoundException | SQLException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			return null;		
		}
	}
	
	
	public static List<ChiNhanh> getListChiNhanh(){//vi du lieu la giong nhau nen chi can goi de lay du lieu
		try {
			String sql = "SELECT SYS.CHINHANH.MACN , sys.chinhanh.TENCN , sys.chinhanh.SOFAX, sys.quan.MATP, sys.phuong.MAQUAN, sys.phuong.MAPHUONG, sys.duong.MADUONG, sys.chinhanh.MANVTRUONG FROM SYS.CHINHANH, SYS.DUONG, SYS.PHUONG, SYS.QUAN WHERE SYS.CHINHANH.MADUONG = SYS.DUONG.MADUONG AND SYS.CHINHANH.MAPHUONG = SYS.PHUONG.MAPHUONG AND SYS.PHUONG.MAQUAN = SYS.QUAN.MAQUAN order by macn";
			ResultSet rs = getResultFromSQL(sql);
			List<ChiNhanh> listChiNhanh = new ArrayList<ChiNhanh>();
			while(rs.next()){
				ChiNhanh chinhanh = new ChiNhanh(new String(rs.getString(1)), new String(rs.getString(2)), new String(rs.getString(3)), new String(rs.getString(4)),
												new String(rs.getString(5)), new String(rs.getString(6)), new String(rs.getString(7)),new String(rs.getString(8)));
				listChiNhanh.add(chinhanh);
//				System.out.println(rs.getString(1)+ ", "+rs.getString(2)+ ", "+rs.getString(3)+ ", "+rs.getString(4)+ ", "+rs.getString(5)+ ", "+rs.getString(6)+ ", "+rs.getString(7)+ ", "+rs.getString(8));
			}
			rs.close();
			return listChiNhanh;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	};
	
	public static ChiNhanh getChiNhanh(String maCN){
		try {
			String sql = "SELECT SYS.CHINHANH.MACN , sys.chinhanh.TENCN , sys.chinhanh.SOFAX, sys.quan.MATP, sys.phuong.MAQUAN, sys.phuong.MAPHUONG, sys.duong.MADUONG, sys.chinhanh.MANVTRUONG FROM SYS.CHINHANH, SYS.DUONG, SYS.PHUONG, SYS.QUAN WHERE SYS.CHINHANH.MADUONG = SYS.DUONG.MADUONG AND SYS.CHINHANH.MAPHUONG = SYS.PHUONG.MAPHUONG AND SYS.PHUONG.MAQUAN = SYS.QUAN.MAQUAN and sys.chinhanh.maCN ='"+maCN+"'";
			ResultSet rs = getResultFromSQL(sql);
			rs.next();
			ChiNhanh chinhanh = new ChiNhanh(new String(rs.getString(1)), new String(rs.getString(2)), new String(rs.getString(3)), new String(rs.getString(4)),
												new String(rs.getString(5)), new String(rs.getString(6)), new String(rs.getString(7)),new String(rs.getString(8)==null?"":rs.getString(8)));
	
			System.out.println(rs.getString(1)+ ", "+rs.getString(2)+ ", "+rs.getString(3)+ ", "+rs.getString(4)+ ", "+rs.getString(5)+ ", "+rs.getString(6)+ ", "+rs.getString(7)+ ", ");
			rs.close();
			return chinhanh;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	};
	
	public static int deleteChiNhanh(String maCN){
		int result = 0;
		try {
			String sql = "select count(*) from sys.chinhanh, sys.nhanvien where sys.nhanvien.macn = sys.chinhanh.macn and sys.chinhanh.macn = '"+maCN+"'";
			ResultSet rs = getResultFromSQL(sql);
			rs.next();			
			if(rs.getInt(1) > 0){
				JOptionPane.showMessageDialog(null, "Chi Nhanh da co nhan vien khong the xoa dc");
			}else{
				int i = JOptionPane.showConfirmDialog(null, "Ban co muon thuc hien viec xoa?", "Waring",JOptionPane.YES_NO_OPTION );
				if(i == 0){
					String sqlDelete = "DELETE FROM SYS.CHINHANH WHERE MACN=" + maCN;
					rs = getResultFromSQL(sqlDelete);
					result = 1;
				}
			}
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	};
	
	public static TableModel setDataTableChiNhanh() {
		String sql = "SELECT sys.chinhanh.MACN as \"M\u00E3\", TENCN as \"T\u00EAn Chi Nh\u00E1nh\", SOFAX as \"S\u1ED1 Fax\", sys.chinhanh.DIACHI as \"Chi Nh\u00E1nh\", SYS.NHANVIEN.HOTEN as \"Tr\u01B0\u1EDFng Chi Nh\u00E1nh\" FROM SYS.CHINHANH LEFT OUTER JOIN SYS.NHANVIEN ON SYS.CHINHANH.MANVTRUONG = SYS.NHANVIEN.MANV order by sys.chinhanh.macn";
		return setDataTableNhanSu(sql);
	};
	
	public static TableModel setDataTableNhanVien() {
		String sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN ORDER BY MANV";
		return setDataTableNhanSu(sql);
	}
	
	
	public static TableModel setDataTableNhanVien(String MaCN, String ChucVu) {
		String sql;
		System.out.println(MaCN + "----" + ChucVu);
		if(MaCN.equals("") && ChucVu.equals("All")){
			sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN ORDER BY MANV";	
		}else if(!MaCN.equals("") && ChucVu.equals("All")){
			sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN where macn="+MaCN + " ORDER BY MANV";
		}else if(MaCN.equals("") && !ChucVu.equals("All")){
			sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN where chucvu='"+ChucVu + "' ORDER BY MANV";
		}else {
			sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN where chucvu='"+ChucVu +"' and maCN="+MaCN+ " ORDER BY MANV";
		}
		return setDataTableNhanSu(sql);
	}
	
	
	public static TableModel setDataTableNhanVienTheoTen(String tenNv) {
		String sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN where upper(HOTEN) like upper('%"+tenNv+"%') ORDER BY MANV";
		return setDataTableNhanSu(sql);
	}
	
	
	public static TableModel setDataTableNhanVienTheoSDT(String SDT) {
		String sql = "SELECT MANV as M\u00E3, HOTEN as \"H\u1ECD v\u00E0 t\u00EAn\", SDT as \"S\u1ED1 \u0111i\u1EC7n tho\u1EA1i\", DIACHI as \"\u0110\u1ECBa ch\u1EC9\", CHUCVU as \"Ch\u1EE9c v\u1EE5\" FROM SYS.NHANVIEN where upper(SDT) like upper('%"+SDT+"%') ORDER BY MANV";
		return setDataTableNhanSu(sql);
	}
	
	
	public static TableModel setDataTable(String sql, List<String> listNhanvien) {
		listNhanvien = new ArrayList<String>();
		try {
			ResultSet rs = getResultFromSQL(sql);
		    ResultSetMetaData metaData = rs.getMetaData();
		    int numberOfColumns = metaData.getColumnCount();
		    Vector<String> columnNames = new Vector<String>();

		    // Get the column names
		    for (int column = 0; column < numberOfColumns; column++) {
		    	columnNames.addElement(metaData.getColumnLabel(column + 1));
		    }

		    // Get all rows.
		    Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

		    while (rs.next()) {
				Vector<Object> newRow = new Vector<Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
				    newRow.addElement(rs.getObject(i));
				   System.out.print(rs.getString(i));
				}
				listNhanvien.add(new String(rs.getString(1)));
//				System.out.println(rs.getString(1) + ", " +rs.getString(2) + ", " +rs.getString(3) + ", " +rs.getString(4) + ", " +rs.getString(5) );
				rows.addElement(newRow);
		    }
		    
		    rs.close();
		    return new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
		
    }
	
	
	public static TableModel setDataTableNhanSu(String sql) {
		try {
			
			ResultSet rs = getResultFromSQL(sql);
		    ResultSetMetaData metaData = rs.getMetaData();
		    int numberOfColumns = metaData.getColumnCount();
		    Vector<String> columnNames = new Vector<String>();

		    // Get the column names
		    for (int column = 0; column < numberOfColumns; column++) {
		    	columnNames.addElement(metaData.getColumnLabel(column + 1));
		    }

		    // Get all rows.
		    Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

		    while (rs.next()) {
				Vector<Object> newRow = new Vector<Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
				    newRow.addElement(rs.getObject(i));
				   System.out.print(rs.getString(i));
				}
				
//				System.out.println(rs.getString(1) + ", " +rs.getString(2) + ", " +rs.getString(3) + ", " +rs.getString(4) + ", " +rs.getString(5) );
				rows.addElement(newRow);
		    }
		    
		    rs.close();
		    return new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
		
    }
	
	
	public static ResultSet getResultFromSQL(String sql){
		Connection connection = ManagerDatabase.getConnection("NhanSuAdmin", "NhanSuAdmin");
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
		    return rs;
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
	};
	
	
	public static String executeSql(String sql){
		Connection connection = ManagerDatabase.getConnection("NhanSuAdmin", "NhanSuAdmin");
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			connection.close();
			st.close();
			rs.close();
		    return "success";
		} catch (Exception e) {
		    e.printStackTrace();
		    return e.getMessage();
		}
	};
	
	
	public static ResultSet getTableDuong(){
		return getResultFromSQL("SELECT *  FROM sys.duong");
	}
	
	public static ResultSet getTableTP(){
		
		return getResultFromSQL("SELECT *  FROM sys.ThanhPho");
	}
	
	public static ResultSet getTableQuan(String maTP){
		String sqlgetQuan = "SELECT distinct sys.quan.maquan , sys.quan.tenquan FROM SYS.quan, sys.thanhpho where sys.quan.matp = sys.thanhpho.matp and sys.thanhpho.matp = '" + maTP + "'";
		return getResultFromSQL(sqlgetQuan);
	}
	
	public static ResultSet getTablePhuong(String maQuan){
		String sqlgetPhuong = "SELECT sys.phuong.maphuong, sys.phuong.tenphuong  FROM SYS.phuong, sys.quan where sys.phuong.maquan = sys.quan.maquan and sys.quan.maquan = '" + maQuan + "'";
		return getResultFromSQL(sqlgetPhuong);
	}
	
	
	public static void UpdateDiaChiNhanVien(){
		String sqlUpdateDiaChi = "update sys.nhanvien set diachi = ( SELECT TENDUONG || ', ' ||TENPHUONG || ', ' ||TENQUAN || ', ' || TENTP FROM sys.DUONG D, sys.PHUONG P, sys.QUAN Q, sys.THANHPHO TP where nhanvien.maduong  =  d.maduong and p.maphuong = nhanvien.maphuong and p.maquan = q.maquan and q.matp = tp.matp)";
		getResultFromSQL(sqlUpdateDiaChi);
	};
	
	
	public static NhanVien getNhanVien(String maNV){
		try {
//			String sql = "select * from sys.nhanvien where manv = "+maNV;
			String sql = "SELECT nv.*, q.MATP, p.MAQUAN FROM SYS.NHANVIEN NV, SYS.DUONG D, SYS.PHUONG P, SYS.QUAN Q  WHERE nv.MADUONG = d.MADUONG AND nv.MAPHUONG = p.MAPHUONG AND p.MAQUAN = q.MAQUAN and nv.manv = " + maNV;
			ResultSet rs = getResultFromSQL(sql);
			SimpleDateFormat formatter2 = new SimpleDateFormat ("dd-MM-YYYY");
			rs.next();
			NhanVien nhanvien = new NhanVien(new String(rs.getString(1)), new String(rs.getString(2)), new String(formatter2.format((java.util.Date)rs.getDate(3))), new String(rs.getString(4)),
												new String(rs.getString(5)), new String(rs.getString(6)), new String(rs.getString(7)),new String(rs.getString(8)),
												new String(rs.getString(9)), new String(rs.getString(10)), new String(rs.getString(11)),new String(rs.getString(13)),
												new String(rs.getString(14)),new String(rs.getString(15)));
			
			System.out.println(rs.getString(1)+ ", "+rs.getString(2)+ ", "+formatter2.format((java.util.Date)rs.getDate(3))+ ", "+rs.getString(4)+ ", "
								+rs.getString(5)+ ", "+rs.getString(6)+ ", "+rs.getString(7)+ ", " +rs.getString(8)
								+ ", "+rs.getString(9)+ ", "+rs.getString(10)+ ", "+rs.getString(11)+ ", "+rs.getString(13)+ ", "+rs.getString(14)+ ", "+rs.getString(15));
			
			rs.close();
			return nhanvien;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	};
	
	public static int deleteNhanVien(String maNV){
		int result = 0;
		try {
			int i = JOptionPane.showConfirmDialog(null, "Ban co muon thuc hien viec xoa?", "Waring",JOptionPane.YES_NO_OPTION );
			if(i == 0){
					String sqlDelete = "DELETE FROM SYS.NHANVIEN WHERE Manv =" + maNV;
					getResultFromSQL("commit");
					ResultSet rs = getResultFromSQL(sqlDelete);
					result = 1;
					rs.close();
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	};

//	public static ResultSet getDSDoiTacPhuTrach(String maNv){
//		
//		return getResultFromSQL(sqlGetDoiTac);
//	};
	
	public static ResultSet getDSDoiTacKhongPhuTrach(String maNv){
		String sqlGetDoiTac = "SELECT * FROM SYS.DOITAC where madt not in (SELECT MADT FROM SYS.DOITAC WHERE MANV = "+maNv+") order by madt";
		return getResultFromSQL(sqlGetDoiTac);
	};
	
	
	public static TableModel setDataTableNhanVien(String maNv) {
		String sql = "SELECT MADT as M\u00E3, TEN as \"T\u00EAn \u0110\u1ED1i t\u00E1c\" FROM SYS.DOITAC WHERE MANV = " + maNv;
		return setDataTableNhanSu(sql);
	}
	
	public static void updateNhanvienDoitac(String maNv, String maDt){
		String sql = "update sys.doitac set manv = "+maNv+" where madt = "+maDt;
		getResultFromSQL(sql);
	}
	
	
//	
//	
//	PHAN HIEN THUC CHO NGUOI DUNG LA KINHDOANH
	
	
	public static ResultSet getResultFromSQLKinhDoanh(String sql){
		Connection connection = ManagerDatabase.getConnection("KinhDoanhA", "KinhDoanhA");
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
		    return rs;
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
	};
	
	public static TableModel setDataTableKinhDoanh(String sql) {
		try {
			
			ResultSet rs = getResultFromSQLKinhDoanh(sql);
		    ResultSetMetaData metaData = rs.getMetaData();
		    int numberOfColumns = metaData.getColumnCount();
		    Vector<String> columnNames = new Vector<String>();

		    // Get the column names
		    for (int column = 0; column < numberOfColumns; column++) {
		    	columnNames.addElement(metaData.getColumnLabel(column + 1));
		    }

		    // Get all rows.
		    Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

		    while (rs.next()) {
				Vector<Object> newRow = new Vector<Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
				    newRow.addElement(rs.getObject(i));
				   System.out.print(rs.getString(i));
				}
				
//				System.out.println(rs.getString(1) + ", " +rs.getString(2) + ", " +rs.getString(3) + ", " +rs.getString(4) + ", " +rs.getString(5) );
				rows.addElement(newRow);
		    }
		    
		    rs.close();
		    return new DefaultTableModel(rows, columnNames);
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
		
    }
	
	
	public static ResultSet getDsBdsChuaThanhCong(){
		String sqlGetBDS ="select * from sys.batdongsan where mabds not in(select mabds from sys.giaodichthanhcong) order by mabds";
		return getResultFromSQLKinhDoanh(sqlGetBDS);
	};
	
	public static TableModel setDataTableBDSChuaThanhCong() {
		String sqlGetBDS ="select * from sys.batdongsan where mabds not in(select mabds from sys.giaodichthanhcong) order by mabds";
		return setDataTableKinhDoanh(sqlGetBDS);
	}
	
	
	public static TableModel setDataTableBDSThanhCong() {
		String sqlGetBDS ="select mabds from sys.giaodichthanhcong order by mabds";
		return setDataTableKinhDoanh(sqlGetBDS);
	}
	
	
		
	public static String getGiaBan(String maBDS){
		String sqlGia ="select GiaBan    from sys.bdsdangban    WHERE ROWNUM = 1 and MABDS = '"+maBDS+"'";
		ResultSet rs =  getResultFromSQLKinhDoanh(sqlGia);
		try {
			rs.next();
			Long Gia = rs.getLong(1);
			return Gia + "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	};
	
	public static String getGiaThue(String maBDS){
		String sqlGia ="select GiaThue    from sys.bdsdangthue    WHERE ROWNUM = 1 and MABDS = '"+maBDS+"'";
		ResultSet rs =  getResultFromSQLKinhDoanh(sqlGia);
		try {
			rs.next();
			Long Gia = rs.getLong(1);
			return Gia + "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
		
	};
	
	public static ResultSet getNhanVienBH(){
		String sqlGetNv ="select nv.manv, nv.hoten from sys.nhanvienbh bh, sys.nhanvien nv where bh.manv = nv.manv";
		return getResultFromSQLKinhDoanh(sqlGetNv);
	};
	
	
	public static ResultSet getGiaoDich(String maBDS){
		String sqlGetNv ="select * from sys.Giaodichthanhcong where mabds = '"+maBDS+"'";
		return getResultFromSQLKinhDoanh(sqlGetNv);
	};
	
	public static String executeSqlKinhDoanh(String sql){
		Connection connection = ManagerDatabase.getConnection("KinhDoanhA", "KinhDoanhA");
		Statement st;
		try {
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql);
			connection.close();
			st.close();
			rs.close();
		    return "success";
		} catch (Exception e) {
		    e.printStackTrace();
		    return e.getMessage();
		}
	}; 
	
	public static String ThucHienGiaoDich(String maBDS, String maNV, String nam, String thang, String ngay, String gia, String loai){
		String sqlgiaodich ="INSERT INTO sys.GIAODICHTHANHCONG VALUES('"+maBDS+"', "+maNV+", '"+nam+"','"+thang+"','"+ngay+"', "+gia+", '"+loai+"' )";
		System.out.println(sqlgiaodich);
		return executeSqlKinhDoanh(sqlgiaodich);
	}
	
	public static String UpdateGiaoDich(String maBDS, String maNV, String nam, String thang, String ngay, String gia, String loai){
		String sqlgiaodich = "update sys.GiaoDichThanhCong set MaNV = "+maNV+", Nam = '"+nam+"', Thang = '"+thang+"', Ngay = '"+ngay+"', Gia = "+gia+", BanHoacThue = '"+loai+"' where maBDS = '"+maBDS+"'";
		System.out.println(sqlgiaodich);
		return executeSqlKinhDoanh(sqlgiaodich);
	}
	
	
	public static int deleteGiaoDich(String maBDS){
		int result = 0;
		try {
			int i = JOptionPane.showConfirmDialog(null, "Ban co muon thuc hien viec xoa?", "Waring",JOptionPane.YES_NO_OPTION );
			if(i == 0){
					String sqlDelete = "DELETE FROM SYS.GIAODICHTHANHCONG WHERE Mabds ='" + maBDS + "'";
					ResultSet rs = getResultFromSQLKinhDoanh(sqlDelete);
					getResultFromSQL("commit");
					result = 1;
					rs.close();
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	};
	
	
}
