package userserver.model;

import userserver.domain.User;

public class TestModelFactory {
    public static User createUser() {
        return new User("userid", "password", "부종민");
    }
}
