package emotionalsongs;

import common.Percezione;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public interface PerceptionDAOInterface
{
	ConcurrentHashMap<String, List<Percezione>> getAll();
	List<Percezione> get(String idCanzone, String idUtente);
	boolean save(Percezione t);
	boolean update(Percezione t, Object[] params);
	boolean delete(Percezione t);
}
