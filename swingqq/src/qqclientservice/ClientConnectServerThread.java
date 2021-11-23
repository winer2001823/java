package qqclientservice;

import java.io.ObjectInputStream;
import java.net.Socket;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;
import swingqq.qqlist;

public class ClientConnectServerThread extends Thread {
		//该线程需要持有Socket
		private Socket socket;
		private User u;
		public ClientConnectServerThread(User u,Socket socket) {
			this.socket = socket;
			this.u = u;
		}
		public void run() {
			qqlist myQQlist = new qqlist(u,socket);
			//因为Thread需要在后台一直和服务器通信，因此做成循环
			try {
				//ObjectInputStream  ois = new ObjectInputStream(socket.getInputStream());
				while(!socket.isClosed())
				{
					System.out.println("客户端线程等待来自服务端的消息");
					ObjectInputStream  ois = new ObjectInputStream(socket.getInputStream());
					Message message = (Message)ois.readObject();//如果服务器没有发送Message对象，线程会阻塞在这里等待
					//后面去要去使用message
					//判断这个message的类型，然后做相应业务处理
					if(message == null)
						break;
					if(message.getMesTyepe().equals(MessageType.MESSAGE_RET_ONLINEFRIEND)) {
						//取出在线列表
						String[] onlineUsers = message.getContent().split(" ");
						for(int i = 0;i<onlineUsers.length;i++) {
							myQQlist.getmyFriend(onlineUsers[i]).setKey(1);
						}
						myQQlist.rePaint();
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_COMM_MES)) {
						//1、把从服务器端转发的消息显示到控制台2、把消息发送到聊天框??????
						//好像应该吧这些都写到这个线程里来，不然传不过去
						System.out.println("\n" + message.getSender() + "对" +message.getGetter() + "说:" + message.getContent());
						myQQlist.getManageChatWindows().getChatWindows(message.getSender()).appendJTextArea(message.getSender(),message.getContent());
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_FILE_MES)) {
						myQQlist.getManageChatWindows().getChatWindows(message.getSender()).receiveFile(message);
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_USER_ONLINE)) {
						//获得某个好友的上线提醒1、根据发送者id找到这个好友2、将这位好友的本地状态改为在线
						myQQlist.getmyFriend(message.getSender()).setKey(1);
						//刷新界面(忘了在界面写显示在线状态了，先补上再来这)
						//补完了
						myQQlist.rePaint();
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_USER_OFF)){
						//收到好友退出系统的消息1、根据发送者id找到这个好友2、将这位好友的本地状态改为离线
						myQQlist.getmyFriend(message.getSender()).setKey(0);
						//刷新界面
						myQQlist.rePaint();
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_GROUP_MES)) {
						//获取群聊窗口，把消息内容显示出来
						System.out.println("\n" + message.getSender() + "在群聊里说:" + message.getContent());
						myQQlist.getManageGroupWindows().getGroupWindows(message.getGetter()).appendJTextArea(message.getSender(),message.getContent());
					}
					else {
						System.out.println("是其他类型message，暂不处理...");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				}
		}		
		//为了更方便的得到socket
		public Socket getSocket() {
			return socket;
		}

}
