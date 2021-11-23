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

//服务端，在监听9999，等待客户端链接，并保持通讯
public class QQServer {
	
	private ServerSocket ss = null;
	
	//创建一个集合，存放多个用户
	private static HashMap<String,User> validUsers = new HashMap<>();
	//HaspMap 没有处理线程安全，因此在多线程情况下不安全
	//这里也可以使用ConcurrentHashMap.可以处理并发集合，没有线程安全问题
	static {
		//静态代码块初始化
		validUsers.put("朱文成",new User("朱文成","123456","Zzzzzzz",new ImageIcon("image/p1.jpg"),0));
		validUsers.put("马家乐",new User("马家乐","123456","责任全在丰雨方",new ImageIcon("image/p2.jpg"),0));
		validUsers.put("丰雨柯",new User("丰雨柯","123456","责任全在马方",new ImageIcon("image/p3.jpg"),0));
		validUsers.put("湛坤",new User("湛坤","123456","要实现共产主义",new ImageIcon("image/p4.jpg"),0));
		validUsers.put("秦屹铭",new User("秦屹铭","123456","寂寞的哥",new ImageIcon("image/p5.jpg"),0));
		validUsers.put("陈迪",new User("陈迪","123456","很抽象",new ImageIcon("image/p6.jpg"),0));
		
	}
	//我需要保存每个用户的好友列表，并让他们对应起来，希望有可扩展性
	//使用HashMap<String,[] User>
	//首先要初始化[]User
	//为了方便限定每人最多十个好友吧
	//妈的 写不出来！！！！！！！！！！！！
	//干脆就在客户端添加所有人吧，这太耻辱了nm
	//即使是这样还是要吧所有人的信息发送给客户端啊
	//这他妈怎么发呢
	//算了，写的狗屎一点不发了
	
	
	private boolean checkUser(String userId,String passwd)//检验用户是否合法
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
	public static User getUserData(String userId)//发送用户信息给客户端	
	{
		User U = validUsers.get(userId);
		U.setKey(1);//把他的在线状态改为在线
		U.setPasswd(null);
		return U;
	}
	
	public static void sendUserOnlineStatus(String UserId)//上线提醒，把上线的消息发给所有在线好友
	{
		String[] onlineUsers = ManageClientThreads.getOnlineUsers().split(" ");//获得在线的好友
		//if(onlineUsers.length != 0) {
		Message message = new Message();
		message.setSender(UserId);
		message.setMesType(MessageType.MESSAGE_USER_ONLINE);//设置上线消息
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
		//端口可以写在配置文件
		try {
			System.out.println("服务端9999端口监听....");
			ss = new ServerSocket(9999);
			
			while(true) {//当和某个客户端建立连接后，会继续监听
				Socket socket = ss.accept();//没有客户端来链接的话会堵塞在这里
				//得到socket关联的对象输入流
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				
				//得到socket关联的对象的输出流
				ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
				//读取客户端发送的对象
				User u =(User)ois.readObject();
				//创建一个Message对象，准备回复客户端
				Message message = new Message();
				//验证
				if(checkUser(u.getUserID(),u.getPasswd()))
				{
					message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
					//将message对象回复给客户端
					oos.writeObject(message);
					//还要将用户详细信息返回
					u = getUserData(u.getUserID());
					oos.writeObject(u);
					System.out.println("发送用户信息成功");
					//创建一个线程，该线程持有socket对象
					ServerConnectClientThread serverConnectClientThread = 
							new ServerConnectClientThread(socket,u.getUserID());
					//启动该线程
					serverConnectClientThread.start();
					//把该线程对象，放入到一个集合中管理
					ManageClientThreads.addClientThread(u.getUserID(),serverConnectClientThread);
					
				}
				else//若验证失败，发送登陆失败消息
				{
					System.out.println("用户 id="+u.getUserID() +" pwd="+u.getPasswd()+"验证失败");
					message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
					oos.writeObject(message);
					//关闭socket
					socket.close();
				}
				}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			//如果服务端退出了while循环，说明服务端不再监听，因此需要关闭ServerSocket
			try {
				ss.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
