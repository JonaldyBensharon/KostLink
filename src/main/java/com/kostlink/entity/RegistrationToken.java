package com.kostlink.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration_tokens")
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tokenValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private boolean used;

    private LocalDateTime usedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public RegistrationToken() {
    }

    public RegistrationToken(String tokenValue, Room room) {
        this.tokenValue = tokenValue;
        this.room = room;
        this.used = false;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isUsable() {
        return !used;
    }

    public void markUsed() {
        this.used = true;
        this.usedAt = LocalDateTime.now();
    }

    public void validate() {
        if (used) {
            throw new IllegalStateException("Token already used");
        }
    }

    public Long getId() {
        return id;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}