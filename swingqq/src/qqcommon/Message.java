package qqcommon;
import java.io.Serializable;

//��ʾ�ͻ��˺ͷ�������ͨѶʱ����Ϣ����
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	private String sender;//������
	private String getter;//������
	private String content;//����
	private String sendTime;//����ʱ��
	private String mesType;//��Ϣ����,�����ڽӿ��ж�����Ϣ����
	
	private byte[] fileBytes;
	private int filelen = 0;
	private String dest;//Ŀ���ļ�Ŀ¼
	private String src;//Դ�ļ�Ŀ¼
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
