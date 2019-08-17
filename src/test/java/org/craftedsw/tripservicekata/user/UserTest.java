package org.craftedsw.tripservicekata.user;

import org.junit.jupiter.api.Test;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    public static final User RAUL = aUser()
            .build();
    public static final User SANDRO = aUser()
            .build();

    @Test
    public void should_return_false_when_is_friend_with_another_user() {
        User user = aUser()
                .friendsWith()
                .build();

        assertFalse(user.isFriendWith(RAUL));
    }


    @Test
    public void should_return_true_when_is_friend_with_another_user() {
        User user = aUser()
                .friendsWith(SANDRO)
                .build();

        assertTrue(user.isFriendWith(SANDRO));
    }
}
