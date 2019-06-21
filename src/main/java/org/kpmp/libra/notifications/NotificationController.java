package org.kpmp.libra.notifications;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/v1/notifications/package/{packageId}/packageType/{packageType}/packageSubmitted/{datePackageSubmitted}/submitter/{submitter}", method = RequestMethod.POST)
	public @ResponseBody PackageNotificationEvent notifyNewPackage(@PathVariable String packageId,
			@PathVariable String packageType,
			@PathVariable("datePackageSubmitted") @DateTimeFormat(pattern = "yyyyMMdd") Date datePackageSubmitted,
			@PathVariable String submitter) {
		log.info("URI: {} | MSG: {}", "/v1/notifications", "Adding notification for PKGID: " + packageId);

		PackageNotificationEvent event = packageEventService.saveNotifyEvent(packageId, packageType,
				datePackageSubmitted, submitter);

		return event;
	}

}
