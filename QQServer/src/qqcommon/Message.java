package qqcommon;
import java.io.Serializable;

//表示客户端和服务器端通讯时的消息对象
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	private String sender;//发送者
	private String getter;//接收者
	private String content;//内容
	private String sendTime;//发送时间
	private String mesType;//消息类型,可以在接口中定义消息类型
	
	private byte[] fileBytes;
	private int filelen = 0;
	private String dest;//目标文件目录
	private String src;//源文件目录
	private String fileName;
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public void setFileBytes(byte[] fileBytes)
	{
		this.fileBytes = fileBytes;
	}
	public void setFilelen(int filelen)
	{
		this.filelen = filelen;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public void setSrc(String src)
	{
		this.src = src;
	}
	public void setSender(String sender)
	{
		this.sender = sender;
	}
	public void setGetter(String getter)
	{
		this.getter = getter;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public void setSendTime(String sendTime)
	{
		this.sendTime = sendTime;
	}
	public void setMesType(String mesType)
	{
		this.mesType = mesType;
	}
	
	public String getSender()
	{
		return sender;
	}
	public String getGetter()
	{
		return getter;
	}
	public String getContent()
	{
		return content;
	}
	public String getSendTime()
	{
		return sendTime;
	}
	public String getMesTyepe()
	{
		return mesType;
	}
	public byte[] getFileBytes()
	{
		return fileBytes;
	}
	public int getFilelen()
	{
		return filelen;
	}
	public String getDest()
	{
		return dest;
	}
	public String getSrc()
	{
		return src;
	}
	public String getFileName()
	{
		return fileName;
	}
}
