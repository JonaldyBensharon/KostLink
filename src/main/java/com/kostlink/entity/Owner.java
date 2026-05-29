package com.kostlink.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Owner() {}

    public Owner(User user, String fullName, String phoneNumber) {
        this.user = user;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.createdAt = LocalDateTime.now();
    }

    // BUSINESS METHODS

    public void updateContact(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void rename(String newFullName) {
        this.fullName = newFullName;
    }

    // GETTERS

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}