package Testpck;

import java.sql.*;

public class Test {

	public static void main(String[] args)

	{

		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=CAP";

		String userName = "sa";

		String userPwd = "zwc2001823";
		
		Connection dbConn = null;

		try

		{

			Class.forName(driverName);

			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);

			System.out.println("�������ݿ�ɹ�");

		}

		catch (Exception e)

		{

			e.printStackTrace();

			System.out.print("����ʧ��");

		}
		
	}

}