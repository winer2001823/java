package qqclientservice;

import java.util.HashMap;

import swingqq.chatWindows;

public class ManageChatWindows {
	//把多个聊天窗口放入HashMap key就是聊天对象id，value就是窗口对象
	private HashMap<String,chatWindows> hm = new HashMap<>();
	//将某个窗口对象加入
	public void addChatWindows(String userId,chatWindows chatwindows)
	{
		hm.put(userId, chatwindows);
		
	}
	//通过userId 得到窗口对象
	public chatWindows getChatWindows (String userId)
	{
		return hm.get(userId);
	}
}
