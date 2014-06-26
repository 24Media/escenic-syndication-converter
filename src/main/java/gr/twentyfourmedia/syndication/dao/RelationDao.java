package gr.twentyfourmedia.syndication.dao;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Relation;

public interface RelationDao extends AbstractDao<Relation> {

	void persistContentRelations(Content content);
	
	void mergeContentRelations(Content content);
}
