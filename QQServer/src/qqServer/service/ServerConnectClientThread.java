package qqServer.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import qqcommon.Message;
import qqcommon.MessageType;

public class ServerConnectClientThread extends Thread {
	
	private Socket socket;
	private String userId;//连接到服务端的用户id
	public ServerConnectClientThread(Socket socket,String userId) {
		this.socket = socket;
		this.userId = userId;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void run() {//这里线程处于run状态，可以发送接消息
		QQServer.sendUserOnlineStatus(userId);//上线提醒
		try {
			//ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(!socket.isClosed()) {
				System.out.println("服务端和客户端保持通信"+userId+"读取数据..");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//这个对应qqlist里面的outputstream
				//System.out.println("============================================");
				Message message = (Message)ois.readObject();
				//后面会使用Message，根据message的类型，做相应的业务处理
				if(message.getMesTyepe().equals(MessageType.MESSAGE_GET_ONLINEFRIEND)) {
					//客户端要显示在线用户列表
					
					System.out.println(message.getSender() + "请求在线用户列表");
					String onlineUser = ManageClientThreads.getOnlineUsers();
					//返回message
					//构建Message对象，返回客户端
					Message message2 = new Message();
					message2.setMesType(MessageType.MESSAGE_RET_ONLINEFRIEND);
					message2.setContent(onlineUser);
					message2.setGetter(message.getSender());
					//写入到数据通道，返回给客户端
					
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(message2);
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_COMM_MES)) {
					//发送消息
					
					//根据message获取getterid id，然后获得其对应线程
					ServerConnectClientThread serverConnectClientThread = 
							ManageClientThreads.getServerConnectClientThread(message.getGetter());
					//得到对应socket的对象输出流，将message对象发给指定的客户端
					ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					oos.writeObject(message);
					System.out.println(message.getSender()+"对"+message.getGetter()+"说"+message.getContent());
					//如果对方不在线，可以把消息保存到数据库，这样可以实现离线留言
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_FILE_MES)) {
					//发送文件消息
					//根据message获取getterid id，然后获得其对应线程
					ServerConnectClientThread serverConnectClientThread = 
							ManageClientThreads.getServerConnectClientThread(message.getGetter());
					//得到对应socket的对象输出流，将message对象发给指定的客户端
					ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					oos.writeObject(message);
					System.out.println(message.getSender()+"给"+message.getGetter()+"发送了文件");
					//如果对方不在线，可以把消息保存到数据库，这样可以实现离线留言
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_GROUP_MES)) {
					//发送群聊消息。遍历在线的用户，给他们发送消息
					String[] onlineUsers = ManageClientThreads.getOnlineUsers().split(" ");
					for(int i = 0;i<onlineUsers.length;i++) {
						if(!onlineUsers[i].equals(userId))
						{
							ServerConnectClientThread serverConnectClientThread = 
								ManageClientThreads.getServerConnectClientThread(onlineUsers[i]);
						ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
						oos.writeObject(message);
						}
					}
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
					//客户端退出1、要把客户端对应的服务端线程删除2、要发送信号给其好友告知其下线
					System.out.println(message.getSender() + " 退出");
					//把该客户端对应的线程删除
					ManageClientThreads.removeServerConnectClientThread(message.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					//告知其好友其已经下线，首先要找出在线的好友，然后通过一个循环来给他们发消息告知
					String[] onlineUsers = ManageClientThreads.getOnlineUsers().split(" ");
					Message message1 = new Message();
					message1.setMesType(MessageType.MESSAGE_USER_OFF);
					message1.setSender(message.getSender());
					for(int i = 0;i<onlineUsers.length && onlineUsers[i] != "";i++) {
						message1.setGetter(onlineUsers[i]);
						ObjectOutputStream oos1 =
								new ObjectOutputStream(ManageClientThreads.getServerConnectClientThread(onlineUsers[i]).getSocket().getOutputStream());
						oos1.writeObject(message1);
					}
					oos.writeObject(null);//告知结束
					socket.close();//关闭连接
					//退出线程
					break;
				}
				else {
					System.out.println("其他的先不处理");
				}
			} 
		}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
