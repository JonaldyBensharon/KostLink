package com.kostlink.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boarding_houses")
public class BoardingHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String boardingCode;

    private String name;

    private String address;

    @Column(nullable = false)
    private boolean setupCompleted;

    @OneToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private Owner owner;

    @OneToMany(mappedBy = "boardingHouse", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public BoardingHouse() {
    }

    public BoardingHouse(Owner owner, String boardingCode) {
        this.owner = owner;
        this.boardingCode = boardingCode;
        this.setupCompleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public void rename(String name) {
        this.name = name;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void markSetupCompleted() {
        this.setupCompleted = true;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        room.setBoardingHouse(this);
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room);
        room.setBoardingHouse(null);
    }

    public Long getId() {
        return id;
    }

    public String getBoardingCode() {
        return boardingCode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isSetupCompleted() {
        return setupCompleted;
    }

    public Owner getOwner() {
        return owner;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}