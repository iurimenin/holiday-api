/**
 * 
 */
package br.com.johanes.api.rest;

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
import br.com.johanes.api.dao.HolidayRepository;
import br.com.johanes.api.model.Consumer;
import br.com.johanes.api.model.Holiday;

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
	public ResponseEntity<?> getAll(@RequestHeader("token") String token) {

        Consumer consumer = consumerRepo.findByToken(token);

        if(ApiHelper.isJedi(consumer)){
            return new ResponseEntity<>(holidayRepository.findAll(), HttpStatus.OK);
        } else {
            String msg = "Only a JEDI can list all holidays";
            log.info(msg);
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping("/month/{month}")
	public ResponseEntity<?> getByMonth(@RequestHeader("token") String token, @PathVariable("month") Integer month) {
		
		Consumer consumer = consumerRepo.findByToken(token);
		
		if(consumer.hasCredit()){
			consumer.setRequestCount(consumer.getRequestCount()+1);
			consumerRepo.save(consumer);
			
			return new ResponseEntity<>(holidayRepository.findByMonth(month), HttpStatus.OK);
		} else {
            String msg = consumer.getEmail() + " don't have enough credit.";
            log.info(msg);
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addHoliday(@RequestHeader("token") String token, @RequestBody Holiday holiday) {

        Consumer consumer = consumerRepo.findByToken(token);

		if(ApiHelper.isJedi(consumer)){
			holidayRepository.save(holiday);
            String msg = "New holiday added: " + holiday.getName() + ".";
            log.info(msg);
			return new ResponseEntity<>(msg, HttpStatus.OK);
		} else {
			String msg = "Only a JEDI can add holidays";
			log.info(msg);
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		}
	}

}
