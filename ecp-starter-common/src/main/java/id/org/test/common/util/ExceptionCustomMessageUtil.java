package id.org.test.common.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;


public class ExceptionCustomMessageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionCustomMessageUtil.class);
	
    private final MessageSource messageSource;
    private static String eMessage;

    public ExceptionCustomMessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String replaceMessage(Exception e) {
    	return replaceMessage(e, LocaleContextHolder.getLocale());
    }
    
    @Deprecated
    public String replaceMessage(Exception e, Locale locale) {
        if (e instanceof DataIntegrityViolationException) {
            logger.info("---------------------------------------------------------------------------------------JDBC Error Exception Tracker-----------------------------------------------------------");
            logger.info("Message : " + e.getMessage());
            logger.info("Caused : " + e.getCause());
            logger.info("Root Cause : " + ((DataIntegrityViolationException) e).getRootCause());
            logger.info("Localized Message : " + e.getLocalizedMessage());
            logger.info("ToString : " + e.toString());
            logger.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            if (((DataIntegrityViolationException) e).getRootCause().toString().contains("Duplicate entry")) {
                eMessage = messageSource.getMessage("jdbc.exception.duplicate-entry", new Object[]{}, locale);
            }else  if (((DataIntegrityViolationException) e).getRootCause().toString().contains("cannot be null")) {
                eMessage = messageSource.getMessage("jdbc.exception.field-key-cannot-be-null", new Object[]{}, locale);
            }
            return eMessage;
        } else {
            return e.getMessage();
        }
    }
}
