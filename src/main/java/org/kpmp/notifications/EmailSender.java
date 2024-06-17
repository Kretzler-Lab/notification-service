package org.kpmp.notifications;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Value("${notifications.mail.from}")
	private String fromAddress;
	@Value("${mail.host}")
	private String host;

	private LoadingCache<NotificationMessage, String> cache;

	public EmailSender() {
		CacheLoader<NotificationMessage, String> loader = new CacheLoader<NotificationMessage, String>() {
			@Override
			public String load(NotificationMessage notification) throws Exception {
				Transport.send(notification.getMessage());
				return notification.getBody();
			}

		};

		cache = CacheBuilder.newBuilder()
				.expireAfterAccess(20, TimeUnit.MINUTES)
				.build(loader);
	}

	public void sendEmail(String subject, String body, List<String> toAddresses) throws MessagingException, IOException {

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(fromAddress);
		if (toAddresses.size() > 0) {
			for (String to : toAddresses) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
		} else {
			throw new MessagingException("No To address provided. Unable to send message.");
		}
		message.setSubject(subject);
		message.setText(body);

		NotificationMessage notificationMessage = new NotificationMessage(message);
		cache.getUnchecked(notificationMessage);
	}
}
