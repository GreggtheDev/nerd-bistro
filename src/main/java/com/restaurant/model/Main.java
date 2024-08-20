package com.restaurant.model;

import com.restaurant.dao.UserDAO;

public class Main {
    public static void main(String[] args) {
        User user = new User("test", "test", "staff");
        UserDAO doa = new UserDAO();

        doa.insert(user);
    }
}
