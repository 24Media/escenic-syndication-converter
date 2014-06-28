package gr.twentyfourmedia.syndication.dao.jdbc;

import gr.twentyfourmedia.syndication.dao.RelationCheckDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRelationCheckDao implements RelationCheckDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_RELATION_QUERY = "INSERT INTO relationCheck(contentApplicationId, source, sourceId, type) VALUES (?, ?, ?, ?)";
	
	/**
	 * Persist Entries Given All Related Data
	 */
	@Override
	public void persistEntry(Long contentApplicationId, String source, String sourceId, String type) {
		
		jdbcTemplate.update(INSERT_RELATION_QUERY, new Object[] { contentApplicationId, source, sourceId, type });
	}
	
	/**
	 * Delete Table Before Inserting New Data
	 */
	@Override
	public void deleteTable() {
	
		jdbcTemplate.update("DELETE FROM relationCheck");
	}
}
