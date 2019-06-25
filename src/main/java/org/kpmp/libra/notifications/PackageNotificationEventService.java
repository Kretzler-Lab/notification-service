package org.kpmp.libra.notifications;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageNotificationEventService {

	private PackageNotificationEventRepository eventRepo;
	private EmailSender emailer;
	private String[] toAddresses = { "rlreamy@umich.edu" };

	@Autowired
	public PackageNotificationEventService(PackageNotificationEventRepository eventRepo, EmailSender emailer) {
		this.eventRepo = eventRepo;
		this.emailer = emailer;
	}

	public PackageNotificationEvent saveNotifyEvent(String packageId, String packageType, Date datePackageSubmitted,
			String submitterName) {

		PackageNotificationEvent event = new PackageNotificationEvent(packageId, packageType, datePackageSubmitted,
				submitterName);
		event = eventRepo.save(event);

		return event;
	}

	public boolean sendNotifyEmail(PackageNotificationEvent packageEvent) {

		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		StringBuffer body = new StringBuffer();
		body.append("Hey ho curator!\n\n");
		body.append(
				"A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n");
		body.append("packageId: " + packageEvent.getPackageId() + "\n");
		body.append("package type: " + packageEvent.getPackageType() + "\n");
		body.append("date submitted: " + formatter.format(packageEvent.getDatePackageSubmitted()) + "\n");
		body.append("submitted by: " + packageEvent.getSubmitter() + "\n");
		body.append("Link to data lake uploader: http://uploader.kpmp.org\n");
		body.append("\n\nThanks!\nYour friendly notification service.");

		return emailer.sendEmail("New package for your review", body.toString(), toAddresses);
	}

}
