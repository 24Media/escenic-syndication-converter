package gr.twentyfourmedia.syndication.service;

import gr.twentyfourmedia.syndication.model.Field;

import java.util.List;

public interface FieldService {

	List<Field> getFieldsByBodyContaining(String token);
}
