package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.Expeditor;
import com.ormvah.repository.ExpeditorRepository;
import com.ormvah.service.ExpeditorService;
import com.ormvah.service.dto.ExpeditorCriteria;
import com.ormvah.service.ExpeditorQueryService;

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

import com.ormvah.domain.enumeration.Sexe;
/**
 * Integration tests for the {@link ExpeditorResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExpeditorResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.Homme;
    private static final Sexe UPDATED_SEXE = Sexe.Femme;

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private ExpeditorRepository expeditorRepository;

    @Autowired
    private ExpeditorService expeditorService;

    @Autowired
    private ExpeditorQueryService expeditorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpeditorMockMvc;

    private Expeditor expeditor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expeditor createEntity(EntityManager em) {
        Expeditor expeditor = new Expeditor()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .nature(DEFAULT_NATURE)
            .sexe(DEFAULT_SEXE)
            .age(DEFAULT_AGE)
            .nationality(DEFAULT_NATIONALITY)
            .adress(DEFAULT_ADRESS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return expeditor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expeditor createUpdatedEntity(EntityManager em) {
        Expeditor expeditor = new Expeditor()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .nature(UPDATED_NATURE)
            .sexe(UPDATED_SEXE)
            .age(UPDATED_AGE)
            .nationality(UPDATED_NATIONALITY)
            .adress(UPDATED_ADRESS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return expeditor;
    }

    @BeforeEach
    public void initTest() {
        expeditor = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpeditor() throws Exception {
        int databaseSizeBeforeCreate = expeditorRepository.findAll().size();
        // Create the Expeditor
        restExpeditorMockMvc.perform(post("/api/expeditors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expeditor)))
            .andExpect(status().isCreated());

        // Validate the Expeditor in the database
        List<Expeditor> expeditorList = expeditorRepository.findAll();
        assertThat(expeditorList).hasSize(databaseSizeBeforeCreate + 1);
        Expeditor testExpeditor = expeditorList.get(expeditorList.size() - 1);
        assertThat(testExpeditor.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testExpeditor.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testExpeditor.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testExpeditor.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testExpeditor.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testExpeditor.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testExpeditor.getAdress()).isEqualTo(DEFAULT_ADRESS);
        assertThat(testExpeditor.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testExpeditor.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testExpeditor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpeditor.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createExpeditorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expeditorRepository.findAll().size();

        // Create the Expeditor with an existing ID
        expeditor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpeditorMockMvc.perform(post("/api/expeditors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expeditor)))
            .andExpect(status().isBadRequest());

        // Validate the Expeditor in the database
        List<Expeditor> expeditorList = expeditorRepository.findAll();
        assertThat(expeditorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExpeditors() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList
        restExpeditorMockMvc.perform(get("/api/expeditors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expeditor.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getExpeditor() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get the expeditor
        restExpeditorMockMvc.perform(get("/api/expeditors/{id}", expeditor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expeditor.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.adress").value(DEFAULT_ADRESS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }


    @Test
    @Transactional
    public void getExpeditorsByIdFiltering() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        Long id = expeditor.getId();

        defaultExpeditorShouldBeFound("id.equals=" + id);
        defaultExpeditorShouldNotBeFound("id.notEquals=" + id);

        defaultExpeditorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpeditorShouldNotBeFound("id.greaterThan=" + id);

        defaultExpeditorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpeditorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where title equals to DEFAULT_TITLE
        defaultExpeditorShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the expeditorList where title equals to UPDATED_TITLE
        defaultExpeditorShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where title not equals to DEFAULT_TITLE
        defaultExpeditorShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the expeditorList where title not equals to UPDATED_TITLE
        defaultExpeditorShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultExpeditorShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the expeditorList where title equals to UPDATED_TITLE
        defaultExpeditorShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where title is not null
        defaultExpeditorShouldBeFound("title.specified=true");

        // Get all the expeditorList where title is null
        defaultExpeditorShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByTitleContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where title contains DEFAULT_TITLE
        defaultExpeditorShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the expeditorList where title contains UPDATED_TITLE
        defaultExpeditorShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where title does not contain DEFAULT_TITLE
        defaultExpeditorShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the expeditorList where title does not contain UPDATED_TITLE
        defaultExpeditorShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where description equals to DEFAULT_DESCRIPTION
        defaultExpeditorShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the expeditorList where description equals to UPDATED_DESCRIPTION
        defaultExpeditorShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where description not equals to DEFAULT_DESCRIPTION
        defaultExpeditorShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the expeditorList where description not equals to UPDATED_DESCRIPTION
        defaultExpeditorShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultExpeditorShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the expeditorList where description equals to UPDATED_DESCRIPTION
        defaultExpeditorShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where description is not null
        defaultExpeditorShouldBeFound("description.specified=true");

        // Get all the expeditorList where description is null
        defaultExpeditorShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where description contains DEFAULT_DESCRIPTION
        defaultExpeditorShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the expeditorList where description contains UPDATED_DESCRIPTION
        defaultExpeditorShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where description does not contain DEFAULT_DESCRIPTION
        defaultExpeditorShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the expeditorList where description does not contain UPDATED_DESCRIPTION
        defaultExpeditorShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByNatureIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nature equals to DEFAULT_NATURE
        defaultExpeditorShouldBeFound("nature.equals=" + DEFAULT_NATURE);

        // Get all the expeditorList where nature equals to UPDATED_NATURE
        defaultExpeditorShouldNotBeFound("nature.equals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nature not equals to DEFAULT_NATURE
        defaultExpeditorShouldNotBeFound("nature.notEquals=" + DEFAULT_NATURE);

        // Get all the expeditorList where nature not equals to UPDATED_NATURE
        defaultExpeditorShouldBeFound("nature.notEquals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNatureIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nature in DEFAULT_NATURE or UPDATED_NATURE
        defaultExpeditorShouldBeFound("nature.in=" + DEFAULT_NATURE + "," + UPDATED_NATURE);

        // Get all the expeditorList where nature equals to UPDATED_NATURE
        defaultExpeditorShouldNotBeFound("nature.in=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nature is not null
        defaultExpeditorShouldBeFound("nature.specified=true");

        // Get all the expeditorList where nature is null
        defaultExpeditorShouldNotBeFound("nature.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByNatureContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nature contains DEFAULT_NATURE
        defaultExpeditorShouldBeFound("nature.contains=" + DEFAULT_NATURE);

        // Get all the expeditorList where nature contains UPDATED_NATURE
        defaultExpeditorShouldNotBeFound("nature.contains=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNatureNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nature does not contain DEFAULT_NATURE
        defaultExpeditorShouldNotBeFound("nature.doesNotContain=" + DEFAULT_NATURE);

        // Get all the expeditorList where nature does not contain UPDATED_NATURE
        defaultExpeditorShouldBeFound("nature.doesNotContain=" + UPDATED_NATURE);
    }


    @Test
    @Transactional
    public void getAllExpeditorsBySexeIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where sexe equals to DEFAULT_SEXE
        defaultExpeditorShouldBeFound("sexe.equals=" + DEFAULT_SEXE);

        // Get all the expeditorList where sexe equals to UPDATED_SEXE
        defaultExpeditorShouldNotBeFound("sexe.equals=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsBySexeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where sexe not equals to DEFAULT_SEXE
        defaultExpeditorShouldNotBeFound("sexe.notEquals=" + DEFAULT_SEXE);

        // Get all the expeditorList where sexe not equals to UPDATED_SEXE
        defaultExpeditorShouldBeFound("sexe.notEquals=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsBySexeIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where sexe in DEFAULT_SEXE or UPDATED_SEXE
        defaultExpeditorShouldBeFound("sexe.in=" + DEFAULT_SEXE + "," + UPDATED_SEXE);

        // Get all the expeditorList where sexe equals to UPDATED_SEXE
        defaultExpeditorShouldNotBeFound("sexe.in=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsBySexeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where sexe is not null
        defaultExpeditorShouldBeFound("sexe.specified=true");

        // Get all the expeditorList where sexe is null
        defaultExpeditorShouldNotBeFound("sexe.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age equals to DEFAULT_AGE
        defaultExpeditorShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the expeditorList where age equals to UPDATED_AGE
        defaultExpeditorShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age not equals to DEFAULT_AGE
        defaultExpeditorShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the expeditorList where age not equals to UPDATED_AGE
        defaultExpeditorShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age in DEFAULT_AGE or UPDATED_AGE
        defaultExpeditorShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the expeditorList where age equals to UPDATED_AGE
        defaultExpeditorShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age is not null
        defaultExpeditorShouldBeFound("age.specified=true");

        // Get all the expeditorList where age is null
        defaultExpeditorShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age is greater than or equal to DEFAULT_AGE
        defaultExpeditorShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the expeditorList where age is greater than or equal to UPDATED_AGE
        defaultExpeditorShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age is less than or equal to DEFAULT_AGE
        defaultExpeditorShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the expeditorList where age is less than or equal to SMALLER_AGE
        defaultExpeditorShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age is less than DEFAULT_AGE
        defaultExpeditorShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the expeditorList where age is less than UPDATED_AGE
        defaultExpeditorShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where age is greater than DEFAULT_AGE
        defaultExpeditorShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the expeditorList where age is greater than SMALLER_AGE
        defaultExpeditorShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nationality equals to DEFAULT_NATIONALITY
        defaultExpeditorShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the expeditorList where nationality equals to UPDATED_NATIONALITY
        defaultExpeditorShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nationality not equals to DEFAULT_NATIONALITY
        defaultExpeditorShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the expeditorList where nationality not equals to UPDATED_NATIONALITY
        defaultExpeditorShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultExpeditorShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the expeditorList where nationality equals to UPDATED_NATIONALITY
        defaultExpeditorShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nationality is not null
        defaultExpeditorShouldBeFound("nationality.specified=true");

        // Get all the expeditorList where nationality is null
        defaultExpeditorShouldNotBeFound("nationality.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByNationalityContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nationality contains DEFAULT_NATIONALITY
        defaultExpeditorShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the expeditorList where nationality contains UPDATED_NATIONALITY
        defaultExpeditorShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where nationality does not contain DEFAULT_NATIONALITY
        defaultExpeditorShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the expeditorList where nationality does not contain UPDATED_NATIONALITY
        defaultExpeditorShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByAdressIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where adress equals to DEFAULT_ADRESS
        defaultExpeditorShouldBeFound("adress.equals=" + DEFAULT_ADRESS);

        // Get all the expeditorList where adress equals to UPDATED_ADRESS
        defaultExpeditorShouldNotBeFound("adress.equals=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAdressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where adress not equals to DEFAULT_ADRESS
        defaultExpeditorShouldNotBeFound("adress.notEquals=" + DEFAULT_ADRESS);

        // Get all the expeditorList where adress not equals to UPDATED_ADRESS
        defaultExpeditorShouldBeFound("adress.notEquals=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAdressIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where adress in DEFAULT_ADRESS or UPDATED_ADRESS
        defaultExpeditorShouldBeFound("adress.in=" + DEFAULT_ADRESS + "," + UPDATED_ADRESS);

        // Get all the expeditorList where adress equals to UPDATED_ADRESS
        defaultExpeditorShouldNotBeFound("adress.in=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAdressIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where adress is not null
        defaultExpeditorShouldBeFound("adress.specified=true");

        // Get all the expeditorList where adress is null
        defaultExpeditorShouldNotBeFound("adress.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByAdressContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where adress contains DEFAULT_ADRESS
        defaultExpeditorShouldBeFound("adress.contains=" + DEFAULT_ADRESS);

        // Get all the expeditorList where adress contains UPDATED_ADRESS
        defaultExpeditorShouldNotBeFound("adress.contains=" + UPDATED_ADRESS);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByAdressNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where adress does not contain DEFAULT_ADRESS
        defaultExpeditorShouldNotBeFound("adress.doesNotContain=" + DEFAULT_ADRESS);

        // Get all the expeditorList where adress does not contain UPDATED_ADRESS
        defaultExpeditorShouldBeFound("adress.doesNotContain=" + UPDATED_ADRESS);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdAt equals to DEFAULT_CREATED_AT
        defaultExpeditorShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the expeditorList where createdAt equals to UPDATED_CREATED_AT
        defaultExpeditorShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdAt not equals to DEFAULT_CREATED_AT
        defaultExpeditorShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the expeditorList where createdAt not equals to UPDATED_CREATED_AT
        defaultExpeditorShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultExpeditorShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the expeditorList where createdAt equals to UPDATED_CREATED_AT
        defaultExpeditorShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdAt is not null
        defaultExpeditorShouldBeFound("createdAt.specified=true");

        // Get all the expeditorList where createdAt is null
        defaultExpeditorShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultExpeditorShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the expeditorList where updatedAt equals to UPDATED_UPDATED_AT
        defaultExpeditorShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultExpeditorShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the expeditorList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultExpeditorShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultExpeditorShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the expeditorList where updatedAt equals to UPDATED_UPDATED_AT
        defaultExpeditorShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedAt is not null
        defaultExpeditorShouldBeFound("updatedAt.specified=true");

        // Get all the expeditorList where updatedAt is null
        defaultExpeditorShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdBy equals to DEFAULT_CREATED_BY
        defaultExpeditorShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the expeditorList where createdBy equals to UPDATED_CREATED_BY
        defaultExpeditorShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdBy not equals to DEFAULT_CREATED_BY
        defaultExpeditorShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the expeditorList where createdBy not equals to UPDATED_CREATED_BY
        defaultExpeditorShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultExpeditorShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the expeditorList where createdBy equals to UPDATED_CREATED_BY
        defaultExpeditorShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdBy is not null
        defaultExpeditorShouldBeFound("createdBy.specified=true");

        // Get all the expeditorList where createdBy is null
        defaultExpeditorShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdBy contains DEFAULT_CREATED_BY
        defaultExpeditorShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the expeditorList where createdBy contains UPDATED_CREATED_BY
        defaultExpeditorShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where createdBy does not contain DEFAULT_CREATED_BY
        defaultExpeditorShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the expeditorList where createdBy does not contain UPDATED_CREATED_BY
        defaultExpeditorShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultExpeditorShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the expeditorList where updatedBy equals to UPDATED_UPDATED_BY
        defaultExpeditorShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultExpeditorShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the expeditorList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultExpeditorShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultExpeditorShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the expeditorList where updatedBy equals to UPDATED_UPDATED_BY
        defaultExpeditorShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedBy is not null
        defaultExpeditorShouldBeFound("updatedBy.specified=true");

        // Get all the expeditorList where updatedBy is null
        defaultExpeditorShouldNotBeFound("updatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllExpeditorsByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedBy contains DEFAULT_UPDATED_BY
        defaultExpeditorShouldBeFound("updatedBy.contains=" + DEFAULT_UPDATED_BY);

        // Get all the expeditorList where updatedBy contains UPDATED_UPDATED_BY
        defaultExpeditorShouldNotBeFound("updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllExpeditorsByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        expeditorRepository.saveAndFlush(expeditor);

        // Get all the expeditorList where updatedBy does not contain DEFAULT_UPDATED_BY
        defaultExpeditorShouldNotBeFound("updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);

        // Get all the expeditorList where updatedBy does not contain UPDATED_UPDATED_BY
        defaultExpeditorShouldBeFound("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpeditorShouldBeFound(String filter) throws Exception {
        restExpeditorMockMvc.perform(get("/api/expeditors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expeditor.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].adress").value(hasItem(DEFAULT_ADRESS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));

        // Check, that the count call also returns 1
        restExpeditorMockMvc.perform(get("/api/expeditors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpeditorShouldNotBeFound(String filter) throws Exception {
        restExpeditorMockMvc.perform(get("/api/expeditors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpeditorMockMvc.perform(get("/api/expeditors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExpeditor() throws Exception {
        // Get the expeditor
        restExpeditorMockMvc.perform(get("/api/expeditors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpeditor() throws Exception {
        // Initialize the database
        expeditorService.save(expeditor);

        int databaseSizeBeforeUpdate = expeditorRepository.findAll().size();

        // Update the expeditor
        Expeditor updatedExpeditor = expeditorRepository.findById(expeditor.getId()).get();
        // Disconnect from session so that the updates on updatedExpeditor are not directly saved in db
        em.detach(updatedExpeditor);
        updatedExpeditor
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .nature(UPDATED_NATURE)
            .sexe(UPDATED_SEXE)
            .age(UPDATED_AGE)
            .nationality(UPDATED_NATIONALITY)
            .adress(UPDATED_ADRESS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restExpeditorMockMvc.perform(put("/api/expeditors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedExpeditor)))
            .andExpect(status().isOk());

        // Validate the Expeditor in the database
        List<Expeditor> expeditorList = expeditorRepository.findAll();
        assertThat(expeditorList).hasSize(databaseSizeBeforeUpdate);
        Expeditor testExpeditor = expeditorList.get(expeditorList.size() - 1);
        assertThat(testExpeditor.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testExpeditor.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testExpeditor.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testExpeditor.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testExpeditor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testExpeditor.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testExpeditor.getAdress()).isEqualTo(UPDATED_ADRESS);
        assertThat(testExpeditor.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testExpeditor.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testExpeditor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpeditor.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingExpeditor() throws Exception {
        int databaseSizeBeforeUpdate = expeditorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpeditorMockMvc.perform(put("/api/expeditors").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(expeditor)))
            .andExpect(status().isBadRequest());

        // Validate the Expeditor in the database
        List<Expeditor> expeditorList = expeditorRepository.findAll();
        assertThat(expeditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpeditor() throws Exception {
        // Initialize the database
        expeditorService.save(expeditor);

        int databaseSizeBeforeDelete = expeditorRepository.findAll().size();

        // Delete the expeditor
        restExpeditorMockMvc.perform(delete("/api/expeditors/{id}", expeditor.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expeditor> expeditorList = expeditorRepository.findAll();
        assertThat(expeditorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
