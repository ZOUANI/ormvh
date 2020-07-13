package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.CourrierObject;
import com.ormvah.repository.CourrierObjectRepository;
import com.ormvah.service.CourrierObjectService;
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
 * Integration tests for the {@Link CourrierObjectResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
public class CourrierObjectResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private CourrierObjectRepository courrierObjectRepository;

    @Autowired
    private CourrierObjectService courrierObjectService;

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

    private MockMvc restCourrierObjectMockMvc;

    private CourrierObject courrierObject;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CourrierObjectResource courrierObjectResource = new CourrierObjectResource(courrierObjectService);
        this.restCourrierObjectMockMvc = MockMvcBuilders.standaloneSetup(courrierObjectResource)
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
    public static CourrierObject createEntity(EntityManager em) {
        CourrierObject courrierObject = new CourrierObject()
            .title(DEFAULT_TITLE)
            .category(DEFAULT_CATEGORY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return courrierObject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourrierObject createUpdatedEntity(EntityManager em) {
        CourrierObject courrierObject = new CourrierObject()
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return courrierObject;
    }

    @BeforeEach
    public void initTest() {
        courrierObject = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourrierObject() throws Exception {
        int databaseSizeBeforeCreate = courrierObjectRepository.findAll().size();

        // Create the CourrierObject
        restCourrierObjectMockMvc.perform(post("/api/courrier-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrierObject)))
            .andExpect(status().isCreated());

        // Validate the CourrierObject in the database
        List<CourrierObject> courrierObjectList = courrierObjectRepository.findAll();
        assertThat(courrierObjectList).hasSize(databaseSizeBeforeCreate + 1);
        CourrierObject testCourrierObject = courrierObjectList.get(courrierObjectList.size() - 1);
        assertThat(testCourrierObject.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourrierObject.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCourrierObject.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCourrierObject.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testCourrierObject.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCourrierObject.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createCourrierObjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courrierObjectRepository.findAll().size();

        // Create the CourrierObject with an existing ID
        courrierObject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourrierObjectMockMvc.perform(post("/api/courrier-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrierObject)))
            .andExpect(status().isBadRequest());

        // Validate the CourrierObject in the database
        List<CourrierObject> courrierObjectList = courrierObjectRepository.findAll();
        assertThat(courrierObjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourrierObjects() throws Exception {
        // Initialize the database
        courrierObjectRepository.saveAndFlush(courrierObject);

        // Get all the courrierObjectList
        restCourrierObjectMockMvc.perform(get("/api/courrier-objects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courrierObject.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getCourrierObject() throws Exception {
        // Initialize the database
        courrierObjectRepository.saveAndFlush(courrierObject);

        // Get the courrierObject
        restCourrierObjectMockMvc.perform(get("/api/courrier-objects/{id}", courrierObject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courrierObject.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourrierObject() throws Exception {
        // Get the courrierObject
        restCourrierObjectMockMvc.perform(get("/api/courrier-objects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourrierObject() throws Exception {
        // Initialize the database
        courrierObjectService.save(courrierObject);

        int databaseSizeBeforeUpdate = courrierObjectRepository.findAll().size();

        // Update the courrierObject
        CourrierObject updatedCourrierObject = courrierObjectRepository.findById(courrierObject.getId()).get();
        // Disconnect from session so that the updates on updatedCourrierObject are not directly saved in db
        em.detach(updatedCourrierObject);
        updatedCourrierObject
            .title(UPDATED_TITLE)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restCourrierObjectMockMvc.perform(put("/api/courrier-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourrierObject)))
            .andExpect(status().isOk());

        // Validate the CourrierObject in the database
        List<CourrierObject> courrierObjectList = courrierObjectRepository.findAll();
        assertThat(courrierObjectList).hasSize(databaseSizeBeforeUpdate);
        CourrierObject testCourrierObject = courrierObjectList.get(courrierObjectList.size() - 1);
        assertThat(testCourrierObject.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourrierObject.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCourrierObject.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCourrierObject.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCourrierObject.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCourrierObject.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCourrierObject() throws Exception {
        int databaseSizeBeforeUpdate = courrierObjectRepository.findAll().size();

        // Create the CourrierObject

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourrierObjectMockMvc.perform(put("/api/courrier-objects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(courrierObject)))
            .andExpect(status().isBadRequest());

        // Validate the CourrierObject in the database
        List<CourrierObject> courrierObjectList = courrierObjectRepository.findAll();
        assertThat(courrierObjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourrierObject() throws Exception {
        // Initialize the database
        courrierObjectService.save(courrierObject);

        int databaseSizeBeforeDelete = courrierObjectRepository.findAll().size();

        // Delete the courrierObject
        restCourrierObjectMockMvc.perform(delete("/api/courrier-objects/{id}", courrierObject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourrierObject> courrierObjectList = courrierObjectRepository.findAll();
        assertThat(courrierObjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourrierObject.class);
        CourrierObject courrierObject1 = new CourrierObject();
        courrierObject1.setId(1L);
        CourrierObject courrierObject2 = new CourrierObject();
        courrierObject2.setId(courrierObject1.getId());
        assertThat(courrierObject1).isEqualTo(courrierObject2);
        courrierObject2.setId(2L);
        assertThat(courrierObject1).isNotEqualTo(courrierObject2);
        courrierObject1.setId(null);
        assertThat(courrierObject1).isNotEqualTo(courrierObject2);
    }
}
