package com.restaurant.model;

// Import statements to bring in necessary libraries for testing
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// This is a test class for the User class
class UserTest {

    // Declare a private User object that we will use in our tests
    private User user;

    // This method runs before each test and sets up a User object to be tested
    @BeforeEach
    void setUp() {
        // Initialize the User object with a username, a hashed password, and a role
        // "testUser" is the username, "testPassword" is the password that will be hashed,
        // and "manager" is the role for this user
        user = new User("testUser", User.hashPassword("testPassword"), "manager");
    }

    // This test checks if the getUsername() method in the User class works correctly
    @Test
    void testGetUsername() {
        // Assert that the username is "testUser"
        // This means we expect the getUsername() method to return "testUser"
        assertEquals("testUser", user.getUsername());
    }

    // This test checks if the getRole() method in the User class works correctly
    @Test
    void testGetRole() {
        // Assert that the role is "manager"
        // This means we expect the getRole() method to return "manager"
        assertEquals("manager", user.getRole());
    }

    // This test checks if the hashPassword() method in the User class works correctly
    @Test
    void testHashPassword() {
        // The plain password that we want to hash
        String plainPassword = "passwordTest";

        // Call the hashPassword() method to get the hashed version of the password
        String hashedPassword = User.hashPassword(plainPassword);

        // Check that the hashed password is not null (it should have some value)
        assertNotNull(hashedPassword);

        // Check that the hashed password is different from the plain password
        // This ensures that the password has been transformed by the hash function
        assertNotEquals(plainPassword, hashedPassword);

        // Optional: Check if the hashed password matches a known SHA-256 hash value
        // This part is useful if you know exactly what the hash should be
        String expectedHashedPassword = "eded82fead402293e0ad6f774aa4cb1d77245c2bc62f3b4c4f3dfcaacb336fe2"; // Example SHA-256 hash
        assertEquals(expectedHashedPassword, hashedPassword);
    }

    // This test checks if the getHashedPassword() method in the User class works correctly
    @Test
    void testGetHashedPassword() {
        // The expected hashed password is what we get when we hash "testPassword"
        String expectedHashedPassword = User.hashPassword("testPassword");

        // Assert that the hashed password stored in the User object is correct
        // We expect the getHashedPassword() method to return the hashed version of "testPassword"
        assertEquals(expectedHashedPassword, user.getHashedPassword());
    }
}
