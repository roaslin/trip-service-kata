package org.craftedsw.tripservicekata.trip;


import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TripServiceTest {


    private static final User NOT_LOGGED_IN_USER = null;
    private static final User LOGGED_IN_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TRIP_TO_BARCELONA = new Trip();
    private static final Trip TRIP_TO_SEVILLE = new Trip();
    private User loggedInUser;
    private TripService tripService;

    @BeforeEach
    void setUp() {
        loggedInUser = LOGGED_IN_USER;
        tripService = new TestableTripService();
    }

    @Test
    public void should_throw_user_not_logged_in_exception() {
        User user = new User();
        loggedInUser = NOT_LOGGED_IN_USER;

        assertThrows(UserNotLoggedInException.class, () -> {
            tripService.getTripsByUser(user, loggedInUser);
        });
    }

    @Test
    public void should_return_no_trips_when_logged_in_user_is_no_friend() {
        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TRIP_TO_BARCELONA, TRIP_TO_SEVILLE)
                .build();

        assertThat(tripService.getTripsByUser(friend, loggedInUser).size(), CoreMatchers.is(0));
    }

    @Test
    public void should_return_trips_when_logged_in_user_is_friend() {
        User friend = aUser()
                .friendsWith(loggedInUser, ANOTHER_USER)
                .withTrips(TRIP_TO_BARCELONA, TRIP_TO_SEVILLE)
                .build();

        assertThat(tripService.getTripsByUser(friend, loggedInUser).size(), CoreMatchers.is(2));
    }

    private class TestableTripService extends TripService {

        @Override
        protected List<Trip> findTripsBy(User user) {
            return user.trips();
        }
    }
}
