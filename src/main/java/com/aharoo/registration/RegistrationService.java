package com.aharoo.registration;

import com.aharoo.auth.ApplicationUser;
import com.aharoo.auth.ApplicationUserService;
import com.aharoo.registration.email.EmailService;
import com.aharoo.registration.email.token.ConfirmationToken;
import com.aharoo.registration.email.token.ConfirmationTokenService;
import com.aharoo.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

	private final ConfirmationTokenService confirmationTokenService;
	private final UserValidator userValidator;
	private final ApplicationUserService userService;
	private final EmailService emailService;

	public String register(ApplicationUser user){


		if (!userValidator.isEmailValid(user.getEmail()))
			throw new IllegalStateException("Email is not valid");
		else if (!userValidator.isPasswordValid(user.getPassword()))
			throw new IllegalStateException("Password is not valid");
		else if (!userValidator.isUsernameValid(user.getUsername()))
			throw new IllegalStateException("Username is not valid");

		ApplicationUser newUser = new ApplicationUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		newUser.setEmail(user.getEmail());
		newUser.setRole(ApplicationUserRole.USER);

		String token = userService.signUpUser(newUser);

		String link = "http://localhost:8080/registration/confirm?token=" + token;

		emailService.send(user.getEmail(),emailService.buildEmail(user.getUsername(),link));

		return token;
	}

	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService
				.getToken(token)
				.orElseThrow(() ->
						new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("email already confirmed");
		}

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}

		confirmationTokenService.setConfirmedAt(token);
		userService.enableUser(
				confirmationToken.getUser().getEmail());
		return "confirmed";
	}
}
