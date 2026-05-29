package com.kostlink.service.auth;

import com.kostlink.entity.BoardingHouse;
import com.kostlink.entity.Owner;
import com.kostlink.entity.Tenant;
import com.kostlink.entity.User;
import com.kostlink.repository.BoardingHouseRepository;
import com.kostlink.repository.OwnerRepository;
import com.kostlink.repository.TenantRepository;
import com.kostlink.repository.UserRepository;
import com.kostlink.service.common.ValidationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final TenantRepository tenantRepository;
    private final BoardingHouseRepository boardingHouseRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            OwnerRepository ownerRepository,
            TenantRepository tenantRepository,
            BoardingHouseRepository boardingHouseRepository,
            ValidationService validationService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.tenantRepository = tenantRepository;
        this.boardingHouseRepository = boardingHouseRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    public Owner loginOwner(
            String boardingCode,
            String username,
            String password
    ) {
        validationService.requireNonEmpty(boardingCode, "Boarding code required");
        validationService.requireNonEmpty(username, "Username required");
        validationService.requireNonEmpty(password, "Password required");

        User user = authenticate(username, password);

        Owner owner = ownerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Owner profile not found"));

        BoardingHouse boardingHouse = boardingHouseRepository.findByOwner(owner)
                .orElseThrow(() -> new IllegalArgumentException("Boarding house not found"));

        if (!boardingHouse.getBoardingCode().equals(boardingCode)) {
            throw new IllegalArgumentException("Invalid boarding code");
        }

        return owner;
    }

    public Tenant loginTenant(
            String boardingCode,
            String username,
            String password
    ) {
        validationService.requireNonEmpty(boardingCode, "Boarding code required");
        validationService.requireNonEmpty(username, "Username required");
        validationService.requireNonEmpty(password, "Password required");

        User user = authenticate(username, password);

        Tenant tenant = tenantRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Tenant profile not found"));

        if (tenant.getRoom() == null) {
            throw new IllegalArgumentException("Tenant is not assigned to a room");
        }

        BoardingHouse boardingHouse = tenant.getRoom().getBoardingHouse();

        if (!boardingHouse.getBoardingCode().equals(boardingCode)) {
            throw new IllegalArgumentException("Invalid boarding code");
        }

        return tenant;
    }

    private User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (!user.isActive()) {
            throw new IllegalStateException("Account is inactive");
        }

        return user;
    }
}