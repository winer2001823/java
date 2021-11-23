package qqclientservice;

import java.util.HashMap;
import swingqq.group;

public class ManageGroupWindows {
	//把多个聊天窗口放入HashMap key就是聊天对象id，value就是窗口对象
	private HashMap<String,group> hm = new HashMap<>();
	//将某个窗口对象加入
	public void addGroupWindows(String groupId,group _group)
	{
		hm.put(groupId, _group);
		
	}
	//通过userId 得到窗口对象
	public group getGroupWindows (String groupId)
	{
		return hm.get(groupId);
	}
}
