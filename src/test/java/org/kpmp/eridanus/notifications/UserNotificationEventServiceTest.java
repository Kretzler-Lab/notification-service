package org.kpmp.eridanus.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class UserNotificationEventServiceTest {

    @Mock
    private EmailSender emailer;
    private UserNotificationEventService service;
    @Mock
    private User user;
    

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        service = new UserNotificationEventService(emailer);
        ReflectionTestUtils.setField(service, "toAddresses", Arrays.asList("cregern@umich.edu"));
    }

    @After
    public void tearDown() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        service = null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testSendFailureEmail() throws Exception {

        service.sendFailureEmail("id1", "upload.miktmc.org");

        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<List> toAddressesCaptor = ArgumentCaptor.forClass(List.class);
		verify(emailer).sendEmail(subjectCaptor.capture(), bodyCaptor.capture(), toAddressesCaptor.capture());

        assertEquals("FAILED Login Attempt for your review from upload.miktmc.org", subjectCaptor.getValue());
        assertEquals("Hey ho curator!\n\n"
            + "An unauthorized user has tried to login. You might want to take a look. Here's some info about them:\n\n"
            + "USER: id1\n\n"
            + "DATE OF ATTEMPTED LOGIN: " + java.time.LocalDate.now() 
            + "\n\nThanks!\nYour friendly notification service", bodyCaptor.getValue());
        
        List<String> toAddresses = toAddressesCaptor.getValue();
		assertEquals(1, toAddresses.size());
		assertEquals("cregern@umich.edu", toAddresses.get(0));
    }
}
