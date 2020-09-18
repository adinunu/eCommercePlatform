package id.org.test.ms.shared.mobile;

import java.io.Serializable;

import lombok.Data;

@Data
public class PublicRegistrationSDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 3002596360648486274L;
	
	private String businessSubDomain;
	private Boolean activated;
}
