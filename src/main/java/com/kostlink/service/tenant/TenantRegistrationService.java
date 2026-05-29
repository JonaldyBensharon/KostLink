package com.kostlink.service.tenant;

import com.kostlink.entity.*;
import com.kostlink.entity.enums.UserRole;
import com.kostlink.repository.BoardingHouseRepository;
import com.kostlink.repository.TenantRepository;
import com.kostlink.repository.UserRepository;
import com.kostlink.service.common.ValidationService;
import com.kostlink.service.room.RoomService;
import com.kostlink.service.token.RegistrationTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TenantRegistrationService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final BoardingHouseRepository boardingHouseRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationTokenService registrationTokenService;
    private final RoomService roomService;

    public TenantRegistrationService(
            UserRepository userRepository,
            TenantRepository tenantRepository,
            BoardingHouseRepository boardingHouseRepository,
            ValidationService validationService,
            PasswordEncoder passwordEncoder,
            RegistrationTokenService registrationTokenService,
            RoomService roomService
    ) {
        this.userRepository = userRepository;
        this.tenantRepository = tenantRepository;
        this.boardingHouseRepository = boardingHouseRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
        this.registrationTokenService = registrationTokenService;
        this.roomService = roomService;
    }

    public Tenant registerTenant(
            String boardingCode,
            String tokenValue,
            String username,
            String password,
            String fullName,
            String phoneNumber
    ) {

        // 1. VALIDASI INPUT
        validationService.requireNonEmpty(boardingCode, "Boarding code required");
        validationService.requireNonEmpty(tokenValue, "Token required");
        validationService.requireNonEmpty(username, "Username required");
        validationService.requireNonEmpty(password, "Password required");

        // 2. VALIDASI BOARDING HOUSE
        BoardingHouse house = boardingHouseRepository.findByBoardingCode(boardingCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid boarding house"));

        // 3. VALIDASI USERNAME
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already used");
        }

        // 4. VALIDASI TOKEN
        RegistrationToken token = registrationTokenService.validateToken(tokenValue);

        if (!token.getRoom().getBoardingHouse().equals(house)) {
            throw new IllegalArgumentException("Token does not belong to this boarding house");
        }

        Room room = token.getRoom();

        // 5. VALIDASI ROOM
        roomService.validateVacancy(room.getId());

        // 6. CREATE USER
        User user = new User(
                username,
                passwordEncoder.encode(password),
                UserRole.TENANT
        );

        user = userRepository.save(user);

        // 7. CREATE TENANT
        Tenant tenant = new Tenant(user, fullName, phoneNumber);
        tenantRepository.save(tenant);

        // 8. ASSIGN ROOM
        roomService.assignTenant(room.getId(), tenant);

        // 9. CONSUME TOKEN
        registrationTokenService.consumeToken(tokenValue);

        return tenant;
    }
}