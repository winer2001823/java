package qqServer.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

//����ˣ��ڼ���9999���ȴ��ͻ������ӣ�������ͨѶ
public class QQServer {
	
	private ServerSocket ss = null;
	
	//����һ�����ϣ���Ŷ���û�
	private static HashMap<String,User> validUsers = new HashMap<>();
	//HaspMap û�д����̰߳�ȫ������ڶ��߳�����²���ȫ
	//����Ҳ����ʹ��ConcurrentHashMap.���Դ��������ϣ�û���̰߳�ȫ����
	static {
		//��̬������ʼ��
		validUsers.put("���ĳ�",new User("���ĳ�","123456","Zzzzzzz",new ImageIcon("image/p1.jpg"),0));
		validUsers.put("�����",new User("�����","123456","����ȫ�ڷ��귽",new ImageIcon("image/p2.jpg"),0));
		validUsers.put("�����",new User("�����","123456","����ȫ����",new ImageIcon("image/p3.jpg"),0));
		validUsers.put("տ��",new User("տ��","123456","Ҫʵ�ֹ�������",new ImageIcon("image/p4.jpg"),0));
		validUsers.put("������",new User("������","123456","��į�ĸ�",new ImageIcon("image/p5.jpg"),0));
		validUsers.put("�µ�",new User("�µ�","123456","�ܳ���",new ImageIcon("image/p6.jpg"),0));
		
	}
	//����Ҫ����ÿ���û��ĺ����б��������Ƕ�Ӧ������ϣ���п���չ��
	//ʹ��HashMap<String,[] User>
	//����Ҫ��ʼ��[]User
	//Ϊ�˷����޶�ÿ�����ʮ�����Ѱ�
	//��� д������������������������������
	//�ɴ���ڿͻ�����������˰ɣ���̫������nm
	//��ʹ����������Ҫ�������˵���Ϣ���͸��ͻ��˰�
	//��������ô����
	//���ˣ�д�Ĺ�ʺһ�㲻����
	
	
	private boolean checkUser(String userId,String passwd)//�����û��Ƿ�Ϸ�
	{
		User user = validUsers.get(userId);
		if(user == null) {
			return false;
		}
		if(!user.getPasswd().equals(passwd)) {
			return false;
		}
		return true;
	}
	public static User getUserData(String userId)//�����û���Ϣ���ͻ���	
	{
		User U = validUsers.get(userId);
		U.setKey(1);//����������״̬��Ϊ����
		U.setPasswd(null);
		return U;
	}
	
	public static void sendUserOnlineStatus(String UserId)//�������ѣ������ߵ���Ϣ�����������ߺ���
	{
		String[] onlineUsers = ManageClientThreads.getOnlineUsers().split(" ");//������ߵĺ���
		//if(onlineUsers.length != 0) {
		Message message = new Message();
		message.setSender(UserId);
		message.setMesType(MessageType.MESSAGE_USER_ONLINE);//����������Ϣ
			for(int i = 0;i<onlineUsers.length;i++) {
				message.setGetter(onlineUsers[i]);
				try {
					ObjectOutputStream oos =
							new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(onlineUsers[i]).getSocket().getOutputStream());
					oos.writeObject(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		//}
	}

	public QQServer()  {
		//�˿ڿ���д�������ļ�
		try {
			System.out.println("�����9999�˿ڼ���....");
			ss = new ServerSocket(9999);
			
			while(true) {//����ĳ���ͻ��˽������Ӻ󣬻��������
				Socket socket = ss.accept();//û�пͻ��������ӵĻ������������
				//�õ�socket�����Ķ���������
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				//�õ�socket�����Ķ���������
				ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
				//��ȡ�ͻ��˷��͵Ķ���
				User u =(User)ois.readObject();
				//����һ��Message����׼���ظ��ͻ���
				Message message = new Message();
				//��֤
				if(checkUser(u.getUserID(),u.getPasswd()))
				{
					message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
					//��message����ظ����ͻ���
					oos.writeObject(message);
					//��Ҫ���û���ϸ��Ϣ����
					u = getUserData(u.getUserID());
					oos.writeObject(u);
					System.out.println("�����û���Ϣ�ɹ�");
					//����һ���̣߳����̳߳���socket����
					ServerConnectClientThread serverConnectClientThread = 
							new ServerConnectClientThread(socket,u.getUserID());
					//�������߳�
					serverConnectClientThread.start();
					//�Ѹ��̶߳��󣬷��뵽һ�������й���
					ManageClientThreads.addClientThread(u.getUserID(),serverConnectClientThread);
					
				}
				else//����֤ʧ�ܣ����͵�½ʧ����Ϣ
				{
					System.out.println("�û� id="+u.getUserID() +" pwd="+u.getPasswd()+"��֤ʧ��");
					message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
					oos.writeObject(message);
					//�ر�socket
					socket.close();
				}
				}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			//���������˳���whileѭ����˵������˲��ټ����������Ҫ�ر�ServerSocket
			try {
				ss.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
