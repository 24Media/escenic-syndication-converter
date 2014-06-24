package gr.twentyfourmedia.syndication.dao;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Field;

public interface FieldDao extends AbstractDao<Field> {

	void persistContentFields(Content content);
}
