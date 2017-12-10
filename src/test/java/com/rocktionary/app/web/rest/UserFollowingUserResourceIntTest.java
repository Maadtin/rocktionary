package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.UserFollowingUser;
import com.rocktionary.app.repository.UserFollowingUserRepository;
import com.rocktionary.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.rocktionary.app.web.rest.TestUtil.sameInstant;
import static com.rocktionary.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserFollowingUserResource REST controller.
 *
 * @see UserFollowingUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class UserFollowingUserResourceIntTest {

    private static final ZonedDateTime DEFAULT_SINCE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SINCE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserFollowingUserRepository userFollowingUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserFollowingUserMockMvc;

    private UserFollowingUser userFollowingUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserFollowingUserResource userFollowingUserResource = new UserFollowingUserResource(userFollowingUserRepository);
        this.restUserFollowingUserMockMvc = MockMvcBuilders.standaloneSetup(userFollowingUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserFollowingUser createEntity(EntityManager em) {
        UserFollowingUser userFollowingUser = new UserFollowingUser()
            .since(DEFAULT_SINCE);
        return userFollowingUser;
    }

    @Before
    public void initTest() {
        userFollowingUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserFollowingUser() throws Exception {
        int databaseSizeBeforeCreate = userFollowingUserRepository.findAll().size();

        // Create the UserFollowingUser
        restUserFollowingUserMockMvc.perform(post("/api/user-following-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFollowingUser)))
            .andExpect(status().isCreated());

        // Validate the UserFollowingUser in the database
        List<UserFollowingUser> userFollowingUserList = userFollowingUserRepository.findAll();
        assertThat(userFollowingUserList).hasSize(databaseSizeBeforeCreate + 1);
        UserFollowingUser testUserFollowingUser = userFollowingUserList.get(userFollowingUserList.size() - 1);
        assertThat(testUserFollowingUser.getSince()).isEqualTo(DEFAULT_SINCE);
    }

    @Test
    @Transactional
    public void createUserFollowingUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userFollowingUserRepository.findAll().size();

        // Create the UserFollowingUser with an existing ID
        userFollowingUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserFollowingUserMockMvc.perform(post("/api/user-following-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFollowingUser)))
            .andExpect(status().isBadRequest());

        // Validate the UserFollowingUser in the database
        List<UserFollowingUser> userFollowingUserList = userFollowingUserRepository.findAll();
        assertThat(userFollowingUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserFollowingUsers() throws Exception {
        // Initialize the database
        userFollowingUserRepository.saveAndFlush(userFollowingUser);

        // Get all the userFollowingUserList
        restUserFollowingUserMockMvc.perform(get("/api/user-following-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userFollowingUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].since").value(hasItem(sameInstant(DEFAULT_SINCE))));
    }

    @Test
    @Transactional
    public void getUserFollowingUser() throws Exception {
        // Initialize the database
        userFollowingUserRepository.saveAndFlush(userFollowingUser);

        // Get the userFollowingUser
        restUserFollowingUserMockMvc.perform(get("/api/user-following-users/{id}", userFollowingUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userFollowingUser.getId().intValue()))
            .andExpect(jsonPath("$.since").value(sameInstant(DEFAULT_SINCE)));
    }

    @Test
    @Transactional
    public void getNonExistingUserFollowingUser() throws Exception {
        // Get the userFollowingUser
        restUserFollowingUserMockMvc.perform(get("/api/user-following-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserFollowingUser() throws Exception {
        // Initialize the database
        userFollowingUserRepository.saveAndFlush(userFollowingUser);
        int databaseSizeBeforeUpdate = userFollowingUserRepository.findAll().size();

        // Update the userFollowingUser
        UserFollowingUser updatedUserFollowingUser = userFollowingUserRepository.findOne(userFollowingUser.getId());
        updatedUserFollowingUser
            .since(UPDATED_SINCE);

        restUserFollowingUserMockMvc.perform(put("/api/user-following-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserFollowingUser)))
            .andExpect(status().isOk());

        // Validate the UserFollowingUser in the database
        List<UserFollowingUser> userFollowingUserList = userFollowingUserRepository.findAll();
        assertThat(userFollowingUserList).hasSize(databaseSizeBeforeUpdate);
        UserFollowingUser testUserFollowingUser = userFollowingUserList.get(userFollowingUserList.size() - 1);
        assertThat(testUserFollowingUser.getSince()).isEqualTo(UPDATED_SINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserFollowingUser() throws Exception {
        int databaseSizeBeforeUpdate = userFollowingUserRepository.findAll().size();

        // Create the UserFollowingUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserFollowingUserMockMvc.perform(put("/api/user-following-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userFollowingUser)))
            .andExpect(status().isCreated());

        // Validate the UserFollowingUser in the database
        List<UserFollowingUser> userFollowingUserList = userFollowingUserRepository.findAll();
        assertThat(userFollowingUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserFollowingUser() throws Exception {
        // Initialize the database
        userFollowingUserRepository.saveAndFlush(userFollowingUser);
        int databaseSizeBeforeDelete = userFollowingUserRepository.findAll().size();

        // Get the userFollowingUser
        restUserFollowingUserMockMvc.perform(delete("/api/user-following-users/{id}", userFollowingUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserFollowingUser> userFollowingUserList = userFollowingUserRepository.findAll();
        assertThat(userFollowingUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserFollowingUser.class);
        UserFollowingUser userFollowingUser1 = new UserFollowingUser();
        userFollowingUser1.setId(1L);
        UserFollowingUser userFollowingUser2 = new UserFollowingUser();
        userFollowingUser2.setId(userFollowingUser1.getId());
        assertThat(userFollowingUser1).isEqualTo(userFollowingUser2);
        userFollowingUser2.setId(2L);
        assertThat(userFollowingUser1).isNotEqualTo(userFollowingUser2);
        userFollowingUser1.setId(null);
        assertThat(userFollowingUser1).isNotEqualTo(userFollowingUser2);
    }
}
