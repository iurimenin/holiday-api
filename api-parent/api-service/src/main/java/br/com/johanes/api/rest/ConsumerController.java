/**
 * 
 */
package br.com.johanes.api.rest;

import java.util.UUID;

import javax.annotation.Resource;

import br.com.johanes.api.utils.ApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.johanes.api.dao.ConsumerRepository;
import br.com.johanes.api.model.Consumer;

/**
 * 
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Jan 23, 2016
 * @version 1.0
 */
@RequestMapping("/consumer")
@RestController
public class ConsumerController {

	private static final Logger log = LoggerFactory.getLogger(ConsumerController.class);

	@Resource
	private ConsumerRepository consumerRepo;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addConsumer(@RequestBody Consumer consumer) {

		consumer.setToken(UUID.randomUUID().toString());
		consumer.setRequestCount(0);
		consumerRepo.save(consumer);

		log.info("New Consumer added: " + consumer.getName() + " - id: " + consumer.getId());

		return new ResponseEntity<>(consumer.getToken(), HttpStatus.OK);
	}

	@RequestMapping("/all")
	public ResponseEntity<?> getAll(@RequestHeader("token") String token) {

		Consumer consumer = consumerRepo.findByToken(token);

		if (ApiHelper.isJedi(consumer)) {
			return new ResponseEntity<>(consumerRepo.findAll(), HttpStatus.OK);
		} else {
			String msg = "Sorry bro, you are not a JEDI";
			log.info(msg);
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteConsumer(@RequestHeader("token") String token, @PathVariable String id) {

		Consumer consumer = consumerRepo.findByToken(token);
		Consumer toDelete = consumerRepo.findOne(id);
		
		if (ApiHelper.isJedi(consumer)) {
			
			if(toDelete != null){
				consumerRepo.delete(toDelete);
				return new ResponseEntity<>("User: "+id+" has been removed.", HttpStatus.OK);
			} else {
				String msg = "Yo no lo conozco señor: " + id;
				log.info(msg);
				return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
			}
		} else {
			String msg = "Sorry bro, you are not a JEDI";
			log.info(msg);
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		}

	}
}
