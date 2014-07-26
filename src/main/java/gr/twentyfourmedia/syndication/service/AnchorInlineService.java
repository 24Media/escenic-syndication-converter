package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.AnchorInline;

public interface AnchorInlineService {

	void persistAnchorInline(AnchorInline anchorInline);
	
	void mergeAnchorInline(AnchorInline anchorInline);
	
	void deleteAllAnchorsInline();
}
