package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.Courrier;
import com.ormvah.domain.Voie;
import com.ormvah.domain.NatureCourrier;
import com.ormvah.domain.Courrier;
import com.ormvah.domain.Task;
import com.ormvah.domain.Expeditor;
import com.ormvah.domain.LeService;
import com.ormvah.domain.Evaluation;
import com.ormvah.domain.CourrierObject;
import com.ormvah.domain.ExpeditorType;
import com.ormvah.domain.Subdivision;
import com.ormvah.domain.Bordereau;
import com.ormvah.repository.CourrierRepository;
import com.ormvah.service.CourrierService;
import com.ormvah.service.dto.CourrierCriteria;
import com.ormvah.service.CourrierQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ormvah.domain.enumeration.TypeCourrier;
import com.ormvah.domain.enumeration.Status;
/**
 * Integration tests for the {@link CourrierResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CourrierResourceIT {

    private static final String DEFAULT_ID_COURRIER = "AAAAAAAAAA";
    private static final String UPDATED_ID_COURRIER = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TypeCourrier DEFAULT_TYPE_COURRIER = TypeCourrier.Arrivee;
    private static final TypeCourrier UPDATED_TYPE_COURRIER = TypeCourrier.Sortie;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Double DEFAULT_DELAI = 1D;
    private static final Double UPDATED_DELAI = 2D;
    private static final Double SMALLER_DELAI = 1D - 1D;

    private static final Double DEFAULT_RELANCE = 1D;
    private static final Double UPDATED_RELANCE = 2D;
    private static final Double SMALLER_RELANCE = 1D - 1D;

    private static final Boolean DEFAULT_ACCUSE = false;
    private static final Boolean UPDATED_ACCUSE = true;

    private static final Boolean DEFAULT_REPONSE = false;
    private static final Boolean UPDATED_REPONSE = true;

    private static final LocalDate DEFAULT_DATE_ACCUSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCUSE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_ACCUSE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_REPONSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REPONSE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REPONSE = LocalDate.ofEpochDay(-1L);

    private static final Status DEFAULT_STATUS = Status.Ouvert;
    private static final Status UPDATED_STATUS = Status.Encours;

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_RECEIVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECEIVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPEDITEUR_DESC = "AAAAAAAAAA";
    private static final String UPDATED_EXPEDITEUR_DESC = "BBBBBBBBBB";

    private static final Instant DEFAULT_SENT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESTINATAIRE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATAIRE_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DESTINATAIRE_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_DESTINATAIRE_VILLE = "BBBBBBBBBB";

    @Autowired
    private CourrierRepository courrierRepository;

    @Mock
    private CourrierRepository courrierRepositoryMock;

    @Mock
    private CourrierService courrierServiceMock;

    @Autowired
    private CourrierService courrierService;

    @Autowired
    private CourrierQueryService courrierQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourrierMockMvc;

    private Courrier courrier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Courrier createEntity(EntityManager em) {
        Courrier courrier = new Courrier()
            .idCourrier(DEFAULT_ID_COURRIER)
            .subject(DEFAULT_SUBJECT)
            .description(DEFAULT_DESCRIPTION)
            .typeCourrier(DEFAULT_TYPE_COURRIER)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .delai(DEFAULT_DELAI)
            .relance(DEFAULT_RELANCE)
            .accuse(DEFAULT_ACCUSE)
            .reponse(DEFAULT_REPONSE)
            .dateAccuse(DEFAULT_DATE_ACCUSE)
            .dateReponse(DEFAULT_DATE_REPONSE)
            .status(DEFAULT_STATUS)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .receivedAt(DEFAULT_RECEIVED_AT)
            .instruction(DEFAULT_INSTRUCTION)
            .expediteurDesc(DEFAULT_EXPEDITEUR_DESC)
            .sentAt(DEFAULT_SENT_AT)
            .destinataireDesc(DEFAULT_DESTINATAIRE_DESC)
            .destinataireVille(DEFAULT_DESTINATAIRE_VILLE);
        return courrier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Courrier createUpdatedEntity(EntityManager em) {
        Courrier courrier = new Courrier()
            .idCourrier(UPDATED_ID_COURRIER)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .typeCourrier(UPDATED_TYPE_COURRIER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .accuse(UPDATED_ACCUSE)
            .reponse(UPDATED_REPONSE)
            .dateAccuse(UPDATED_DATE_ACCUSE)
            .dateReponse(UPDATED_DATE_REPONSE)
            .status(UPDATED_STATUS)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .receivedAt(UPDATED_RECEIVED_AT)
            .instruction(UPDATED_INSTRUCTION)
            .expediteurDesc(UPDATED_EXPEDITEUR_DESC)
            .sentAt(UPDATED_SENT_AT)
            .destinataireDesc(UPDATED_DESTINATAIRE_DESC)
            .destinataireVille(UPDATED_DESTINATAIRE_VILLE);
        return courrier;
    }

    @BeforeEach
    public void initTest() {
        courrier = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourrier() throws Exception {
        int databaseSizeBeforeCreate = courrierRepository.findAll().size();
        // Create the Courrier
        restCourrierMockMvc.perform(post("/api/courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courrier)))
            .andExpect(status().isCreated());

        // Validate the Courrier in the database
        List<Courrier> courrierList = courrierRepository.findAll();
        assertThat(courrierList).hasSize(databaseSizeBeforeCreate + 1);
        Courrier testCourrier = courrierList.get(courrierList.size() - 1);
        assertThat(testCourrier.getIdCourrier()).isEqualTo(DEFAULT_ID_COURRIER);
        assertThat(testCourrier.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testCourrier.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourrier.getTypeCourrier()).isEqualTo(DEFAULT_TYPE_COURRIER);
        assertThat(testCourrier.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCourrier.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testCourrier.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCourrier.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCourrier.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testCourrier.getRelance()).isEqualTo(DEFAULT_RELANCE);
        assertThat(testCourrier.isAccuse()).isEqualTo(DEFAULT_ACCUSE);
        assertThat(testCourrier.isReponse()).isEqualTo(DEFAULT_REPONSE);
        assertThat(testCourrier.getDateAccuse()).isEqualTo(DEFAULT_DATE_ACCUSE);
        assertThat(testCourrier.getDateReponse()).isEqualTo(DEFAULT_DATE_REPONSE);
        assertThat(testCourrier.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCourrier.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCourrier.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testCourrier.getReceivedAt()).isEqualTo(DEFAULT_RECEIVED_AT);
        assertThat(testCourrier.getInstruction()).isEqualTo(DEFAULT_INSTRUCTION);
        assertThat(testCourrier.getExpediteurDesc()).isEqualTo(DEFAULT_EXPEDITEUR_DESC);
        assertThat(testCourrier.getSentAt()).isEqualTo(DEFAULT_SENT_AT);
        assertThat(testCourrier.getDestinataireDesc()).isEqualTo(DEFAULT_DESTINATAIRE_DESC);
        assertThat(testCourrier.getDestinataireVille()).isEqualTo(DEFAULT_DESTINATAIRE_VILLE);
    }

    @Test
    @Transactional
    public void createCourrierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courrierRepository.findAll().size();

        // Create the Courrier with an existing ID
        courrier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourrierMockMvc.perform(post("/api/courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courrier)))
            .andExpect(status().isBadRequest());

        // Validate the Courrier in the database
        List<Courrier> courrierList = courrierRepository.findAll();
        assertThat(courrierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCourriers() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList
        restCourrierMockMvc.perform(get("/api/courriers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCourrier").value(hasItem(DEFAULT_ID_COURRIER)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].typeCourrier").value(hasItem(DEFAULT_TYPE_COURRIER.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.doubleValue())))
            .andExpect(jsonPath("$.[*].relance").value(hasItem(DEFAULT_RELANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accuse").value(hasItem(DEFAULT_ACCUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].reponse").value(hasItem(DEFAULT_REPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAccuse").value(hasItem(DEFAULT_DATE_ACCUSE.toString())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].receivedAt").value(hasItem(DEFAULT_RECEIVED_AT.toString())))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION)))
            .andExpect(jsonPath("$.[*].expediteurDesc").value(hasItem(DEFAULT_EXPEDITEUR_DESC)))
            .andExpect(jsonPath("$.[*].sentAt").value(hasItem(DEFAULT_SENT_AT.toString())))
            .andExpect(jsonPath("$.[*].destinataireDesc").value(hasItem(DEFAULT_DESTINATAIRE_DESC)))
            .andExpect(jsonPath("$.[*].destinataireVille").value(hasItem(DEFAULT_DESTINATAIRE_VILLE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCourriersWithEagerRelationshipsIsEnabled() throws Exception {
        when(courrierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCourrierMockMvc.perform(get("/api/courriers?eagerload=true"))
            .andExpect(status().isOk());

        verify(courrierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCourriersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(courrierServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCourrierMockMvc.perform(get("/api/courriers?eagerload=true"))
            .andExpect(status().isOk());

        verify(courrierServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCourrier() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get the courrier
        restCourrierMockMvc.perform(get("/api/courriers/{id}", courrier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courrier.getId().intValue()))
            .andExpect(jsonPath("$.idCourrier").value(DEFAULT_ID_COURRIER))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.typeCourrier").value(DEFAULT_TYPE_COURRIER.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.doubleValue()))
            .andExpect(jsonPath("$.relance").value(DEFAULT_RELANCE.doubleValue()))
            .andExpect(jsonPath("$.accuse").value(DEFAULT_ACCUSE.booleanValue()))
            .andExpect(jsonPath("$.reponse").value(DEFAULT_REPONSE.booleanValue()))
            .andExpect(jsonPath("$.dateAccuse").value(DEFAULT_DATE_ACCUSE.toString()))
            .andExpect(jsonPath("$.dateReponse").value(DEFAULT_DATE_REPONSE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.receivedAt").value(DEFAULT_RECEIVED_AT.toString()))
            .andExpect(jsonPath("$.instruction").value(DEFAULT_INSTRUCTION))
            .andExpect(jsonPath("$.expediteurDesc").value(DEFAULT_EXPEDITEUR_DESC))
            .andExpect(jsonPath("$.sentAt").value(DEFAULT_SENT_AT.toString()))
            .andExpect(jsonPath("$.destinataireDesc").value(DEFAULT_DESTINATAIRE_DESC))
            .andExpect(jsonPath("$.destinataireVille").value(DEFAULT_DESTINATAIRE_VILLE));
    }


    @Test
    @Transactional
    public void getCourriersByIdFiltering() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        Long id = courrier.getId();

        defaultCourrierShouldBeFound("id.equals=" + id);
        defaultCourrierShouldNotBeFound("id.notEquals=" + id);

        defaultCourrierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCourrierShouldNotBeFound("id.greaterThan=" + id);

        defaultCourrierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCourrierShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCourriersByIdCourrierIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where idCourrier equals to DEFAULT_ID_COURRIER
        defaultCourrierShouldBeFound("idCourrier.equals=" + DEFAULT_ID_COURRIER);

        // Get all the courrierList where idCourrier equals to UPDATED_ID_COURRIER
        defaultCourrierShouldNotBeFound("idCourrier.equals=" + UPDATED_ID_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByIdCourrierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where idCourrier not equals to DEFAULT_ID_COURRIER
        defaultCourrierShouldNotBeFound("idCourrier.notEquals=" + DEFAULT_ID_COURRIER);

        // Get all the courrierList where idCourrier not equals to UPDATED_ID_COURRIER
        defaultCourrierShouldBeFound("idCourrier.notEquals=" + UPDATED_ID_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByIdCourrierIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where idCourrier in DEFAULT_ID_COURRIER or UPDATED_ID_COURRIER
        defaultCourrierShouldBeFound("idCourrier.in=" + DEFAULT_ID_COURRIER + "," + UPDATED_ID_COURRIER);

        // Get all the courrierList where idCourrier equals to UPDATED_ID_COURRIER
        defaultCourrierShouldNotBeFound("idCourrier.in=" + UPDATED_ID_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByIdCourrierIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where idCourrier is not null
        defaultCourrierShouldBeFound("idCourrier.specified=true");

        // Get all the courrierList where idCourrier is null
        defaultCourrierShouldNotBeFound("idCourrier.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByIdCourrierContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where idCourrier contains DEFAULT_ID_COURRIER
        defaultCourrierShouldBeFound("idCourrier.contains=" + DEFAULT_ID_COURRIER);

        // Get all the courrierList where idCourrier contains UPDATED_ID_COURRIER
        defaultCourrierShouldNotBeFound("idCourrier.contains=" + UPDATED_ID_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByIdCourrierNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where idCourrier does not contain DEFAULT_ID_COURRIER
        defaultCourrierShouldNotBeFound("idCourrier.doesNotContain=" + DEFAULT_ID_COURRIER);

        // Get all the courrierList where idCourrier does not contain UPDATED_ID_COURRIER
        defaultCourrierShouldBeFound("idCourrier.doesNotContain=" + UPDATED_ID_COURRIER);
    }


    @Test
    @Transactional
    public void getAllCourriersBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where subject equals to DEFAULT_SUBJECT
        defaultCourrierShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the courrierList where subject equals to UPDATED_SUBJECT
        defaultCourrierShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySubjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where subject not equals to DEFAULT_SUBJECT
        defaultCourrierShouldNotBeFound("subject.notEquals=" + DEFAULT_SUBJECT);

        // Get all the courrierList where subject not equals to UPDATED_SUBJECT
        defaultCourrierShouldBeFound("subject.notEquals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultCourrierShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the courrierList where subject equals to UPDATED_SUBJECT
        defaultCourrierShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where subject is not null
        defaultCourrierShouldBeFound("subject.specified=true");

        // Get all the courrierList where subject is null
        defaultCourrierShouldNotBeFound("subject.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersBySubjectContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where subject contains DEFAULT_SUBJECT
        defaultCourrierShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the courrierList where subject contains UPDATED_SUBJECT
        defaultCourrierShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where subject does not contain DEFAULT_SUBJECT
        defaultCourrierShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the courrierList where subject does not contain UPDATED_SUBJECT
        defaultCourrierShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }


    @Test
    @Transactional
    public void getAllCourriersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where description equals to DEFAULT_DESCRIPTION
        defaultCourrierShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the courrierList where description equals to UPDATED_DESCRIPTION
        defaultCourrierShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where description not equals to DEFAULT_DESCRIPTION
        defaultCourrierShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the courrierList where description not equals to UPDATED_DESCRIPTION
        defaultCourrierShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCourrierShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the courrierList where description equals to UPDATED_DESCRIPTION
        defaultCourrierShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where description is not null
        defaultCourrierShouldBeFound("description.specified=true");

        // Get all the courrierList where description is null
        defaultCourrierShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where description contains DEFAULT_DESCRIPTION
        defaultCourrierShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the courrierList where description contains UPDATED_DESCRIPTION
        defaultCourrierShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where description does not contain DEFAULT_DESCRIPTION
        defaultCourrierShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the courrierList where description does not contain UPDATED_DESCRIPTION
        defaultCourrierShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCourriersByTypeCourrierIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where typeCourrier equals to DEFAULT_TYPE_COURRIER
        defaultCourrierShouldBeFound("typeCourrier.equals=" + DEFAULT_TYPE_COURRIER);

        // Get all the courrierList where typeCourrier equals to UPDATED_TYPE_COURRIER
        defaultCourrierShouldNotBeFound("typeCourrier.equals=" + UPDATED_TYPE_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByTypeCourrierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where typeCourrier not equals to DEFAULT_TYPE_COURRIER
        defaultCourrierShouldNotBeFound("typeCourrier.notEquals=" + DEFAULT_TYPE_COURRIER);

        // Get all the courrierList where typeCourrier not equals to UPDATED_TYPE_COURRIER
        defaultCourrierShouldBeFound("typeCourrier.notEquals=" + UPDATED_TYPE_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByTypeCourrierIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where typeCourrier in DEFAULT_TYPE_COURRIER or UPDATED_TYPE_COURRIER
        defaultCourrierShouldBeFound("typeCourrier.in=" + DEFAULT_TYPE_COURRIER + "," + UPDATED_TYPE_COURRIER);

        // Get all the courrierList where typeCourrier equals to UPDATED_TYPE_COURRIER
        defaultCourrierShouldNotBeFound("typeCourrier.in=" + UPDATED_TYPE_COURRIER);
    }

    @Test
    @Transactional
    public void getAllCourriersByTypeCourrierIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where typeCourrier is not null
        defaultCourrierShouldBeFound("typeCourrier.specified=true");

        // Get all the courrierList where typeCourrier is null
        defaultCourrierShouldNotBeFound("typeCourrier.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdAt equals to DEFAULT_CREATED_AT
        defaultCourrierShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the courrierList where createdAt equals to UPDATED_CREATED_AT
        defaultCourrierShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdAt not equals to DEFAULT_CREATED_AT
        defaultCourrierShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the courrierList where createdAt not equals to UPDATED_CREATED_AT
        defaultCourrierShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultCourrierShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the courrierList where createdAt equals to UPDATED_CREATED_AT
        defaultCourrierShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdAt is not null
        defaultCourrierShouldBeFound("createdAt.specified=true");

        // Get all the courrierList where createdAt is null
        defaultCourrierShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultCourrierShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the courrierList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCourrierShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultCourrierShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the courrierList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultCourrierShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultCourrierShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the courrierList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCourrierShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedAt is not null
        defaultCourrierShouldBeFound("updatedAt.specified=true");

        // Get all the courrierList where updatedAt is null
        defaultCourrierShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdBy equals to DEFAULT_CREATED_BY
        defaultCourrierShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the courrierList where createdBy equals to UPDATED_CREATED_BY
        defaultCourrierShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdBy not equals to DEFAULT_CREATED_BY
        defaultCourrierShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the courrierList where createdBy not equals to UPDATED_CREATED_BY
        defaultCourrierShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultCourrierShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the courrierList where createdBy equals to UPDATED_CREATED_BY
        defaultCourrierShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdBy is not null
        defaultCourrierShouldBeFound("createdBy.specified=true");

        // Get all the courrierList where createdBy is null
        defaultCourrierShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdBy contains DEFAULT_CREATED_BY
        defaultCourrierShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the courrierList where createdBy contains UPDATED_CREATED_BY
        defaultCourrierShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where createdBy does not contain DEFAULT_CREATED_BY
        defaultCourrierShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the courrierList where createdBy does not contain UPDATED_CREATED_BY
        defaultCourrierShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllCourriersByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultCourrierShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the courrierList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCourrierShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedBy not equals to DEFAULT_UPDATED_BY
        defaultCourrierShouldNotBeFound("updatedBy.notEquals=" + DEFAULT_UPDATED_BY);

        // Get all the courrierList where updatedBy not equals to UPDATED_UPDATED_BY
        defaultCourrierShouldBeFound("updatedBy.notEquals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultCourrierShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the courrierList where updatedBy equals to UPDATED_UPDATED_BY
        defaultCourrierShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedBy is not null
        defaultCourrierShouldBeFound("updatedBy.specified=true");

        // Get all the courrierList where updatedBy is null
        defaultCourrierShouldNotBeFound("updatedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByUpdatedByContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedBy contains DEFAULT_UPDATED_BY
        defaultCourrierShouldBeFound("updatedBy.contains=" + DEFAULT_UPDATED_BY);

        // Get all the courrierList where updatedBy contains UPDATED_UPDATED_BY
        defaultCourrierShouldNotBeFound("updatedBy.contains=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllCourriersByUpdatedByNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where updatedBy does not contain DEFAULT_UPDATED_BY
        defaultCourrierShouldNotBeFound("updatedBy.doesNotContain=" + DEFAULT_UPDATED_BY);

        // Get all the courrierList where updatedBy does not contain UPDATED_UPDATED_BY
        defaultCourrierShouldBeFound("updatedBy.doesNotContain=" + UPDATED_UPDATED_BY);
    }


    @Test
    @Transactional
    public void getAllCourriersByDelaiIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai equals to DEFAULT_DELAI
        defaultCourrierShouldBeFound("delai.equals=" + DEFAULT_DELAI);

        // Get all the courrierList where delai equals to UPDATED_DELAI
        defaultCourrierShouldNotBeFound("delai.equals=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai not equals to DEFAULT_DELAI
        defaultCourrierShouldNotBeFound("delai.notEquals=" + DEFAULT_DELAI);

        // Get all the courrierList where delai not equals to UPDATED_DELAI
        defaultCourrierShouldBeFound("delai.notEquals=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai in DEFAULT_DELAI or UPDATED_DELAI
        defaultCourrierShouldBeFound("delai.in=" + DEFAULT_DELAI + "," + UPDATED_DELAI);

        // Get all the courrierList where delai equals to UPDATED_DELAI
        defaultCourrierShouldNotBeFound("delai.in=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai is not null
        defaultCourrierShouldBeFound("delai.specified=true");

        // Get all the courrierList where delai is null
        defaultCourrierShouldNotBeFound("delai.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai is greater than or equal to DEFAULT_DELAI
        defaultCourrierShouldBeFound("delai.greaterThanOrEqual=" + DEFAULT_DELAI);

        // Get all the courrierList where delai is greater than or equal to UPDATED_DELAI
        defaultCourrierShouldNotBeFound("delai.greaterThanOrEqual=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai is less than or equal to DEFAULT_DELAI
        defaultCourrierShouldBeFound("delai.lessThanOrEqual=" + DEFAULT_DELAI);

        // Get all the courrierList where delai is less than or equal to SMALLER_DELAI
        defaultCourrierShouldNotBeFound("delai.lessThanOrEqual=" + SMALLER_DELAI);
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsLessThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai is less than DEFAULT_DELAI
        defaultCourrierShouldNotBeFound("delai.lessThan=" + DEFAULT_DELAI);

        // Get all the courrierList where delai is less than UPDATED_DELAI
        defaultCourrierShouldBeFound("delai.lessThan=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllCourriersByDelaiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where delai is greater than DEFAULT_DELAI
        defaultCourrierShouldNotBeFound("delai.greaterThan=" + DEFAULT_DELAI);

        // Get all the courrierList where delai is greater than SMALLER_DELAI
        defaultCourrierShouldBeFound("delai.greaterThan=" + SMALLER_DELAI);
    }


    @Test
    @Transactional
    public void getAllCourriersByRelanceIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance equals to DEFAULT_RELANCE
        defaultCourrierShouldBeFound("relance.equals=" + DEFAULT_RELANCE);

        // Get all the courrierList where relance equals to UPDATED_RELANCE
        defaultCourrierShouldNotBeFound("relance.equals=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance not equals to DEFAULT_RELANCE
        defaultCourrierShouldNotBeFound("relance.notEquals=" + DEFAULT_RELANCE);

        // Get all the courrierList where relance not equals to UPDATED_RELANCE
        defaultCourrierShouldBeFound("relance.notEquals=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance in DEFAULT_RELANCE or UPDATED_RELANCE
        defaultCourrierShouldBeFound("relance.in=" + DEFAULT_RELANCE + "," + UPDATED_RELANCE);

        // Get all the courrierList where relance equals to UPDATED_RELANCE
        defaultCourrierShouldNotBeFound("relance.in=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance is not null
        defaultCourrierShouldBeFound("relance.specified=true");

        // Get all the courrierList where relance is null
        defaultCourrierShouldNotBeFound("relance.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance is greater than or equal to DEFAULT_RELANCE
        defaultCourrierShouldBeFound("relance.greaterThanOrEqual=" + DEFAULT_RELANCE);

        // Get all the courrierList where relance is greater than or equal to UPDATED_RELANCE
        defaultCourrierShouldNotBeFound("relance.greaterThanOrEqual=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance is less than or equal to DEFAULT_RELANCE
        defaultCourrierShouldBeFound("relance.lessThanOrEqual=" + DEFAULT_RELANCE);

        // Get all the courrierList where relance is less than or equal to SMALLER_RELANCE
        defaultCourrierShouldNotBeFound("relance.lessThanOrEqual=" + SMALLER_RELANCE);
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsLessThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance is less than DEFAULT_RELANCE
        defaultCourrierShouldNotBeFound("relance.lessThan=" + DEFAULT_RELANCE);

        // Get all the courrierList where relance is less than UPDATED_RELANCE
        defaultCourrierShouldBeFound("relance.lessThan=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllCourriersByRelanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where relance is greater than DEFAULT_RELANCE
        defaultCourrierShouldNotBeFound("relance.greaterThan=" + DEFAULT_RELANCE);

        // Get all the courrierList where relance is greater than SMALLER_RELANCE
        defaultCourrierShouldBeFound("relance.greaterThan=" + SMALLER_RELANCE);
    }


    @Test
    @Transactional
    public void getAllCourriersByAccuseIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where accuse equals to DEFAULT_ACCUSE
        defaultCourrierShouldBeFound("accuse.equals=" + DEFAULT_ACCUSE);

        // Get all the courrierList where accuse equals to UPDATED_ACCUSE
        defaultCourrierShouldNotBeFound("accuse.equals=" + UPDATED_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByAccuseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where accuse not equals to DEFAULT_ACCUSE
        defaultCourrierShouldNotBeFound("accuse.notEquals=" + DEFAULT_ACCUSE);

        // Get all the courrierList where accuse not equals to UPDATED_ACCUSE
        defaultCourrierShouldBeFound("accuse.notEquals=" + UPDATED_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByAccuseIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where accuse in DEFAULT_ACCUSE or UPDATED_ACCUSE
        defaultCourrierShouldBeFound("accuse.in=" + DEFAULT_ACCUSE + "," + UPDATED_ACCUSE);

        // Get all the courrierList where accuse equals to UPDATED_ACCUSE
        defaultCourrierShouldNotBeFound("accuse.in=" + UPDATED_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByAccuseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where accuse is not null
        defaultCourrierShouldBeFound("accuse.specified=true");

        // Get all the courrierList where accuse is null
        defaultCourrierShouldNotBeFound("accuse.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByReponseIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where reponse equals to DEFAULT_REPONSE
        defaultCourrierShouldBeFound("reponse.equals=" + DEFAULT_REPONSE);

        // Get all the courrierList where reponse equals to UPDATED_REPONSE
        defaultCourrierShouldNotBeFound("reponse.equals=" + UPDATED_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByReponseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where reponse not equals to DEFAULT_REPONSE
        defaultCourrierShouldNotBeFound("reponse.notEquals=" + DEFAULT_REPONSE);

        // Get all the courrierList where reponse not equals to UPDATED_REPONSE
        defaultCourrierShouldBeFound("reponse.notEquals=" + UPDATED_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByReponseIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where reponse in DEFAULT_REPONSE or UPDATED_REPONSE
        defaultCourrierShouldBeFound("reponse.in=" + DEFAULT_REPONSE + "," + UPDATED_REPONSE);

        // Get all the courrierList where reponse equals to UPDATED_REPONSE
        defaultCourrierShouldNotBeFound("reponse.in=" + UPDATED_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByReponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where reponse is not null
        defaultCourrierShouldBeFound("reponse.specified=true");

        // Get all the courrierList where reponse is null
        defaultCourrierShouldNotBeFound("reponse.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse equals to DEFAULT_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.equals=" + DEFAULT_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse equals to UPDATED_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.equals=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse not equals to DEFAULT_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.notEquals=" + DEFAULT_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse not equals to UPDATED_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.notEquals=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse in DEFAULT_DATE_ACCUSE or UPDATED_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.in=" + DEFAULT_DATE_ACCUSE + "," + UPDATED_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse equals to UPDATED_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.in=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse is not null
        defaultCourrierShouldBeFound("dateAccuse.specified=true");

        // Get all the courrierList where dateAccuse is null
        defaultCourrierShouldNotBeFound("dateAccuse.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse is greater than or equal to DEFAULT_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.greaterThanOrEqual=" + DEFAULT_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse is greater than or equal to UPDATED_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.greaterThanOrEqual=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse is less than or equal to DEFAULT_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.lessThanOrEqual=" + DEFAULT_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse is less than or equal to SMALLER_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.lessThanOrEqual=" + SMALLER_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsLessThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse is less than DEFAULT_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.lessThan=" + DEFAULT_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse is less than UPDATED_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.lessThan=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateAccuseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateAccuse is greater than DEFAULT_DATE_ACCUSE
        defaultCourrierShouldNotBeFound("dateAccuse.greaterThan=" + DEFAULT_DATE_ACCUSE);

        // Get all the courrierList where dateAccuse is greater than SMALLER_DATE_ACCUSE
        defaultCourrierShouldBeFound("dateAccuse.greaterThan=" + SMALLER_DATE_ACCUSE);
    }


    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse equals to DEFAULT_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.equals=" + DEFAULT_DATE_REPONSE);

        // Get all the courrierList where dateReponse equals to UPDATED_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.equals=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse not equals to DEFAULT_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.notEquals=" + DEFAULT_DATE_REPONSE);

        // Get all the courrierList where dateReponse not equals to UPDATED_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.notEquals=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse in DEFAULT_DATE_REPONSE or UPDATED_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.in=" + DEFAULT_DATE_REPONSE + "," + UPDATED_DATE_REPONSE);

        // Get all the courrierList where dateReponse equals to UPDATED_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.in=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse is not null
        defaultCourrierShouldBeFound("dateReponse.specified=true");

        // Get all the courrierList where dateReponse is null
        defaultCourrierShouldNotBeFound("dateReponse.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse is greater than or equal to DEFAULT_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.greaterThanOrEqual=" + DEFAULT_DATE_REPONSE);

        // Get all the courrierList where dateReponse is greater than or equal to UPDATED_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.greaterThanOrEqual=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse is less than or equal to DEFAULT_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.lessThanOrEqual=" + DEFAULT_DATE_REPONSE);

        // Get all the courrierList where dateReponse is less than or equal to SMALLER_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.lessThanOrEqual=" + SMALLER_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsLessThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse is less than DEFAULT_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.lessThan=" + DEFAULT_DATE_REPONSE);

        // Get all the courrierList where dateReponse is less than UPDATED_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.lessThan=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDateReponseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where dateReponse is greater than DEFAULT_DATE_REPONSE
        defaultCourrierShouldNotBeFound("dateReponse.greaterThan=" + DEFAULT_DATE_REPONSE);

        // Get all the courrierList where dateReponse is greater than SMALLER_DATE_REPONSE
        defaultCourrierShouldBeFound("dateReponse.greaterThan=" + SMALLER_DATE_REPONSE);
    }


    @Test
    @Transactional
    public void getAllCourriersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where status equals to DEFAULT_STATUS
        defaultCourrierShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the courrierList where status equals to UPDATED_STATUS
        defaultCourrierShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCourriersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where status not equals to DEFAULT_STATUS
        defaultCourrierShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the courrierList where status not equals to UPDATED_STATUS
        defaultCourrierShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCourriersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCourrierShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the courrierList where status equals to UPDATED_STATUS
        defaultCourrierShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCourriersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where status is not null
        defaultCourrierShouldBeFound("status.specified=true");

        // Get all the courrierList where status is null
        defaultCourrierShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByReceivedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where receivedAt equals to DEFAULT_RECEIVED_AT
        defaultCourrierShouldBeFound("receivedAt.equals=" + DEFAULT_RECEIVED_AT);

        // Get all the courrierList where receivedAt equals to UPDATED_RECEIVED_AT
        defaultCourrierShouldNotBeFound("receivedAt.equals=" + UPDATED_RECEIVED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByReceivedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where receivedAt not equals to DEFAULT_RECEIVED_AT
        defaultCourrierShouldNotBeFound("receivedAt.notEquals=" + DEFAULT_RECEIVED_AT);

        // Get all the courrierList where receivedAt not equals to UPDATED_RECEIVED_AT
        defaultCourrierShouldBeFound("receivedAt.notEquals=" + UPDATED_RECEIVED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByReceivedAtIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where receivedAt in DEFAULT_RECEIVED_AT or UPDATED_RECEIVED_AT
        defaultCourrierShouldBeFound("receivedAt.in=" + DEFAULT_RECEIVED_AT + "," + UPDATED_RECEIVED_AT);

        // Get all the courrierList where receivedAt equals to UPDATED_RECEIVED_AT
        defaultCourrierShouldNotBeFound("receivedAt.in=" + UPDATED_RECEIVED_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersByReceivedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where receivedAt is not null
        defaultCourrierShouldBeFound("receivedAt.specified=true");

        // Get all the courrierList where receivedAt is null
        defaultCourrierShouldNotBeFound("receivedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByInstructionIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where instruction equals to DEFAULT_INSTRUCTION
        defaultCourrierShouldBeFound("instruction.equals=" + DEFAULT_INSTRUCTION);

        // Get all the courrierList where instruction equals to UPDATED_INSTRUCTION
        defaultCourrierShouldNotBeFound("instruction.equals=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByInstructionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where instruction not equals to DEFAULT_INSTRUCTION
        defaultCourrierShouldNotBeFound("instruction.notEquals=" + DEFAULT_INSTRUCTION);

        // Get all the courrierList where instruction not equals to UPDATED_INSTRUCTION
        defaultCourrierShouldBeFound("instruction.notEquals=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByInstructionIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where instruction in DEFAULT_INSTRUCTION or UPDATED_INSTRUCTION
        defaultCourrierShouldBeFound("instruction.in=" + DEFAULT_INSTRUCTION + "," + UPDATED_INSTRUCTION);

        // Get all the courrierList where instruction equals to UPDATED_INSTRUCTION
        defaultCourrierShouldNotBeFound("instruction.in=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByInstructionIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where instruction is not null
        defaultCourrierShouldBeFound("instruction.specified=true");

        // Get all the courrierList where instruction is null
        defaultCourrierShouldNotBeFound("instruction.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByInstructionContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where instruction contains DEFAULT_INSTRUCTION
        defaultCourrierShouldBeFound("instruction.contains=" + DEFAULT_INSTRUCTION);

        // Get all the courrierList where instruction contains UPDATED_INSTRUCTION
        defaultCourrierShouldNotBeFound("instruction.contains=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllCourriersByInstructionNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where instruction does not contain DEFAULT_INSTRUCTION
        defaultCourrierShouldNotBeFound("instruction.doesNotContain=" + DEFAULT_INSTRUCTION);

        // Get all the courrierList where instruction does not contain UPDATED_INSTRUCTION
        defaultCourrierShouldBeFound("instruction.doesNotContain=" + UPDATED_INSTRUCTION);
    }


    @Test
    @Transactional
    public void getAllCourriersByExpediteurDescIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where expediteurDesc equals to DEFAULT_EXPEDITEUR_DESC
        defaultCourrierShouldBeFound("expediteurDesc.equals=" + DEFAULT_EXPEDITEUR_DESC);

        // Get all the courrierList where expediteurDesc equals to UPDATED_EXPEDITEUR_DESC
        defaultCourrierShouldNotBeFound("expediteurDesc.equals=" + UPDATED_EXPEDITEUR_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByExpediteurDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where expediteurDesc not equals to DEFAULT_EXPEDITEUR_DESC
        defaultCourrierShouldNotBeFound("expediteurDesc.notEquals=" + DEFAULT_EXPEDITEUR_DESC);

        // Get all the courrierList where expediteurDesc not equals to UPDATED_EXPEDITEUR_DESC
        defaultCourrierShouldBeFound("expediteurDesc.notEquals=" + UPDATED_EXPEDITEUR_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByExpediteurDescIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where expediteurDesc in DEFAULT_EXPEDITEUR_DESC or UPDATED_EXPEDITEUR_DESC
        defaultCourrierShouldBeFound("expediteurDesc.in=" + DEFAULT_EXPEDITEUR_DESC + "," + UPDATED_EXPEDITEUR_DESC);

        // Get all the courrierList where expediteurDesc equals to UPDATED_EXPEDITEUR_DESC
        defaultCourrierShouldNotBeFound("expediteurDesc.in=" + UPDATED_EXPEDITEUR_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByExpediteurDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where expediteurDesc is not null
        defaultCourrierShouldBeFound("expediteurDesc.specified=true");

        // Get all the courrierList where expediteurDesc is null
        defaultCourrierShouldNotBeFound("expediteurDesc.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByExpediteurDescContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where expediteurDesc contains DEFAULT_EXPEDITEUR_DESC
        defaultCourrierShouldBeFound("expediteurDesc.contains=" + DEFAULT_EXPEDITEUR_DESC);

        // Get all the courrierList where expediteurDesc contains UPDATED_EXPEDITEUR_DESC
        defaultCourrierShouldNotBeFound("expediteurDesc.contains=" + UPDATED_EXPEDITEUR_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByExpediteurDescNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where expediteurDesc does not contain DEFAULT_EXPEDITEUR_DESC
        defaultCourrierShouldNotBeFound("expediteurDesc.doesNotContain=" + DEFAULT_EXPEDITEUR_DESC);

        // Get all the courrierList where expediteurDesc does not contain UPDATED_EXPEDITEUR_DESC
        defaultCourrierShouldBeFound("expediteurDesc.doesNotContain=" + UPDATED_EXPEDITEUR_DESC);
    }


    @Test
    @Transactional
    public void getAllCourriersBySentAtIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where sentAt equals to DEFAULT_SENT_AT
        defaultCourrierShouldBeFound("sentAt.equals=" + DEFAULT_SENT_AT);

        // Get all the courrierList where sentAt equals to UPDATED_SENT_AT
        defaultCourrierShouldNotBeFound("sentAt.equals=" + UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySentAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where sentAt not equals to DEFAULT_SENT_AT
        defaultCourrierShouldNotBeFound("sentAt.notEquals=" + DEFAULT_SENT_AT);

        // Get all the courrierList where sentAt not equals to UPDATED_SENT_AT
        defaultCourrierShouldBeFound("sentAt.notEquals=" + UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySentAtIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where sentAt in DEFAULT_SENT_AT or UPDATED_SENT_AT
        defaultCourrierShouldBeFound("sentAt.in=" + DEFAULT_SENT_AT + "," + UPDATED_SENT_AT);

        // Get all the courrierList where sentAt equals to UPDATED_SENT_AT
        defaultCourrierShouldNotBeFound("sentAt.in=" + UPDATED_SENT_AT);
    }

    @Test
    @Transactional
    public void getAllCourriersBySentAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where sentAt is not null
        defaultCourrierShouldBeFound("sentAt.specified=true");

        // Get all the courrierList where sentAt is null
        defaultCourrierShouldNotBeFound("sentAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireDescIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireDesc equals to DEFAULT_DESTINATAIRE_DESC
        defaultCourrierShouldBeFound("destinataireDesc.equals=" + DEFAULT_DESTINATAIRE_DESC);

        // Get all the courrierList where destinataireDesc equals to UPDATED_DESTINATAIRE_DESC
        defaultCourrierShouldNotBeFound("destinataireDesc.equals=" + UPDATED_DESTINATAIRE_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireDesc not equals to DEFAULT_DESTINATAIRE_DESC
        defaultCourrierShouldNotBeFound("destinataireDesc.notEquals=" + DEFAULT_DESTINATAIRE_DESC);

        // Get all the courrierList where destinataireDesc not equals to UPDATED_DESTINATAIRE_DESC
        defaultCourrierShouldBeFound("destinataireDesc.notEquals=" + UPDATED_DESTINATAIRE_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireDescIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireDesc in DEFAULT_DESTINATAIRE_DESC or UPDATED_DESTINATAIRE_DESC
        defaultCourrierShouldBeFound("destinataireDesc.in=" + DEFAULT_DESTINATAIRE_DESC + "," + UPDATED_DESTINATAIRE_DESC);

        // Get all the courrierList where destinataireDesc equals to UPDATED_DESTINATAIRE_DESC
        defaultCourrierShouldNotBeFound("destinataireDesc.in=" + UPDATED_DESTINATAIRE_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireDesc is not null
        defaultCourrierShouldBeFound("destinataireDesc.specified=true");

        // Get all the courrierList where destinataireDesc is null
        defaultCourrierShouldNotBeFound("destinataireDesc.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByDestinataireDescContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireDesc contains DEFAULT_DESTINATAIRE_DESC
        defaultCourrierShouldBeFound("destinataireDesc.contains=" + DEFAULT_DESTINATAIRE_DESC);

        // Get all the courrierList where destinataireDesc contains UPDATED_DESTINATAIRE_DESC
        defaultCourrierShouldNotBeFound("destinataireDesc.contains=" + UPDATED_DESTINATAIRE_DESC);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireDescNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireDesc does not contain DEFAULT_DESTINATAIRE_DESC
        defaultCourrierShouldNotBeFound("destinataireDesc.doesNotContain=" + DEFAULT_DESTINATAIRE_DESC);

        // Get all the courrierList where destinataireDesc does not contain UPDATED_DESTINATAIRE_DESC
        defaultCourrierShouldBeFound("destinataireDesc.doesNotContain=" + UPDATED_DESTINATAIRE_DESC);
    }


    @Test
    @Transactional
    public void getAllCourriersByDestinataireVilleIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireVille equals to DEFAULT_DESTINATAIRE_VILLE
        defaultCourrierShouldBeFound("destinataireVille.equals=" + DEFAULT_DESTINATAIRE_VILLE);

        // Get all the courrierList where destinataireVille equals to UPDATED_DESTINATAIRE_VILLE
        defaultCourrierShouldNotBeFound("destinataireVille.equals=" + UPDATED_DESTINATAIRE_VILLE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireVilleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireVille not equals to DEFAULT_DESTINATAIRE_VILLE
        defaultCourrierShouldNotBeFound("destinataireVille.notEquals=" + DEFAULT_DESTINATAIRE_VILLE);

        // Get all the courrierList where destinataireVille not equals to UPDATED_DESTINATAIRE_VILLE
        defaultCourrierShouldBeFound("destinataireVille.notEquals=" + UPDATED_DESTINATAIRE_VILLE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireVilleIsInShouldWork() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireVille in DEFAULT_DESTINATAIRE_VILLE or UPDATED_DESTINATAIRE_VILLE
        defaultCourrierShouldBeFound("destinataireVille.in=" + DEFAULT_DESTINATAIRE_VILLE + "," + UPDATED_DESTINATAIRE_VILLE);

        // Get all the courrierList where destinataireVille equals to UPDATED_DESTINATAIRE_VILLE
        defaultCourrierShouldNotBeFound("destinataireVille.in=" + UPDATED_DESTINATAIRE_VILLE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireVilleIsNullOrNotNull() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireVille is not null
        defaultCourrierShouldBeFound("destinataireVille.specified=true");

        // Get all the courrierList where destinataireVille is null
        defaultCourrierShouldNotBeFound("destinataireVille.specified=false");
    }
                @Test
    @Transactional
    public void getAllCourriersByDestinataireVilleContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireVille contains DEFAULT_DESTINATAIRE_VILLE
        defaultCourrierShouldBeFound("destinataireVille.contains=" + DEFAULT_DESTINATAIRE_VILLE);

        // Get all the courrierList where destinataireVille contains UPDATED_DESTINATAIRE_VILLE
        defaultCourrierShouldNotBeFound("destinataireVille.contains=" + UPDATED_DESTINATAIRE_VILLE);
    }

    @Test
    @Transactional
    public void getAllCourriersByDestinataireVilleNotContainsSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);

        // Get all the courrierList where destinataireVille does not contain DEFAULT_DESTINATAIRE_VILLE
        defaultCourrierShouldNotBeFound("destinataireVille.doesNotContain=" + DEFAULT_DESTINATAIRE_VILLE);

        // Get all the courrierList where destinataireVille does not contain UPDATED_DESTINATAIRE_VILLE
        defaultCourrierShouldBeFound("destinataireVille.doesNotContain=" + UPDATED_DESTINATAIRE_VILLE);
    }


    @Test
    @Transactional
    public void getAllCourriersByVoieIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Voie voie = VoieResourceIT.createEntity(em);
        em.persist(voie);
        em.flush();
        courrier.setVoie(voie);
        courrierRepository.saveAndFlush(courrier);
        Long voieId = voie.getId();

        // Get all the courrierList where voie equals to voieId
        defaultCourrierShouldBeFound("voieId.equals=" + voieId);

        // Get all the courrierList where voie equals to voieId + 1
        defaultCourrierShouldNotBeFound("voieId.equals=" + (voieId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByNatureCourrierIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        NatureCourrier natureCourrier = NatureCourrierResourceIT.createEntity(em);
        em.persist(natureCourrier);
        em.flush();
        courrier.setNatureCourrier(natureCourrier);
        courrierRepository.saveAndFlush(courrier);
        Long natureCourrierId = natureCourrier.getId();

        // Get all the courrierList where natureCourrier equals to natureCourrierId
        defaultCourrierShouldBeFound("natureCourrierId.equals=" + natureCourrierId);

        // Get all the courrierList where natureCourrier equals to natureCourrierId + 1
        defaultCourrierShouldNotBeFound("natureCourrierId.equals=" + (natureCourrierId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByLinkedToIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Courrier linkedTo = CourrierResourceIT.createEntity(em);
        em.persist(linkedTo);
        em.flush();
        courrier.setLinkedTo(linkedTo);
        courrierRepository.saveAndFlush(courrier);
        Long linkedToId = linkedTo.getId();

        // Get all the courrierList where linkedTo equals to linkedToId
        defaultCourrierShouldBeFound("linkedToId.equals=" + linkedToId);

        // Get all the courrierList where linkedTo equals to linkedToId + 1
        defaultCourrierShouldNotBeFound("linkedToId.equals=" + (linkedToId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Task task = TaskResourceIT.createEntity(em);
        em.persist(task);
        em.flush();
        courrier.addTask(task);
        courrierRepository.saveAndFlush(courrier);
        Long taskId = task.getId();

        // Get all the courrierList where task equals to taskId
        defaultCourrierShouldBeFound("taskId.equals=" + taskId);

        // Get all the courrierList where task equals to taskId + 1
        defaultCourrierShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByExpeditorIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Expeditor expeditor = ExpeditorResourceIT.createEntity(em);
        em.persist(expeditor);
        em.flush();
        courrier.setExpeditor(expeditor);
        courrierRepository.saveAndFlush(courrier);
        Long expeditorId = expeditor.getId();

        // Get all the courrierList where expeditor equals to expeditorId
        defaultCourrierShouldBeFound("expeditorId.equals=" + expeditorId);

        // Get all the courrierList where expeditor equals to expeditorId + 1
        defaultCourrierShouldNotBeFound("expeditorId.equals=" + (expeditorId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByDestinatorIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Expeditor destinator = ExpeditorResourceIT.createEntity(em);
        em.persist(destinator);
        em.flush();
        courrier.setDestinator(destinator);
        courrierRepository.saveAndFlush(courrier);
        Long destinatorId = destinator.getId();

        // Get all the courrierList where destinator equals to destinatorId
        defaultCourrierShouldBeFound("destinatorId.equals=" + destinatorId);

        // Get all the courrierList where destinator equals to destinatorId + 1
        defaultCourrierShouldNotBeFound("destinatorId.equals=" + (destinatorId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByCoordinatorIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        LeService coordinator = LeServiceResourceIT.createEntity(em);
        em.persist(coordinator);
        em.flush();
        courrier.setCoordinator(coordinator);
        courrierRepository.saveAndFlush(courrier);
        Long coordinatorId = coordinator.getId();

        // Get all the courrierList where coordinator equals to coordinatorId
        defaultCourrierShouldBeFound("coordinatorId.equals=" + coordinatorId);

        // Get all the courrierList where coordinator equals to coordinatorId + 1
        defaultCourrierShouldNotBeFound("coordinatorId.equals=" + (coordinatorId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByEmetteurIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        LeService emetteur = LeServiceResourceIT.createEntity(em);
        em.persist(emetteur);
        em.flush();
        courrier.setEmetteur(emetteur);
        courrierRepository.saveAndFlush(courrier);
        Long emetteurId = emetteur.getId();

        // Get all the courrierList where emetteur equals to emetteurId
        defaultCourrierShouldBeFound("emetteurId.equals=" + emetteurId);

        // Get all the courrierList where emetteur equals to emetteurId + 1
        defaultCourrierShouldNotBeFound("emetteurId.equals=" + (emetteurId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByEvaluationIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Evaluation evaluation = EvaluationResourceIT.createEntity(em);
        em.persist(evaluation);
        em.flush();
        courrier.setEvaluation(evaluation);
        courrierRepository.saveAndFlush(courrier);
        Long evaluationId = evaluation.getId();

        // Get all the courrierList where evaluation equals to evaluationId
        defaultCourrierShouldBeFound("evaluationId.equals=" + evaluationId);

        // Get all the courrierList where evaluation equals to evaluationId + 1
        defaultCourrierShouldNotBeFound("evaluationId.equals=" + (evaluationId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByCourrierObjectIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        CourrierObject courrierObject = CourrierObjectResourceIT.createEntity(em);
        em.persist(courrierObject);
        em.flush();
        courrier.setCourrierObject(courrierObject);
        courrierRepository.saveAndFlush(courrier);
        Long courrierObjectId = courrierObject.getId();

        // Get all the courrierList where courrierObject equals to courrierObjectId
        defaultCourrierShouldBeFound("courrierObjectId.equals=" + courrierObjectId);

        // Get all the courrierList where courrierObject equals to courrierObjectId + 1
        defaultCourrierShouldNotBeFound("courrierObjectId.equals=" + (courrierObjectId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByExpeditorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        ExpeditorType expeditorType = ExpeditorTypeResourceIT.createEntity(em);
        em.persist(expeditorType);
        em.flush();
        courrier.setExpeditorType(expeditorType);
        courrierRepository.saveAndFlush(courrier);
        Long expeditorTypeId = expeditorType.getId();

        // Get all the courrierList where expeditorType equals to expeditorTypeId
        defaultCourrierShouldBeFound("expeditorTypeId.equals=" + expeditorTypeId);

        // Get all the courrierList where expeditorType equals to expeditorTypeId + 1
        defaultCourrierShouldNotBeFound("expeditorTypeId.equals=" + (expeditorTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersBySubdivisionIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Subdivision subdivision = SubdivisionResourceIT.createEntity(em);
        em.persist(subdivision);
        em.flush();
        courrier.setSubdivision(subdivision);
        courrierRepository.saveAndFlush(courrier);
        Long subdivisionId = subdivision.getId();

        // Get all the courrierList where subdivision equals to subdivisionId
        defaultCourrierShouldBeFound("subdivisionId.equals=" + subdivisionId);

        // Get all the courrierList where subdivision equals to subdivisionId + 1
        defaultCourrierShouldNotBeFound("subdivisionId.equals=" + (subdivisionId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        LeService services = LeServiceResourceIT.createEntity(em);
        em.persist(services);
        em.flush();
        courrier.addServices(services);
        courrierRepository.saveAndFlush(courrier);
        Long servicesId = services.getId();

        // Get all the courrierList where services equals to servicesId
        defaultCourrierShouldBeFound("servicesId.equals=" + servicesId);

        // Get all the courrierList where services equals to servicesId + 1
        defaultCourrierShouldNotBeFound("servicesId.equals=" + (servicesId + 1));
    }


    @Test
    @Transactional
    public void getAllCourriersByBordereauIsEqualToSomething() throws Exception {
        // Initialize the database
        courrierRepository.saveAndFlush(courrier);
        Bordereau bordereau = BordereauResourceIT.createEntity(em);
        em.persist(bordereau);
        em.flush();
        courrier.setBordereau(bordereau);
        courrierRepository.saveAndFlush(courrier);
        Long bordereauId = bordereau.getId();

        // Get all the courrierList where bordereau equals to bordereauId
        defaultCourrierShouldBeFound("bordereauId.equals=" + bordereauId);

        // Get all the courrierList where bordereau equals to bordereauId + 1
        defaultCourrierShouldNotBeFound("bordereauId.equals=" + (bordereauId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCourrierShouldBeFound(String filter) throws Exception {
        restCourrierMockMvc.perform(get("/api/courriers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courrier.getId().intValue())))
            .andExpect(jsonPath("$.[*].idCourrier").value(hasItem(DEFAULT_ID_COURRIER)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].typeCourrier").value(hasItem(DEFAULT_TYPE_COURRIER.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.doubleValue())))
            .andExpect(jsonPath("$.[*].relance").value(hasItem(DEFAULT_RELANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accuse").value(hasItem(DEFAULT_ACCUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].reponse").value(hasItem(DEFAULT_REPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAccuse").value(hasItem(DEFAULT_DATE_ACCUSE.toString())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].receivedAt").value(hasItem(DEFAULT_RECEIVED_AT.toString())))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION)))
            .andExpect(jsonPath("$.[*].expediteurDesc").value(hasItem(DEFAULT_EXPEDITEUR_DESC)))
            .andExpect(jsonPath("$.[*].sentAt").value(hasItem(DEFAULT_SENT_AT.toString())))
            .andExpect(jsonPath("$.[*].destinataireDesc").value(hasItem(DEFAULT_DESTINATAIRE_DESC)))
            .andExpect(jsonPath("$.[*].destinataireVille").value(hasItem(DEFAULT_DESTINATAIRE_VILLE)));

        // Check, that the count call also returns 1
        restCourrierMockMvc.perform(get("/api/courriers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCourrierShouldNotBeFound(String filter) throws Exception {
        restCourrierMockMvc.perform(get("/api/courriers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCourrierMockMvc.perform(get("/api/courriers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCourrier() throws Exception {
        // Get the courrier
        restCourrierMockMvc.perform(get("/api/courriers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourrier() throws Exception {
        // Initialize the database
        courrierService.save(courrier);

        int databaseSizeBeforeUpdate = courrierRepository.findAll().size();

        // Update the courrier
        Courrier updatedCourrier = courrierRepository.findById(courrier.getId()).get();
        // Disconnect from session so that the updates on updatedCourrier are not directly saved in db
        em.detach(updatedCourrier);
        updatedCourrier
            .idCourrier(UPDATED_ID_COURRIER)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .typeCourrier(UPDATED_TYPE_COURRIER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .accuse(UPDATED_ACCUSE)
            .reponse(UPDATED_REPONSE)
            .dateAccuse(UPDATED_DATE_ACCUSE)
            .dateReponse(UPDATED_DATE_REPONSE)
            .status(UPDATED_STATUS)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .receivedAt(UPDATED_RECEIVED_AT)
            .instruction(UPDATED_INSTRUCTION)
            .expediteurDesc(UPDATED_EXPEDITEUR_DESC)
            .sentAt(UPDATED_SENT_AT)
            .destinataireDesc(UPDATED_DESTINATAIRE_DESC)
            .destinataireVille(UPDATED_DESTINATAIRE_VILLE);

        restCourrierMockMvc.perform(put("/api/courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCourrier)))
            .andExpect(status().isOk());

        // Validate the Courrier in the database
        List<Courrier> courrierList = courrierRepository.findAll();
        assertThat(courrierList).hasSize(databaseSizeBeforeUpdate);
        Courrier testCourrier = courrierList.get(courrierList.size() - 1);
        assertThat(testCourrier.getIdCourrier()).isEqualTo(UPDATED_ID_COURRIER);
        assertThat(testCourrier.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testCourrier.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourrier.getTypeCourrier()).isEqualTo(UPDATED_TYPE_COURRIER);
        assertThat(testCourrier.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCourrier.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCourrier.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCourrier.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCourrier.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testCourrier.getRelance()).isEqualTo(UPDATED_RELANCE);
        assertThat(testCourrier.isAccuse()).isEqualTo(UPDATED_ACCUSE);
        assertThat(testCourrier.isReponse()).isEqualTo(UPDATED_REPONSE);
        assertThat(testCourrier.getDateAccuse()).isEqualTo(UPDATED_DATE_ACCUSE);
        assertThat(testCourrier.getDateReponse()).isEqualTo(UPDATED_DATE_REPONSE);
        assertThat(testCourrier.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCourrier.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCourrier.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testCourrier.getReceivedAt()).isEqualTo(UPDATED_RECEIVED_AT);
        assertThat(testCourrier.getInstruction()).isEqualTo(UPDATED_INSTRUCTION);
        assertThat(testCourrier.getExpediteurDesc()).isEqualTo(UPDATED_EXPEDITEUR_DESC);
        assertThat(testCourrier.getSentAt()).isEqualTo(UPDATED_SENT_AT);
        assertThat(testCourrier.getDestinataireDesc()).isEqualTo(UPDATED_DESTINATAIRE_DESC);
        assertThat(testCourrier.getDestinataireVille()).isEqualTo(UPDATED_DESTINATAIRE_VILLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourrier() throws Exception {
        int databaseSizeBeforeUpdate = courrierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourrierMockMvc.perform(put("/api/courriers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courrier)))
            .andExpect(status().isBadRequest());

        // Validate the Courrier in the database
        List<Courrier> courrierList = courrierRepository.findAll();
        assertThat(courrierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourrier() throws Exception {
        // Initialize the database
        courrierService.save(courrier);

        int databaseSizeBeforeDelete = courrierRepository.findAll().size();

        // Delete the courrier
        restCourrierMockMvc.perform(delete("/api/courriers/{id}", courrier.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Courrier> courrierList = courrierRepository.findAll();
        assertThat(courrierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
