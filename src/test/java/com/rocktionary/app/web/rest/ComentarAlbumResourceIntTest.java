package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.ComentarAlbum;
import com.rocktionary.app.repository.ComentarAlbumRepository;
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
 * Test class for the ComentarAlbumResource REST controller.
 *
 * @see ComentarAlbumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class ComentarAlbumResourceIntTest {

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_COMENTARIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_COMENTARIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ComentarAlbumRepository comentarAlbumRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComentarAlbumMockMvc;

    private ComentarAlbum comentarAlbum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComentarAlbumResource comentarAlbumResource = new ComentarAlbumResource(comentarAlbumRepository);
        this.restComentarAlbumMockMvc = MockMvcBuilders.standaloneSetup(comentarAlbumResource)
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
    public static ComentarAlbum createEntity(EntityManager em) {
        ComentarAlbum comentarAlbum = new ComentarAlbum()
            .comentario(DEFAULT_COMENTARIO)
            .fechaComentario(DEFAULT_FECHA_COMENTARIO);
        return comentarAlbum;
    }

    @Before
    public void initTest() {
        comentarAlbum = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarAlbum() throws Exception {
        int databaseSizeBeforeCreate = comentarAlbumRepository.findAll().size();

        // Create the ComentarAlbum
        restComentarAlbumMockMvc.perform(post("/api/comentar-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarAlbum)))
            .andExpect(status().isCreated());

        // Validate the ComentarAlbum in the database
        List<ComentarAlbum> comentarAlbumList = comentarAlbumRepository.findAll();
        assertThat(comentarAlbumList).hasSize(databaseSizeBeforeCreate + 1);
        ComentarAlbum testComentarAlbum = comentarAlbumList.get(comentarAlbumList.size() - 1);
        assertThat(testComentarAlbum.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testComentarAlbum.getFechaComentario()).isEqualTo(DEFAULT_FECHA_COMENTARIO);
    }

    @Test
    @Transactional
    public void createComentarAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comentarAlbumRepository.findAll().size();

        // Create the ComentarAlbum with an existing ID
        comentarAlbum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComentarAlbumMockMvc.perform(post("/api/comentar-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarAlbum)))
            .andExpect(status().isBadRequest());

        // Validate the ComentarAlbum in the database
        List<ComentarAlbum> comentarAlbumList = comentarAlbumRepository.findAll();
        assertThat(comentarAlbumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComentarAlbums() throws Exception {
        // Initialize the database
        comentarAlbumRepository.saveAndFlush(comentarAlbum);

        // Get all the comentarAlbumList
        restComentarAlbumMockMvc.perform(get("/api/comentar-albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comentarAlbum.getId().intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].fechaComentario").value(hasItem(sameInstant(DEFAULT_FECHA_COMENTARIO))));
    }

    @Test
    @Transactional
    public void getComentarAlbum() throws Exception {
        // Initialize the database
        comentarAlbumRepository.saveAndFlush(comentarAlbum);

        // Get the comentarAlbum
        restComentarAlbumMockMvc.perform(get("/api/comentar-albums/{id}", comentarAlbum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarAlbum.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.fechaComentario").value(sameInstant(DEFAULT_FECHA_COMENTARIO)));
    }

    @Test
    @Transactional
    public void getNonExistingComentarAlbum() throws Exception {
        // Get the comentarAlbum
        restComentarAlbumMockMvc.perform(get("/api/comentar-albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarAlbum() throws Exception {
        // Initialize the database
        comentarAlbumRepository.saveAndFlush(comentarAlbum);
        int databaseSizeBeforeUpdate = comentarAlbumRepository.findAll().size();

        // Update the comentarAlbum
        ComentarAlbum updatedComentarAlbum = comentarAlbumRepository.findOne(comentarAlbum.getId());
        updatedComentarAlbum
            .comentario(UPDATED_COMENTARIO)
            .fechaComentario(UPDATED_FECHA_COMENTARIO);

        restComentarAlbumMockMvc.perform(put("/api/comentar-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComentarAlbum)))
            .andExpect(status().isOk());

        // Validate the ComentarAlbum in the database
        List<ComentarAlbum> comentarAlbumList = comentarAlbumRepository.findAll();
        assertThat(comentarAlbumList).hasSize(databaseSizeBeforeUpdate);
        ComentarAlbum testComentarAlbum = comentarAlbumList.get(comentarAlbumList.size() - 1);
        assertThat(testComentarAlbum.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testComentarAlbum.getFechaComentario()).isEqualTo(UPDATED_FECHA_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingComentarAlbum() throws Exception {
        int databaseSizeBeforeUpdate = comentarAlbumRepository.findAll().size();

        // Create the ComentarAlbum

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComentarAlbumMockMvc.perform(put("/api/comentar-albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comentarAlbum)))
            .andExpect(status().isCreated());

        // Validate the ComentarAlbum in the database
        List<ComentarAlbum> comentarAlbumList = comentarAlbumRepository.findAll();
        assertThat(comentarAlbumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComentarAlbum() throws Exception {
        // Initialize the database
        comentarAlbumRepository.saveAndFlush(comentarAlbum);
        int databaseSizeBeforeDelete = comentarAlbumRepository.findAll().size();

        // Get the comentarAlbum
        restComentarAlbumMockMvc.perform(delete("/api/comentar-albums/{id}", comentarAlbum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ComentarAlbum> comentarAlbumList = comentarAlbumRepository.findAll();
        assertThat(comentarAlbumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComentarAlbum.class);
        ComentarAlbum comentarAlbum1 = new ComentarAlbum();
        comentarAlbum1.setId(1L);
        ComentarAlbum comentarAlbum2 = new ComentarAlbum();
        comentarAlbum2.setId(comentarAlbum1.getId());
        assertThat(comentarAlbum1).isEqualTo(comentarAlbum2);
        comentarAlbum2.setId(2L);
        assertThat(comentarAlbum1).isNotEqualTo(comentarAlbum2);
        comentarAlbum1.setId(null);
        assertThat(comentarAlbum1).isNotEqualTo(comentarAlbum2);
    }
}
