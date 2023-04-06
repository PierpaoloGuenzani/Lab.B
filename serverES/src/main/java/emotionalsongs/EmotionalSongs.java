package emotionalsongs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmotionalSongs
{
	public static void main(String[] args)
	{
		Connection c;
		try
		{
			c = DriverManager.getConnection("jdbc:postgresql://localhost/dbES","postgres","admin");
			System.out.println("connessione con successo");
			new SongSQLDB(c).getAll();
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("ERRORE");
		}
	}
}
