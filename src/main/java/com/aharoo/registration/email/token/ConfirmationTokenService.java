package com.aharoo.registration.email.token;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

	private final ConfirmationTokenRepository confirmationTokenRepository;

	public void saveConfirmationToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}

	public Optional<ConfirmationToken> getToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}

	public Optional<ConfirmationToken> findByUser(ApplicationUser user){return confirmationTokenRepository.findByUser(user);}

	public int setConfirmedAt(String token) {
		return confirmationTokenRepository.updateConfirmedAt(
				token, LocalDateTime.now());
	}
}
