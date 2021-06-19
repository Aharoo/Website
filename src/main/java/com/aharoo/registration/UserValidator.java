package com.aharoo.registration;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidator {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static final Pattern VALID_USERNAME_REGEX =
			Pattern.compile("(?:[a-zA-Z0-9-])");

	public static final Pattern VALID_PASSWORD_REGEX =
			Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}");

	public boolean isEmailValid(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}
	public boolean isUsernameValid(String username) {
		Matcher matcher = VALID_USERNAME_REGEX.matcher(username);
		return matcher.find();
	}
	public boolean isPasswordValid(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}
}
