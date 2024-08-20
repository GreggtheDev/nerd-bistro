package org.example;

public class User {
    private int id;
    private String username;
    private String password;
    private String salt;
    private String role;

    public User(int id, String username, String password, String salt, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getRole() {
        return role;
    }
}
