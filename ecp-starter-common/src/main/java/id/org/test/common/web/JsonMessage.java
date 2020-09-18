package id.org.test.common.web;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonMessage implements Serializable {
	
    private static final long serialVersionUID = -1956773799183380271L;

    public static final String MSG_TITLE_INFO = "Information";
    public static final String MSG_TITLE_ERROR = "Error";
    public static final String MSG_TITLE_CONFIRMATION = "Confirmation";
    public static final String MSG_TITLE_WARNING = "Warning";
    
    private String messageTitle;
    private String messageDetail;
    private String messageBody;
    private Object object;

    public JsonMessage() {}

    public JsonMessage(String messageTitle, String messageBody) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
    }

    public JsonMessage(String messageTitle, String messageBody, String messageDetail) {
        this.messageTitle = messageTitle;
        this.messageDetail = messageDetail;
        this.messageBody = messageBody;
    }

    public JsonMessage(String messageTitle, String messageBody, Object object) {
        this.messageTitle = messageTitle;
        this.object = object;
        this.messageBody = messageBody;
    }

    public static JsonMessage showInfoMessage(String messageBody) {
        return new JsonMessage(MSG_TITLE_INFO, messageBody);
    }

    public static JsonMessage showInfoMessage(String messageBody, Object object) {
        return new JsonMessage(MSG_TITLE_INFO, messageBody, object);
    }

    public static JsonMessage showErrorMessage(String messageBody) {
        return new JsonMessage(MSG_TITLE_ERROR, messageBody);
    }
    
    public static JsonMessage showErrorMessage(String messageBody, String messageDetail) {
        return new JsonMessage(MSG_TITLE_ERROR, messageBody, messageDetail);
    }

    public static JsonMessage showConfirmMessage(String messageBody) {
        return new JsonMessage(MSG_TITLE_CONFIRMATION, messageBody);
    }
    
    public static JsonMessage showConfirmMessage(String messageBody, String messageDetail) {
        return new JsonMessage(MSG_TITLE_CONFIRMATION, messageBody, messageDetail);
    }
    
    public static JsonMessage showWarningMessage(String messageBody) {
        return new JsonMessage(MSG_TITLE_WARNING, messageBody);
    }
    
    public static JsonMessage showWarningMessage(String messageBody, String messageDetail) {
        return new JsonMessage(MSG_TITLE_WARNING, messageBody, messageDetail);
    }
}
