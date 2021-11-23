package qqclientservice;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

//该类完成用户登录和注册等功能
public class UserClientService {
	
	private User u = new User();//可能在其他地方使用
	private Socket socket;
	public boolean checkUser(String userId,String pwd)  {
		boolean b = false;
		//创建user对象
		u.setUserID(userId);
		u.setPasswd(pwd);
		//连接到服务器，发送u对象;
		try {
		socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
		//得到ObjectOutputStream对象
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		//oos.writeObject(u);//发送User对象
		//读取从服务器回复的Message对象
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		oos.writeObject(u);//发送User对象
		Message ms = (Message) ois.readObject();
		if(ms.getMesTyepe().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
			//登陆成功就从服务端获取用户详细信息
			u = (User)ois.readObject();
			//还要获取在线用户
			
			//onlineFriendList();

			//创建一个和服务器端保持通讯的线程->创建一个类 ClientConnectServerThread
			ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(u,socket);
			//启动线程
			clientConnectServerThread.start();
			//为了客户端的扩展性，将线程放入集合管理
			ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
			b = true;
		}
		else
		{
			//如果登陆失败了,就不能启动线程，要关闭socket
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
	//向服务器端请求在线用户列表
	public void onlineFriendList() {
		//给服务端发送信号
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_ONLINEFRIEND);
		message.setSender(u.getUserID());
		//发送给服务器
		//应该得到当前线程socket对应的ObjectOutputStream对象
		try{ 
			//ObjectOutputStream oos = 
				//	new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID()).getSocket().getOutputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);//发送message给服务端
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//退出客户端并给服务端发送退出消息的方法
	//向服务端请求好友列表
	/*public void getFriendList() {
		//给服务端发信号
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_USERFRIEND);
		message.setSender(u.getUserID());
		//发送给服务器
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID()).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	
}
	

