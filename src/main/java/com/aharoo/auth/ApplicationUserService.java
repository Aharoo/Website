package com.aharoo.auth;

import com.aharoo.registration.email.EmailService;
import com.aharoo.registration.email.token.ConfirmationToken;
import com.aharoo.registration.email.token.ConfirmationTokenService;
import com.aharoo.repository.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
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

	@Transactional
	public String recoverPassword(String email){
		String password = new PasswordGenerator().generateRandomPassword();
		String encodedPassword = passwordEncoder.encode(password);

		ApplicationUser user = userRepository.findByEmail(email).orElseThrow(()
				-> new UsernameNotFoundException(String.format("User with email %s was not found",email)));
		boolean userExists = userRepository.findByEmail(email).isPresent();
		if (userExists)
			userRepository.updatePassword(encodedPassword,email);
		else
			throw new IllegalStateException("User was not found");

		emailSender.send(email,emailSender.buildRecoveringEmail(user.getUsername(),password));
		return password;
	}

	public List<ApplicationUser> loadAllUsers(){
		return userRepository.findAll();
	}

	public void enableUser(String email){userRepository.enableAppUser(email);}

	public Optional<ApplicationUser> findById(Integer user_id){ return userRepository.findById(user_id);}

	public ApplicationUser updateUser(ApplicationUser user){ return userRepository.save(user);}

	public void deleteUser(Integer id){ userRepository.deleteById(id);}

	public Optional<ApplicationUser> findByEmail(String email){return userRepository.findByEmail(email);}

	class PasswordGenerator {

		SecureRandom randomChar = new SecureRandom();

		public String generateRandomPassword(){
			String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			String smallLetters = "abcdefghijklmnopqrstuvwxyz";
			String numbers = "1234567890";
			String values = capitalLetters + smallLetters + numbers;
			char[] password = new char[12];
			for (int i = 0; i < 12; i++){
				password[i] = values.charAt(randomChar.nextInt(values.length()));
			}
			String passwordString = String.valueOf(password);
			return passwordString;
		}
	}

}
