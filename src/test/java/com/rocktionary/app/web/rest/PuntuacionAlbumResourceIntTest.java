package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.PuntuacionAlbum;
import com.rocktionary.app.repository.PuntuacionAlbumRepository;
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
 * Test class for the PuntuacionAlbumResource REST controller.
 *
 * @see PuntuacionAlbumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class PuntuacionAlbumResourceIntTest {

    private static final Integer DEFAULT_VALORACION = 1;
    private static final Integer UPDATED_VALORACION = 2;

    private static final ZonedDateTime DEFAULT_FECHA_PUNTUACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_PUNTUACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PuntuacionAlbumRepository puntuacionAlbumRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPuntuacionAlbumMockMvc;

    private PuntuacionAlbum puntuacionAlbum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuntuacionAlbumResource puntuacionAlbumResource = new PuntuacionAlbumResource(puntuacionAlbumRepository);
        this.restPuntuacionAlbumMockMvc = MockMvcBuilders.standaloneSetup(puntuacionAlbumResource)
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
    public static PuntuacionAlbum createEntity(EntityManager em) {
        PuntuacionAlbum puntuacionAlbum = new PuntuacionAlbum()
            .valoracion(DEFAULT_VALORACION)
            .fechaPuntuacion(DEFAULT_FECHA_PUNTUACION);
        return puntuacionAlbum;
    }

    @Before
    public void initTest() {
        puntuacionAlbum = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuntuacionAlbum() throws Exception {
        int databaseSizeBeforeCreate = puntuacionAlbumRepository.findAll().size();

        // Create the PuntuacionAlbum
        restPuntuacionAlbumMockMvc.perform(post("/api/puntuacion-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionAlbum)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionAlbum in the database
        List<PuntuacionAlbum> puntuacionAlbumList = puntuacionAlbumRepository.findAll();
        assertThat(puntuacionAlbumList).hasSize(databaseSizeBeforeCreate + 1);
        PuntuacionAlbum testPuntuacionAlbum = puntuacionAlbumList.get(puntuacionAlbumList.size() - 1);
        assertThat(testPuntuacionAlbum.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testPuntuacionAlbum.getFechaPuntuacion()).isEqualTo(DEFAULT_FECHA_PUNTUACION);
    }

    @Test
    @Transactional
    public void createPuntuacionAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puntuacionAlbumRepository.findAll().size();

        // Create the PuntuacionAlbum with an existing ID
        puntuacionAlbum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntuacionAlbumMockMvc.perform(post("/api/puntuacion-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionAlbum)))
            .andExpect(status().isBadRequest());

        // Validate the PuntuacionAlbum in the database
        List<PuntuacionAlbum> puntuacionAlbumList = puntuacionAlbumRepository.findAll();
        assertThat(puntuacionAlbumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuntuacionAlbums() throws Exception {
        // Initialize the database
        puntuacionAlbumRepository.saveAndFlush(puntuacionAlbum);

        // Get all the puntuacionAlbumList
        restPuntuacionAlbumMockMvc.perform(get("/api/puntuacion-albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntuacionAlbum.getId().intValue())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION)))
            .andExpect(jsonPath("$.[*].fechaPuntuacion").value(hasItem(sameInstant(DEFAULT_FECHA_PUNTUACION))));
    }

    @Test
    @Transactional
    public void getPuntuacionAlbum() throws Exception {
        // Initialize the database
        puntuacionAlbumRepository.saveAndFlush(puntuacionAlbum);

        // Get the puntuacionAlbum
        restPuntuacionAlbumMockMvc.perform(get("/api/puntuacion-albums/{id}", puntuacionAlbum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puntuacionAlbum.getId().intValue()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION))
            .andExpect(jsonPath("$.fechaPuntuacion").value(sameInstant(DEFAULT_FECHA_PUNTUACION)));
    }

    @Test
    @Transactional
    public void getNonExistingPuntuacionAlbum() throws Exception {
        // Get the puntuacionAlbum
        restPuntuacionAlbumMockMvc.perform(get("/api/puntuacion-albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuntuacionAlbum() throws Exception {
        // Initialize the database
        puntuacionAlbumRepository.saveAndFlush(puntuacionAlbum);
        int databaseSizeBeforeUpdate = puntuacionAlbumRepository.findAll().size();

        // Update the puntuacionAlbum
        PuntuacionAlbum updatedPuntuacionAlbum = puntuacionAlbumRepository.findOne(puntuacionAlbum.getId());
        updatedPuntuacionAlbum
            .valoracion(UPDATED_VALORACION)
            .fechaPuntuacion(UPDATED_FECHA_PUNTUACION);

        restPuntuacionAlbumMockMvc.perform(put("/api/puntuacion-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPuntuacionAlbum)))
            .andExpect(status().isOk());

        // Validate the PuntuacionAlbum in the database
        List<PuntuacionAlbum> puntuacionAlbumList = puntuacionAlbumRepository.findAll();
        assertThat(puntuacionAlbumList).hasSize(databaseSizeBeforeUpdate);
        PuntuacionAlbum testPuntuacionAlbum = puntuacionAlbumList.get(puntuacionAlbumList.size() - 1);
        assertThat(testPuntuacionAlbum.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testPuntuacionAlbum.getFechaPuntuacion()).isEqualTo(UPDATED_FECHA_PUNTUACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPuntuacionAlbum() throws Exception {
        int databaseSizeBeforeUpdate = puntuacionAlbumRepository.findAll().size();

        // Create the PuntuacionAlbum

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPuntuacionAlbumMockMvc.perform(put("/api/puntuacion-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionAlbum)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionAlbum in the database
        List<PuntuacionAlbum> puntuacionAlbumList = puntuacionAlbumRepository.findAll();
        assertThat(puntuacionAlbumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePuntuacionAlbum() throws Exception {
        // Initialize the database
        puntuacionAlbumRepository.saveAndFlush(puntuacionAlbum);
        int databaseSizeBeforeDelete = puntuacionAlbumRepository.findAll().size();

        // Get the puntuacionAlbum
        restPuntuacionAlbumMockMvc.perform(delete("/api/puntuacion-albums/{id}", puntuacionAlbum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PuntuacionAlbum> puntuacionAlbumList = puntuacionAlbumRepository.findAll();
        assertThat(puntuacionAlbumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntuacionAlbum.class);
        PuntuacionAlbum puntuacionAlbum1 = new PuntuacionAlbum();
        puntuacionAlbum1.setId(1L);
        PuntuacionAlbum puntuacionAlbum2 = new PuntuacionAlbum();
        puntuacionAlbum2.setId(puntuacionAlbum1.getId());
        assertThat(puntuacionAlbum1).isEqualTo(puntuacionAlbum2);
        puntuacionAlbum2.setId(2L);
        assertThat(puntuacionAlbum1).isNotEqualTo(puntuacionAlbum2);
        puntuacionAlbum1.setId(null);
        assertThat(puntuacionAlbum1).isNotEqualTo(puntuacionAlbum2);
    }
}
