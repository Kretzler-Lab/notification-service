package org.kpmp.libra.notifications;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/v1/package/notifications", method = RequestMethod.POST)
	public @ResponseBody Boolean notifyNewPackage(String packageId, String packageType, Date dateSubmitted,
			String submitterName) {
		log.info("URI: {} | MSG: {}", "/v1/package/notifications", "Adding notification for PKGID: " + packageId);
		return false;
	}

}
