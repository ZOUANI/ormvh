package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.Subdivision;
import com.ormvah.repository.SubdivisionRepository;
import com.ormvah.service.SubdivisionService;

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
 * Integration tests for the {@link SubdivisionResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubdivisionResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private SubdivisionRepository subdivisionRepository;

    @Autowired
    private SubdivisionService subdivisionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubdivisionMockMvc;

    private Subdivision subdivision;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subdivision createEntity(EntityManager em) {
        Subdivision subdivision = new Subdivision()
            .title(DEFAULT_TITLE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return subdivision;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subdivision createUpdatedEntity(EntityManager em) {
        Subdivision subdivision = new Subdivision()
            .title(UPDATED_TITLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return subdivision;
    }

    @BeforeEach
    public void initTest() {
        subdivision = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubdivision() throws Exception {
        int databaseSizeBeforeCreate = subdivisionRepository.findAll().size();
        // Create the Subdivision
        restSubdivisionMockMvc.perform(post("/api/subdivisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subdivision)))
            .andExpect(status().isCreated());

        // Validate the Subdivision in the database
        List<Subdivision> subdivisionList = subdivisionRepository.findAll();
        assertThat(subdivisionList).hasSize(databaseSizeBeforeCreate + 1);
        Subdivision testSubdivision = subdivisionList.get(subdivisionList.size() - 1);
        assertThat(testSubdivision.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSubdivision.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSubdivision.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testSubdivision.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSubdivision.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createSubdivisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subdivisionRepository.findAll().size();

        // Create the Subdivision with an existing ID
        subdivision.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubdivisionMockMvc.perform(post("/api/subdivisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subdivision)))
            .andExpect(status().isBadRequest());

        // Validate the Subdivision in the database
        List<Subdivision> subdivisionList = subdivisionRepository.findAll();
        assertThat(subdivisionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubdivisions() throws Exception {
        // Initialize the database
        subdivisionRepository.saveAndFlush(subdivision);

        // Get all the subdivisionList
        restSubdivisionMockMvc.perform(get("/api/subdivisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subdivision.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getSubdivision() throws Exception {
        // Initialize the database
        subdivisionRepository.saveAndFlush(subdivision);

        // Get the subdivision
        restSubdivisionMockMvc.perform(get("/api/subdivisions/{id}", subdivision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subdivision.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingSubdivision() throws Exception {
        // Get the subdivision
        restSubdivisionMockMvc.perform(get("/api/subdivisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubdivision() throws Exception {
        // Initialize the database
        subdivisionService.save(subdivision);

        int databaseSizeBeforeUpdate = subdivisionRepository.findAll().size();

        // Update the subdivision
        Subdivision updatedSubdivision = subdivisionRepository.findById(subdivision.getId()).get();
        // Disconnect from session so that the updates on updatedSubdivision are not directly saved in db
        em.detach(updatedSubdivision);
        updatedSubdivision
            .title(UPDATED_TITLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restSubdivisionMockMvc.perform(put("/api/subdivisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubdivision)))
            .andExpect(status().isOk());

        // Validate the Subdivision in the database
        List<Subdivision> subdivisionList = subdivisionRepository.findAll();
        assertThat(subdivisionList).hasSize(databaseSizeBeforeUpdate);
        Subdivision testSubdivision = subdivisionList.get(subdivisionList.size() - 1);
        assertThat(testSubdivision.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSubdivision.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSubdivision.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testSubdivision.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSubdivision.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingSubdivision() throws Exception {
        int databaseSizeBeforeUpdate = subdivisionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubdivisionMockMvc.perform(put("/api/subdivisions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subdivision)))
            .andExpect(status().isBadRequest());

        // Validate the Subdivision in the database
        List<Subdivision> subdivisionList = subdivisionRepository.findAll();
        assertThat(subdivisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubdivision() throws Exception {
        // Initialize the database
        subdivisionService.save(subdivision);

        int databaseSizeBeforeDelete = subdivisionRepository.findAll().size();

        // Delete the subdivision
        restSubdivisionMockMvc.perform(delete("/api/subdivisions/{id}", subdivision.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subdivision> subdivisionList = subdivisionRepository.findAll();
        assertThat(subdivisionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
