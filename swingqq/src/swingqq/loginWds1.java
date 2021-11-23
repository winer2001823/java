package swingqq;

import java.awt.event.*;
import javax.swing.*;

//
public class loginWds1 extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JFrame wds1,wds2;
	JPanel jp1,jp2;
	JButton b1,b2;
	JLabel mesg1,mesg2;
	public void correctWds1()
	{
	wds1 = new JFrame();
	wds1.setLayout(null);
	wds1.setSize(300,300);
	b1 = new JButton();
	jp1 = new JPanel();
	mesg1 = new JLabel("’À∫≈√‹¬Î’˝»∑");
	jp1.setLayout(null);
	jp1.setSize(200,200);
	b1.setBounds(100,180,50,30);
	b1.addActionListener(this);
	mesg1.setBounds(75,150,100,30);
	jp1.add(b1);
	jp1.add(mesg1);
	wds1.setContentPane(jp1);
	wds1.setVisible(true);
	}
	public void errorWds1()
	{
	wds2 = new JFrame();
	wds2.setLayout(null);
	wds2.setSize(300,300);
	b2 = new JButton();
	jp2 = new JPanel();
	mesg2 = new JLabel("’À∫≈√‹¬Î≤ª∆•≈‰");
	jp2.setLayout(null);
	jp2.setSize(200,200);
	b2.setBounds(100,180,50,30);
	b2.addActionListener(this);
	mesg2.setBounds(75,150,100,30);
	jp2.add(b2);
	jp2.add(mesg2);
	wds2.setContentPane(jp2);
	wds2.setVisible(true);
	}
	/*public static void main(String[] args)
	{
		(new loginWds1()).correctWds1();
	}*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1)
		{
			wds1.dispose();
			//wds1.getComponent(DISPOSE_ON_CLOSE)
			
		}
		else
			wds2.dispose();
	}
}
