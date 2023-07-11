package emotionalsongs;

import common.Playlist;

public interface PlaylistDAOInterface extends Dao<Playlist>
{
	public boolean addSong(String idPlaylist, String idCanzone);
}
