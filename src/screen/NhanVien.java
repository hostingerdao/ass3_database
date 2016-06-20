package screen;

public class NhanVien {

	String MaNV; 
	String HoTen; 
	String NgaySinh; 
	String CMND; 
	String Email; 
	String SDT; 
	String NamVao; 
	String ChucVu; 
	String Luong; 
	String maduong;
	String  maphuong;
	String MaCN;
	String maTP;
	String maQuan;
	
	
	public NhanVien() {
		
	}
	
	public NhanVien(String maNV, String hoTen, String ngaySinh, String cMND, String email, String sDT, String namVao,
			String chucVu, String luong, String maduong, String maphuong, String maCN, String maTP, String maQuan) {
		super();
		MaNV = maNV;
		HoTen = hoTen;
		NgaySinh = ngaySinh;
		CMND = cMND;
		Email = email;
		SDT = sDT;
		NamVao = namVao;
		ChucVu = chucVu;
		Luong = luong;
		this.maduong = maduong;
		this.maphuong = maphuong;
		MaCN = maCN;
		this.maTP = maTP;
		this.maQuan = maQuan;
	}

	public String getMaNV() {
		return MaNV;
	}
	public void setMaNV(String maNV) {
		MaNV = maNV;
	}
	public String getHoTen() {
		return HoTen;
	}
	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}
	public String getNgaySinh() {
		return NgaySinh;
	}
	public void setNgaySinh(String ngaySinh) {
		NgaySinh = ngaySinh;
	}
	public String getCMND() {
		return CMND;
	}
	public void setCMND(String cMND) {
		CMND = cMND;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSDT() {
		return SDT;
	}
	public void setSDT(String sDT) {
		SDT = sDT;
	}
	public String getNamVao() {
		return NamVao;
	}
	public void setNamVao(String namVao) {
		NamVao = namVao;
	}
	public String getChucVu() {
		return ChucVu;
	}
	public void setChucVu(String chucVu) {
		ChucVu = chucVu;
	}
	public String getLuong() {
		return Luong;
	}
	public void setLuong(String luong) {
		Luong = luong;
	}
	public String getMaduong() {
		return maduong;
	}
	public void setMaduong(String maduong) {
		this.maduong = maduong;
	}
	public String getMaphuong() {
		return maphuong;
	}
	public void setMaphuong(String maphuong) {
		this.maphuong = maphuong;
	}
	public String getMaCN() {
		return MaCN;
	}
	public void setMaCN(String maCN) {
		MaCN = maCN;
	}

	public String getMaTP() {
		return maTP;
	}

	public void setMaTP(String maTP) {
		this.maTP = maTP;
	}

	public String getMaQuan() {
		return maQuan;
	}

	public void setMaQuan(String maQuan) {
		this.maQuan = maQuan;
	}

	
	
}
