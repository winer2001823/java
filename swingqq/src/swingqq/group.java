package swingqq;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import qqclientservice.MessageClientService;
import qqcommon.Message;
import qqcommon.User;
public class group extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	JFrame chatWds,frame;
	JPanel cp1,cp2,cp3,cp4,fileWds;
	JButton send,fileSend,agree,disagree;
	JTextArea inText,toText;
	JTextField j1;
	MessageClientService messageClientService = new MessageClientService();
	Message message;
	private User U,u;
	public group(User U0,User u0)
	{
		U = U0;
		u = u0;
		chatWds = new JFrame();
		chatWds.setTitle(u.getUserID());
		chatWds.setLayout(null);
		chatWds.setSize(600,630);
		cp1 = new JPanel();
		cp2 = new JPanel();
		cp3 = new JPanel();
		send = new JButton("����");
		inText = new JTextArea();
		toText = new JTextArea();
		cp1.setLayout(null);
		cp1.setSize(600,600);
		send.setBounds(0,560,600,30);
		send.addActionListener(this);
		//fileSend.setBounds(0,370,50,30);
		inText.setBounds(0,400,600,170);
		inText.setFont(new Font("����",Font.BOLD,16));
		toText.setBounds(0,0,600,370);
		toText.setFont(new Font("����",Font.BOLD,16));
		toText.setEditable(false);
		cp2.setBounds(0,370,600,30);
		cp2.setBackground(Color.orange);
		cp1.add(cp2);
		cp1.add(inText);
		cp1.add(send);
		cp1.add(toText);
		chatWds.add(cp1);
		chatWds.setVisible(true);
	}
	
	//��info����׷�ӵ�JTextArea��
	public void appendJTextArea(String userId,String info) {
		toText.append(userId+":\n"+info+"\n");
		toText.paintImmediately(toText.getBounds()); 
	}
	/*public void receiveFile(Message _message) {
		message = _message;
		frame = new JFrame("�ļ�����");
		fileWds = new JPanel();
		agree = new JButton("����");
		disagree = new JButton("������");
		j1 = new JTextField(message.getSender());
		j1.setEditable(false);
		agree.addActionListener(this);
		disagree.addActionListener(this);
		fileWds.add(j1);
		fileWds.add(agree);
		fileWds.add(disagree);
		frame.add(fileWds);
		frame.setSize(250,100);
		frame.setVisible(true);
	}*/
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == send) {
		//������ͺ�1����ȡ�ı������������2��������Ϣ�������(����дһ������)3������ı�������4���������ʾ��Ϣ���ݺͷ�����
		appendJTextArea(U.getUserID(),inText.getText());
		messageClientService.sendMessageToGroup(inText.getText(),U.getUserID(),u.getUserID());
		inText.setText("");
		}
		/*else if(e.getSource() == fileSend) {
			JFileChooser jfc = new JFileChooser("D:\\");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(new JLabel(),"ѡ��");
			String src = jfc.getSelectedFile().getPath();
			messageClientService.sendFileToOne(src,U.getUserID(),u.getUserID());
			
		}
		else if(e.getSource() == agree) {
			//ͬ������ļ���1ѡ�񱣴�Ŀ¼2����3��ʾ�ɹ��رմ���
			JFileChooser saveChooser = new JFileChooser("D:\\");
			saveChooser.setDialogTitle("ѡ�񱣴�λ��");
			saveChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			saveChooser.showSaveDialog(null);
			String path = saveChooser.getSelectedFile().getPath();
			
			try {
				System.out.println(path);
				String _path = path + "\\" + message.getFileName();
				FileOutputStream fileOutputStream = new FileOutputStream(_path);
				fileOutputStream.write(message.getFileBytes());
				fileOutputStream.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			};
			frame.dispose();
		}
		else if(e.getSource() == disagree){
			frame.dispose();
		}*/
	}

}

