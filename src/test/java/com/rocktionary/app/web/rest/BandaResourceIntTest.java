package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.Banda;
import com.rocktionary.app.repository.BandaRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.rocktionary.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BandaResource REST controller.
 *
 * @see BandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class BandaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_LOCALIZACION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATACREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATACREACION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ANOSACTIVO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANOSACTIVO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TEMAS = "AAAAAAAAAA";
    private static final String UPDATED_TEMAS = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOGRAFICA = "AAAAAAAAAA";
    private static final String UPDATED_DISCOGRAFICA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_BIOGRAFIA = "AAAAAAAAAA";
    private static final String UPDATED_BIOGRAFIA = "BBBBBBBBBB";

    @Autowired
    private BandaRepository bandaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBandaMockMvc;

    private Banda banda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BandaResource bandaResource = new BandaResource(bandaRepository);
        this.restBandaMockMvc = MockMvcBuilders.standaloneSetup(bandaResource)
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
    public static Banda createEntity(EntityManager em) {
        Banda banda = new Banda()
            .nombre(DEFAULT_NOMBRE)
            .localizacion(DEFAULT_LOCALIZACION)
            .datacreacion(DEFAULT_DATACREACION)
            .anosactivo(DEFAULT_ANOSACTIVO)
            .temas(DEFAULT_TEMAS)
            .discografica(DEFAULT_DISCOGRAFICA)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .pais(DEFAULT_PAIS)
            .estado(DEFAULT_ESTADO)
            .genero(DEFAULT_GENERO)
            .biografia(DEFAULT_BIOGRAFIA);
        return banda;
    }

    @Before
    public void initTest() {
        banda = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanda() throws Exception {
        int databaseSizeBeforeCreate = bandaRepository.findAll().size();

        // Create the Banda
        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banda)))
            .andExpect(status().isCreated());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeCreate + 1);
        Banda testBanda = bandaList.get(bandaList.size() - 1);
        assertThat(testBanda.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testBanda.getLocalizacion()).isEqualTo(DEFAULT_LOCALIZACION);
        assertThat(testBanda.getDatacreacion()).isEqualTo(DEFAULT_DATACREACION);
        assertThat(testBanda.getAnosactivo()).isEqualTo(DEFAULT_ANOSACTIVO);
        assertThat(testBanda.getTemas()).isEqualTo(DEFAULT_TEMAS);
        assertThat(testBanda.getDiscografica()).isEqualTo(DEFAULT_DISCOGRAFICA);
        assertThat(testBanda.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testBanda.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testBanda.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBanda.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testBanda.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testBanda.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testBanda.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testBanda.getBiografia()).isEqualTo(DEFAULT_BIOGRAFIA);
    }

    @Test
    @Transactional
    public void createBandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bandaRepository.findAll().size();

        // Create the Banda with an existing ID
        banda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBandaMockMvc.perform(post("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banda)))
            .andExpect(status().isBadRequest());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBandas() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);

        // Get all the bandaList
        restBandaMockMvc.perform(get("/api/bandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banda.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].localizacion").value(hasItem(DEFAULT_LOCALIZACION.toString())))
            .andExpect(jsonPath("$.[*].datacreacion").value(hasItem(DEFAULT_DATACREACION.toString())))
            .andExpect(jsonPath("$.[*].anosactivo").value(hasItem(DEFAULT_ANOSACTIVO.toString())))
            .andExpect(jsonPath("$.[*].temas").value(hasItem(DEFAULT_TEMAS.toString())))
            .andExpect(jsonPath("$.[*].discografica").value(hasItem(DEFAULT_DISCOGRAFICA.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].biografia").value(hasItem(DEFAULT_BIOGRAFIA.toString())));
    }

    @Test
    @Transactional
    public void getBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);

        // Get the banda
        restBandaMockMvc.perform(get("/api/bandas/{id}", banda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banda.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.localizacion").value(DEFAULT_LOCALIZACION.toString()))
            .andExpect(jsonPath("$.datacreacion").value(DEFAULT_DATACREACION.toString()))
            .andExpect(jsonPath("$.anosactivo").value(DEFAULT_ANOSACTIVO.toString()))
            .andExpect(jsonPath("$.temas").value(DEFAULT_TEMAS.toString()))
            .andExpect(jsonPath("$.discografica").value(DEFAULT_DISCOGRAFICA.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.biografia").value(DEFAULT_BIOGRAFIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBanda() throws Exception {
        // Get the banda
        restBandaMockMvc.perform(get("/api/bandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);
        int databaseSizeBeforeUpdate = bandaRepository.findAll().size();

        // Update the banda
        Banda updatedBanda = bandaRepository.findOne(banda.getId());
        updatedBanda
            .nombre(UPDATED_NOMBRE)
            .localizacion(UPDATED_LOCALIZACION)
            .datacreacion(UPDATED_DATACREACION)
            .anosactivo(UPDATED_ANOSACTIVO)
            .temas(UPDATED_TEMAS)
            .discografica(UPDATED_DISCOGRAFICA)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .genero(UPDATED_GENERO)
            .biografia(UPDATED_BIOGRAFIA);

        restBandaMockMvc.perform(put("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanda)))
            .andExpect(status().isOk());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeUpdate);
        Banda testBanda = bandaList.get(bandaList.size() - 1);
        assertThat(testBanda.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testBanda.getLocalizacion()).isEqualTo(UPDATED_LOCALIZACION);
        assertThat(testBanda.getDatacreacion()).isEqualTo(UPDATED_DATACREACION);
        assertThat(testBanda.getAnosactivo()).isEqualTo(UPDATED_ANOSACTIVO);
        assertThat(testBanda.getTemas()).isEqualTo(UPDATED_TEMAS);
        assertThat(testBanda.getDiscografica()).isEqualTo(UPDATED_DISCOGRAFICA);
        assertThat(testBanda.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testBanda.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testBanda.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBanda.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testBanda.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testBanda.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testBanda.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testBanda.getBiografia()).isEqualTo(UPDATED_BIOGRAFIA);
    }

    @Test
    @Transactional
    public void updateNonExistingBanda() throws Exception {
        int databaseSizeBeforeUpdate = bandaRepository.findAll().size();

        // Create the Banda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBandaMockMvc.perform(put("/api/bandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banda)))
            .andExpect(status().isCreated());

        // Validate the Banda in the database
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanda() throws Exception {
        // Initialize the database
        bandaRepository.saveAndFlush(banda);
        int databaseSizeBeforeDelete = bandaRepository.findAll().size();

        // Get the banda
        restBandaMockMvc.perform(delete("/api/bandas/{id}", banda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banda> bandaList = bandaRepository.findAll();
        assertThat(bandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banda.class);
        Banda banda1 = new Banda();
        banda1.setId(1L);
        Banda banda2 = new Banda();
        banda2.setId(banda1.getId());
        assertThat(banda1).isEqualTo(banda2);
        banda2.setId(2L);
        assertThat(banda1).isNotEqualTo(banda2);
        banda1.setId(null);
        assertThat(banda1).isNotEqualTo(banda2);
    }
}
