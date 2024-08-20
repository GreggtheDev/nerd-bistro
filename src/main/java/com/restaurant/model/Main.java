package com.restaurant.model;

import com.restaurant.dao.UserDOA;

public class Main {
    public static void main(String[] args) {
        User user = new User("test", "test", "staff");
        UserDOA doa = new UserDOA();

        doa.insert(user);
    }
}
