package emotionalsongs;

import java.util.Map;
import java.util.Optional;


public interface Dao<T>
{
	Optional<T> get(String id);
	Map<String, T> getAll();
	boolean save(T t);
	boolean update(T t, Object[] params);
	boolean delete(T t);
}
