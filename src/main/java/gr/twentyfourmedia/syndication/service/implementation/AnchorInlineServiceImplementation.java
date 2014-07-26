package gr.twentyfourmedia.syndication.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.twentyfourmedia.syndication.dao.AnchorInlineDao;
import gr.twentyfourmedia.syndication.model.AnchorInline;
import gr.twentyfourmedia.syndication.service.AnchorInlineService;

@Service
@Transactional
public class AnchorInlineServiceImplementation implements AnchorInlineService {

	@Autowired
	private AnchorInlineDao anchorInlineDao;

	@Override
	public void persistAnchorInline(AnchorInline anchorInline) {
	
		anchorInlineDao.persist(anchorInline);
	}

	@Override
	public void mergeAnchorInline(AnchorInline anchorInline) {

		anchorInlineDao.merge(anchorInline);
	}

	@Override
	public void deleteAllAnchorsInline() {

		anchorInlineDao.deleteAll();
	}
}