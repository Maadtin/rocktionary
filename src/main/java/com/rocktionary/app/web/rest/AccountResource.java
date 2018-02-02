package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.rocktionary.app.domain.User;
import com.rocktionary.app.repository.UserRepository;
import com.rocktionary.app.security.SecurityUtils;
import com.rocktionary.app.service.MailService;
import com.rocktionary.app.service.UserService;
import com.rocktionary.app.service.dto.UserDTO;
import com.rocktionary.app.web.rest.errors.*;
import com.rocktionary.app.web.rest.vm.KeyAndPasswordVM;
import com.rocktionary.app.web.rest.vm.ManagedUserVM;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
* REST controller for managing the current user's account.
*/
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
    * POST  /register : register the user.
    *
    * @param managedUserVM the managed user View Model
    * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
    * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
    * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
    */
    @PostMapping("/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).ifPresent(u -> {throw new LoginAlreadyUsedException();});
        userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).ifPresent(u -> {throw new EmailAlreadyUsedException();});
        User user = userService.registerUser(managedUserVM);
        mailService.sendActivationEmail(user);
    }

    /**
    * GET  /activate : activate the registered user.
    *
    * @param key the activation key
    * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
    */
    @GetMapping("/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        };
    }

    /**
    * GET  /authenticate : check if the user is authenticated, and return its login.
    *
    * @param request the HTTP request
    * @return the login if the user is authenticated
    */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
    * GET  /account : get the current user.
    *
    * @return the current user
    * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
    */
    @GetMapping("/account")
    @Timed
    public UserDTO getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(UserDTO::new)
            .orElseThrow(() -> new InternalServerErrorException("User could not be found"));
    }

    /**
    * POST  /account : update the current user information.
    *
    * @param userDTO the current user information
    * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
    * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
    */
    @PostMapping("/account")
    @Timed
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
   }

    /**
    * POST  /account/change-password : changes the current user's password
    *
    * @param password the new password
    * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
    */
    @PostMapping(path = "/account/change-password")
    @Timed
    public void changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(password);
   }

    /**
    * POST   /account/reset-password/init : Send an email to reset the password of the user
    *
    * @param mail the mail of the user
    * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
    */
    @PostMapping(path = "/account/reset-password/init")
    @Timed
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    /**
    * POST   /account/reset-password/finish : Finish to reset the password of the user
    *
    * @param keyAndPassword the generated key and the new password
    * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
    * @throws RuntimeException 500 (Internal Server Error) if the password could not be reset
    */
    @PostMapping(path = "/account/reset-password/finish")
    @Timed
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }

    private String generateRandomString (int length) {
        StringBuilder text = new StringBuilder();
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            text.append(possible.charAt((int) Math.floor(Math.random() * possible.length())));
        }
        return text.toString();
    }


    @GetMapping("/account/login-spotify")
    public String testSpotify () throws IOException {
        OkHttpClient client = new OkHttpClient();

        String state = generateRandomString(16);


        HttpUrl httpUrl = new HttpUrl.Builder()
            .scheme("http")
            .host("localhost")
            .port(8080)
            .addPathSegment("authorize")
            .addQueryParameter("response_type", "code")
            .addQueryParameter("client_id", "acb078a8d60f4603bfbfb488651a6ca4")
            .addQueryParameter("redirect_uri", "http://localhost:8888/callback/")
            .addQueryParameter("scope", "user-read-private user-read-email user-follow-read")
            .addQueryParameter("state", state)
            .addQueryParameter("show_dialog", "true")
            .build();

        Request request = new Request.Builder()
            .header("accept", "text/html")
            .url(httpUrl)
            .build();

        Response response = client.newCall(request).execute();

        return response.body().string();

    }


}

