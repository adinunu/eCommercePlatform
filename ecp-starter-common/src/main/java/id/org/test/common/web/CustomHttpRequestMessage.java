package id.org.test.common.web;

public class CustomHttpRequestMessage {

    private String messageTitle;
    private String messageBody;
    private String messageFooter;

    public CustomHttpRequestMessage() {
    }

    public CustomHttpRequestMessage(String messageTitle, String messageBody, String messageFooter) {
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.messageFooter = messageFooter;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageFooter() {
        return messageFooter;
    }

    public void setMessageFooter(String messageFooter) {
        this.messageFooter = messageFooter;
    }

    @Override
    public String toString() {
        return "CustomHttpRequestMessage{" +
                "messageTitle='" + messageTitle + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", messageFooter='" + messageFooter + '\'' +
                '}';
    }
}
