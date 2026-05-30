package com.kostlink.ui.auth.mock;

import java.util.HashMap;
import java.util.Map;

public class AuthMockDatabase {

    private static final Map<String, UserRecord> usersByKostId = new HashMap<>();

    private AuthMockDatabase() {}

    // =========================
    // ROLE ENUM (lebih aman dari string)
    // =========================
    public enum Role {
        OWNER,
        TENANT
    }

    // =========================
    // USER MODEL
    // =========================
    public static class UserRecord {
        public String kostId;
        public String username;
        public String password;
        public Role role;

        public UserRecord(String kostId, String username, String password, Role role) {
            this.kostId = kostId;
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }

    // =========================
    // REGISTER OWNER
    // =========================
    public static void registerOwner(String kostId, String username, String password) {

        String normalizedKostId = normalize(kostId);
        String normalizedUsername = normalize(username);

        UserRecord user = new UserRecord(
                normalizedKostId,
                normalizedUsername,
                password,
                Role.OWNER
        );

        usersByKostId.put(normalizedKostId, user);
    }

    // =========================
    // VALIDATE LOGIN
    // =========================
    public static boolean validateLogin(String kostId, String username, String password) {

        String normalizedKostId = normalize(kostId);
        String normalizedUsername = normalize(username);

        if (!usersByKostId.containsKey(normalizedKostId)) {
            return false;
        }

        UserRecord user = usersByKostId.get(normalizedKostId);

        return user.username.equals(normalizedUsername)
                && user.password.equals(password);
    }

    // =========================
    // GET ROLE
    // =========================
    public static Role getRole(String kostId) {

        UserRecord user = usersByKostId.get(normalize(kostId));

        return user != null ? user.role : null;
    }

    // =========================
    // EXISTS CHECK
    // =========================
    public static boolean exists(String kostId) {
        return usersByKostId.containsKey(normalize(kostId));
    }

    // =========================
    // NORMALIZATION UTILITY
    // =========================
    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }
}