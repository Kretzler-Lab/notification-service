package org.kpmp.libra.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationControllerTest {

	@Mock
	private PackageNotificationEventService packageEventService;
	private NotificationController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new NotificationController(packageEventService);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
	}

	@Test
	public void testNotifyNewPackage() {
		Date datePackageSubmitted = new Date();
		PackageNotificationEvent expectedEvent = mock(PackageNotificationEvent.class);
		when(packageEventService.saveNotifyEvent("packageId", "packageType", datePackageSubmitted, "submitterName"))
				.thenReturn(expectedEvent);
		when(packageEventService.sendNotifyEmail(expectedEvent)).thenReturn(true);

		Boolean success = controller.notifyNewPackage("packageId", "packageType", datePackageSubmitted,
				"submitterName");

		verify(packageEventService).saveNotifyEvent("packageId", "packageType", datePackageSubmitted, "submitterName");
		verify(packageEventService).sendNotifyEmail(expectedEvent);
		assertEquals(true, success);
	}

}
