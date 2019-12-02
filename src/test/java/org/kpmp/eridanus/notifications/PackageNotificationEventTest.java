package org.kpmp.eridanus.notifications;

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
	public void testSetDateEventSubmitted() throws Exception {
		Date dateSubmitted = new Date();
		event.setDateEventSubmitted(dateSubmitted);

		assertEquals(dateSubmitted, event.getDateEventSubmitted());
	}

	@Test
	public void testSetSpecimenId() throws Exception {
		event.setSpecimenId("2235-it");

		assertEquals("2235-it", event.getSpecimenId());
	}

	@Test
	public void testSetOrigin() throws Exception {
		event.setOrigin("origin");
		assertEquals("origin", event.getOrigin());
	}

	@Test
	public void testSetPackageState() throws Exception {
		event.setPackageState("packageState");
		assertEquals("packageState", event.getPackageState());
	}

}
