package org.kpmp.notifications;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;

public class NotificationMessage {

    private String subject;
    private String body;
    private MimeMessage message;

    public NotificationMessage(MimeMessage message) throws MessagingException, IOException {
        this.message = message;
        this.subject = message.getSubject();
        this.body = (String) message.getContent();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MimeMessage getMessage() {
        return message;
    }

    public void setMessage(MimeMessage message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationMessage that = (NotificationMessage) o;
        return subject.equals(that.subject) && body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body);
    }
}
