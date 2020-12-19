package com.juan.gamedevforums.service;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.juan.gamedevforums.persistence.dao.PasswordResetTokenRepository;
import com.juan.gamedevforums.persistence.dao.RoleRepository;
import com.juan.gamedevforums.persistence.dao.UserRepository;
import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.dto.DtoConverter.ToDtoConverter;
import com.juan.gamedevforums.web.error.UserAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameNotFoundException;
import com.juan.gamedevforums.persistence.dao.VerificationTokenRepository;
import com.juan.gamedevforums.persistence.model.PasswordResetToken;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Environment env;

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String APP_NAME = "SpringRegistration";

    // API

    @Override
    public User registerNewUserAccount(final UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
	if(usernameExists(accountDto.getUsername())) {	
            throw new UsernameAlreadyExistException("There is an account with that username: " + accountDto.getUsername());
        }
        final User user = new User();

        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        userRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
            .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(final String username) {
	User user = this.userRepository.findByUsername(username);
	if(user == null ) {
	    throw new UsernameNotFoundException("User not found");
        }    
        return user;
    }
    
    @Override
    public User findUserByEmailIgnoreCase(final String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    @Override
    public Optional<User> getUserById(final long id) {
        return userRepository.findById(id);
    } 

    @Override
    public UserDto getUserByUsername(String username) {
	User user = this.userRepository.findByUsername(username);
	if(user == null) {
	    throw new UsernameNotFoundException("User not found");
        }    
	return ToDtoConverter.userToDto(user);
    }									      

    @Override
    public UserDto updateUserByUsername(String username, final UserDto accountDto) {
	User user2 = this.userRepository.findByUsername(username);
	if(user2  == null ) {
	    throw new UsernameNotFoundException("User not found");
        }
	user2.setBio(accountDto.getBio());
	user2.setImage(accountDto.getImage());
	this.userRepository.save(user2);
	return ToDtoConverter.userToDto(user2);
    }
	
    
    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime() - cal.getTime()
            .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean usernameExists(final String name) {
        return userRepository.findByUsername(name) != null;
    }

}
