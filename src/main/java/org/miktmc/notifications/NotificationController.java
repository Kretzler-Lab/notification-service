package org.miktmc.notifications;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class NotificationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private PackageNotificationEventService packageEventService;
    private UserNotificationEventService userNotificationEventService;

	@Autowired
	public NotificationController(PackageNotificationEventService packageEventService, UserNotificationEventService userNotificationEventService) {
		this.packageEventService = packageEventService;
        this.userNotificationEventService = userNotificationEventService;
	}

	@RequestMapping(value = "/v2/notifications/package", method = RequestMethod.POST)
	public @ResponseBody Boolean notify(@RequestBody StateChangeEvent event, HttpServletRequest request) {

		log.info("URI: {} | MSG: {}", request.getRequestURI(),
				"Sending notification for PKGID: " + event.getPackageId());

		try {
			packageEventService.sendNotifyEmail(event);
		} catch (MessagingException | IOException e) {
			log.error(e.getMessage());
			return false;
		}

		return true;
	}

    @RequestMapping(value = "/v2/notifications/user", method = RequestMethod.POST)
	public @ResponseBody Boolean sendFailureEmail(@RequestBody NotificationEvent event, HttpServletRequest request) {

		log.info("URI: {} | MSG: {}", request.getRequestURI(),
				"Sending notification for User: " + event.getUserId());

		try {
			userNotificationEventService.sendFailureEmail(event);
		} catch (MessagingException e) {
			log.error(e.getMessage());
			return false;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return true;
	}

}
