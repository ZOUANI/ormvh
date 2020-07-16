package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.LeService;
import com.ormvah.domain.Courrier;
import com.ormvah.repository.LeServiceRepository;
import com.ormvah.service.LeServiceService;
import com.ormvah.service.dto.LeServiceCriteria;
import com.ormvah.service.LeServiceQueryService;

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
 * Integration tests for the {@link LeServiceResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeServiceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private LeServiceRepository leServiceRepository;

    @Autowired
    private LeServiceService leServiceService;

    @Autowired
    private LeServiceQueryService leServiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeServiceMockMvc;

    private LeService leService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeService createEntity(EntityManager em) {
        LeService leService = new LeService()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return leService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeService createUpdatedEntity(EntityManager em) {
        LeService leService = new LeService()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return leService;
    }

    @BeforeEach
    public void initTest() {
        leService = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeService() throws Exception {
        int databaseSizeBeforeCreate = leServiceRepository.findAll().size();
        // Create the LeService
        restLeServiceMockMvc.perform(post("/api/le-services").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leService)))
            .andExpect(status().isCreated());

        // Validate the LeService in the database
        List<LeService> leServiceList = leServiceRepository.findAll();
        assertThat(leServiceList).hasSize(databaseSizeBeforeCreate + 1);
        LeService testLeService = leServiceList.get(leServiceList.size() - 1);
        assertThat(testLeService.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLeService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLeService.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeService.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testLeService.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeService.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createLeServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leServiceRepository.findAll().size();

        // Create the LeService with an existing ID
        leService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeServiceMockMvc.perform(post("/api/le-services").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leService)))
            .andExpect(status().isBadRequest());

        // Validate the LeService in the database
        List<LeService> leServiceList = leServiceRepository.findAll();
        assertThat(leServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeServices() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList
        restLeServiceMockMvc.perform(get("/api/le-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leService.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getLeService() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get the leService
        restLeServiceMockMvc.perform(get("/api/le-services/{id}", leService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leService.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }


    @Test
    @Transactional
    public void getLeServicesByIdFiltering() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        Long id = leService.getId();

        defaultLeServiceShouldBeFound("id.equals=" + id);
        defaultLeServiceShouldNotBeFound("id.notEquals=" + id);

        defaultLeServiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeServiceShouldNotBeFound("id.greaterThan=" + id);

        defaultLeServiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeServiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeServicesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where title equals to DEFAULT_TITLE
        defaultLeServiceShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the leServiceList where title equals to UPDATED_TITLE
        defaultLeServiceShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLeServicesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where title not equals to DEFAULT_TITLE
        defaultLeServiceShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the leServiceList where title not equals to UPDATED_TITLE
        defaultLeServiceShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLeServicesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultLeServiceShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the leServiceList where title equals to UPDATED_TITLE
        defaultLeServiceShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLeServicesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where title is not null
        defaultLeServiceShouldBeFound("title.specified=true");

        // Get all the leServiceList where title is null
        defaultLeServiceShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeServicesByTitleContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where title contains DEFAULT_TITLE
        defaultLeServiceShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the leServiceList where title contains UPDATED_TITLE
        defaultLeServiceShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllLeServicesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where title does not contain DEFAULT_TITLE
        defaultLeServiceShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the leServiceList where title does not contain UPDATED_TITLE
        defaultLeServiceShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllLeServicesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where description equals to DEFAULT_DESCRIPTION
        defaultLeServiceShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the leServiceList where description equals to UPDATED_DESCRIPTION
        defaultLeServiceShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeServicesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where description not equals to DEFAULT_DESCRIPTION
        defaultLeServiceShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the leServiceList where description not equals to UPDATED_DESCRIPTION
        defaultLeServiceShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeServicesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLeServiceShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the leServiceList where description equals to UPDATED_DESCRIPTION
        defaultLeServiceShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeServicesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where description is not null
        defaultLeServiceShouldBeFound("description.specified=true");

        // Get all the leServiceList where description is null
        defaultLeServiceShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeServicesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where description contains DEFAULT_DESCRIPTION
        defaultLeServiceShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the leServiceList where description contains UPDATED_DESCRIPTION
        defaultLeServiceShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLeServicesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where description does not contain DEFAULT_DESCRIPTION
        defaultLeServiceShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the leServiceList where description does not contain UPDATED_DESCRIPTION
        defaultLeServiceShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLeServicesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdAt equals to DEFAULT_CREATED_AT
        defaultLeServiceShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the leServiceList where createdAt equals to UPDATED_CREATED_AT
        defaultLeServiceShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdAt not equals to DEFAULT_CREATED_AT
        defaultLeServiceShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the leServiceList where createdAt not equals to UPDATED_CREATED_AT
        defaultLeServiceShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultLeServiceShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the leServiceList where createdAt equals to UPDATED_CREATED_AT
        defaultLeServiceShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdAt is not null
        defaultLeServiceShouldBeFound("createdAt.specified=true");

        // Get all the leServiceList where createdAt is null
        defaultLeServiceShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultLeServiceShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the leServiceList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeServiceShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultLeServiceShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the leServiceList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultLeServiceShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultLeServiceShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the leServiceList where updatedAt equals to UPDATED_UPDATED_AT
        defaultLeServiceShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedAt is not null
        defaultLeServiceShouldBeFound("updatedAt.specified=true");

        // Get all the leServiceList where updatedAt is null
        defaultLeServiceShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdBy equals to DEFAULT_CREATED_BY
        defaultLeServiceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the leServiceList where createdBy equals to UPDATED_CREATED_BY
        defaultLeServiceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdBy not equals to DEFAULT_CREATED_BY
        defaultLeServiceShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the leServiceList where createdBy not equals to UPDATED_CREATED_BY
        defaultLeServiceShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLeServiceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the leServiceList where createdBy equals to UPDATED_CREATED_BY
        defaultLeServiceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdBy is not null
        defaultLeServiceShouldBeFound("createdBy.specified=true");

        // Get all the leServiceList where createdBy is null
        defaultLeServiceShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeServicesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdBy contains DEFAULT_CREATED_BY
        defaultLeServiceShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the leServiceList where createdBy contains UPDATED_CREATED_BY
        defaultLeServiceShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLeServiceShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the leServiceList where createdBy does not contain UPDATED_CREATED_BY
        defaultLeServiceShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllLeServicesByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultLeServiceShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the leServiceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultLeServiceShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultLeServiceShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the leServiceList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultLeServiceShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultLeServiceShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the leServiceList where updatedBy equals to UPDATED_UPDATED_BY
        defaultLeServiceShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedBy is not null
        defaultLeServiceShouldBeFound("updatedBy.specified=true");

        // Get all the leServiceList where updatedBy is null
        defaultLeServiceShouldNotBeFound("updatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeServicesByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedBy contains DEFAULT_UPDATED_BY
        defaultLeServiceShouldBeFound("updatedBy.contains=" + DEFAULT_UPDATED_BY);

        // Get all the leServiceList where updatedBy contains UPDATED_UPDATED_BY
        defaultLeServiceShouldNotBeFound("updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllLeServicesByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);

        // Get all the leServiceList where updatedBy does not contain DEFAULT_UPDATED_BY
        defaultLeServiceShouldNotBeFound("updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);

        // Get all the leServiceList where updatedBy does not contain UPDATED_UPDATED_BY
        defaultLeServiceShouldBeFound("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY);
    }


    @Test
    @Transactional
    public void getAllLeServicesByCourrierIsEqualToSomething() throws Exception {
        // Initialize the database
        leServiceRepository.saveAndFlush(leService);
        Courrier courrier = CourrierResourceIT.createEntity(em);
        em.persist(courrier);
        em.flush();
        leService.addCourrier(courrier);
        leServiceRepository.saveAndFlush(leService);
        Long courrierId = courrier.getId();

        // Get all the leServiceList where courrier equals to courrierId
        defaultLeServiceShouldBeFound("courrierId.equals=" + courrierId);

        // Get all the leServiceList where courrier equals to courrierId + 1
        defaultLeServiceShouldNotBeFound("courrierId.equals=" + (courrierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeServiceShouldBeFound(String filter) throws Exception {
        restLeServiceMockMvc.perform(get("/api/le-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leService.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));

        // Check, that the count call also returns 1
        restLeServiceMockMvc.perform(get("/api/le-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeServiceShouldNotBeFound(String filter) throws Exception {
        restLeServiceMockMvc.perform(get("/api/le-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeServiceMockMvc.perform(get("/api/le-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeService() throws Exception {
        // Get the leService
        restLeServiceMockMvc.perform(get("/api/le-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeService() throws Exception {
        // Initialize the database
        leServiceService.save(leService);

        int databaseSizeBeforeUpdate = leServiceRepository.findAll().size();

        // Update the leService
        LeService updatedLeService = leServiceRepository.findById(leService.getId()).get();
        // Disconnect from session so that the updates on updatedLeService are not directly saved in db
        em.detach(updatedLeService);
        updatedLeService
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restLeServiceMockMvc.perform(put("/api/le-services").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeService)))
            .andExpect(status().isOk());

        // Validate the LeService in the database
        List<LeService> leServiceList = leServiceRepository.findAll();
        assertThat(leServiceList).hasSize(databaseSizeBeforeUpdate);
        LeService testLeService = leServiceList.get(leServiceList.size() - 1);
        assertThat(testLeService.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLeService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLeService.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeService.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testLeService.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeService.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingLeService() throws Exception {
        int databaseSizeBeforeUpdate = leServiceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeServiceMockMvc.perform(put("/api/le-services").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leService)))
            .andExpect(status().isBadRequest());

        // Validate the LeService in the database
        List<LeService> leServiceList = leServiceRepository.findAll();
        assertThat(leServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeService() throws Exception {
        // Initialize the database
        leServiceService.save(leService);

        int databaseSizeBeforeDelete = leServiceRepository.findAll().size();

        // Delete the leService
        restLeServiceMockMvc.perform(delete("/api/le-services/{id}", leService.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeService> leServiceList = leServiceRepository.findAll();
        assertThat(leServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
