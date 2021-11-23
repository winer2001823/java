package swingqq;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import qqclientservice.ManageChatWindows;
import qqclientservice.ManageClientConnectServerThread;
import qqclientservice.ManageGroupWindows;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;
public class qqlist extends JFrame implements ActionListener,TreeSelectionListener,MouseListener {
	private static final long serialVersionUID = 1L;
	private JFrame list1;
	//private JPanel jplist1;//jplist2,jplist3;
	private JPanel linkList;
	private JLabel backImg,iPhoto;
	//这里需要所登录用户详细信息
	//这里应该读取服务端好友列表。。。
	//先用个hashmap存着吧
	private static HashMap<String,User> myfriends = new HashMap<>();
	static {
		//静态代码块初始化
		myfriends.put("朱文成",new User("朱文成","Zzzzzzz",new ImageIcon("image/p1.jpg"),0));
		myfriends.put("马家乐",new User("马家乐","责任全在丰雨方",new ImageIcon("image/p2.jpg"),0));
		myfriends.put("丰雨柯",new User("丰雨柯","责任全在马方",new ImageIcon("image/p3.jpg"),0));
		myfriends.put("湛坤",new User("湛坤","要实现共产主义",new ImageIcon("image/p4.jpg"),0));
		myfriends.put("秦屹铭",new User("秦屹铭","寂寞的哥",new ImageIcon("image/p5.jpg"),0));
		myfriends.put("陈迪",new User("陈迪","很抽象",new ImageIcon("image/p6.jpg"),0));
		myfriends.put("群聊",new User("群聊","",new ImageIcon("image/群.png"),1));
	}
	public User getmyFriend(String userId) {
		return myfriends.get(userId);
	}
	public void rePaint() {
		list1.validate();
	}
	
	private User root = new User();
	private JTree tree;
	private User U;
	private Socket socket;
	//还需要一个聊天窗口管理类用来管理聊天窗口类
	private ManageChatWindows manageChatWindows = new ManageChatWindows();
	private ManageGroupWindows managegroupwindows = new ManageGroupWindows();
	//写个函数方便获得管理类
	public ManageChatWindows getManageChatWindows()
	{
		return manageChatWindows;
	}
	public ManageGroupWindows getManageGroupWindows()
	{
		return managegroupwindows;
	}
	public qqlist(User u,Socket _socket)//传一个参数User,根据User来初始化界面
	{ 
		U = u;
		socket = _socket;
		getFriendList();
		ImageIcon backGround = new ImageIcon("image/listBack1.jpg");
		ImageIcon photo = u.getImg();
		list1 = new JFrame();
		/*jplist1 = new JPanel();
		jplist2 = new JPanel();*/
		backImg = new JLabel();
		iPhoto = new JLabel();
		
		list1.setLayout(null);
		list1.setSize(350,670);
		backImg.setBounds(0,0,350,150);
		backGround.setImage(backGround.getImage().getScaledInstance(350,400,Image.SCALE_DEFAULT));
		backImg.setIcon(backGround);
		
		iPhoto.setBounds(0,0,50,50);
		photo.setImage(photo.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
		iPhoto.setIcon(photo);
		backImg.add(iPhoto);
		list1.add(backImg);
		
		linkList = new JPanel();
		linkList.setLayout(null);
		linkList.setBounds(0,150,350,500);
		
		//构建好友列表树
		root.add(myfriends.get("朱文成"));
		root.add(myfriends.get("马家乐"));
		root.add(myfriends.get("丰雨柯"));
		root.add(myfriends.get("湛坤"));
		root.add(myfriends.get("秦屹铭"));
		root.add(myfriends.get("陈迪"));
		root.add(myfriends.get("群聊"));
		
		tree = new JTree(root);
		tree.putClientProperty("JTree.lineStyle","Horizontal");//去掉连接线
		tree.setFont(new Font("微软雅黑",Font.PLAIN,15));//字体
		tree.setRowHeight(60);//节点高度
		//tree.addTreeSelectionListener(null);
		tree.setCellRenderer(new FriendNodeRenderer());//节点渲染
		//tree.addMouseListener(new MouseAdapter());
		tree.setToggleClickCount(1);//单击展开树
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);//选择监听
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		scrollPane.setBounds(0,0,350,500);
		linkList.add(scrollPane);
		list1.add(linkList);
		list1.setVisible(true);
		list1.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				 logout();
			}
		});
	}
	public void actionPerformed(ActionEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isLeftMouseButton(e))
		{
			TreePath path = tree.getSelectionPath(); 
	        if(path != null) tree.expandPath(path);
		}	
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		User node = (User) tree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
        	//创建聊天窗口并将其加入聊天窗口管理类
        	if(node.getUserID().equals("群聊"))
        		managegroupwindows.addGroupWindows(node.getUserID(),new group(U,node));
        	else
        		manageChatWindows.addChatWindows(node.getUserID(),new chatWindows(U,node));
        }
	}
	public void getFriendList() {
		//给服务端发信号
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_ONLINEFRIEND);
		message.setSender(U.getUserID());
		//发送给服务器
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(U.getUserID()).getSocket().getOutputStream());
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void logout() {
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
		message.setSender(U.getUserID());//指定退出的客户端
		
		//发送message
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);
			System.out.println("退出系统");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
