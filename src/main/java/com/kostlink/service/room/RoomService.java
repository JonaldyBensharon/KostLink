package com.kostlink.service.room;

import com.kostlink.entity.Room;
import com.kostlink.entity.Tenant;
import com.kostlink.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Ambil room berdasarkan ID
     */
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    /**
     * Validasi apakah room masih tersedia
     */
    public boolean isAvailable(Long roomId) {
        Room room = getRoomById(roomId);
        return room.hasVacancy();
    }

    /**
     * Assign tenant ke room
     */
    public void assignTenant(Long roomId, Tenant tenant) {
        Room room = getRoomById(roomId);

        if (!room.hasVacancy()) {
            throw new IllegalStateException("Room is full");
        }

        room.assignTenant(tenant);

        roomRepository.save(room);
    }

    /**
     * Lepaskan tenant dari room
     */
    public void releaseTenant(Long roomId, Tenant tenant) {
        Room room = getRoomById(roomId);

        room.removeTenant(tenant);

        roomRepository.save(room);
    }

    /**
     * Validasi room memiliki slot
     */
    public void validateVacancy(Long roomId) {
        if (!isAvailable(roomId)) {
            throw new IllegalStateException("Room is full");
        }
    }
}