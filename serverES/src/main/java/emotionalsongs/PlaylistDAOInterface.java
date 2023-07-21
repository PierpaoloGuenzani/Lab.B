package emotionalsongs;

import common.Playlist;

import java.io.IOException;

public interface PlaylistDAOInterface extends Dao<Playlist>
{
	public boolean addSong(String idPlaylist, String idCanzone) throws IOException;
}
