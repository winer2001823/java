package swingqq;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import qqcommon.User;
//树节点渲染器
public class FriendNodeRenderer extends DefaultTreeCellRenderer{//重写渲染器
	private static final long serialVersionUID = 1L;
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
			boolean expanded,boolean leaf, int row, boolean hasFocus)
	{
		String text;
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		User user = (User)value;
		ImageIcon icon = new ImageIcon(user.getImg()+"");
		icon.setImage(icon.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
		this.setIcon(icon);
		if(user.getUserID() == null)
		{
			text = "我的好友";
		}
		else 
			if(user.getKey() == 1)
				text = "<html>" + user.getUserID() + "  [在线]" + "<br/>" + user.getUniqueSig() + "<html/>";
			else
				text = "<html>" + user.getUserID() + "  [离线]" + "<br/>" + user.getUniqueSig() + "<html/>";
		setIconTextGap(15);
		setText(text);
		return this;
	}

}
