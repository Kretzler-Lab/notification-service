package org.kpmp.libra.notifications;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pacakageNotificationEvents")
public class PackageNotificationEvent {

	@Id
	private String id;
	private String packageId;
	private String packageType;
	private Date dateSubmitted;
	private String submitter;

	public PackageNotificationEvent(String packageId, String packageType, Date dateSubmitted, String submitter) {
		this.packageId = packageId;
		this.packageType = packageType;
		this.dateSubmitted = dateSubmitted;
		this.submitter = submitter;
	}

	public PackageNotificationEvent() {
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
