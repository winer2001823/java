package qqcommon;

public interface MessageType {
	//���峣����ʾ��Ϣ����
	String MESSAGE_LOGIN_SUCCEED = "1";//��ʾ��½�ɹ�
	String MESSAGE_LOGIN_FAIL = "2";//��ʾ��½ʧ��
	String MESSAGE_COMM_MES = "3";//��ͨ��Ϣ��
	String MESSAGE_GET_ONLINEFRIEND = "4";//Ҫ�󷵻������û��б�
	String MESSAGE_RET_ONLINEFRIEND = "5";//���������û��б�
	String MESSAGE_CLIENT_EXIT = "6";//�ͻ��������˳�
	String MESSAGE_USER_ONLINE = "7";//�û�����
	String MESSAGE_USER_OFF = "8";//�û�����
	String MESSAGE_FILE_MES = "9";//�ļ���Ϣ
	String MESSAGE_GROUP_MES = "10";//Ⱥ����Ϣ
}
