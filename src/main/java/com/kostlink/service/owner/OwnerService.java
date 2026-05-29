package com.kostlink.service.owner;

import com.kostlink.entity.Owner;
import com.kostlink.entity.User;
import com.kostlink.entity.enums.UserRole;
import com.kostlink.repository.OwnerRepository;
import com.kostlink.repository.UserRepository;
import com.kostlink.service.boardinghouse.BoardingHouseService;
import com.kostlink.service.common.ValidationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final BoardingHouseService boardingHouseService;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    public OwnerService(
            UserRepository userRepository,
            OwnerRepository ownerRepository,
            BoardingHouseService boardingHouseService,
            ValidationService validationService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
        this.boardingHouseService = boardingHouseService;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    public Owner registerOwner(
            String username,
            String password,
            String fullName,
            String phoneNumber
    ) {
        validationService.requireNonEmpty(username, "Username required");
        validationService.requireNonEmpty(password, "Password required");
        validationService.requireNonEmpty(fullName, "Full name required");
        validationService.requireNonEmpty(phoneNumber, "Phone number required");

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already used");
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                UserRole.OWNER
        );

        user = userRepository.save(user);

        Owner owner = new Owner(
                user,
                fullName,
                phoneNumber
        );

        owner = ownerRepository.save(owner);

        boardingHouseService.createBoardingHouse(owner);

        return owner;
    }

    public Owner updateOwnerProfile(
            Long ownerId,
            String fullName,
            String phoneNumber
    ) {
        validationService.requireNonEmpty(fullName, "Full name required");
        validationService.requireNonEmpty(phoneNumber, "Phone number required");

        Owner owner = findById(ownerId);

        owner.rename(fullName);
        owner.updateContact(phoneNumber);

        return ownerRepository.save(owner);
    }

    public Owner findById(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }
}