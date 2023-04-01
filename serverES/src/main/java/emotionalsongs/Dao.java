package emotionalsongs;

import java.util.Optional;
import java.util.TreeMap;

public interface Dao<T>
{
	Optional<T> get(String id);
	TreeMap<String, T> getAll();
	boolean save(T t);
	boolean update(T t, Object[] params);
	boolean delete(T t);
}
