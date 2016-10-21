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
public class Holiday {

	@Id
	private String id;
	private String name;	
	private Integer day;
	private Integer month;
}
