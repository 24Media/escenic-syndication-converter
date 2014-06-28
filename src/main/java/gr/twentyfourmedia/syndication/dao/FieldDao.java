package gr.twentyfourmedia.syndication.dao;

import java.util.List;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;

public interface FieldDao extends AbstractDao<Field> {

	void persistContentFields(Content content);
	
	void mergeContentFields(Content content);
	
	List<Field> getByBodyContaining(String token);
}
