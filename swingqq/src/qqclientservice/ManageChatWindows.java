package qqclientservice;

import java.util.HashMap;

import swingqq.chatWindows;

public class ManageChatWindows {
	//�Ѷ�����촰�ڷ���HashMap key�����������id��value���Ǵ��ڶ���
	private HashMap<String,chatWindows> hm = new HashMap<>();
	//��ĳ�����ڶ������
	public void addChatWindows(String userId,chatWindows chatwindows)
	{
		hm.put(userId, chatwindows);
		
	}
	//ͨ��userId �õ����ڶ���
	public chatWindows getChatWindows (String userId)
	{
		return hm.get(userId);
	}
}
