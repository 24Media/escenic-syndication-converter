package gr.twentyfourmedia.syndication.dao;

import gr.twentyfourmedia.syndication.model.Content;
import gr.twentyfourmedia.syndication.model.Creator;

public interface CreatorDao extends AbstractDao<Creator> {

	void persistContentCreators(Content content);
	
	void mergeContentCreators(Content content);
}
