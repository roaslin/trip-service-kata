package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {
    private static final User A_USER = new User();
    private static final User NOT_LOGGED_IN_USER = null;
    private static final User LOGGED_IN_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TRIP_TO_BARCELONA = new Trip();
    private static final Trip TRIP_TO_SEVILLE = new Trip();
    private TripService realTripService;

    @Mock
    private TripDAO tripDAO;

    @Before
    public void setUp() {
        realTripService = new TripService(tripDAO);
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_user_not_logged_in_exception() {

        realTripService.getTripsByUser(A_USER, NOT_LOGGED_IN_USER);
    }

    @Test
    public void should_return_no_trips_when_logged_in_user_is_no_friend() {
        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TRIP_TO_BARCELONA, TRIP_TO_SEVILLE)
                .build();

        assertThat(realTripService.getTripsByUser(friend, LOGGED_IN_USER).size(), CoreMatchers.is(0));
    }

    @Test
    public void should_return_trips_when_logged_in_user_is_friend() {
        User friend = aUser()
                .friendsWith(LOGGED_IN_USER, ANOTHER_USER)
                .withTrips(TRIP_TO_BARCELONA, TRIP_TO_SEVILLE)
                .build();

        given(tripDAO.findTripsBy(friend)).willReturn(Arrays.asList(TRIP_TO_BARCELONA, TRIP_TO_SEVILLE));

        assertThat(realTripService.getTripsByUser(friend, LOGGED_IN_USER).size(), CoreMatchers.is(2));
    }
}
