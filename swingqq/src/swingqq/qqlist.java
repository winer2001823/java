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
	//������Ҫ����¼�û���ϸ��Ϣ
	//����Ӧ�ö�ȡ����˺����б�����
	//���ø�hashmap���Ű�
	private static HashMap<String,User> myfriends = new HashMap<>();
	static {
		//��̬������ʼ��
		myfriends.put("���ĳ�",new User("���ĳ�","Zzzzzzz",new ImageIcon("image/p1.jpg"),0));
		myfriends.put("�����",new User("�����","����ȫ�ڷ��귽",new ImageIcon("image/p2.jpg"),0));
		myfriends.put("�����",new User("�����","����ȫ����",new ImageIcon("image/p3.jpg"),0));
		myfriends.put("տ��",new User("տ��","Ҫʵ�ֹ�������",new ImageIcon("image/p4.jpg"),0));
		myfriends.put("������",new User("������","��į�ĸ�",new ImageIcon("image/p5.jpg"),0));
		myfriends.put("�µ�",new User("�µ�","�ܳ���",new ImageIcon("image/p6.jpg"),0));
		myfriends.put("Ⱥ��",new User("Ⱥ��","",new ImageIcon("image/Ⱥ.png"),1));
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
	//����Ҫһ�����촰�ڹ����������������촰����
	private ManageChatWindows manageChatWindows = new ManageChatWindows();
	private ManageGroupWindows managegroupwindows = new ManageGroupWindows();
	//д�����������ù�����
	public ManageChatWindows getManageChatWindows()
	{
		return manageChatWindows;
	}
	public ManageGroupWindows getManageGroupWindows()
	{
		return managegroupwindows;
	}
	public qqlist(User u,Socket _socket)//��һ������User,����User����ʼ������
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
		
		//���������б���
		root.add(myfriends.get("���ĳ�"));
		root.add(myfriends.get("�����"));
		root.add(myfriends.get("�����"));
		root.add(myfriends.get("տ��"));
		root.add(myfriends.get("������"));
		root.add(myfriends.get("�µ�"));
		root.add(myfriends.get("Ⱥ��"));
		
		tree = new JTree(root);
		tree.putClientProperty("JTree.lineStyle","Horizontal");//ȥ��������
		tree.setFont(new Font("΢���ź�",Font.PLAIN,15));//����
		tree.setRowHeight(60);//�ڵ�߶�
		//tree.addTreeSelectionListener(null);
		tree.setCellRenderer(new FriendNodeRenderer());//�ڵ���Ⱦ
		//tree.addMouseListener(new MouseAdapter());
		tree.setToggleClickCount(1);//����չ����
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);//ѡ�����
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
        	//�������촰�ڲ�����������촰�ڹ�����
        	if(node.getUserID().equals("Ⱥ��"))
        		managegroupwindows.addGroupWindows(node.getUserID(),new group(U,node));
        	else
        		manageChatWindows.addChatWindows(node.getUserID(),new chatWindows(U,node));
        }
	}
	public void getFriendList() {
		//������˷��ź�
		Message message = new Message();
		message.setMesType(MessageType.MESSAGE_GET_ONLINEFRIEND);
		message.setSender(U.getUserID());
		//���͸�������
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
		message.setSender(U.getUserID());//ָ���˳��Ŀͻ���
		
		//����message
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);
			System.out.println("�˳�ϵͳ");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
