package com.aharoo;

import com.aharoo.auth.ApplicationUserService;
import com.aharoo.registration.email.EmailService;
import com.aharoo.registration.email.token.ConfirmationTokenService;
import com.aharoo.repository.ApplicationUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;


public class ApplicationUserServiceTest {

    @Mock
    private ApplicationUserRepository userRepository;

    private ApplicationUserService underTest;
    private PasswordEncoder passwordEncoder;
    private ConfirmationTokenService tokenService;
    private EmailService emailSender;

    @BeforeEach
    void setUp(){underTest = new ApplicationUserService(userRepository,passwordEncoder,tokenService,emailSender);}

    @Test
    void canGetUserById(){
        underTest.loadAllUsers();

        verify(userRepository).findAll();
    }
}
