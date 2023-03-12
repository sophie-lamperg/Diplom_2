package org.example.models;

public class Credentials {
    private String email;
    private String password;

    public Credentials (String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials() {};
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static Credentials from(User courier) {
        return new Credentials(courier.getEmail(), courier.getPassword());
    }
}
