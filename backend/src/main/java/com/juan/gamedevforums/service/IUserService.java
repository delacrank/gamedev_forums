package com.juan.gamedevforums.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.error.UserAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameNotFoundException;
import com.juan.gamedevforums.persistence.model.PasswordResetToken;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.VerificationToken;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;
    
    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    User findUserByEmailIgnoreCase(String email);

    User findUserByUsername(String username);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    Optional<User> getUserById(long id);

    UserDto getUserByUsername(String username);

    UserDto updateUserByUsername(String username, UserDto accountDto);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

}
