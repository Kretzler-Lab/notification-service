package org.miktmc.notifications;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationEventService {
    private EmailSender emailSender;
    @Value("#{'${notifications.mail.to}'.split(',')}")
    private List<String> toAddresses;

    @Autowired
    public UserNotificationEventService(EmailSender emailer){
        this.emailSender = emailer;
    }

    public void sendFailureEmail(NotificationEvent event) throws MessagingException, IOException {

        StringBuffer body = new StringBuffer();
        body.append("Hey ho curator!\n\n");
        body.append("An unauthorized user has tried to login. You might want to take a look. Here's some info about them:\n\n");
        body.append("USER: " + event.getUserId() + "\n\n");
        body.append("DATE OF ATTEMPTED LOGIN: " + java.time.LocalDate.now());
        body.append("\n\nThanks!\nYour friendly notification service");

        emailSender.sendEmail("FAILED Login Attempt for your review", body.toString(), toAddresses);
    }
}
