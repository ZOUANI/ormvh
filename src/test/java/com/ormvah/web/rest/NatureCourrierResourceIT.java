package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.NatureCourrier;
import com.ormvah.repository.NatureCourrierRepository;
import com.ormvah.service.NatureCourrierService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NatureCourrierResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NatureCourrierResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_DELAI = 1D;
    private static final Double UPDATED_DELAI = 2D;

    private static final Double DEFAULT_RELANCE = 1D;
    private static final Double UPDATED_RELANCE = 2D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private NatureCourrierRepository natureCourrierRepository;

    @Autowired
    private NatureCourrierService natureCourrierService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureCourrierMockMvc;

    private NatureCourrier natureCourrier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureCourrier createEntity(EntityManager em) {
        NatureCourrier natureCourrier = new NatureCourrier()
            .libelle(DEFAULT_LIBELLE)
            .delai(DEFAULT_DELAI)
            .relance(DEFAULT_RELANCE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return natureCourrier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureCourrier createUpdatedEntity(EntityManager em) {
        NatureCourrier natureCourrier = new NatureCourrier()
            .libelle(UPDATED_LIBELLE)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return natureCourrier;
    }

    @BeforeEach
    public void initTest() {
        natureCourrier = createEntity(em);
    }

    @Test
    @Transactional
    public void createNatureCourrier() throws Exception {
        int databaseSizeBeforeCreate = natureCourrierRepository.findAll().size();
        // Create the NatureCourrier
        restNatureCourrierMockMvc.perform(post("/api/nature-courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isCreated());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeCreate + 1);
        NatureCourrier testNatureCourrier = natureCourrierList.get(natureCourrierList.size() - 1);
        assertThat(testNatureCourrier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNatureCourrier.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testNatureCourrier.getRelance()).isEqualTo(DEFAULT_RELANCE);
        assertThat(testNatureCourrier.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testNatureCourrier.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testNatureCourrier.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureCourrier.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createNatureCourrierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = natureCourrierRepository.findAll().size();

        // Create the NatureCourrier with an existing ID
        natureCourrier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureCourrierMockMvc.perform(post("/api/nature-courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isBadRequest());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = natureCourrierRepository.findAll().size();
        // set the field null
        natureCourrier.setLibelle(null);

        // Create the NatureCourrier, which fails.


        restNatureCourrierMockMvc.perform(post("/api/nature-courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isBadRequest());

        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNatureCourriers() throws Exception {
        // Initialize the database
        natureCourrierRepository.saveAndFlush(natureCourrier);

        // Get all the natureCourrierList
        restNatureCourrierMockMvc.perform(get("/api/nature-courriers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureCourrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.doubleValue())))
            .andExpect(jsonPath("$.[*].relance").value(hasItem(DEFAULT_RELANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getNatureCourrier() throws Exception {
        // Initialize the database
        natureCourrierRepository.saveAndFlush(natureCourrier);

        // Get the natureCourrier
        restNatureCourrierMockMvc.perform(get("/api/nature-courriers/{id}", natureCourrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureCourrier.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.doubleValue()))
            .andExpect(jsonPath("$.relance").value(DEFAULT_RELANCE.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingNatureCourrier() throws Exception {
        // Get the natureCourrier
        restNatureCourrierMockMvc.perform(get("/api/nature-courriers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNatureCourrier() throws Exception {
        // Initialize the database
        natureCourrierService.save(natureCourrier);

        int databaseSizeBeforeUpdate = natureCourrierRepository.findAll().size();

        // Update the natureCourrier
        NatureCourrier updatedNatureCourrier = natureCourrierRepository.findById(natureCourrier.getId()).get();
        // Disconnect from session so that the updates on updatedNatureCourrier are not directly saved in db
        em.detach(updatedNatureCourrier);
        updatedNatureCourrier
            .libelle(UPDATED_LIBELLE)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restNatureCourrierMockMvc.perform(put("/api/nature-courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNatureCourrier)))
            .andExpect(status().isOk());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeUpdate);
        NatureCourrier testNatureCourrier = natureCourrierList.get(natureCourrierList.size() - 1);
        assertThat(testNatureCourrier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNatureCourrier.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testNatureCourrier.getRelance()).isEqualTo(UPDATED_RELANCE);
        assertThat(testNatureCourrier.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testNatureCourrier.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testNatureCourrier.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureCourrier.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingNatureCourrier() throws Exception {
        int databaseSizeBeforeUpdate = natureCourrierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureCourrierMockMvc.perform(put("/api/nature-courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(natureCourrier)))
            .andExpect(status().isBadRequest());

        // Validate the NatureCourrier in the database
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNatureCourrier() throws Exception {
        // Initialize the database
        natureCourrierService.save(natureCourrier);

        int databaseSizeBeforeDelete = natureCourrierRepository.findAll().size();

        // Delete the natureCourrier
        restNatureCourrierMockMvc.perform(delete("/api/nature-courriers/{id}", natureCourrier.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureCourrier> natureCourrierList = natureCourrierRepository.findAll();
        assertThat(natureCourrierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
