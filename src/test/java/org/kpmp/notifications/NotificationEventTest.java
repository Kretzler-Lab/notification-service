package org.kpmp.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NotificationEventTest {
    private NotificationEvent event;

    @Before
    public void setUp() throws Exception {
        event = new NotificationEvent("id1", "origin");
    }

    @After
    public void tearDown() throws Exception {
        event = null;
    }

    @Test
    public void testSetUserId() {
        event.setUserId("id1");
        assertEquals("id1", event.getUserId());
    }

    @Test
    public void testSetOrigin() {
        event.setOrigin("origin");
        assertEquals("origin", event.getOrigin());
    }

    @Test
    public void testConstructor() throws Exception{
        event = new NotificationEvent("id1", "origin");
        assertEquals("id1", event.getUserId());
        assertEquals("origin", event.getOrigin());
    }
}
