package org.kpmp.libra.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	private PackageNotificationEventService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new PackageNotificationEventService(eventRepo);
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

}
