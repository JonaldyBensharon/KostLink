package com.kostlink.service.token;

import com.kostlink.entity.RegistrationToken;
import com.kostlink.entity.Room;
import com.kostlink.repository.RegistrationTokenRepository;
import com.kostlink.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationTokenService {

    private final RegistrationTokenRepository tokenRepository;
    private final RoomRepository roomRepository;
    private final TokenGenerator tokenGenerator;

    public RegistrationTokenService(
            RegistrationTokenRepository tokenRepository,
            RoomRepository roomRepository,
            TokenGenerator tokenGenerator
    ) {
        this.tokenRepository = tokenRepository;
        this.roomRepository = roomRepository;
        this.tokenGenerator = tokenGenerator;
    }

    /**
     * Generate token untuk room tertentu
     */
    public RegistrationToken generateToken(Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (!room.hasVacancy()) {
            throw new IllegalStateException("Room is full, cannot generate token");
        }

        String tokenValue = tokenGenerator.generate();

        RegistrationToken token = new RegistrationToken(tokenValue, room);

        return tokenRepository.save(token);
    }

    /**
     * Validasi token
     */
    public RegistrationToken validateToken(String tokenValue) {

        RegistrationToken token = tokenRepository.findByTokenValue(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (token.isUsed()) {
            throw new IllegalStateException("Token already used");
        }

        if (token.getRoom() == null) {
            throw new IllegalStateException("Token not bound to room");
        }

        return token;
    }

    /**
     * Consume token setelah registrasi sukses
     */
    public void consumeToken(String tokenValue) {

        RegistrationToken token = validateToken(tokenValue);

        token.markUsed();

        tokenRepository.save(token);
    }

    /**
     * Check apakah token masih bisa dipakai
     */
    public boolean isUsable(String tokenValue) {
        try {
            validateToken(tokenValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}