package org.craftedsw.tripservicekata.user;

import org.junit.jupiter.api.Test;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    public void should_return_false_when_is_friend_with_another_user() {
        User anotherUser = aUser()
                .build();
        User user = aUser()
                .friendsWith()
                .build();

        assertFalse(user.isFriendWith(anotherUser));
    }


    @Test
    public void should_return_true_when_is_friend_with_another_user() {
        User anotherUser = aUser()
                .build();
        User user = aUser()
                .friendsWith(anotherUser)
                .build();

        assertTrue(user.isFriendWith(anotherUser));
    }
}
