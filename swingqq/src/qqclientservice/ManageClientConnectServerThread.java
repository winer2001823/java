package qqclientservice;

import java.util.HashMap;

public class ManageClientConnectServerThread {
	//�Ѷ���̷߳���HashMap key�����û�id��value�����߳�
	private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();
	//��ĳ���̼߳���
	public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread)
	{
		hm.put(userId, clientConnectServerThread);
		
	}
	//ͨ��userId �õ��߳�
	public static ClientConnectServerThread getClientConnectServerThread (String userId)
	{
		return hm.get(userId);
	}
}
