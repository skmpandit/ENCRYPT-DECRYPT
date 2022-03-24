import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.awt.event.ActionEvent;

public class Encrypt extends JFrame {

	private JPanel contentPane;
	private JTextField t1;
	private JTextField t2;
	private JPasswordField pw;
private JButton b1,b2,b3,ok;
RSA rsa;
File textfile,imagefile;
static final int N=50;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Encrypt frame = new Encrypt();
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
	public Encrypt() {
	rsa=new RSA();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 645, 408);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Encrypt");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(281, 50, 78, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Choose Text File");
		lblNewLabel_1.setBounds(119, 99, 114, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Choose Image File");
		lblNewLabel_2.setBounds(119, 140, 127, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel l3 = new JLabel("Password");
		l3.setBounds(119, 187, 98, 14);
		l3.setVisible(false);
		contentPane.add(l3);
		
		t1 = new JTextField();
		t1.setBounds(273, 96, 121, 20);
		contentPane.add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setBounds(273, 137, 121, 20);
		contentPane.add(t2);
		t2.setColumns(10);
		
		pw = new JPasswordField();
		pw.setBounds(273, 184, 121, 20);
		pw.setVisible(false);
		contentPane.add(pw);
		
		b1 = new JButton("Choose");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			Object ob=e.getSource();
			if(ob==b1)
			{
				JFileChooser filechooser=new JFileChooser();
				int v=filechooser.showOpenDialog(null);
FileNameExtensionFilter filter=new FileNameExtensionFilter("Text File", "txt");
				filechooser.setFileFilter(filter);
				if(v==JFileChooser.APPROVE_OPTION) {
					textfile=filechooser.getSelectedFile();
					String abspath=textfile.getAbsolutePath();
					t1.setText(abspath);
				}
			}
			}
		});
		b1.setBounds(417, 95, 89, 23);
		contentPane.add(b1);
		
		b2 = new JButton("Choose");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob=e.getSource();
				if(ob==b2)
				{
					JFileChooser filechooser=new JFileChooser();
					int v=filechooser.showOpenDialog(null);
	FileNameExtensionFilter filter=new FileNameExtensionFilter("JPG & PNG", "jpg","png");
					filechooser.setFileFilter(filter);
					if(v==JFileChooser.APPROVE_OPTION) {
						imagefile=filechooser.getSelectedFile();
						String abspath=imagefile.getAbsolutePath();
						t2.setText(abspath);
					}
				}
			}
		});
		b2.setBounds(417, 136, 89, 23);
		contentPane.add(b2);
		
		ok = new JButton("OK");
		ok.setVisible(false);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob=e.getSource();
				if(ob==ok){
					
					try{
						
						String text="";
						FileInputStream fis=new FileInputStream(textfile);
						int i=0;
						while((i=fis.read())!=-1){
								text=text+(char)i;
						}
						System.out.println("Actual Message: "+text);
							String enc_msg="";
						int max=N/8;
						for(i=0;i<text.length();i=i+max){
								if(text.length()-i>max){
									int j=i+max;
							String sub=text.substring(i,j);
							byte b[]=sub.getBytes();
							BigInteger bin=new BigInteger(b);
							BigInteger enc=rsa.encrypt(bin);
							String enc_s=enc.toString();
							enc_msg=enc_msg+enc_s+"#";
									
									System.out.println(sub+"->"+enc_s);
								}
								else{
									String sub=text.substring(i);
									byte b[]=sub.getBytes();
									BigInteger bin=new BigInteger(b);
									BigInteger enc=rsa.encrypt(bin);
									String enc_s=enc.toString();
									enc_msg=enc_msg+enc_s+"#";
									
									System.out.println(sub+"->"+enc_s);
								}
						}
						System.out.println("Encrypted Message: "+enc_msg);
						

		String pwd=pw.getText();
		if(pwd.length()>=4 && pwd.length()<=6){
		byte bb[]=pwd.getBytes();
		BigInteger pass=new BigInteger(bb);
		BigInteger encpwd=rsa.encrypt(pass);
		enc_msg=encpwd+"#"+enc_msg+rsa.getModulus()+"#"+rsa.getPrivateKey();
		System.out.println("Encrypted Message with password and key: "+enc_msg);
			
		enc_msg=" "+enc_msg;
											
		FileOutputStream out=new FileOutputStream(imagefile,true);
		byte ascii[]=enc_msg.getBytes();
		out.write(ascii);
							
		JOptionPane.showMessageDialog(null,"Message has been encrypted to "+imagefile.getName());
							
							out.close();	
							fis.close();
			
							ok.setEnabled(false);
						//	b4.setEnabled(true);
						}
						else
							JOptionPane.showMessageDialog(null,"Password must contain 4 to 6 chars");
					}
					catch(Exception ex){
						ex.printStackTrace();
					}

				}

				
			}
			
		});
		ok.setBounds(417, 183, 89, 23);
		contentPane.add(ok);
		
		b3 = new JButton("Encrypt");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object ob=e.getSource();
				if(ob==b3)
				{
				l3.setVisible(true);
				ok.setVisible(true);
				pw.setVisible(true);
				b3.setEnabled(false);
				ok.setEnabled(true);
				}
			}
		});
		b3.setBounds(273, 246, 121, 23);
		contentPane.add(b3);
	}
}
