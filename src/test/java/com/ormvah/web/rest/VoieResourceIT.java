package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.Voie;
import com.ormvah.repository.VoieRepository;
import com.ormvah.service.VoieService;
import com.ormvah.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ormvah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VoieResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
public class VoieResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private VoieRepository voieRepository;

    @Autowired
    private VoieService voieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVoieMockMvc;

    private Voie voie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoieResource voieResource = new VoieResource(voieService);
        this.restVoieMockMvc = MockMvcBuilders.standaloneSetup(voieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voie createEntity(EntityManager em) {
        Voie voie = new Voie()
            .libelle(DEFAULT_LIBELLE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return voie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voie createUpdatedEntity(EntityManager em) {
        Voie voie = new Voie()
            .libelle(UPDATED_LIBELLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return voie;
    }

    @BeforeEach
    public void initTest() {
        voie = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoie() throws Exception {
        int databaseSizeBeforeCreate = voieRepository.findAll().size();

        // Create the Voie
        restVoieMockMvc.perform(post("/api/voies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voie)))
            .andExpect(status().isCreated());

        // Validate the Voie in the database
        List<Voie> voieList = voieRepository.findAll();
        assertThat(voieList).hasSize(databaseSizeBeforeCreate + 1);
        Voie testVoie = voieList.get(voieList.size() - 1);
        assertThat(testVoie.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testVoie.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testVoie.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testVoie.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testVoie.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createVoieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voieRepository.findAll().size();

        // Create the Voie with an existing ID
        voie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoieMockMvc.perform(post("/api/voies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voie)))
            .andExpect(status().isBadRequest());

        // Validate the Voie in the database
        List<Voie> voieList = voieRepository.findAll();
        assertThat(voieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = voieRepository.findAll().size();
        // set the field null
        voie.setLibelle(null);

        // Create the Voie, which fails.

        restVoieMockMvc.perform(post("/api/voies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voie)))
            .andExpect(status().isBadRequest());

        List<Voie> voieList = voieRepository.findAll();
        assertThat(voieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVoies() throws Exception {
        // Initialize the database
        voieRepository.saveAndFlush(voie);

        // Get all the voieList
        restVoieMockMvc.perform(get("/api/voies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getVoie() throws Exception {
        // Initialize the database
        voieRepository.saveAndFlush(voie);

        // Get the voie
        restVoieMockMvc.perform(get("/api/voies/{id}", voie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(voie.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoie() throws Exception {
        // Get the voie
        restVoieMockMvc.perform(get("/api/voies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoie() throws Exception {
        // Initialize the database
        voieService.save(voie);

        int databaseSizeBeforeUpdate = voieRepository.findAll().size();

        // Update the voie
        Voie updatedVoie = voieRepository.findById(voie.getId()).get();
        // Disconnect from session so that the updates on updatedVoie are not directly saved in db
        em.detach(updatedVoie);
        updatedVoie
            .libelle(UPDATED_LIBELLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restVoieMockMvc.perform(put("/api/voies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoie)))
            .andExpect(status().isOk());

        // Validate the Voie in the database
        List<Voie> voieList = voieRepository.findAll();
        assertThat(voieList).hasSize(databaseSizeBeforeUpdate);
        Voie testVoie = voieList.get(voieList.size() - 1);
        assertThat(testVoie.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testVoie.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testVoie.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testVoie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVoie.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingVoie() throws Exception {
        int databaseSizeBeforeUpdate = voieRepository.findAll().size();

        // Create the Voie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoieMockMvc.perform(put("/api/voies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(voie)))
            .andExpect(status().isBadRequest());

        // Validate the Voie in the database
        List<Voie> voieList = voieRepository.findAll();
        assertThat(voieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoie() throws Exception {
        // Initialize the database
        voieService.save(voie);

        int databaseSizeBeforeDelete = voieRepository.findAll().size();

        // Delete the voie
        restVoieMockMvc.perform(delete("/api/voies/{id}", voie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voie> voieList = voieRepository.findAll();
        assertThat(voieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voie.class);
        Voie voie1 = new Voie();
        voie1.setId(1L);
        Voie voie2 = new Voie();
        voie2.setId(voie1.getId());
        assertThat(voie1).isEqualTo(voie2);
        voie2.setId(2L);
        assertThat(voie1).isNotEqualTo(voie2);
        voie1.setId(null);
        assertThat(voie1).isNotEqualTo(voie2);
    }
}
