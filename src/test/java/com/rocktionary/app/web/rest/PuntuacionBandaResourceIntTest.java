package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.PuntuacionBanda;
import com.rocktionary.app.repository.PuntuacionBandaRepository;
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
 * Test class for the PuntuacionBandaResource REST controller.
 *
 * @see PuntuacionBandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class PuntuacionBandaResourceIntTest {

    private static final Integer DEFAULT_VALORACION = 1;
    private static final Integer UPDATED_VALORACION = 2;

    private static final ZonedDateTime DEFAULT_FECHA_PUNTUACION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_PUNTUACION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PuntuacionBandaRepository puntuacionBandaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPuntuacionBandaMockMvc;

    private PuntuacionBanda puntuacionBanda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PuntuacionBandaResource puntuacionBandaResource = new PuntuacionBandaResource(puntuacionBandaRepository);
        this.restPuntuacionBandaMockMvc = MockMvcBuilders.standaloneSetup(puntuacionBandaResource)
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
    public static PuntuacionBanda createEntity(EntityManager em) {
        PuntuacionBanda puntuacionBanda = new PuntuacionBanda()
            .valoracion(DEFAULT_VALORACION)
            .fechaPuntuacion(DEFAULT_FECHA_PUNTUACION);
        return puntuacionBanda;
    }

    @Before
    public void initTest() {
        puntuacionBanda = createEntity(em);
    }

    @Test
    @Transactional
    public void createPuntuacionBanda() throws Exception {
        int databaseSizeBeforeCreate = puntuacionBandaRepository.findAll().size();

        // Create the PuntuacionBanda
        restPuntuacionBandaMockMvc.perform(post("/api/puntuacion-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionBanda)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionBanda in the database
        List<PuntuacionBanda> puntuacionBandaList = puntuacionBandaRepository.findAll();
        assertThat(puntuacionBandaList).hasSize(databaseSizeBeforeCreate + 1);
        PuntuacionBanda testPuntuacionBanda = puntuacionBandaList.get(puntuacionBandaList.size() - 1);
        assertThat(testPuntuacionBanda.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testPuntuacionBanda.getFechaPuntuacion()).isEqualTo(DEFAULT_FECHA_PUNTUACION);
    }

    @Test
    @Transactional
    public void createPuntuacionBandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = puntuacionBandaRepository.findAll().size();

        // Create the PuntuacionBanda with an existing ID
        puntuacionBanda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuntuacionBandaMockMvc.perform(post("/api/puntuacion-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionBanda)))
            .andExpect(status().isBadRequest());

        // Validate the PuntuacionBanda in the database
        List<PuntuacionBanda> puntuacionBandaList = puntuacionBandaRepository.findAll();
        assertThat(puntuacionBandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPuntuacionBandas() throws Exception {
        // Initialize the database
        puntuacionBandaRepository.saveAndFlush(puntuacionBanda);

        // Get all the puntuacionBandaList
        restPuntuacionBandaMockMvc.perform(get("/api/puntuacion-bandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puntuacionBanda.getId().intValue())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION)))
            .andExpect(jsonPath("$.[*].fechaPuntuacion").value(hasItem(sameInstant(DEFAULT_FECHA_PUNTUACION))));
    }

    @Test
    @Transactional
    public void getPuntuacionBanda() throws Exception {
        // Initialize the database
        puntuacionBandaRepository.saveAndFlush(puntuacionBanda);

        // Get the puntuacionBanda
        restPuntuacionBandaMockMvc.perform(get("/api/puntuacion-bandas/{id}", puntuacionBanda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(puntuacionBanda.getId().intValue()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION))
            .andExpect(jsonPath("$.fechaPuntuacion").value(sameInstant(DEFAULT_FECHA_PUNTUACION)));
    }

    @Test
    @Transactional
    public void getNonExistingPuntuacionBanda() throws Exception {
        // Get the puntuacionBanda
        restPuntuacionBandaMockMvc.perform(get("/api/puntuacion-bandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePuntuacionBanda() throws Exception {
        // Initialize the database
        puntuacionBandaRepository.saveAndFlush(puntuacionBanda);
        int databaseSizeBeforeUpdate = puntuacionBandaRepository.findAll().size();

        // Update the puntuacionBanda
        PuntuacionBanda updatedPuntuacionBanda = puntuacionBandaRepository.findOne(puntuacionBanda.getId());
        updatedPuntuacionBanda
            .valoracion(UPDATED_VALORACION)
            .fechaPuntuacion(UPDATED_FECHA_PUNTUACION);

        restPuntuacionBandaMockMvc.perform(put("/api/puntuacion-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPuntuacionBanda)))
            .andExpect(status().isOk());

        // Validate the PuntuacionBanda in the database
        List<PuntuacionBanda> puntuacionBandaList = puntuacionBandaRepository.findAll();
        assertThat(puntuacionBandaList).hasSize(databaseSizeBeforeUpdate);
        PuntuacionBanda testPuntuacionBanda = puntuacionBandaList.get(puntuacionBandaList.size() - 1);
        assertThat(testPuntuacionBanda.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testPuntuacionBanda.getFechaPuntuacion()).isEqualTo(UPDATED_FECHA_PUNTUACION);
    }

    @Test
    @Transactional
    public void updateNonExistingPuntuacionBanda() throws Exception {
        int databaseSizeBeforeUpdate = puntuacionBandaRepository.findAll().size();

        // Create the PuntuacionBanda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPuntuacionBandaMockMvc.perform(put("/api/puntuacion-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(puntuacionBanda)))
            .andExpect(status().isCreated());

        // Validate the PuntuacionBanda in the database
        List<PuntuacionBanda> puntuacionBandaList = puntuacionBandaRepository.findAll();
        assertThat(puntuacionBandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePuntuacionBanda() throws Exception {
        // Initialize the database
        puntuacionBandaRepository.saveAndFlush(puntuacionBanda);
        int databaseSizeBeforeDelete = puntuacionBandaRepository.findAll().size();

        // Get the puntuacionBanda
        restPuntuacionBandaMockMvc.perform(delete("/api/puntuacion-bandas/{id}", puntuacionBanda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PuntuacionBanda> puntuacionBandaList = puntuacionBandaRepository.findAll();
        assertThat(puntuacionBandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PuntuacionBanda.class);
        PuntuacionBanda puntuacionBanda1 = new PuntuacionBanda();
        puntuacionBanda1.setId(1L);
        PuntuacionBanda puntuacionBanda2 = new PuntuacionBanda();
        puntuacionBanda2.setId(puntuacionBanda1.getId());
        assertThat(puntuacionBanda1).isEqualTo(puntuacionBanda2);
        puntuacionBanda2.setId(2L);
        assertThat(puntuacionBanda1).isNotEqualTo(puntuacionBanda2);
        puntuacionBanda1.setId(null);
        assertThat(puntuacionBanda1).isNotEqualTo(puntuacionBanda2);
    }
}
