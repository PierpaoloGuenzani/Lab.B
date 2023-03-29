package emotionalsongs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLDB
{
	private static final Logger LOGGER = Logger.getLogger(SQLDB.class.getName());
	private Connection serverSQL;
	private static Connection sharedSQLserver;
	
	public SQLDB(String url, String user, String password, boolean sharedServer)
	{
		Connection c = null;
		try
		{
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e)
		{
			LOGGER.log(Level.SEVERE, "eccezione all apertura del db :", e);
		}
		
		if(sharedServer)
		{
			sharedSQLserver = c;
		}
		else
		{
			this.serverSQL = c;
		}
	}
	
	public SQLDB()
	{
		if(sharedSQLserver != null)
		{
			try
			{
				sharedSQLserver = DriverManager.getConnection("localhost", "postgres", "admin");
			} catch (SQLException e)
			{
				LOGGER.log(Level.SEVERE, "eccezione all apertura del db :", e);
			}
		}
	}
	
	public Connection getServerSQL()
	{
		return serverSQL;
	}
	
	public void setServerSQL(Connection serverSQL)
	{
		this.serverSQL = serverSQL;
	}
	
	public static Connection getSharedSQLserver()
	{
		return sharedSQLserver;
	}
	
	public static void setSharedSQLserver(Connection sharedSQLserver)
	{
		SQLDB.sharedSQLserver = sharedSQLserver;
	}
}
