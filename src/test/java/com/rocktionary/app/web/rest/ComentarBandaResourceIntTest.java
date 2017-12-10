package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.ComentarBanda;
import com.rocktionary.app.repository.ComentarBandaRepository;
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
 * Test class for the ComentarBandaResource REST controller.
 *
 * @see ComentarBandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class ComentarBandaResourceIntTest {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_COMENTARIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_COMENTARIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ComentarBandaRepository comentarBandaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComentarBandaMockMvc;

    private ComentarBanda comentarBanda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComentarBandaResource comentarBandaResource = new ComentarBandaResource(comentarBandaRepository);
        this.restComentarBandaMockMvc = MockMvcBuilders.standaloneSetup(comentarBandaResource)
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
    public static ComentarBanda createEntity(EntityManager em) {
        ComentarBanda comentarBanda = new ComentarBanda()
            .comentario(DEFAULT_COMENTARIO)
            .fechaComentario(DEFAULT_FECHA_COMENTARIO);
        return comentarBanda;
    }

    @Before
    public void initTest() {
        comentarBanda = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarBanda() throws Exception {
        int databaseSizeBeforeCreate = comentarBandaRepository.findAll().size();

        // Create the ComentarBanda
        restComentarBandaMockMvc.perform(post("/api/comentar-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarBanda)))
            .andExpect(status().isCreated());

        // Validate the ComentarBanda in the database
        List<ComentarBanda> comentarBandaList = comentarBandaRepository.findAll();
        assertThat(comentarBandaList).hasSize(databaseSizeBeforeCreate + 1);
        ComentarBanda testComentarBanda = comentarBandaList.get(comentarBandaList.size() - 1);
        assertThat(testComentarBanda.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarBanda.getFechaComentario()).isEqualTo(DEFAULT_FECHA_COMENTARIO);
    }

    @Test
    @Transactional
    public void createComentarBandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarBandaRepository.findAll().size();

        // Create the ComentarBanda with an existing ID
        comentarBanda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarBandaMockMvc.perform(post("/api/comentar-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarBanda)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarBanda in the database
        List<ComentarBanda> comentarBandaList = comentarBandaRepository.findAll();
        assertThat(comentarBandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComentarBandas() throws Exception {
        // Initialize the database
        comentarBandaRepository.saveAndFlush(comentarBanda);

        // Get all the comentarBandaList
        restComentarBandaMockMvc.perform(get("/api/comentar-bandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarBanda.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fechaComentario").value(hasItem(sameInstant(DEFAULT_FECHA_COMENTARIO))));
    }

    @Test
    @Transactional
    public void getComentarBanda() throws Exception {
        // Initialize the database
        comentarBandaRepository.saveAndFlush(comentarBanda);

        // Get the comentarBanda
        restComentarBandaMockMvc.perform(get("/api/comentar-bandas/{id}", comentarBanda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarBanda.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.fechaComentario").value(sameInstant(DEFAULT_FECHA_COMENTARIO)));
    }

    @Test
    @Transactional
    public void getNonExistingComentarBanda() throws Exception {
        // Get the comentarBanda
        restComentarBandaMockMvc.perform(get("/api/comentar-bandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarBanda() throws Exception {
        // Initialize the database
        comentarBandaRepository.saveAndFlush(comentarBanda);
        int databaseSizeBeforeUpdate = comentarBandaRepository.findAll().size();

        // Update the comentarBanda
        ComentarBanda updatedComentarBanda = comentarBandaRepository.findOne(comentarBanda.getId());
        updatedComentarBanda
            .comentario(UPDATED_COMENTARIO)
            .fechaComentario(UPDATED_FECHA_COMENTARIO);

        restComentarBandaMockMvc.perform(put("/api/comentar-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentarBanda)))
            .andExpect(status().isOk());

        // Validate the ComentarBanda in the database
        List<ComentarBanda> comentarBandaList = comentarBandaRepository.findAll();
        assertThat(comentarBandaList).hasSize(databaseSizeBeforeUpdate);
        ComentarBanda testComentarBanda = comentarBandaList.get(comentarBandaList.size() - 1);
        assertThat(testComentarBanda.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarBanda.getFechaComentario()).isEqualTo(UPDATED_FECHA_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarBanda() throws Exception {
        int databaseSizeBeforeUpdate = comentarBandaRepository.findAll().size();

        // Create the ComentarBanda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComentarBandaMockMvc.perform(put("/api/comentar-bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarBanda)))
            .andExpect(status().isCreated());

        // Validate the ComentarBanda in the database
        List<ComentarBanda> comentarBandaList = comentarBandaRepository.findAll();
        assertThat(comentarBandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComentarBanda() throws Exception {
        // Initialize the database
        comentarBandaRepository.saveAndFlush(comentarBanda);
        int databaseSizeBeforeDelete = comentarBandaRepository.findAll().size();

        // Get the comentarBanda
        restComentarBandaMockMvc.perform(delete("/api/comentar-bandas/{id}", comentarBanda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ComentarBanda> comentarBandaList = comentarBandaRepository.findAll();
        assertThat(comentarBandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarBanda.class);
        ComentarBanda comentarBanda1 = new ComentarBanda();
        comentarBanda1.setId(1L);
        ComentarBanda comentarBanda2 = new ComentarBanda();
        comentarBanda2.setId(comentarBanda1.getId());
        assertThat(comentarBanda1).isEqualTo(comentarBanda2);
        comentarBanda2.setId(2L);
        assertThat(comentarBanda1).isNotEqualTo(comentarBanda2);
        comentarBanda1.setId(null);
        assertThat(comentarBanda1).isNotEqualTo(comentarBanda2);
    }
}
