package org.miktmc.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class PackageNotificationEventServiceTest {

	@Mock
	private EmailSender emailer;
	private PackageNotificationEventService service;
	@Mock
	private PackageRepository packageRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		service = new PackageNotificationEventService(emailer, packageRepository);
		ReflectionTestUtils.setField(service, "toAddresses", Arrays.asList("rlreamy@umich.edu"));
		ReflectionTestUtils.setField(service, "uploadSuccess", "success");
		ReflectionTestUtils.setField(service, "uploadFail", "fail");
	}

	@After
	public void tearDown() throws Exception {
        MockitoAnnotations.openMocks(this).close();
		service = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSendNotifyEmail_successState() throws Exception {
		StateChangeEvent packageEvent = new StateChangeEvent();
		packageEvent.setOrigin("upload.miktmc.org");
		packageEvent.setState("success");
		packageEvent.setPackageId("packageId");

		Package packageInfo = new Package();
		when(packageRepository.findByPackageId("packageId")).thenReturn(packageInfo);
		Date dateSubmitted = new Date();
		packageInfo.setCreatedAt(dateSubmitted);
		packageInfo.setPackageId("packageId");
		packageInfo.setPackageType("package type");
        packageInfo.setStudyId("studyId");
		User submitter = new User();
		submitter.setFirstName("submitter");
		submitter.setLastName("name");
		packageInfo.setSubmitter(submitter);
		packageInfo.setSubjectId("specimenId");
        packageInfo.setStudy("study");
        packageInfo.setStudyId("studyId");

		service.sendNotifyEmail(packageEvent);

		ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> toAddressesCaptor = ArgumentCaptor.forClass(List.class);
		verify(emailer).sendEmail(subjectCaptor.capture(), bodyCaptor.capture(), toAddressesCaptor.capture());
		assertEquals("New package for your review from upload.miktmc.org", subjectCaptor.getValue());
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String date = formatter.format(dateSubmitted);
		assertEquals("Hey ho curator!\n" + "\n"
				+ "A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n\n"
				+ "PACKAGE ID: packageId\n\n" + "PACKAGE TYPE: package type\n\n"
				+ "STUDY ID: studyId\n\nDATE SUBMITTED: " + date + "\n\n" + "SUBMITTED BY: submitter name\n\n"
				+ "Link to data lake uploader: http://upload.miktmc.org/datalake/study/package_packageId\n" + "\n" + "\n" + "Thanks!\n"
				+ "Your friendly notification service.", bodyCaptor.getValue());
		List<String> toAddresses = toAddressesCaptor.getValue();
		assertEquals(1, toAddresses.size());
		assertEquals("rlreamy@umich.edu", toAddresses.get(0));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSendNotifyEmail_successStateWhenException() throws Exception {
		StateChangeEvent packageEvent = new StateChangeEvent();
		packageEvent.setOrigin("upload.miktmc.org");
		packageEvent.setState("success");
		packageEvent.setPackageId("packageId");

		Package packageInfo = new Package();
		when(packageRepository.findByPackageId("packageId")).thenReturn(packageInfo);
		Date dateSubmitted = new Date();
		packageInfo.setCreatedAt(dateSubmitted);
		packageInfo.setPackageId("packageId");
		packageInfo.setPackageType("package type");
		User submitter = new User();
		submitter.setFirstName("submitter");
		submitter.setLastName("name");
		packageInfo.setSubmitter(submitter);
		packageInfo.setSubjectId("specimenId");
        packageInfo.setStudy("study");
		doThrow(new MessagingException("BOOM")).when(emailer).sendEmail(any(String.class), any(String.class),
				any(List.class));

		try {
			service.sendNotifyEmail(packageEvent);
		} catch (MessagingException expected) {
			assertEquals("BOOM", expected.getMessage());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSendNotifyEmail_failState() throws Exception {
		StateChangeEvent packageEvent = new StateChangeEvent();
		packageEvent.setOrigin("upload.miktmc.org");
		packageEvent.setState("fail");
		packageEvent.setPackageId("packageId");
		packageEvent.setCodicil("could not do it");

		Package packageInfo = new Package();
		when(packageRepository.findByPackageId("packageId")).thenReturn(packageInfo);
		Date dateSubmitted = new Date();
		packageInfo.setCreatedAt(dateSubmitted);
		packageInfo.setPackageId("packageId");
		packageInfo.setPackageType("package type");
		User submitter = new User();
		submitter.setFirstName("submitter");
		submitter.setLastName("name");
		packageInfo.setSubmitter(submitter);
		packageInfo.setStudyId("studyId");
        packageInfo.setStudy("study");

		service.sendNotifyEmail(packageEvent);

		ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> toAddressesCaptor = ArgumentCaptor.forClass(List.class);
		verify(emailer).sendEmail(subjectCaptor.capture(), bodyCaptor.capture(), toAddressesCaptor.capture());
		assertEquals("FAILED package for your review from upload.miktmc.org", subjectCaptor.getValue());
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String date = formatter.format(dateSubmitted);
		assertEquals("Hey ho curator!\n" + "\n"
				+ "A new package has failed uploading.  You might wanna take a look. Here's some info about it:\n\n"
				+ "FAILURE REASON: could not do it\n\n" + "PACKAGE ID: packageId\n\n" + "PACKAGE TYPE: package type\n\n"
				+ "STUDY ID: studyId\n\n" + "DATE SUBMITTED: " + date + "\n\n"
				+ "SUBMITTED BY: submitter name\n\n" + "Link to data lake uploader: http://upload.miktmc.org/datalake/study/package_packageId\n" + "\n"
				+ "\n" + "Thanks!\n" + "Your friendly notification service.", bodyCaptor.getValue());
		List<String> toAddresses = toAddressesCaptor.getValue();
		assertEquals(1, toAddresses.size());
		assertEquals("rlreamy@umich.edu", toAddresses.get(0));
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testSendNotifyEmail_failStateWhenException() throws Exception {
		StateChangeEvent packageEvent = new StateChangeEvent();
		packageEvent.setOrigin("upload.miktmc.org");
		packageEvent.setState("fail");
		packageEvent.setPackageId("packageId");
		packageEvent.setCodicil("could not do it");

		Package packageInfo = new Package();
		when(packageRepository.findByPackageId("packageId")).thenReturn(packageInfo);
		Date dateSubmitted = new Date();
		packageInfo.setCreatedAt(dateSubmitted);
		packageInfo.setPackageId("packageId");
		packageInfo.setPackageType("package type");
		User submitter = new User();
		submitter.setFirstName("submitter");
		submitter.setLastName("name");
		packageInfo.setSubmitter(submitter);
		packageInfo.setSubjectId("specimenId");
        packageInfo.setStudy("study");
        packageInfo.setStudyId("studyid");
		doThrow(new MessagingException("BOOM")).when(emailer).sendEmail(any(String.class), any(String.class),
				any(List.class));

		try {
			service.sendNotifyEmail(packageEvent);
		} catch (MessagingException expected) {
			assertEquals("BOOM", expected.getMessage());
		}

	}

}
