package gr.twentyfourmedia.syndication.dao.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gr.twentyfourmedia.syndication.dao.RelationCheckDao;
import gr.twentyfourmedia.syndication.model.RelationCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRelationCheckDao implements RelationCheckDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_RELATION_QUERY = "INSERT INTO relationCheck(contentApplicationId, contentType, contentHomeSection, source, sourceId, relationType) VALUES (?, ?, ?, ?, ?, ?)";
		
	/**
	 * Persist Entries Given All Related Data
	 */
	@Override
	public void persistEntry(Long contentApplicationId, String contentType, String contentHomeSection, String source, String sourceId, String relationType) {
		
		jdbcTemplate.update(INSERT_RELATION_QUERY, new Object[] { contentApplicationId, contentType, contentHomeSection, source, sourceId, relationType });
	}

	@Override
	public List<RelationCheck> getEntries() {
		
		String sql = "SELECT R.applicationId, R.contentApplicationId, R.contentType, R.contentHomeSection, R.source, R.sourceId, R.relationType, C.type, R.applicationDateUpdated " +
					 "FROM relationCheck AS R " +
					 "LEFT JOIN content AS C " +
					 "ON R.source = C.source AND R.sourceId = C.sourceId " +
					 "ORDER BY R.contentApplicationId, R.relationType ASC";
		
		List<RelationCheck> result = new ArrayList<RelationCheck>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	
		for(Map<String, Object> row : rows) {
			
			RelationCheck relationCheck = new RelationCheck();
			relationCheck.setApplicationId((Integer) row.get("applicationId"));
			relationCheck.setContentApplicationId((Integer) row.get("contentApplicationId"));
			relationCheck.setContentType((String) row.get("contentType"));
			relationCheck.setContentHomeSection((String) row.get("contentHomeSection"));
			relationCheck.setSource((String) row.get("source"));
			relationCheck.setSourceId((String) row.get("sourceId"));
			relationCheck.setRelationType((String) row.get("relationType"));
			relationCheck.setRelatedContentType((String) row.get("type"));
			relationCheck.setApplicationDateUpdated((Timestamp) row.get("applicationDateUpdated"));

			result.add(relationCheck);
		}
		
		return result;
	}
	
	/**
	 * Delete Table Before Inserting New Data
	 */
	@Override
	public void deleteTable() {
	
		jdbcTemplate.update("DELETE FROM relationCheck");
	}
}
