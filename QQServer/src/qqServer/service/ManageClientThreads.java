package qqServer.service;

import java.util.HashMap;
import java.util.Iterator;


//该类用于管理和客户端进行通讯的线程
public class ManageClientThreads {
	private static HashMap<String,ServerConnectClientThread> hm = new HashMap<>();
	
	//添加线程对象到hm集合
	public static void addClientThread(String userId,ServerConnectClientThread serverConnectClientThread)
	{
		hm.put(userId, serverConnectClientThread);
	}
	//根据userId 返回ServerConnectClientThread线程
	public static ServerConnectClientThread getServerConnectClientThread(String userId)
	{
		return hm.get(userId);
	}
	
	public static String getOnlineUsers() {
		//集合遍历
		Iterator<String> iterator = hm.keySet().iterator();
		String onlineUserList = "";
		while(iterator.hasNext()) {
			onlineUserList += iterator.next().toString() + " ";
		}
		return onlineUserList;
	}
	//从集合中移除某个线程
	public static void removeServerConnectClientThread(String userId)
	{
		hm.remove(userId);
	}
}
