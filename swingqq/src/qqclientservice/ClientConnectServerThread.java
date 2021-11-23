package qqclientservice;

import java.io.ObjectInputStream;
import java.net.Socket;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;
import swingqq.qqlist;

public class ClientConnectServerThread extends Thread {
		//���߳���Ҫ����Socket
		private Socket socket;
		private User u;
		public ClientConnectServerThread(User u,Socket socket) {
			this.socket = socket;
			this.u = u;
		}
		public void run() {
			qqlist myQQlist = new qqlist(u,socket);
			//��ΪThread��Ҫ�ں�̨һֱ�ͷ�����ͨ�ţ��������ѭ��
			try {
				//ObjectInputStream  ois = new ObjectInputStream(socket.getInputStream());
				while(!socket.isClosed())
				{
					System.out.println("�ͻ����̵߳ȴ����Է���˵���Ϣ");
					ObjectInputStream  ois = new ObjectInputStream(socket.getInputStream());
					Message message = (Message)ois.readObject();//���������û�з���Message�����̻߳�����������ȴ�
					//����ȥҪȥʹ��message
					//�ж����message�����ͣ�Ȼ������Ӧҵ����
					if(message == null)
						break;
					if(message.getMesTyepe().equals(MessageType.MESSAGE_RET_ONLINEFRIEND)) {
						//ȡ�������б�
						String[] onlineUsers = message.getContent().split(" ");
						for(int i = 0;i<onlineUsers.length;i++) {
							myQQlist.getmyFriend(onlineUsers[i]).setKey(1);
						}
						myQQlist.rePaint();
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_COMM_MES)) {
						//1���Ѵӷ�������ת������Ϣ��ʾ������̨2������Ϣ���͵������??????
						//����Ӧ�ð���Щ��д������߳���������Ȼ������ȥ
						System.out.println("\n" + message.getSender() + "��" +message.getGetter() + "˵:" + message.getContent());
						myQQlist.getManageChatWindows().getChatWindows(message.getSender()).appendJTextArea(message.getSender(),message.getContent());
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_FILE_MES)) {
						myQQlist.getManageChatWindows().getChatWindows(message.getSender()).receiveFile(message);
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_USER_ONLINE)) {
						//���ĳ�����ѵ���������1�����ݷ�����id�ҵ��������2������λ���ѵı���״̬��Ϊ����
						myQQlist.getmyFriend(message.getSender()).setKey(1);
						//ˢ�½���(�����ڽ���д��ʾ����״̬�ˣ��Ȳ���������)
						//������
						myQQlist.rePaint();
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_USER_OFF)){
						//�յ������˳�ϵͳ����Ϣ1�����ݷ�����id�ҵ��������2������λ���ѵı���״̬��Ϊ����
						myQQlist.getmyFriend(message.getSender()).setKey(0);
						//ˢ�½���
						myQQlist.rePaint();
					}
					else if(message.getMesTyepe().equals(MessageType.MESSAGE_GROUP_MES)) {
						//��ȡȺ�Ĵ��ڣ�����Ϣ������ʾ����
						System.out.println("\n" + message.getSender() + "��Ⱥ����˵:" + message.getContent());
						myQQlist.getManageGroupWindows().getGroupWindows(message.getGetter()).appendJTextArea(message.getSender(),message.getContent());
					}
					else {
						System.out.println("����������message���ݲ�����...");
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				}
		}		
		//Ϊ�˸�����ĵõ�socket
		public Socket getSocket() {
			return socket;
		}

}
