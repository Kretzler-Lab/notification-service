package org.kpmp.eridanus.notifications;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PackageNotificationEventService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private EmailSender emailer;
	@Value("#{'${notifications.mail.to}'.split(',')}")
	private List<String> toAddresses;
	@Value("${package.state.upload.succeeded}")
	private String uploadSuccess;
	@Value("${package.state.upload.failed}")
	private String uploadFail;

	@Autowired
	public PackageNotificationEventService(EmailSender emailer) {
		this.emailer = emailer;
	}

	public boolean sendNotifyEmail(StateChangeEvent event) {
		// 1. get the package from mongo
		// 2. call the appropriate method to send the email

//		String packageState = event.getState();
//		if (packageState.equalsIgnoreCase(uploadSuccess)) {
//			return sendSuccessEmail();
//		} else if (packageState.equalsIgnoreCase(uploadFail)) {
//			return sendFailureEmail();
//		}
//		log.info("URI: PackageNotificationEventService.sendNotifyEmail | PKGID: {} | MSG: {}", event.getPackageId(),
//				"No notifications defined for this state: " + packageState);
		return false;
	}

	@Deprecated
	public boolean sendNotifyEmail(PackageNotificationEvent packageEvent) {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		StringBuffer body = new StringBuffer();
		body.append("Hey ho curator!\n\n");
		body.append(
				"A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n\n");
		body.append("PACKAGE ID: " + packageEvent.getPackageId() + "\n\n");
		body.append("PACKAGE TYPE: " + packageEvent.getPackageType() + "\n\n");
		body.append("SPECIMEN ID: " + packageEvent.getSpecimenId() + "\n\n");
		body.append("DATE SUBMITTED: " + formatter.format(packageEvent.getDatePackageSubmitted()) + "\n\n");
		body.append("SUBMITTED BY: " + packageEvent.getSubmitter() + "\n\n");
		body.append("Link to data lake uploader: http://" + packageEvent.getOrigin() + "\n");
		body.append("\n\nThanks!\nYour friendly notification service.");

		return emailer.sendEmail("New package for your review from " + packageEvent.getOrigin(), body.toString(),
				toAddresses);
	}

	private boolean sendFailureEmail(PackageNotificationEvent packageEvent) {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		StringBuffer body = new StringBuffer();
		body.append("Hey ho curator!\n\n");
		body.append("A new package has failed uploading.  You might wanna take a look. Here's some info about it:\n\n");
		body.append("PACKAGE ID: " + packageEvent.getPackageId() + "\n\n");
		body.append("PACKAGE TYPE: " + packageEvent.getPackageType() + "\n\n");
		body.append("SPECIMEN ID: " + packageEvent.getSpecimenId() + "\n\n");
		body.append("DATE SUBMITTED: " + formatter.format(packageEvent.getDatePackageSubmitted()) + "\n\n");
		body.append("SUBMITTED BY: " + packageEvent.getSubmitter() + "\n\n");
		body.append("Link to data lake uploader: http://" + packageEvent.getOrigin() + "\n");
		body.append("\n\nThanks!\nYour friendly notification service.");

		return emailer.sendEmail("FAILED package for your review from " + packageEvent.getOrigin(), body.toString(),
				toAddresses);
	}

}
