package qqServer.service;

import java.util.HashMap;
import java.util.Iterator;


//�������ڹ���Ϳͻ��˽���ͨѶ���߳�
public class ManageClientThreads {
	private static HashMap<String,ServerConnectClientThread> hm = new HashMap<>();
	
	//����̶߳���hm����
	public static void addClientThread(String userId,ServerConnectClientThread serverConnectClientThread)
	{
		hm.put(userId, serverConnectClientThread);
	}
	//����userId ����ServerConnectClientThread�߳�
	public static ServerConnectClientThread getServerConnectClientThread(String userId)
	{
		return hm.get(userId);
	}
	
	public static String getOnlineUsers() {
		//���ϱ���
		Iterator<String> iterator = hm.keySet().iterator();
		String onlineUserList = "";
		while(iterator.hasNext()) {
			onlineUserList += iterator.next().toString() + " ";
		}
		return onlineUserList;
	}
	//�Ӽ������Ƴ�ĳ���߳�
	public static void removeServerConnectClientThread(String userId)
	{
		hm.remove(userId);
	}
}
