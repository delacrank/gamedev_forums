package com.juan.gamedevforums.web.controller;

import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.ResponseEntity;
import com.juan.gamedevforums.persistence.model.Privilege;
import com.juan.gamedevforums.persistence.model.Role;
import com.juan.gamedevforums.persistence.model.User;
import com.juan.gamedevforums.persistence.model.VerificationToken;
import org.springframework.ui.ModelMap;
import com.juan.gamedevforums.web.error.UserAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameAlreadyExistException;
import com.juan.gamedevforums.web.error.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.juan.gamedevforums.registration.OnRegistrationCompleteEvent;
import com.juan.gamedevforums.security.ISecurityUserService;
import com.juan.gamedevforums.service.IUserService;
import com.juan.gamedevforums.web.dto.PasswordDto;
import com.juan.gamedevforums.web.dto.UserDto;
import com.juan.gamedevforums.web.error.InvalidOldPasswordException;
import com.juan.gamedevforums.web.util.GenericResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="http://localhost:4200")
public class RegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityUserService securityUserService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public RegistrationController() {
        super();
    }

    @RequestMapping()
    public Principal user(Principal user){
        return user;
    }
    
    // Registration
    @PostMapping(value="/registration")
    public GenericResponse registerUserAccount(@Valid @RequestBody final UserDto accountDto,
					       final HttpServletRequest request) {
        LOGGER.debug("Registering user account with information: {}", accountDto);
	try{
            final User registered = userService.registerNewUserAccount(accountDto);
	    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));	    
        } catch(final UserAlreadyExistException uaeEx) {
	    return new GenericResponse("user Already Exists", "userAlreadyExists");
	} catch(final UsernameAlreadyExistException unaeEx) {
	    return new GenericResponse("username Already Exists", "usernameAlreadyExists");
	} catch(final RuntimeException ex) {
	    return new GenericResponse("Invalid Email", "InvalidEmail");
	}
        return new GenericResponse("Success");
    }

    @GetMapping("/registrationConfirm")
    public ModelAndView confirmRegistration(final HttpServletRequest request, @RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
            authWithoutPassword(user);
            return new ModelAndView("redirect:/successRegistration");
        }
        return new ModelAndView("redirect:/badUser");
    }

    public void authWithoutPassword(User user) {
        List<Privilege> privileges = user.getRoles()
	    .stream()
	    .map(Role::getPrivileges)
	    .flatMap(Collection::stream)
	    .distinct()
	    .collect(Collectors.toList());
        List<GrantedAuthority> authorities = privileges.stream()
	    .map(p -> new SimpleGrantedAuthority(p.getName()))
	    .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
    // User activation - verification
    @GetMapping("/resendRegistrationToken")
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return new GenericResponse("We will send an email with a new registration token to your email account");
    }

    // Reset password
    @PostMapping("/resetPassword")
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        final User user = userService.findUserByEmailIgnoreCase(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
	    return new GenericResponse("Success");
        }
        return new GenericResponse("Failed to find email");
    }

    @GetMapping("/changePassword")
    public ModelAndView changePassword( @RequestParam("token") final String token)       {
        final String result = securityUserService.validatePasswordResetToken(token);

        if(result != null) {
            return new ModelAndView("redirect:/failedReset");
        } else {
            return new ModelAndView("redirect:/updatePassword?token=" + token);
        }
    }
    
    // Save password
    @PostMapping("/savePassword")
    public GenericResponse savePassword(@Valid @RequestBody PasswordDto passwordDto) {

        final String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse("Invalid Token");
        }

        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());

        if(user.isPresent()) {
	    String oldPass = user.get().getPassword();
	    // String newPass = passwordEncoder.encode(passwordDto.getNewPassword());
	    String newPass = passwordDto.getNewPassword();
	    if(passwordEncoder.matches(newPass, oldPass)) {
	    	return new GenericResponse("New password must be different");
	    }
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse("Success");
        } else {
            return new GenericResponse("Failed to update your password");
        }
    }

    // Change user password
    @PostMapping("/updatePassword")
    public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
        final User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new GenericResponse("Password updated successfully");
    }

    // ============== NON-API ============

    private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, final VerificationToken newToken, final User user) {
        final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        final String message = "We will send an email with a new registration token to your email account.";
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/api/user/changePassword?token=" + token;
        final String message = "Reset Password";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
