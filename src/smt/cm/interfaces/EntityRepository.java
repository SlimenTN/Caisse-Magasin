package smt.cm.interfaces;

import java.util.List;

/**
 * EntityRepository an interface that contains CRUD methodes
 * @author Slimen
 *
 * @param <T>
 */
public interface EntityRepository<T> {
	
	/**
	 * Create new entity
	 * @param obj
	 * @return
	 */
	public int create(T obj);
	
	/**
	 * Read list of entities
	 * @return
	 */
	public List<T> read();
	
	/**
	 * Update entity
	 * @param obj
	 */
	public void update(T obj);
	
	/**
	 * Delete entity
	 * @param idObj
	 */
	public void delete(T idObj);
	
	/**
	 * Get entity by id
	 * @param id
	 * @return
	 */
	public T findById(int id);
	
	/**
	 * Get entity by specific property
	 * @param field
	 * @param value
	 * @return
	 */
	public T findBy(String field, String value);
}
