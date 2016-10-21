/**
 * 
 */
package br.com.johanes.api.rest;

import java.util.ArrayList;
import java.util.List;

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
import br.com.johanes.api.dao.HolidayRepository;
import br.com.johanes.api.model.Consumer;
import br.com.johanes.api.model.Holiday;
import br.com.johanes.api.model.Subscription;

/**
 * 
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Oct 20, 2016
 * @version 1.0
 */
@RestController
@RequestMapping("/holiday")
public class HolidayController {

	private static final Logger log = LoggerFactory.getLogger(HolidayController.class);

	@Resource
	private ConsumerRepository consumerRepo;

	@Resource
	private HolidayRepository holidayRepository;

	@RequestMapping("/all")
	public ResponseEntity<List<Holiday>> getAll(@RequestHeader("token") String token) {
		
		Consumer consumer = consumerRepo.findByToken(token);
		
		if(consumer.getSubscription().equals(Subscription.JEDI)){
			return new ResponseEntity<List<Holiday>>(holidayRepository.findAll(), HttpStatus.OK);
		} else {
			log.info("Sorry bro, you are not a JEDI");
			return new ResponseEntity<List<Holiday>>(new ArrayList<>(), HttpStatus.FORBIDDEN);	
		}
	}
	
	@RequestMapping("/month/{month}")
	public ResponseEntity<List<Holiday>> getByMonth(@RequestHeader("token") String token, @PathVariable("month") Integer month) {
		
		Consumer consumer = consumerRepo.findByToken(token);
		
		if(consumer.hasCredit()){
			consumer.setRequestCount(consumer.getRequestCount()+1);
			consumerRepo.save(consumer);
			
			return new ResponseEntity<List<Holiday>>(holidayRepository.findByMonth(month), HttpStatus.OK);
		} else {
			log.info(consumer.getEmail() +" dont have enough credit.");
			return new ResponseEntity<List<Holiday>>(new ArrayList<>(), HttpStatus.FORBIDDEN);	
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> addHoliday(@RequestHeader("token") String token, @RequestBody Holiday holiday) {

		holidayRepository.save(holiday);
		log.info("New holiday added: "+holiday.getName()+".");
		return new ResponseEntity<String>(holiday.getId(), HttpStatus.OK);
	}

}
