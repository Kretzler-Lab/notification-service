package org.miktmc.notifications;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "packages")
public class Package {

	@Id
	private String packageId;
	private String packageType;
	private Date createdAt;
	private String subjectId;
    private String studyId;
    private String study;
	@DBRef(lazy = false)
	private User submitter;

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId){
        this.studyId = studyId;
    }

	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

    public String getStudy(){
        return study;
    }

    public void setStudy(String study){
        this.study = study;
    }
}
