package com.kostlink.service.boardinghouse;

import com.kostlink.entity.BoardingHouse;
import com.kostlink.entity.Owner;
import com.kostlink.entity.Room;
import com.kostlink.repository.BoardingHouseRepository;
import com.kostlink.repository.RoomRepository;
import com.kostlink.service.common.ValidationService;
import com.kostlink.service.token.TokenGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BoardingHouseService {

    private final BoardingHouseRepository boardingHouseRepository;
    private final RoomRepository roomRepository;
    private final ValidationService validationService;
    private final TokenGenerator tokenGenerator;

    public BoardingHouseService(
            BoardingHouseRepository boardingHouseRepository,
            RoomRepository roomRepository,
            ValidationService validationService,
            TokenGenerator tokenGenerator
    ) {
        this.boardingHouseRepository = boardingHouseRepository;
        this.roomRepository = roomRepository;
        this.validationService = validationService;
        this.tokenGenerator = tokenGenerator;
    }

    public BoardingHouse createBoardingHouse(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner is required");
        }

        String boardingCode = tokenGenerator.generate();

        BoardingHouse boardingHouse = new BoardingHouse(owner, boardingCode);

        return boardingHouseRepository.save(boardingHouse);
    }

    public BoardingHouse completeSetup(
            Long boardingHouseId,
            String name,
            String address
    ) {
        validationService.requireNonEmpty(name, "Boarding house name required");
        validationService.requireNonEmpty(address, "Address required");

        BoardingHouse boardingHouse = findById(boardingHouseId);

        boardingHouse.rename(name);
        boardingHouse.updateAddress(address);
        boardingHouse.markSetupCompleted();

        return boardingHouseRepository.save(boardingHouse);
    }

    public Room addRoom(
            Long boardingHouseId,
            String roomNumber,
            int capacity,
            BigDecimal monthlyPrice
    ) {
        validationService.requireNonEmpty(roomNumber, "Room number required");

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }

        if (monthlyPrice == null || monthlyPrice.signum() <= 0) {
            throw new IllegalArgumentException("Monthly price must be greater than zero");
        }

        BoardingHouse boardingHouse = findById(boardingHouseId);

        Room room = new Room(roomNumber, capacity, monthlyPrice);

        boardingHouse.addRoom(room);

        roomRepository.save(room);
        boardingHouseRepository.save(boardingHouse);

        return room;
    }

    public BoardingHouse findByCode(String boardingCode) {
        validationService.requireNonEmpty(boardingCode, "Boarding code required");

        return boardingHouseRepository.findByBoardingCode(boardingCode)
                .orElseThrow(() -> new IllegalArgumentException("Boarding house not found"));
    }

    public BoardingHouse findById(Long id) {
        return boardingHouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Boarding house not found"));
    }
}