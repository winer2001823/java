package qqclientservice;

import java.util.HashMap;
import swingqq.group;

public class ManageGroupWindows {
	//�Ѷ�����촰�ڷ���HashMap key�����������id��value���Ǵ��ڶ���
	private HashMap<String,group> hm = new HashMap<>();
	//��ĳ�����ڶ������
	public void addGroupWindows(String groupId,group _group)
	{
		hm.put(groupId, _group);
		
	}
	//ͨ��userId �õ����ڶ���
	public group getGroupWindows (String groupId)
	{
		return hm.get(groupId);
	}
}
