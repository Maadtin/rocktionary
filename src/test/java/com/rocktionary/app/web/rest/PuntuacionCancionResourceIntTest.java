package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.PuntuacionCancion;
import com.rocktionary.app.repository.PuntuacionCancionRepository;
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
 * Test class for the PuntuacionCancionResource REST controller.
 *
 * @see PuntuacionCancionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class PuntuacionCancionResourceIntTest {

    private static final Integer DEFAULT_VALORACION = 1;
    private static final Integer UPDATED_VALORACION = 2;

    private static final ZonedDateTime DEFAULT_FECHA_PUNTUACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_PUNTUACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PuntuacionCancionRepository puntuacionCancionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPuntuacionCancionMockMvc;

    private PuntuacionCancion puntuacionCancion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuntuacionCancionResource puntuacionCancionResource = new PuntuacionCancionResource(puntuacionCancionRepository);
        this.restPuntuacionCancionMockMvc = MockMvcBuilders.standaloneSetup(puntuacionCancionResource)
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
    public static PuntuacionCancion createEntity(EntityManager em) {
        PuntuacionCancion puntuacionCancion = new PuntuacionCancion()
            .valoracion(DEFAULT_VALORACION)
            .fechaPuntuacion(DEFAULT_FECHA_PUNTUACION);
        return puntuacionCancion;
    }

    @Before
    public void initTest() {
        puntuacionCancion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuntuacionCancion() throws Exception {
        int databaseSizeBeforeCreate = puntuacionCancionRepository.findAll().size();

        // Create the PuntuacionCancion
        restPuntuacionCancionMockMvc.perform(post("/api/puntuacion-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionCancion)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionCancion in the database
        List<PuntuacionCancion> puntuacionCancionList = puntuacionCancionRepository.findAll();
        assertThat(puntuacionCancionList).hasSize(databaseSizeBeforeCreate + 1);
        PuntuacionCancion testPuntuacionCancion = puntuacionCancionList.get(puntuacionCancionList.size() - 1);
        assertThat(testPuntuacionCancion.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testPuntuacionCancion.getFechaPuntuacion()).isEqualTo(DEFAULT_FECHA_PUNTUACION);
    }

    @Test
    @Transactional
    public void createPuntuacionCancionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puntuacionCancionRepository.findAll().size();

        // Create the PuntuacionCancion with an existing ID
        puntuacionCancion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntuacionCancionMockMvc.perform(post("/api/puntuacion-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionCancion)))
            .andExpect(status().isBadRequest());

        // Validate the PuntuacionCancion in the database
        List<PuntuacionCancion> puntuacionCancionList = puntuacionCancionRepository.findAll();
        assertThat(puntuacionCancionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuntuacionCancions() throws Exception {
        // Initialize the database
        puntuacionCancionRepository.saveAndFlush(puntuacionCancion);

        // Get all the puntuacionCancionList
        restPuntuacionCancionMockMvc.perform(get("/api/puntuacion-cancions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntuacionCancion.getId().intValue())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION)))
            .andExpect(jsonPath("$.[*].fechaPuntuacion").value(hasItem(sameInstant(DEFAULT_FECHA_PUNTUACION))));
    }

    @Test
    @Transactional
    public void getPuntuacionCancion() throws Exception {
        // Initialize the database
        puntuacionCancionRepository.saveAndFlush(puntuacionCancion);

        // Get the puntuacionCancion
        restPuntuacionCancionMockMvc.perform(get("/api/puntuacion-cancions/{id}", puntuacionCancion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puntuacionCancion.getId().intValue()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION))
            .andExpect(jsonPath("$.fechaPuntuacion").value(sameInstant(DEFAULT_FECHA_PUNTUACION)));
    }

    @Test
    @Transactional
    public void getNonExistingPuntuacionCancion() throws Exception {
        // Get the puntuacionCancion
        restPuntuacionCancionMockMvc.perform(get("/api/puntuacion-cancions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuntuacionCancion() throws Exception {
        // Initialize the database
        puntuacionCancionRepository.saveAndFlush(puntuacionCancion);
        int databaseSizeBeforeUpdate = puntuacionCancionRepository.findAll().size();

        // Update the puntuacionCancion
        PuntuacionCancion updatedPuntuacionCancion = puntuacionCancionRepository.findOne(puntuacionCancion.getId());
        updatedPuntuacionCancion
            .valoracion(UPDATED_VALORACION)
            .fechaPuntuacion(UPDATED_FECHA_PUNTUACION);

        restPuntuacionCancionMockMvc.perform(put("/api/puntuacion-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPuntuacionCancion)))
            .andExpect(status().isOk());

        // Validate the PuntuacionCancion in the database
        List<PuntuacionCancion> puntuacionCancionList = puntuacionCancionRepository.findAll();
        assertThat(puntuacionCancionList).hasSize(databaseSizeBeforeUpdate);
        PuntuacionCancion testPuntuacionCancion = puntuacionCancionList.get(puntuacionCancionList.size() - 1);
        assertThat(testPuntuacionCancion.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testPuntuacionCancion.getFechaPuntuacion()).isEqualTo(UPDATED_FECHA_PUNTUACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPuntuacionCancion() throws Exception {
        int databaseSizeBeforeUpdate = puntuacionCancionRepository.findAll().size();

        // Create the PuntuacionCancion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPuntuacionCancionMockMvc.perform(put("/api/puntuacion-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionCancion)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionCancion in the database
        List<PuntuacionCancion> puntuacionCancionList = puntuacionCancionRepository.findAll();
        assertThat(puntuacionCancionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePuntuacionCancion() throws Exception {
        // Initialize the database
        puntuacionCancionRepository.saveAndFlush(puntuacionCancion);
        int databaseSizeBeforeDelete = puntuacionCancionRepository.findAll().size();

        // Get the puntuacionCancion
        restPuntuacionCancionMockMvc.perform(delete("/api/puntuacion-cancions/{id}", puntuacionCancion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PuntuacionCancion> puntuacionCancionList = puntuacionCancionRepository.findAll();
        assertThat(puntuacionCancionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntuacionCancion.class);
        PuntuacionCancion puntuacionCancion1 = new PuntuacionCancion();
        puntuacionCancion1.setId(1L);
        PuntuacionCancion puntuacionCancion2 = new PuntuacionCancion();
        puntuacionCancion2.setId(puntuacionCancion1.getId());
        assertThat(puntuacionCancion1).isEqualTo(puntuacionCancion2);
        puntuacionCancion2.setId(2L);
        assertThat(puntuacionCancion1).isNotEqualTo(puntuacionCancion2);
        puntuacionCancion1.setId(null);
        assertThat(puntuacionCancion1).isNotEqualTo(puntuacionCancion2);
    }
}
