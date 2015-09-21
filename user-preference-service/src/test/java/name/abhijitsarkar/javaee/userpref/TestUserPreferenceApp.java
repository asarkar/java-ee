package name.abhijitsarkar.javaee.userpref;

import org.springframework.boot.SpringApplication;

import name.abhijitsarkar.javaee.userpref.UserPreferenceApp;

public class TestUserPreferenceApp extends UserPreferenceApp {
    public static void main(String[] args) throws Exception {
	SpringApplication.run(UserPreferenceApp.class, args);
    }
}
