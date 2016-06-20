package screen;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class screenLogIn extends JFrame {

	private JPanel contentPane;
	private JTextField tfUserName;
	private JTextField tfPassword;
	String connectionInfo = "jdbc:oralce:thin:@localhost:1521:xe";
	private JButton btnConnect;
	private JLabel tvError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					screenLogIn frame = new screenLogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public screenLogIn() {
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnConnect = new JButton("LOGIN");
		btnConnect.setBounds(192, 172, 137, 23);
		contentPane.add(btnConnect);
		
		tfPassword = new JTextField();
		tfPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfPassword.setBounds(181, 36, 182, 33);
		contentPane.add(tfPassword);
		tfPassword.setText("KinhDoanhA");
		tfPassword.setColumns(10);
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setBounds(181, 11, 182, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(181, 80, 182, 14);
		contentPane.add(lblPassword);
		
		tfUserName = new JTextField();
		tfUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfUserName.setBounds(181, 105, 182, 31);
		contentPane.add(tfUserName);
		tfUserName.setText("KinhDoanhA");
		tfUserName.setColumns(10);
		
		JLabel iconlogin = new JLabel("");
		iconlogin.setBounds(10, 11, 150, 169);
		Image img = new ImageIcon(this.getClass().getResource("/login_icon.png")).getImage();
		iconlogin.setIcon(new ImageIcon(img));
		contentPane.add(iconlogin);
		
		tvError = new JLabel("");
		tvError.setBounds(182, 147, 253, 14);
		contentPane.add(tvError);
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Class.forName ("oracle.jdbc.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", tfUserName.getText(),tfPassword.getText());
					Statement st = con.createStatement();
					if(tfUserName.getText().equals("NhanSuAdmin")){
						screen3 intent = new screen3();
						intent.setVisible(true);
						setVisible(false);
						dispose();
					}else if(tfUserName.getText().equals("KinhDoanhA")){
						GiaoDichThanhCong intent = new GiaoDichThanhCong();
						intent.setVisible(true);
						setVisible(false);
						dispose();
					}
					
					con.close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					tvError.setText("Error: "+e1.getMessage());
				}
			}
		});
	}
}
