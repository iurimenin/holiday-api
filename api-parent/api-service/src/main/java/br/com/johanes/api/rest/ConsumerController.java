/**
 * 
 */
package br.com.johanes.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

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
import br.com.johanes.api.model.Subscription;

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
	public ResponseEntity<String> addConsumer(@RequestBody Consumer consumer) {

		consumer.setToken(UUID.randomUUID().toString());
		consumer.setRequestCount(0);
		consumerRepo.save(consumer);

		log.info("New Consumer added: " + consumer.getName() + " - id: " + consumer.getId());

		return new ResponseEntity<String>(consumer.getToken(), HttpStatus.OK);
	}

	@RequestMapping("/all")
	public ResponseEntity<List<Consumer>> getAll(@RequestHeader("token") String token) {

		Consumer consumer = consumerRepo.findByToken(token);

		if (consumer != null && Subscription.JEDI.equals(consumer.getSubscription())) {
			return new ResponseEntity<List<Consumer>>(consumerRepo.findAll(), HttpStatus.OK);
		} else {
			log.info("Sorry bro, you are not a JEDI");
			return new ResponseEntity<List<Consumer>>(new ArrayList<>(), HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteConsumer(@RequestHeader("token") String token, @PathVariable String id) {

		Consumer consumer = consumerRepo.findByToken(token);
		Consumer toDelete = consumerRepo.findOne(id);
		
		if (consumer != null && Subscription.JEDI.equals(consumer.getSubscription())) {
			
			if(toDelete != null){
				consumerRepo.delete(toDelete);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			} else {
				log.info("Yo no lo conozco señor: "+id);
				return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
			}
		} else {
			log.info("Sorry bro, you are not a JEDI");
			return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
		}

	}
}
