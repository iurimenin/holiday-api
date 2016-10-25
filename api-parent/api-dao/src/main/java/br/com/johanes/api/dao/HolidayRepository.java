/**
 * 
 */
package br.com.johanes.api.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.johanes.api.model.Holiday;

/**
 * 
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Oct 20, 2016
 * @version 1.0
 */
public interface HolidayRepository extends MongoRepository<Holiday, String> {
	
	List<Holiday> findByMonth(Integer month);
	List<Holiday> findByDayAndMonth(Integer day, Integer month);

}
