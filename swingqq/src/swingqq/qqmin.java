package swingqq;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import qqclientservice.UserClientService;
public class qqmin extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private UserClientService userClientService = new UserClientService();//用于登录服务器
	//private MessageClientService messageClientService = new MessageClientService();//用于发送消息
	JPanel jp1,jp2,jp3;
	JLabel Ilabel,Tlabel1,Tlabel2;
	JButton b1;
	JTextField name;
	JPasswordField pass;
	Icon bc1 = new ImageIcon("image/qq.png");//431*164
	Icon bc2 = new ImageIcon("image/登录.png");//297*45
	public void mainMenu()
	{
		JFrame frame = new JFrame();
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		Ilabel = new JLabel();
		Tlabel1 = new JLabel("账号");
		Tlabel2 = new JLabel("密码");
		b1 = new  JButton();
		name = new JTextField();
		pass = new JPasswordField();
		pass.setEchoChar('*');
		name.setBounds(130,180,200,30);
		pass.setBounds(130,210,200,30);
		Ilabel.setIcon(bc1);
		b1.setIcon(bc2);
		b1.addActionListener(this);
		jp2.add(Ilabel);
		jp1.setLayout(null);
		jp1.setSize(500,500);
		jp2.setBounds(0,0,431,164);
		b1.setBounds(80,260,297,45);
		Tlabel1.setBounds(90,180,40,30);
		Tlabel2.setBounds(90,210,40,30);
		jp1.add(jp2);
		jp1.add(b1);
		jp1.add(name);
		jp1.add(pass);
		jp1.add(Tlabel1);
		jp1.add(Tlabel2);
		frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(431,360);
		frame.setContentPane(jp1);
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		loginWds1 msgwds = new loginWds1();
		String inputname = name.getText();
		String password = new String(pass.getPassword());
		if(userClientService.checkUser(inputname,password))//判断账号密码 和服务端链接
		{	
			((Window) b1.getRootPane().getParent()).dispose();//获取窗体并销毁
			msgwds.correctWds1();
		}
		else
			msgwds.errorWds1();
	}
	public static void main(String[] args) {
		(new qqmin()).mainMenu();
	}
}
