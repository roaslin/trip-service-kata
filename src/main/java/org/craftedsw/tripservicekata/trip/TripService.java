package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.List;

import static java.util.Collections.emptyList;

public class TripService {

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        if (loggedUser() == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendWith(loggedUser()) ? findTripsBy(user)
                : emptyList();
    }

    protected List<Trip> findTripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User loggedUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
