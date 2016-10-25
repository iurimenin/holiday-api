/**
 * 
 */
package br.com.johanes.api.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.johanes.api.model.Consumer;

/**
 * 
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Oct 20, 2016
 * @version 1.0
 */
public interface ConsumerRepository extends MongoRepository<Consumer, String> {

	Consumer findByToken(String token);
}
