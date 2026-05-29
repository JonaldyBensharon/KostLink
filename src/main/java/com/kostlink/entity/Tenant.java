package com.kostlink.entity;

import com.kostlink.entity.enums.TenantStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    private String emergencyContact;

    private LocalDate rentalStartDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Tenant() {}

    // onboarding awal (belum assign room)
    public Tenant(User user, String fullName, String phoneNumber) {
        this.user = user;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = TenantStatus.REGISTERED;
        this.createdAt = LocalDateTime.now();
    }

    // ===== BUSINESS METHODS =====

    public void assignRoom(Room room) {
        this.room = room;
    }

    public void removeRoom() {
        this.room = null;
    }

    public void rename(String newFullName) {
        this.fullName = newFullName;
    }

    public void updateContact(String newPhoneNumber, String emergencyContact) {
        this.phoneNumber = newPhoneNumber;
        this.emergencyContact = emergencyContact;
    }

    public void activate() {
        if (room == null) {
            throw new IllegalStateException("Cannot activate tenant without room");
        }
        this.status = TenantStatus.ACTIVE;
        this.rentalStartDate = LocalDate.now();
    }

    public void moveOut() {
        this.status = TenantStatus.MOVING_OUT;
    }

    public void vacate() {
        this.status = TenantStatus.VACATED;
    }

    // ===== GETTERS =====

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}