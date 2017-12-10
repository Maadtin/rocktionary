package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.Cancion;
import com.rocktionary.app.repository.CancionRepository;
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
import java.util.List;

import static com.rocktionary.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CancionResource REST controller.
 *
 * @see CancionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class CancionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_DURACION = 1D;
    private static final Double UPDATED_DURACION = 2D;

    private static final String DEFAULT_LETRA = "AAAAAAAAAA";
    private static final String UPDATED_LETRA = "BBBBBBBBBB";

    @Autowired
    private CancionRepository cancionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCancionMockMvc;

    private Cancion cancion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CancionResource cancionResource = new CancionResource(cancionRepository);
        this.restCancionMockMvc = MockMvcBuilders.standaloneSetup(cancionResource)
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
    public static Cancion createEntity(EntityManager em) {
        Cancion cancion = new Cancion()
            .nombre(DEFAULT_NOMBRE)
            .duracion(DEFAULT_DURACION)
            .letra(DEFAULT_LETRA);
        return cancion;
    }

    @Before
    public void initTest() {
        cancion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCancion() throws Exception {
        int databaseSizeBeforeCreate = cancionRepository.findAll().size();

        // Create the Cancion
        restCancionMockMvc.perform(post("/api/cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancion)))
            .andExpect(status().isCreated());

        // Validate the Cancion in the database
        List<Cancion> cancionList = cancionRepository.findAll();
        assertThat(cancionList).hasSize(databaseSizeBeforeCreate + 1);
        Cancion testCancion = cancionList.get(cancionList.size() - 1);
        assertThat(testCancion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCancion.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testCancion.getLetra()).isEqualTo(DEFAULT_LETRA);
    }

    @Test
    @Transactional
    public void createCancionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cancionRepository.findAll().size();

        // Create the Cancion with an existing ID
        cancion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCancionMockMvc.perform(post("/api/cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancion)))
            .andExpect(status().isBadRequest());

        // Validate the Cancion in the database
        List<Cancion> cancionList = cancionRepository.findAll();
        assertThat(cancionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCancions() throws Exception {
        // Initialize the database
        cancionRepository.saveAndFlush(cancion);

        // Get all the cancionList
        restCancionMockMvc.perform(get("/api/cancions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION.doubleValue())))
            .andExpect(jsonPath("$.[*].letra").value(hasItem(DEFAULT_LETRA.toString())));
    }

    @Test
    @Transactional
    public void getCancion() throws Exception {
        // Initialize the database
        cancionRepository.saveAndFlush(cancion);

        // Get the cancion
        restCancionMockMvc.perform(get("/api/cancions/{id}", cancion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cancion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION.doubleValue()))
            .andExpect(jsonPath("$.letra").value(DEFAULT_LETRA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCancion() throws Exception {
        // Get the cancion
        restCancionMockMvc.perform(get("/api/cancions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCancion() throws Exception {
        // Initialize the database
        cancionRepository.saveAndFlush(cancion);
        int databaseSizeBeforeUpdate = cancionRepository.findAll().size();

        // Update the cancion
        Cancion updatedCancion = cancionRepository.findOne(cancion.getId());
        updatedCancion
            .nombre(UPDATED_NOMBRE)
            .duracion(UPDATED_DURACION)
            .letra(UPDATED_LETRA);

        restCancionMockMvc.perform(put("/api/cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCancion)))
            .andExpect(status().isOk());

        // Validate the Cancion in the database
        List<Cancion> cancionList = cancionRepository.findAll();
        assertThat(cancionList).hasSize(databaseSizeBeforeUpdate);
        Cancion testCancion = cancionList.get(cancionList.size() - 1);
        assertThat(testCancion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCancion.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testCancion.getLetra()).isEqualTo(UPDATED_LETRA);
    }

    @Test
    @Transactional
    public void updateNonExistingCancion() throws Exception {
        int databaseSizeBeforeUpdate = cancionRepository.findAll().size();

        // Create the Cancion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCancionMockMvc.perform(put("/api/cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cancion)))
            .andExpect(status().isCreated());

        // Validate the Cancion in the database
        List<Cancion> cancionList = cancionRepository.findAll();
        assertThat(cancionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCancion() throws Exception {
        // Initialize the database
        cancionRepository.saveAndFlush(cancion);
        int databaseSizeBeforeDelete = cancionRepository.findAll().size();

        // Get the cancion
        restCancionMockMvc.perform(delete("/api/cancions/{id}", cancion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cancion> cancionList = cancionRepository.findAll();
        assertThat(cancionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cancion.class);
        Cancion cancion1 = new Cancion();
        cancion1.setId(1L);
        Cancion cancion2 = new Cancion();
        cancion2.setId(cancion1.getId());
        assertThat(cancion1).isEqualTo(cancion2);
        cancion2.setId(2L);
        assertThat(cancion1).isNotEqualTo(cancion2);
        cancion1.setId(null);
        assertThat(cancion1).isNotEqualTo(cancion2);
    }
}
