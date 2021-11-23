package qqclientservice;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

//��������û���¼��ע��ȹ���
public class UserClientService {
	
	private User u = new User();//�����������ط�ʹ��
	private Socket socket;
	public boolean checkUser(String userId,String pwd)  {
		boolean b = false;
		//����user����
		u.setUserID(userId);
		u.setPasswd(pwd);
		//���ӵ�������������u����;
		try {
		socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
		//�õ�ObjectOutputStream����
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		//oos.writeObject(u);//����User����
		//��ȡ�ӷ������ظ���Message����
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		oos.writeObject(u);//����User����
		Message ms = (Message) ois.readObject();
		if(ms.getMesTyepe().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
			//��½�ɹ��ʹӷ���˻�ȡ�û���ϸ��Ϣ
			u = (User)ois.readObject();
			//��Ҫ��ȡ�����û�
			
			//onlineFriendList();

			//����һ���ͷ������˱���ͨѶ���߳�->����һ���� ClientConnectServerThread
			ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(u,socket);
			//�����߳�
			clientConnectServerThread.start();
			//Ϊ�˿ͻ��˵���չ�ԣ����̷߳��뼯�Ϲ���
			ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
			b = true;
		}
		else
		{
			//�����½ʧ����,�Ͳ��������̣߳�Ҫ�ر�socket
			socket.close();
			
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public User getUser() {
		return u;
	}
	public Socket getSocket() {
		return socket;
	}
	//������������������û��б�
	public void onlineFriendList() {
		//������˷����ź�
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_ONLINEFRIEND);
		message.setSender(u.getUserID());
		//���͸�������
		//Ӧ�õõ���ǰ�߳�socket��Ӧ��ObjectOutputStream����
		try{ 
			//ObjectOutputStream oos = 
				//	new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID()).getSocket().getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);//����message�������
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//�˳��ͻ��˲�������˷����˳���Ϣ�ķ���
	//��������������б�
	/*public void getFriendList() {
		//������˷��ź�
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_USERFRIEND);
		message.setSender(u.getUserID());
		//���͸�������
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID()).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	
}
	

