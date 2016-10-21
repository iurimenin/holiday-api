package br.com.johanes.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Oct 20, 2016
 * @version 1.0
 */
@RestController
@RequestMapping("/status")
public class StatusController {

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", defaultValue="Stranger") String name) {
    	return "Hey "+name+", our Service is up and running!";
    }
}
