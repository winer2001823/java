package qqclientservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import qqcommon.Message;
import qqcommon.MessageType;

//���� �ṩ�ͷ������Ϣ��صķ��񷽷�
public class MessageClientService {
	
	public void sendMessageToOne(String content,String senderId,String getterId){
		//����message
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_COMM_MES);
		message.setSender(senderId);
		message.setGetter(getterId);
		message.setContent(content);
		message.setSendTime(new Date().toString());
		System.out.println(senderId + "��" + getterId + "˵��һ�仰");
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendMessageToGroup(String content,String senderId,String groupId) {
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GROUP_MES);
		message.setSender(senderId);
		message.setContent(content);
		message.setGetter(groupId);
		message.setSendTime(new Date().toString());
		System.out.println(senderId + "��Ⱥ��˵��һ�仰");
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendFileToOne(String src,String senderId,String getterId) {
		File file = new File(src);
		System.out.println(file.getPath());
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_FILE_MES);
		message.setSender(senderId);
		message.setGetter(getterId);
		message.setSrc(src);
		message.setFileName(file.getName());
		message.setSendTime(new Date().toString());
		//��ȡ�ļ�
		FileInputStream fileInputStream = null;
		byte[] fileBytes = new byte[(int)new File(src).length()];
		System.out.println(senderId + "��" + getterId +"�������ļ�");
		try {
			fileInputStream = new FileInputStream(src);
			fileInputStream.read(fileBytes);//��ȡ�ļ�������
			message.setFileBytes(fileBytes);
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
}
