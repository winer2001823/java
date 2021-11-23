package qqServer.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import qqcommon.Message;
import qqcommon.MessageType;

public class ServerConnectClientThread extends Thread {
	
	private Socket socket;
	private String userId;//���ӵ�����˵��û�id
	public ServerConnectClientThread(Socket socket,String userId) {
		this.socket = socket;
		this.userId = userId;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void run() {//�����̴߳���run״̬�����Է��ͽ���Ϣ
		QQServer.sendUserOnlineStatus(userId);//��������
		try {
			//ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(!socket.isClosed()) {
				System.out.println("����˺Ϳͻ��˱���ͨ��"+userId+"��ȡ����..");
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//�����Ӧqqlist�����outputstream
				//System.out.println("============================================");
				Message message = (Message)ois.readObject();
				//�����ʹ��Message������message�����ͣ�����Ӧ��ҵ����
				if(message.getMesTyepe().equals(MessageType.MESSAGE_GET_ONLINEFRIEND)) {
					//�ͻ���Ҫ��ʾ�����û��б�
					
					System.out.println(message.getSender() + "���������û��б�");
					String onlineUser = ManageClientThreads.getOnlineUsers();
					//����message
					//����Message���󣬷��ؿͻ���
					Message message2 = new Message();
					message2.setMesType(MessageType.MESSAGE_RET_ONLINEFRIEND);
					message2.setContent(onlineUser);
					message2.setGetter(message.getSender());
					//д�뵽����ͨ�������ظ��ͻ���
					
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(message2);
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_COMM_MES)) {
					//������Ϣ
					
					//����message��ȡgetterid id��Ȼ�������Ӧ�߳�
					ServerConnectClientThread serverConnectClientThread = 
							ManageClientThreads.getServerConnectClientThread(message.getGetter());
					//�õ���Ӧsocket�Ķ������������message���󷢸�ָ���Ŀͻ���
					ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					oos.writeObject(message);
					System.out.println(message.getSender()+"��"+message.getGetter()+"˵"+message.getContent());
					//����Է������ߣ����԰���Ϣ���浽���ݿ⣬��������ʵ����������
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_FILE_MES)) {
					//�����ļ���Ϣ
					//����message��ȡgetterid id��Ȼ�������Ӧ�߳�
					ServerConnectClientThread serverConnectClientThread = 
							ManageClientThreads.getServerConnectClientThread(message.getGetter());
					//�õ���Ӧsocket�Ķ������������message���󷢸�ָ���Ŀͻ���
					ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
					oos.writeObject(message);
					System.out.println(message.getSender()+"��"+message.getGetter()+"�������ļ�");
					//����Է������ߣ����԰���Ϣ���浽���ݿ⣬��������ʵ����������
				}
				else if(message.getMesTyepe().equals(MessageType.MESSAGE_GROUP_MES)) {
					//����Ⱥ����Ϣ���������ߵ��û��������Ƿ�����Ϣ
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
					//�ͻ����˳�1��Ҫ�ѿͻ��˶�Ӧ�ķ�����߳�ɾ��2��Ҫ�����źŸ�����Ѹ�֪������
					System.out.println(message.getSender() + " �˳�");
					//�Ѹÿͻ��˶�Ӧ���߳�ɾ��
					ManageClientThreads.removeServerConnectClientThread(message.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					//��֪��������Ѿ����ߣ�����Ҫ�ҳ����ߵĺ��ѣ�Ȼ��ͨ��һ��ѭ���������Ƿ���Ϣ��֪
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
					oos.writeObject(null);//��֪����
					socket.close();//�ر�����
					//�˳��߳�
					break;
				}
				else {
					System.out.println("�������Ȳ�����");
				}
			} 
		}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
