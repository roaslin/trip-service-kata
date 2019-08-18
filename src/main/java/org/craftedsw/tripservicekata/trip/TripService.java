package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.List;

import static java.util.Collections.emptyList;

public class TripService {

	private TripDAO tripDAO;

	public TripService(TripDAO tripDAO) {

		this.tripDAO = tripDAO;
	}

	public List<Trip> getTripsByUser(User user, User loggedInUser) throws UserNotLoggedInException {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendWith(loggedInUser) ? findTripsBy(user)
                : noTrips();
    }

	private List<Trip> noTrips() {
		return emptyList();
	}

	protected List<Trip> findTripsBy(User user) {
        return tripDAO
				.findTripsBy(user);
    }
}
