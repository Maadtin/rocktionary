package com.rocktionary.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocktionary.app.domain.UserFollowingUser;

import com.rocktionary.app.repository.UserFollowingUserRepository;
import com.rocktionary.app.web.rest.errors.BadRequestAlertException;
import com.rocktionary.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserFollowingUser.
 */
@RestController
@RequestMapping("/api")
public class UserFollowingUserResource {

    private final Logger log = LoggerFactory.getLogger(UserFollowingUserResource.class);

    private static final String ENTITY_NAME = "userFollowingUser";

    private final UserFollowingUserRepository userFollowingUserRepository;

    public UserFollowingUserResource(UserFollowingUserRepository userFollowingUserRepository) {
        this.userFollowingUserRepository = userFollowingUserRepository;
    }

    /**
     * POST  /user-following-users : Create a new userFollowingUser.
     *
     * @param userFollowingUser the userFollowingUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userFollowingUser, or with status 400 (Bad Request) if the userFollowingUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-following-users")
    @Timed
    public ResponseEntity<UserFollowingUser> createUserFollowingUser(@RequestBody UserFollowingUser userFollowingUser) throws URISyntaxException {
        log.debug("REST request to save UserFollowingUser : {}", userFollowingUser);
        if (userFollowingUser.getId() != null) {
            throw new BadRequestAlertException("A new userFollowingUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserFollowingUser result = userFollowingUserRepository.save(userFollowingUser);
        return ResponseEntity.created(new URI("/api/user-following-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-following-users : Updates an existing userFollowingUser.
     *
     * @param userFollowingUser the userFollowingUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userFollowingUser,
     * or with status 400 (Bad Request) if the userFollowingUser is not valid,
     * or with status 500 (Internal Server Error) if the userFollowingUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-following-users")
    @Timed
    public ResponseEntity<UserFollowingUser> updateUserFollowingUser(@RequestBody UserFollowingUser userFollowingUser) throws URISyntaxException {
        log.debug("REST request to update UserFollowingUser : {}", userFollowingUser);
        if (userFollowingUser.getId() == null) {
            return createUserFollowingUser(userFollowingUser);
        }
        UserFollowingUser result = userFollowingUserRepository.save(userFollowingUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userFollowingUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-following-users : get all the userFollowingUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userFollowingUsers in body
     */
    @GetMapping("/user-following-users")
    @Timed
    public List<UserFollowingUser> getAllUserFollowingUsers() {
        log.debug("REST request to get all UserFollowingUsers");
        return userFollowingUserRepository.findAll();
        }

    /**
     * GET  /user-following-users/:id : get the "id" userFollowingUser.
     *
     * @param id the id of the userFollowingUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userFollowingUser, or with status 404 (Not Found)
     */
    @GetMapping("/user-following-users/{id}")
    @Timed
    public ResponseEntity<UserFollowingUser> getUserFollowingUser(@PathVariable Long id) {
        log.debug("REST request to get UserFollowingUser : {}", id);
        UserFollowingUser userFollowingUser = userFollowingUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userFollowingUser));
    }

    /**
     * DELETE  /user-following-users/:id : delete the "id" userFollowingUser.
     *
     * @param id the id of the userFollowingUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-following-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserFollowingUser(@PathVariable Long id) {
        log.debug("REST request to delete UserFollowingUser : {}", id);
        userFollowingUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
