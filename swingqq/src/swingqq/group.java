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
		send = new JButton("发送");
		inText = new JTextArea();
		toText = new JTextArea();
		cp1.setLayout(null);
		cp1.setSize(600,600);
		send.setBounds(0,560,600,30);
		send.addActionListener(this);
		//fileSend.setBounds(0,370,50,30);
		inText.setBounds(0,400,600,170);
		inText.setFont(new Font("楷体",Font.BOLD,16));
		toText.setBounds(0,0,600,370);
		toText.setFont(new Font("楷体",Font.BOLD,16));
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
	
	//将info立即追加到JTextArea中
	public void appendJTextArea(String userId,String info) {
		toText.append(userId+":\n"+info+"\n");
		toText.paintImmediately(toText.getBounds()); 
	}
	/*public void receiveFile(Message _message) {
		message = _message;
		frame = new JFrame("文件接收");
		fileWds = new JPanel();
		agree = new JButton("接收");
		disagree = new JButton("不接收");
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
		//点击发送后，1、获取文本域输入的内容2、发送消息给服务端(这里写一个方法)3、清楚文本域内容4在聊天框显示消息内容和发送者
		appendJTextArea(U.getUserID(),inText.getText());
		messageClientService.sendMessageToGroup(inText.getText(),U.getUserID(),u.getUserID());
		inText.setText("");
		}
		/*else if(e.getSource() == fileSend) {
			JFileChooser jfc = new JFileChooser("D:\\");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jfc.showDialog(new JLabel(),"选择");
			String src = jfc.getSelectedFile().getPath();
			messageClientService.sendFileToOne(src,U.getUserID(),u.getUserID());
			
		}
		else if(e.getSource() == agree) {
			//同意接受文件，1选择保存目录2保存3提示成功关闭窗口
			JFileChooser saveChooser = new JFileChooser("D:\\");
			saveChooser.setDialogTitle("选择保存位置");
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

