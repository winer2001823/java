package qqcommon;

import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;


//用户信息
public class User extends DefaultMutableTreeNode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String userID;
	private String passwd;
	private String uniqueSig;//个性签名
	private int key = 0;//在线状态
	private ImageIcon img;//头像
	public User(String userID,String passwd) {
		this.userID = userID;
		this.passwd = passwd;
	}
	public User(String userID,String passwd,String uniqueSig,ImageIcon img,int key)
	{
		this.userID = userID;
		this.passwd = passwd;
		this.uniqueSig = uniqueSig;
		this.img = img;
		this.key = key;
	}
	public User(String UserID,String uniqueSig,ImageIcon img,int key)
	{
		this.userID = UserID;
		this.uniqueSig = uniqueSig;
		this.img = img;
		this.key = key;
	}
	public User() {};
	public String getUserID()
	{
		return userID;
	}
	public String getPasswd()
	{
		return passwd;
	}
	public String getUniqueSig()
	{
		return uniqueSig;
	}
	public int getKey()
	{
		return key;
	}
	public ImageIcon getImg()
	{
		return img;
	}
	
	
	public void setUserID(String userID)
	{
		this.userID=userID;
	}
	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}
	public void setUniqueSig(String uniquesig)
	{
		this.uniqueSig = uniquesig;
	}
	
	public void setKey(int key)
	{
		this.key = key;
	}
	public void setImg(ImageIcon img)
	{
		this.img = img;
	}
}
