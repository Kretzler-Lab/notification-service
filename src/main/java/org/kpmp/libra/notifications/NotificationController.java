package org.kpmp.libra.notifications;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private PackageNotificationEventService packageEventService;

	@Autowired
	public NotificationController(PackageNotificationEventService packageEventService) {
		this.packageEventService = packageEventService;
	}

	@RequestMapping(value = "/v1/package/notifications", method = RequestMethod.POST)
	public @ResponseBody PackageNotificationEvent notifyNewPackage(String packageId, String packageType,
			Date datePackageSubmitted, String submitterName) {
		log.info("URI: {} | MSG: {}", "/v1/package/notifications", "Adding notification for PKGID: " + packageId);

		PackageNotificationEvent event = packageEventService.saveNotifyEvent(packageId, packageType,
				datePackageSubmitted, submitterName);

		return event;
	}

}
