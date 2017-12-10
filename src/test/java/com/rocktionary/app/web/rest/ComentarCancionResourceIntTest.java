package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.ComentarCancion;
import com.rocktionary.app.repository.ComentarCancionRepository;
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
 * Test class for the ComentarCancionResource REST controller.
 *
 * @see ComentarCancionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class ComentarCancionResourceIntTest {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_COMENTARIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_COMENTARIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ComentarCancionRepository comentarCancionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComentarCancionMockMvc;

    private ComentarCancion comentarCancion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComentarCancionResource comentarCancionResource = new ComentarCancionResource(comentarCancionRepository);
        this.restComentarCancionMockMvc = MockMvcBuilders.standaloneSetup(comentarCancionResource)
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
    public static ComentarCancion createEntity(EntityManager em) {
        ComentarCancion comentarCancion = new ComentarCancion()
            .comentario(DEFAULT_COMENTARIO)
            .fechaComentario(DEFAULT_FECHA_COMENTARIO);
        return comentarCancion;
    }

    @Before
    public void initTest() {
        comentarCancion = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarCancion() throws Exception {
        int databaseSizeBeforeCreate = comentarCancionRepository.findAll().size();

        // Create the ComentarCancion
        restComentarCancionMockMvc.perform(post("/api/comentar-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarCancion)))
            .andExpect(status().isCreated());

        // Validate the ComentarCancion in the database
        List<ComentarCancion> comentarCancionList = comentarCancionRepository.findAll();
        assertThat(comentarCancionList).hasSize(databaseSizeBeforeCreate + 1);
        ComentarCancion testComentarCancion = comentarCancionList.get(comentarCancionList.size() - 1);
        assertThat(testComentarCancion.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarCancion.getFechaComentario()).isEqualTo(DEFAULT_FECHA_COMENTARIO);
    }

    @Test
    @Transactional
    public void createComentarCancionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarCancionRepository.findAll().size();

        // Create the ComentarCancion with an existing ID
        comentarCancion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarCancionMockMvc.perform(post("/api/comentar-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarCancion)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarCancion in the database
        List<ComentarCancion> comentarCancionList = comentarCancionRepository.findAll();
        assertThat(comentarCancionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComentarCancions() throws Exception {
        // Initialize the database
        comentarCancionRepository.saveAndFlush(comentarCancion);

        // Get all the comentarCancionList
        restComentarCancionMockMvc.perform(get("/api/comentar-cancions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarCancion.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fechaComentario").value(hasItem(sameInstant(DEFAULT_FECHA_COMENTARIO))));
    }

    @Test
    @Transactional
    public void getComentarCancion() throws Exception {
        // Initialize the database
        comentarCancionRepository.saveAndFlush(comentarCancion);

        // Get the comentarCancion
        restComentarCancionMockMvc.perform(get("/api/comentar-cancions/{id}", comentarCancion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarCancion.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.fechaComentario").value(sameInstant(DEFAULT_FECHA_COMENTARIO)));
    }

    @Test
    @Transactional
    public void getNonExistingComentarCancion() throws Exception {
        // Get the comentarCancion
        restComentarCancionMockMvc.perform(get("/api/comentar-cancions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarCancion() throws Exception {
        // Initialize the database
        comentarCancionRepository.saveAndFlush(comentarCancion);
        int databaseSizeBeforeUpdate = comentarCancionRepository.findAll().size();

        // Update the comentarCancion
        ComentarCancion updatedComentarCancion = comentarCancionRepository.findOne(comentarCancion.getId());
        updatedComentarCancion
            .comentario(UPDATED_COMENTARIO)
            .fechaComentario(UPDATED_FECHA_COMENTARIO);

        restComentarCancionMockMvc.perform(put("/api/comentar-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentarCancion)))
            .andExpect(status().isOk());

        // Validate the ComentarCancion in the database
        List<ComentarCancion> comentarCancionList = comentarCancionRepository.findAll();
        assertThat(comentarCancionList).hasSize(databaseSizeBeforeUpdate);
        ComentarCancion testComentarCancion = comentarCancionList.get(comentarCancionList.size() - 1);
        assertThat(testComentarCancion.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarCancion.getFechaComentario()).isEqualTo(UPDATED_FECHA_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarCancion() throws Exception {
        int databaseSizeBeforeUpdate = comentarCancionRepository.findAll().size();

        // Create the ComentarCancion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComentarCancionMockMvc.perform(put("/api/comentar-cancions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarCancion)))
            .andExpect(status().isCreated());

        // Validate the ComentarCancion in the database
        List<ComentarCancion> comentarCancionList = comentarCancionRepository.findAll();
        assertThat(comentarCancionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComentarCancion() throws Exception {
        // Initialize the database
        comentarCancionRepository.saveAndFlush(comentarCancion);
        int databaseSizeBeforeDelete = comentarCancionRepository.findAll().size();

        // Get the comentarCancion
        restComentarCancionMockMvc.perform(delete("/api/comentar-cancions/{id}", comentarCancion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ComentarCancion> comentarCancionList = comentarCancionRepository.findAll();
        assertThat(comentarCancionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarCancion.class);
        ComentarCancion comentarCancion1 = new ComentarCancion();
        comentarCancion1.setId(1L);
        ComentarCancion comentarCancion2 = new ComentarCancion();
        comentarCancion2.setId(comentarCancion1.getId());
        assertThat(comentarCancion1).isEqualTo(comentarCancion2);
        comentarCancion2.setId(2L);
        assertThat(comentarCancion1).isNotEqualTo(comentarCancion2);
        comentarCancion1.setId(null);
        assertThat(comentarCancion1).isNotEqualTo(comentarCancion2);
    }
}
