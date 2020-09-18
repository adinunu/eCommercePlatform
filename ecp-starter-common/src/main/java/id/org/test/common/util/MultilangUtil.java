package id.org.test.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MultilangUtil {
	
	private MessageSource messageSource;
	
	public MultilangUtil(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String get(String code) {
		return messageSource.getMessage(code, null, createDefaultMessage(code), LocaleContextHolder.getLocale());
	}
	
	public String get(String code, Object[] param) {
		return messageSource.getMessage(code, param, createDefaultMessage(code), LocaleContextHolder.getLocale());
	}
	
	private String createDefaultMessage(String code) {
		StringBuilder dmsg = new StringBuilder();
		dmsg.append("*missing*[").append(code).append("]");
		return dmsg.toString();
	}

}
