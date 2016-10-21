/**
 * 
 */
package br.com.johanes.api.model;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * @author <a href="mailto:johanes.ferreira@gmail.com">Johanes Ferreira</a>
 * @since Oct 20, 2016
 * @version 1.0
 */
@Data
public class Consumer {

	@Id
	private String id;
	private String name;
	private String email;
	private String token;
	private Subscription subscription;
	private Integer requestCount;

	public boolean hasCredit() {

		switch (this.subscription) {
			case COPPER:
				return requestCount < 5 ? true : false;
			case SILVER:
				return requestCount < 8 ? true : false;
			case GOLD:
				return requestCount < 20 ? true : false;
			case PLATINUM:
			case JEDI:
				return true;
			default:
				return false;
		}
	}
}
