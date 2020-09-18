package id.org.test.data.service.wrapper;

import id.org.test.data.model.Member;
import lombok.Data;

@Data
public class RegistrationAccountWrapper {
	
    private String mailRegistrant;
    private String activationCode;
    private String birthDate;
    private Member account;

}
