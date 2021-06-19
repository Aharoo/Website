package com.aharoo.auth;

import com.aharoo.registration.email.EmailService;
import com.aharoo.registration.email.token.ConfirmationToken;
import com.aharoo.registration.email.token.ConfirmationTokenService;
import com.aharoo.repository.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

	private final ApplicationUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ConfirmationTokenService tokenService;
	private final EmailService emailSender;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
				String.format("User with username %s was not found",username)));
	}

	public String signUpUser(ApplicationUser user){
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

		if (userExists){
			ConfirmationToken userToken = tokenService.findByUser(user).orElseThrow(()
					-> new IllegalStateException("Token was not found"));
			if (userToken.getConfirmedAt() == null) {
				String link = "http://localhost:8080/registration/confirm?token=" + userToken;
				emailSender.send(user.getEmail(),emailSender.buildEmail(user.getUsername(),link));
			} else
				throw new IllegalStateException("Username was already taken");
		}

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepository.save(user);

		String token = UUID.randomUUID().toString();

		ConfirmationToken confirmationToken = new ConfirmationToken(
				token,
				LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(15),
				null,
				user
		);

		tokenService.saveConfirmationToken(confirmationToken);

		return token;
	}

	public List<ApplicationUser> loadAllUsers(){
		return userRepository.findAll();
	}

	public void enableUser(String email){userRepository.enableAppUser(email);}

	public Optional<ApplicationUser> findById(Integer user_id){ return userRepository.findById(user_id);}

}
