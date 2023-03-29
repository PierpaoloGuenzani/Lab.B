package emotionalsongs;

import java.util.Optional;
import java.util.TreeMap;

public interface Dao<T>
{
	Optional<T> get(String id);
	TreeMap<String, T> getAll();
	void save(T t);
	void update(T t, Object[] params);
	void delete(T t);
}
