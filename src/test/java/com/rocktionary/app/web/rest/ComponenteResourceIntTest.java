package com.rocktionary.app.web.rest;

import com.rocktionary.app.RocktionaryApp;

import com.rocktionary.app.domain.Componente;
import com.rocktionary.app.repository.ComponenteRepository;
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
 * Test class for the ComponenteResource REST controller.
 *
 * @see ComponenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RocktionaryApp.class)
public class ComponenteResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCION_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_FUNCION_GRUPO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_FECHA_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ComponenteRepository componenteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComponenteMockMvc;

    private Componente componente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComponenteResource componenteResource = new ComponenteResource(componenteRepository);
        this.restComponenteMockMvc = MockMvcBuilders.standaloneSetup(componenteResource)
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
    public static Componente createEntity(EntityManager em) {
        Componente componente = new Componente()
            .nombre(DEFAULT_NOMBRE)
            .edad(DEFAULT_EDAD)
            .sexo(DEFAULT_SEXO)
            .funcionGrupo(DEFAULT_FUNCION_GRUPO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .fechaEntrada(DEFAULT_FECHA_ENTRADA)
            .fechaSalida(DEFAULT_FECHA_SALIDA);
        return componente;
    }

    @Before
    public void initTest() {
        componente = createEntity(em);
    }

    @Test
    @Transactional
    public void createComponente() throws Exception {
        int databaseSizeBeforeCreate = componenteRepository.findAll().size();

        // Create the Componente
        restComponenteMockMvc.perform(post("/api/componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componente)))
            .andExpect(status().isCreated());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeCreate + 1);
        Componente testComponente = componenteList.get(componenteList.size() - 1);
        assertThat(testComponente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testComponente.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testComponente.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testComponente.getFuncionGrupo()).isEqualTo(DEFAULT_FUNCION_GRUPO);
        assertThat(testComponente.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testComponente.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testComponente.getFechaEntrada()).isEqualTo(DEFAULT_FECHA_ENTRADA);
        assertThat(testComponente.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void createComponenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = componenteRepository.findAll().size();

        // Create the Componente with an existing ID
        componente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComponenteMockMvc.perform(post("/api/componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componente)))
            .andExpect(status().isBadRequest());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComponentes() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        // Get all the componenteList
        restComponenteMockMvc.perform(get("/api/componentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(componente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].funcionGrupo").value(hasItem(DEFAULT_FUNCION_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].fechaEntrada").value(hasItem(DEFAULT_FECHA_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())));
    }

    @Test
    @Transactional
    public void getComponente() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);

        // Get the componente
        restComponenteMockMvc.perform(get("/api/componentes/{id}", componente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(componente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.funcionGrupo").value(DEFAULT_FUNCION_GRUPO.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.fechaEntrada").value(DEFAULT_FECHA_ENTRADA.toString()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComponente() throws Exception {
        // Get the componente
        restComponenteMockMvc.perform(get("/api/componentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComponente() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();

        // Update the componente
        Componente updatedComponente = componenteRepository.findOne(componente.getId());
        updatedComponente
            .nombre(UPDATED_NOMBRE)
            .edad(UPDATED_EDAD)
            .sexo(UPDATED_SEXO)
            .funcionGrupo(UPDATED_FUNCION_GRUPO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .fechaEntrada(UPDATED_FECHA_ENTRADA)
            .fechaSalida(UPDATED_FECHA_SALIDA);

        restComponenteMockMvc.perform(put("/api/componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComponente)))
            .andExpect(status().isOk());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate);
        Componente testComponente = componenteList.get(componenteList.size() - 1);
        assertThat(testComponente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testComponente.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testComponente.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testComponente.getFuncionGrupo()).isEqualTo(UPDATED_FUNCION_GRUPO);
        assertThat(testComponente.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testComponente.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testComponente.getFechaEntrada()).isEqualTo(UPDATED_FECHA_ENTRADA);
        assertThat(testComponente.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingComponente() throws Exception {
        int databaseSizeBeforeUpdate = componenteRepository.findAll().size();

        // Create the Componente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComponenteMockMvc.perform(put("/api/componentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(componente)))
            .andExpect(status().isCreated());

        // Validate the Componente in the database
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComponente() throws Exception {
        // Initialize the database
        componenteRepository.saveAndFlush(componente);
        int databaseSizeBeforeDelete = componenteRepository.findAll().size();

        // Get the componente
        restComponenteMockMvc.perform(delete("/api/componentes/{id}", componente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Componente> componenteList = componenteRepository.findAll();
        assertThat(componenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Componente.class);
        Componente componente1 = new Componente();
        componente1.setId(1L);
        Componente componente2 = new Componente();
        componente2.setId(componente1.getId());
        assertThat(componente1).isEqualTo(componente2);
        componente2.setId(2L);
        assertThat(componente1).isNotEqualTo(componente2);
        componente1.setId(null);
        assertThat(componente1).isNotEqualTo(componente2);
    }
}
