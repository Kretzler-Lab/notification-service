package org.kpmp.libra.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PackageNotificationEventServiceTest {

	@Mock
	private PackageNotificationEventRepository eventRepo;
	@Mock
	private EmailSender emailer;
	private PackageNotificationEventService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new PackageNotificationEventService(eventRepo, emailer);
	}

	@After
	public void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testSaveNotifyEvent() {
		Date datePackageSubmitted = new Date();
		PackageNotificationEvent expectedEventResult = mock(PackageNotificationEvent.class);
		when(eventRepo.save(any(PackageNotificationEvent.class))).thenReturn(expectedEventResult);

		PackageNotificationEvent savedEvent = service.saveNotifyEvent("packageId", "packageType", datePackageSubmitted,
				"submitterName");

		ArgumentCaptor<PackageNotificationEvent> eventCaptor = ArgumentCaptor.forClass(PackageNotificationEvent.class);
		verify(eventRepo).save(eventCaptor.capture());
		assertEquals("packageId", eventCaptor.getValue().getPackageId());
		assertEquals("packageType", eventCaptor.getValue().getPackageType());
		assertEquals(datePackageSubmitted, eventCaptor.getValue().getDatePackageSubmitted());
		assertEquals("submitterName", eventCaptor.getValue().getSubmitter());
		assertEquals(expectedEventResult, savedEvent);
	}

	@Test
	public void testSendNotifyEmail() throws Exception {
		PackageNotificationEvent packageEvent = new PackageNotificationEvent();
		Date dateSubmitted = new Date();
		packageEvent.setDatePackageSubmitted(dateSubmitted);
		packageEvent.setPackageId("packageId");
		packageEvent.setPackageType("package type");
		packageEvent.setSubmitter("submitter name");
		when(emailer.sendEmail(any(String.class), any(String.class), any(String[].class))).thenReturn(true);

		boolean result = service.sendNotifyEmail(packageEvent);

		assertEquals(true, result);
		ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String[]> toAddressesCaptor = ArgumentCaptor.forClass(String[].class);
		verify(emailer).sendEmail(subjectCaptor.capture(), bodyCaptor.capture(), toAddressesCaptor.capture());
		assertEquals("New package for your review", subjectCaptor.getValue());
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String date = formatter.format(dateSubmitted);
		assertEquals("Hey ho curator!\n" + "\n"
				+ "A new package has been uploaded to the data lake.  You might wanna take a look. Here's some info about it:\n"
				+ "packageId: packageId\n" + "package type: package type\n" + "date submitted: " + date + "\n"
				+ "submitted by: submitter name\n" + "Link to data lake uploader: http://uploader.kpmp.org\n" + "\n"
				+ "\n" + "Thanks!\n" + "Your friendly notification service.", bodyCaptor.getValue());
		assertEquals(true, Arrays.equals(new String[] { "rlreamy@umich.edu" }, (toAddressesCaptor.getValue())));
	}

}
