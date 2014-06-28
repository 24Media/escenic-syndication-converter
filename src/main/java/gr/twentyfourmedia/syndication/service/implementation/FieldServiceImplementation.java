package gr.twentyfourmedia.syndication.service.implementation;

import java.util.List;

import gr.twentyfourmedia.syndication.dao.FieldDao;
import gr.twentyfourmedia.syndication.model.Field;
import gr.twentyfourmedia.syndication.service.FieldService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FieldServiceImplementation implements FieldService {

	@Autowired
	private FieldDao fieldDao;
	
	@Override
	public List<Field> getFieldsByBodyContaining(String token) {
		
		return fieldDao.getByBodyContaining(token);
	}
}
