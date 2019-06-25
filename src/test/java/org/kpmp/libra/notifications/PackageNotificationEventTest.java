package org.kpmp.libra.notifications;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PackageNotificationEventTest {

	private PackageNotificationEvent event;

	@Before
	public void setUp() throws Exception {
		event = new PackageNotificationEvent();
	}

	@After
	public void tearDown() throws Exception {
		event = null;
	}

	@Test
	public void testSetPackageId() {
		event.setPackageId("packageId");
		assertEquals("packageId", event.getPackageId());
	}

	@Test
	public void testSetPackageType() {
		event.setPackageType("packageType");
		assertEquals("packageType", event.getPackageType());
	}

	@Test
	public void testSetDatePackageSubmitted() {
		Date dateSubmitted = new Date();
		event.setDatePackageSubmitted(dateSubmitted);

		assertEquals(dateSubmitted, event.getDatePackageSubmitted());
	}

	@Test
	public void testSetSubmitter() {
		event.setSubmitter("submitter name");
		assertEquals("submitter name", event.getSubmitter());
	}

	@Test
	public void testSetId() throws Exception {
		event.setId("id");
		assertEquals("id", event.getId());
	}

	@Test
	public void testDateEventSubmitted() throws Exception {
		Date dateSubmitted = new Date();
		event.setDateEventSubmitted(dateSubmitted);

		assertEquals(dateSubmitted, event.getDateEventSubmitted());
	}

	@Test
	public void testConstructor() throws Exception {
		Date dateSubmitted = new Date();
		event = new PackageNotificationEvent("packageId", "packageType", dateSubmitted, "submitter");

		assertEquals("packageId", event.getPackageId());
		assertEquals("packageType", event.getPackageType());
		assertEquals(dateSubmitted, event.getDatePackageSubmitted());
		assertEquals("submitter", event.getSubmitter());
		Date eventSubmitDate = event.getDateEventSubmitted();
		Calendar eventDate = Calendar.getInstance();
		eventDate.setTime(eventSubmitDate);
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		assertEquals(true, isSameDay(eventDate, today));
	}

	private static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

}
