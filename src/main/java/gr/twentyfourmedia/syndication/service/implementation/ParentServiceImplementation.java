package gr.twentyfourmedia.syndication.service.implementation;

import gr.twentyfourmedia.syndication.dao.ParentDao;
import gr.twentyfourmedia.syndication.model.Parent;
import gr.twentyfourmedia.syndication.service.ParentService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ParentServiceImplementation implements ParentService {

	@Autowired
	private ParentDao parentDao;
	
	@Override
	public Parent getParent(Long applicationId) {
		
		return parentDao.get(applicationId);
	}
}
