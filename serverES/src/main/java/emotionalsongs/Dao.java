package emotionalsongs;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public interface Dao<T>
{
	Optional<T> get(String id);
	ConcurrentHashMap<String, T> getAll();
	boolean save(T t);
	boolean update(T t, Object[] params);
	boolean delete(T t);
}
