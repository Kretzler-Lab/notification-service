package org.kpmp.libra.notifications;

import static org.junit.Assert.assertEquals;

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
	public void testSetDateSubmitted() {
		Date dateSubmitted = new Date();
		event.setDateSubmitted(dateSubmitted);

		assertEquals(dateSubmitted, event.getDateSubmitted());
	}

	@Test
	public void testSetSubmitter() {
		event.setSubmitter("submitter name");
		assertEquals("submitter name", event.getSubmitter());
	}

	@Test
	public void testConstructor() throws Exception {
		Date dateSubmitted = new Date();
		event = new PackageNotificationEvent("packageId", "packageType", dateSubmitted, "submitter");

		assertEquals("packageId", event.getPackageId());
		assertEquals("packageType", event.getPackageType());
		assertEquals(dateSubmitted, event.getDateSubmitted());
		assertEquals("submitter", event.getSubmitter());
	}

}
