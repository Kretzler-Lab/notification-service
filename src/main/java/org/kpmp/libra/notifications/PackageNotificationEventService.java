package org.kpmp.libra.notifications;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageNotificationEventService {

	private PackageNotificationEventRepository eventRepo;

	@Autowired
	public PackageNotificationEventService(PackageNotificationEventRepository eventRepo) {
		this.eventRepo = eventRepo;
	}

	public PackageNotificationEvent saveNotifyEvent(String packageId, String packageType, Date datePackageSubmitted,
			String submitterName) {

		PackageNotificationEvent event = new PackageNotificationEvent(packageId, packageType, datePackageSubmitted,
				submitterName);
		event = eventRepo.save(event);

		return event;
	}

}
