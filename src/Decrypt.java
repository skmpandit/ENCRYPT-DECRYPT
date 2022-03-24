import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.RandomAccess;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Decrypt extends JFrame {

	private JPanel contentPane;
	private JTextField t1;
	private JTextField t2;
	private JPasswordField pw;
	private JButton b1,b2,b3;
	RSA rsa;
	File textfile,imagefile;
	static final int N=50;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Decrypt frame = new Decrypt();
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
	public Decrypt() {
		rsa=new RSA();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 634, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DECRYPT");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 19));
		lblNewLabel.setBounds(313, 50, 153, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Choose Image");
		lblNewLabel_1.setBounds(145, 103, 100, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Choose Text File");
		lblNewLabel_2.setBounds(145, 157, 100, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Enter Key");
		lblNewLabel_3.setBounds(145, 218, 100, 14);
		contentPane.add(lblNewLabel_3);
		
		t1 = new JTextField();
		t1.setBounds(310, 100, 156, 20);
		contentPane.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setBounds(310, 157, 156, 20);
		contentPane.add(t2);
		t2.setColumns(10);
		
		pw = new JPasswordField();
		pw.setBounds(310, 215, 156, 20);
		contentPane.add(pw);
		
	 b1 = new JButton("Choose");
	 b1.addActionListener(new ActionListener() {
	 	public void actionPerformed(ActionEvent e) {
	 		Object ob=e.getSource();
			if(ob==b1)
			{
				JFileChooser filechooser=new JFileChooser();
				int v=filechooser.showOpenDialog(null);
FileNameExtensionFilter filter=new FileNameExtensionFilter("JPG & PNG", "jpg","png");
				filechooser.setFileFilter(filter);
				if(v==JFileChooser.APPROVE_OPTION) {
					imagefile=filechooser.getSelectedFile();
					String abspath=imagefile.getAbsolutePath();
					t1.setText(abspath);
				}
			}
	 	}
	 });
		b1.setBounds(492, 99, 116, 23);
		contentPane.add(b1);
		
		b2 = new JButton("Choose");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob=e.getSource();
				if(ob==b2)
				{
					JFileChooser filechooser=new JFileChooser();
					int v=filechooser.showOpenDialog(null);
	FileNameExtensionFilter filter=new FileNameExtensionFilter("Text File", "txt");
					filechooser.setFileFilter(filter);
					if(v==JFileChooser.APPROVE_OPTION) {
						textfile=filechooser.getSelectedFile();
						String abspath=textfile.getAbsolutePath();
						t2.setText(abspath);
					}
				}
				}
			
		});
		b2.setBounds(492, 153, 116, 23);
		contentPane.add(b2);
		
		b3 = new JButton("Decrypt");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try
			{
				int index=0,cnt=0;
			RandomAccessFile file=new RandomAccessFile(imagefile, "r");
				int i=0;
				while((i=file.read())!=-1)
				{
					cnt++;
					if(i==32)
						index=cnt;
				}
				file.seek(index);
				String str="";
				while((i=file.read())!=-1)
				{
				str=str+(char)i;
				}
				System.out.println("str"+str);
				str=str.trim();
				String msg[]=str.split("#");
				String encpwd=msg[0];
				String modulus=msg[msg.length-2];
				String privatekey=msg[msg.length-1];
				System.out.println("Enc password"+encpwd);
				System.out.println("Modulud"+modulus);
				System.out.println("Private Key"+privatekey);
				BigInteger p=new BigInteger(encpwd);
				BigInteger m=new BigInteger(modulus);
				BigInteger pk=new BigInteger(privatekey);
				BigInteger pwd=p.modPow(pk,m);
				byte b[]=pwd.toByteArray();
				String pass=new String(b);
				System.out.println("Actual password"+pass);
				String passwd=pw.getText();
				if(pass.equals(passwd))
				{
				String original="";
				for(i=1;i<=msg.length-3;i++)
				{
					BigInteger em=new BigInteger(msg[i]);
					BigInteger dm=em.modPow(pk, m);
					byte b1[]=dm.toByteArray();
					String om=new String(b1);
					original=original+om;
				}
				System.out.println("Original Message"+original);
				FileOutputStream out=new FileOutputStream(textfile);
				out.write(original.getBytes());
				JOptionPane.showMessageDialog(null, "Message has been decrypted");
					out.close();	
				}else
				{
JOptionPane.showMessageDialog(null, "Wrong Password");
					file.close();					
				}
			}catch (Exception e2) {
			e2.printStackTrace();
			}
			}
		});
		b3.setBounds(309, 277, 89, 23);
		contentPane.add(b3);
	}
}
