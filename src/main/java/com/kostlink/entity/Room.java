package com.kostlink.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private BigDecimal monthlyPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "boarding_house_id")
    private BoardingHouse boardingHouse;

    @OneToMany(mappedBy = "room")
    private List<Tenant> tenants = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Room() {
    }

    public Room(String roomNumber, Integer capacity, BigDecimal monthlyPrice) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.monthlyPrice = monthlyPrice;
        this.createdAt = LocalDateTime.now();
    }

    public void renameRoom(String newName) {
        this.roomNumber = newName;
    }

    public void updatePrice(BigDecimal newPrice) {
        this.monthlyPrice = newPrice;
    }

    public void updateCapacity(int newCapacity) {
        if (newCapacity < this.tenants.size()) {
            throw new IllegalStateException("Capacity cannot be less than current tenants");
        }
        this.capacity = newCapacity;
    }

    public boolean hasVacancy() {
        return tenants.size() < capacity;
    }

    public void assignTenant(Tenant tenant) {
        if (!hasVacancy()) {
            throw new IllegalStateException("Room is full");
        }

        if (!this.tenants.contains(tenant)) {
            this.tenants.add(tenant);
            tenant.assignRoom(this);
        }
    }

    public void removeTenant(Tenant tenant) {
        if (this.tenants.remove(tenant)) {
            tenant.removeRoom();
        }
    }

    public void setBoardingHouse(BoardingHouse boardingHouse) {
        this.boardingHouse = boardingHouse;
    }

    public Long getId() {
        return id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public BoardingHouse getBoardingHouse() {
        return boardingHouse;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}