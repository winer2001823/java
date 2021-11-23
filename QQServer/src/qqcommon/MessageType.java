package qqcommon;

public interface MessageType {
	//定义常量表示消息类型
	String MESSAGE_LOGIN_SUCCEED = "1";//表示登陆成功
	String MESSAGE_LOGIN_FAIL = "2";//表示登陆失败
	String MESSAGE_COMM_MES = "3";//普通信息包
	String MESSAGE_GET_ONLINEFRIEND = "4";//要求返回在线用户列表
	String MESSAGE_RET_ONLINEFRIEND = "5";//返回在线用户列表
	String MESSAGE_CLIENT_EXIT = "6";//客户端请求退出
	String MESSAGE_USER_ONLINE = "7";//用户上线
	String MESSAGE_USER_OFF = "8";//用户离线
	String MESSAGE_FILE_MES = "9";//文件消息
	String MESSAGE_GROUP_MES = "10";//群聊消息
}
