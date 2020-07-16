package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.ExpeditorType;
import com.ormvah.repository.ExpeditorTypeRepository;
import com.ormvah.service.ExpeditorTypeService;

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
 * Integration tests for the {@link ExpeditorTypeResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpeditorTypeResourceIT {

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
    private ExpeditorTypeRepository expeditorTypeRepository;

    @Autowired
    private ExpeditorTypeService expeditorTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpeditorTypeMockMvc;

    private ExpeditorType expeditorType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpeditorType createEntity(EntityManager em) {
        ExpeditorType expeditorType = new ExpeditorType()
            .title(DEFAULT_TITLE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return expeditorType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpeditorType createUpdatedEntity(EntityManager em) {
        ExpeditorType expeditorType = new ExpeditorType()
            .title(UPDATED_TITLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return expeditorType;
    }

    @BeforeEach
    public void initTest() {
        expeditorType = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpeditorType() throws Exception {
        int databaseSizeBeforeCreate = expeditorTypeRepository.findAll().size();
        // Create the ExpeditorType
        restExpeditorTypeMockMvc.perform(post("/api/expeditor-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expeditorType)))
            .andExpect(status().isCreated());

        // Validate the ExpeditorType in the database
        List<ExpeditorType> expeditorTypeList = expeditorTypeRepository.findAll();
        assertThat(expeditorTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ExpeditorType testExpeditorType = expeditorTypeList.get(expeditorTypeList.size() - 1);
        assertThat(testExpeditorType.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExpeditorType.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testExpeditorType.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testExpeditorType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpeditorType.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createExpeditorTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expeditorTypeRepository.findAll().size();

        // Create the ExpeditorType with an existing ID
        expeditorType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpeditorTypeMockMvc.perform(post("/api/expeditor-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expeditorType)))
            .andExpect(status().isBadRequest());

        // Validate the ExpeditorType in the database
        List<ExpeditorType> expeditorTypeList = expeditorTypeRepository.findAll();
        assertThat(expeditorTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExpeditorTypes() throws Exception {
        // Initialize the database
        expeditorTypeRepository.saveAndFlush(expeditorType);

        // Get all the expeditorTypeList
        restExpeditorTypeMockMvc.perform(get("/api/expeditor-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expeditorType.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getExpeditorType() throws Exception {
        // Initialize the database
        expeditorTypeRepository.saveAndFlush(expeditorType);

        // Get the expeditorType
        restExpeditorTypeMockMvc.perform(get("/api/expeditor-types/{id}", expeditorType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expeditorType.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingExpeditorType() throws Exception {
        // Get the expeditorType
        restExpeditorTypeMockMvc.perform(get("/api/expeditor-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpeditorType() throws Exception {
        // Initialize the database
        expeditorTypeService.save(expeditorType);

        int databaseSizeBeforeUpdate = expeditorTypeRepository.findAll().size();

        // Update the expeditorType
        ExpeditorType updatedExpeditorType = expeditorTypeRepository.findById(expeditorType.getId()).get();
        // Disconnect from session so that the updates on updatedExpeditorType are not directly saved in db
        em.detach(updatedExpeditorType);
        updatedExpeditorType
            .title(UPDATED_TITLE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restExpeditorTypeMockMvc.perform(put("/api/expeditor-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpeditorType)))
            .andExpect(status().isOk());

        // Validate the ExpeditorType in the database
        List<ExpeditorType> expeditorTypeList = expeditorTypeRepository.findAll();
        assertThat(expeditorTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpeditorType testExpeditorType = expeditorTypeList.get(expeditorTypeList.size() - 1);
        assertThat(testExpeditorType.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExpeditorType.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testExpeditorType.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testExpeditorType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpeditorType.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingExpeditorType() throws Exception {
        int databaseSizeBeforeUpdate = expeditorTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpeditorTypeMockMvc.perform(put("/api/expeditor-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expeditorType)))
            .andExpect(status().isBadRequest());

        // Validate the ExpeditorType in the database
        List<ExpeditorType> expeditorTypeList = expeditorTypeRepository.findAll();
        assertThat(expeditorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpeditorType() throws Exception {
        // Initialize the database
        expeditorTypeService.save(expeditorType);

        int databaseSizeBeforeDelete = expeditorTypeRepository.findAll().size();

        // Delete the expeditorType
        restExpeditorTypeMockMvc.perform(delete("/api/expeditor-types/{id}", expeditorType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpeditorType> expeditorTypeList = expeditorTypeRepository.findAll();
        assertThat(expeditorTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
