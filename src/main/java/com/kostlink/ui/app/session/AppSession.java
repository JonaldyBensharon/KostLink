package com.kostlink.ui.app.session;

public class AppSession {

    private static String kostId;
    private static String username;
    private static Role role;

    private static boolean loggedIn = false;
    private static boolean onboardingCompleted = false;

    public enum Role {
        OWNER,
        TENANT
    }

    private AppSession() {}

    public static void startSession(String kId, String user, Role r) {
        kostId = kId;
        username = user;
        role = r;
        loggedIn = true;

        // reset state session baru
        onboardingCompleted = false;
    }

    public static void clear() {
        kostId = null;
        username = null;
        role = null;
        loggedIn = false;
        onboardingCompleted = false;
    }

    public static String getKostId() {
        return kostId;
    }

    public static String getUsername() {
        return username;
    }

    public static Role getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static boolean isOnboardingCompleted() {
        return onboardingCompleted;
    }

    public static void setOnboardingCompleted(boolean status) {
        onboardingCompleted = status;
    }
}