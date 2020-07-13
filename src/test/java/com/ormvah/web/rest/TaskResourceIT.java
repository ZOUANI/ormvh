package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.Task;
import com.ormvah.domain.User;
import com.ormvah.domain.Courrier;
import com.ormvah.repository.TaskRepository;
import com.ormvah.service.TaskService;
import com.ormvah.web.rest.errors.ExceptionTranslator;
import com.ormvah.service.dto.TaskCriteria;
import com.ormvah.service.TaskQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ormvah.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ormvah.domain.enumeration.Status;
/**
 * Integration tests for the {@Link TaskResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
public class TaskResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ASSIGNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PROCESSED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PROCESSED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Ouvert;
    private static final Status UPDATED_STATUS = Status.Encours;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Double DEFAULT_DELAI = 1D;
    private static final Double UPDATED_DELAI = 2D;

    private static final Double DEFAULT_RELANCE = 1D;
    private static final Double UPDATED_RELANCE = 2D;

    private static final Boolean DEFAULT_ACCUSE = false;
    private static final Boolean UPDATED_ACCUSE = true;

    private static final Boolean DEFAULT_REPONSE = false;
    private static final Boolean UPDATED_REPONSE = true;

    private static final LocalDate DEFAULT_DATE_ACCUSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACCUSE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_REPONSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REPONSE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskQueryService taskQueryService;

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

    private MockMvc restTaskMockMvc;

    private Task task;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TaskResource taskResource = new TaskResource(taskService, taskQueryService);
        this.restTaskMockMvc = MockMvcBuilders.standaloneSetup(taskResource)
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
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .assignedAt(DEFAULT_ASSIGNED_AT)
            .processedAt(DEFAULT_PROCESSED_AT)
            .observation(DEFAULT_OBSERVATION)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY)
            .delai(DEFAULT_DELAI)
            .relance(DEFAULT_RELANCE)
            .accuse(DEFAULT_ACCUSE)
            .reponse(DEFAULT_REPONSE)
            .dateAccuse(DEFAULT_DATE_ACCUSE)
            .dateReponse(DEFAULT_DATE_REPONSE);
        return task;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .assignedAt(UPDATED_ASSIGNED_AT)
            .processedAt(UPDATED_PROCESSED_AT)
            .observation(UPDATED_OBSERVATION)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .accuse(UPDATED_ACCUSE)
            .reponse(UPDATED_REPONSE)
            .dateAccuse(UPDATED_DATE_ACCUSE)
            .dateReponse(UPDATED_DATE_REPONSE);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    public void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTask.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTask.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testTask.getAssignedAt()).isEqualTo(DEFAULT_ASSIGNED_AT);
        assertThat(testTask.getProcessedAt()).isEqualTo(DEFAULT_PROCESSED_AT);
        assertThat(testTask.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTask.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTask.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTask.getDelai()).isEqualTo(DEFAULT_DELAI);
        assertThat(testTask.getRelance()).isEqualTo(DEFAULT_RELANCE);
        assertThat(testTask.isAccuse()).isEqualTo(DEFAULT_ACCUSE);
        assertThat(testTask.isReponse()).isEqualTo(DEFAULT_REPONSE);
        assertThat(testTask.getDateAccuse()).isEqualTo(DEFAULT_DATE_ACCUSE);
        assertThat(testTask.getDateReponse()).isEqualTo(DEFAULT_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void createTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // Create the Task with an existing ID
        task.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc.perform(post("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].assignedAt").value(hasItem(DEFAULT_ASSIGNED_AT.toString())))
            .andExpect(jsonPath("$.[*].processedAt").value(hasItem(DEFAULT_PROCESSED_AT.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.doubleValue())))
            .andExpect(jsonPath("$.[*].relance").value(hasItem(DEFAULT_RELANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accuse").value(hasItem(DEFAULT_ACCUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].reponse").value(hasItem(DEFAULT_REPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAccuse").value(hasItem(DEFAULT_DATE_ACCUSE.toString())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())));
    }
    
    @Test
    @Transactional
    public void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.assignedAt").value(DEFAULT_ASSIGNED_AT.toString()))
            .andExpect(jsonPath("$.processedAt").value(DEFAULT_PROCESSED_AT.toString()))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.delai").value(DEFAULT_DELAI.doubleValue()))
            .andExpect(jsonPath("$.relance").value(DEFAULT_RELANCE.doubleValue()))
            .andExpect(jsonPath("$.accuse").value(DEFAULT_ACCUSE.booleanValue()))
            .andExpect(jsonPath("$.reponse").value(DEFAULT_REPONSE.booleanValue()))
            .andExpect(jsonPath("$.dateAccuse").value(DEFAULT_DATE_ACCUSE.toString()))
            .andExpect(jsonPath("$.dateReponse").value(DEFAULT_DATE_REPONSE.toString()));
    }

    @Test
    @Transactional
    public void getAllTasksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title equals to DEFAULT_TITLE
        defaultTaskShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the taskList where title equals to UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTasksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTaskShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the taskList where title equals to UPDATED_TITLE
        defaultTaskShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTasksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where title is not null
        defaultTaskShouldBeFound("title.specified=true");

        // Get all the taskList where title is null
        defaultTaskShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description equals to DEFAULT_DESCRIPTION
        defaultTaskShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the taskList where description equals to UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTaskShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the taskList where description equals to UPDATED_DESCRIPTION
        defaultTaskShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTasksByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where description is not null
        defaultTaskShouldBeFound("description.specified=true");

        // Get all the taskList where description is null
        defaultTaskShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdAt equals to DEFAULT_CREATED_AT
        defaultTaskShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the taskList where createdAt equals to UPDATED_CREATED_AT
        defaultTaskShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultTaskShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the taskList where createdAt equals to UPDATED_CREATED_AT
        defaultTaskShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdAt is not null
        defaultTaskShouldBeFound("createdAt.specified=true");

        // Get all the taskList where createdAt is null
        defaultTaskShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultTaskShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the taskList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTaskShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultTaskShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the taskList where updatedAt equals to UPDATED_UPDATED_AT
        defaultTaskShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where updatedAt is not null
        defaultTaskShouldBeFound("updatedAt.specified=true");

        // Get all the taskList where updatedAt is null
        defaultTaskShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByAssignedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where assignedAt equals to DEFAULT_ASSIGNED_AT
        defaultTaskShouldBeFound("assignedAt.equals=" + DEFAULT_ASSIGNED_AT);

        // Get all the taskList where assignedAt equals to UPDATED_ASSIGNED_AT
        defaultTaskShouldNotBeFound("assignedAt.equals=" + UPDATED_ASSIGNED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByAssignedAtIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where assignedAt in DEFAULT_ASSIGNED_AT or UPDATED_ASSIGNED_AT
        defaultTaskShouldBeFound("assignedAt.in=" + DEFAULT_ASSIGNED_AT + "," + UPDATED_ASSIGNED_AT);

        // Get all the taskList where assignedAt equals to UPDATED_ASSIGNED_AT
        defaultTaskShouldNotBeFound("assignedAt.in=" + UPDATED_ASSIGNED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByAssignedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where assignedAt is not null
        defaultTaskShouldBeFound("assignedAt.specified=true");

        // Get all the taskList where assignedAt is null
        defaultTaskShouldNotBeFound("assignedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByProcessedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where processedAt equals to DEFAULT_PROCESSED_AT
        defaultTaskShouldBeFound("processedAt.equals=" + DEFAULT_PROCESSED_AT);

        // Get all the taskList where processedAt equals to UPDATED_PROCESSED_AT
        defaultTaskShouldNotBeFound("processedAt.equals=" + UPDATED_PROCESSED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByProcessedAtIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where processedAt in DEFAULT_PROCESSED_AT or UPDATED_PROCESSED_AT
        defaultTaskShouldBeFound("processedAt.in=" + DEFAULT_PROCESSED_AT + "," + UPDATED_PROCESSED_AT);

        // Get all the taskList where processedAt equals to UPDATED_PROCESSED_AT
        defaultTaskShouldNotBeFound("processedAt.in=" + UPDATED_PROCESSED_AT);
    }

    @Test
    @Transactional
    public void getAllTasksByProcessedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where processedAt is not null
        defaultTaskShouldBeFound("processedAt.specified=true");

        // Get all the taskList where processedAt is null
        defaultTaskShouldNotBeFound("processedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByObservationIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where observation equals to DEFAULT_OBSERVATION
        defaultTaskShouldBeFound("observation.equals=" + DEFAULT_OBSERVATION);

        // Get all the taskList where observation equals to UPDATED_OBSERVATION
        defaultTaskShouldNotBeFound("observation.equals=" + UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllTasksByObservationIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where observation in DEFAULT_OBSERVATION or UPDATED_OBSERVATION
        defaultTaskShouldBeFound("observation.in=" + DEFAULT_OBSERVATION + "," + UPDATED_OBSERVATION);

        // Get all the taskList where observation equals to UPDATED_OBSERVATION
        defaultTaskShouldNotBeFound("observation.in=" + UPDATED_OBSERVATION);
    }

    @Test
    @Transactional
    public void getAllTasksByObservationIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where observation is not null
        defaultTaskShouldBeFound("observation.specified=true");

        // Get all the taskList where observation is null
        defaultTaskShouldNotBeFound("observation.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status equals to DEFAULT_STATUS
        defaultTaskShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the taskList where status equals to UPDATED_STATUS
        defaultTaskShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTasksByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTaskShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the taskList where status equals to UPDATED_STATUS
        defaultTaskShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTasksByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where status is not null
        defaultTaskShouldBeFound("status.specified=true");

        // Get all the taskList where status is null
        defaultTaskShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdBy equals to DEFAULT_CREATED_BY
        defaultTaskShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the taskList where createdBy equals to UPDATED_CREATED_BY
        defaultTaskShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultTaskShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the taskList where createdBy equals to UPDATED_CREATED_BY
        defaultTaskShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllTasksByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where createdBy is not null
        defaultTaskShouldBeFound("createdBy.specified=true");

        // Get all the taskList where createdBy is null
        defaultTaskShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByUpdatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where updatedBy equals to DEFAULT_UPDATED_BY
        defaultTaskShouldBeFound("updatedBy.equals=" + DEFAULT_UPDATED_BY);

        // Get all the taskList where updatedBy equals to UPDATED_UPDATED_BY
        defaultTaskShouldNotBeFound("updatedBy.equals=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllTasksByUpdatedByIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where updatedBy in DEFAULT_UPDATED_BY or UPDATED_UPDATED_BY
        defaultTaskShouldBeFound("updatedBy.in=" + DEFAULT_UPDATED_BY + "," + UPDATED_UPDATED_BY);

        // Get all the taskList where updatedBy equals to UPDATED_UPDATED_BY
        defaultTaskShouldNotBeFound("updatedBy.in=" + UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllTasksByUpdatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where updatedBy is not null
        defaultTaskShouldBeFound("updatedBy.specified=true");

        // Get all the taskList where updatedBy is null
        defaultTaskShouldNotBeFound("updatedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDelaiIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where delai equals to DEFAULT_DELAI
        defaultTaskShouldBeFound("delai.equals=" + DEFAULT_DELAI);

        // Get all the taskList where delai equals to UPDATED_DELAI
        defaultTaskShouldNotBeFound("delai.equals=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllTasksByDelaiIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where delai in DEFAULT_DELAI or UPDATED_DELAI
        defaultTaskShouldBeFound("delai.in=" + DEFAULT_DELAI + "," + UPDATED_DELAI);

        // Get all the taskList where delai equals to UPDATED_DELAI
        defaultTaskShouldNotBeFound("delai.in=" + UPDATED_DELAI);
    }

    @Test
    @Transactional
    public void getAllTasksByDelaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where delai is not null
        defaultTaskShouldBeFound("delai.specified=true");

        // Get all the taskList where delai is null
        defaultTaskShouldNotBeFound("delai.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByRelanceIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where relance equals to DEFAULT_RELANCE
        defaultTaskShouldBeFound("relance.equals=" + DEFAULT_RELANCE);

        // Get all the taskList where relance equals to UPDATED_RELANCE
        defaultTaskShouldNotBeFound("relance.equals=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllTasksByRelanceIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where relance in DEFAULT_RELANCE or UPDATED_RELANCE
        defaultTaskShouldBeFound("relance.in=" + DEFAULT_RELANCE + "," + UPDATED_RELANCE);

        // Get all the taskList where relance equals to UPDATED_RELANCE
        defaultTaskShouldNotBeFound("relance.in=" + UPDATED_RELANCE);
    }

    @Test
    @Transactional
    public void getAllTasksByRelanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where relance is not null
        defaultTaskShouldBeFound("relance.specified=true");

        // Get all the taskList where relance is null
        defaultTaskShouldNotBeFound("relance.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByAccuseIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where accuse equals to DEFAULT_ACCUSE
        defaultTaskShouldBeFound("accuse.equals=" + DEFAULT_ACCUSE);

        // Get all the taskList where accuse equals to UPDATED_ACCUSE
        defaultTaskShouldNotBeFound("accuse.equals=" + UPDATED_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllTasksByAccuseIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where accuse in DEFAULT_ACCUSE or UPDATED_ACCUSE
        defaultTaskShouldBeFound("accuse.in=" + DEFAULT_ACCUSE + "," + UPDATED_ACCUSE);

        // Get all the taskList where accuse equals to UPDATED_ACCUSE
        defaultTaskShouldNotBeFound("accuse.in=" + UPDATED_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllTasksByAccuseIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where accuse is not null
        defaultTaskShouldBeFound("accuse.specified=true");

        // Get all the taskList where accuse is null
        defaultTaskShouldNotBeFound("accuse.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByReponseIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where reponse equals to DEFAULT_REPONSE
        defaultTaskShouldBeFound("reponse.equals=" + DEFAULT_REPONSE);

        // Get all the taskList where reponse equals to UPDATED_REPONSE
        defaultTaskShouldNotBeFound("reponse.equals=" + UPDATED_REPONSE);
    }

    @Test
    @Transactional
    public void getAllTasksByReponseIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where reponse in DEFAULT_REPONSE or UPDATED_REPONSE
        defaultTaskShouldBeFound("reponse.in=" + DEFAULT_REPONSE + "," + UPDATED_REPONSE);

        // Get all the taskList where reponse equals to UPDATED_REPONSE
        defaultTaskShouldNotBeFound("reponse.in=" + UPDATED_REPONSE);
    }

    @Test
    @Transactional
    public void getAllTasksByReponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where reponse is not null
        defaultTaskShouldBeFound("reponse.specified=true");

        // Get all the taskList where reponse is null
        defaultTaskShouldNotBeFound("reponse.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDateAccuseIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateAccuse equals to DEFAULT_DATE_ACCUSE
        defaultTaskShouldBeFound("dateAccuse.equals=" + DEFAULT_DATE_ACCUSE);

        // Get all the taskList where dateAccuse equals to UPDATED_DATE_ACCUSE
        defaultTaskShouldNotBeFound("dateAccuse.equals=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllTasksByDateAccuseIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateAccuse in DEFAULT_DATE_ACCUSE or UPDATED_DATE_ACCUSE
        defaultTaskShouldBeFound("dateAccuse.in=" + DEFAULT_DATE_ACCUSE + "," + UPDATED_DATE_ACCUSE);

        // Get all the taskList where dateAccuse equals to UPDATED_DATE_ACCUSE
        defaultTaskShouldNotBeFound("dateAccuse.in=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllTasksByDateAccuseIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateAccuse is not null
        defaultTaskShouldBeFound("dateAccuse.specified=true");

        // Get all the taskList where dateAccuse is null
        defaultTaskShouldNotBeFound("dateAccuse.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDateAccuseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateAccuse greater than or equals to DEFAULT_DATE_ACCUSE
        defaultTaskShouldBeFound("dateAccuse.greaterOrEqualThan=" + DEFAULT_DATE_ACCUSE);

        // Get all the taskList where dateAccuse greater than or equals to UPDATED_DATE_ACCUSE
        defaultTaskShouldNotBeFound("dateAccuse.greaterOrEqualThan=" + UPDATED_DATE_ACCUSE);
    }

    @Test
    @Transactional
    public void getAllTasksByDateAccuseIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateAccuse less than or equals to DEFAULT_DATE_ACCUSE
        defaultTaskShouldNotBeFound("dateAccuse.lessThan=" + DEFAULT_DATE_ACCUSE);

        // Get all the taskList where dateAccuse less than or equals to UPDATED_DATE_ACCUSE
        defaultTaskShouldBeFound("dateAccuse.lessThan=" + UPDATED_DATE_ACCUSE);
    }


    @Test
    @Transactional
    public void getAllTasksByDateReponseIsEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateReponse equals to DEFAULT_DATE_REPONSE
        defaultTaskShouldBeFound("dateReponse.equals=" + DEFAULT_DATE_REPONSE);

        // Get all the taskList where dateReponse equals to UPDATED_DATE_REPONSE
        defaultTaskShouldNotBeFound("dateReponse.equals=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllTasksByDateReponseIsInShouldWork() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateReponse in DEFAULT_DATE_REPONSE or UPDATED_DATE_REPONSE
        defaultTaskShouldBeFound("dateReponse.in=" + DEFAULT_DATE_REPONSE + "," + UPDATED_DATE_REPONSE);

        // Get all the taskList where dateReponse equals to UPDATED_DATE_REPONSE
        defaultTaskShouldNotBeFound("dateReponse.in=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllTasksByDateReponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateReponse is not null
        defaultTaskShouldBeFound("dateReponse.specified=true");

        // Get all the taskList where dateReponse is null
        defaultTaskShouldNotBeFound("dateReponse.specified=false");
    }

    @Test
    @Transactional
    public void getAllTasksByDateReponseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateReponse greater than or equals to DEFAULT_DATE_REPONSE
        defaultTaskShouldBeFound("dateReponse.greaterOrEqualThan=" + DEFAULT_DATE_REPONSE);

        // Get all the taskList where dateReponse greater than or equals to UPDATED_DATE_REPONSE
        defaultTaskShouldNotBeFound("dateReponse.greaterOrEqualThan=" + UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void getAllTasksByDateReponseIsLessThanSomething() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList where dateReponse less than or equals to DEFAULT_DATE_REPONSE
        defaultTaskShouldNotBeFound("dateReponse.lessThan=" + DEFAULT_DATE_REPONSE);

        // Get all the taskList where dateReponse less than or equals to UPDATED_DATE_REPONSE
        defaultTaskShouldBeFound("dateReponse.lessThan=" + UPDATED_DATE_REPONSE);
    }


    @Test
    @Transactional
    public void getAllTasksByAssigneIsEqualToSomething() throws Exception {
        // Initialize the database
        User assigne = UserResourceIT.createEntity(em);
        em.persist(assigne);
        em.flush();
        task.setAssigne(assigne);
        taskRepository.saveAndFlush(task);
        Long assigneId = assigne.getId();

        // Get all the taskList where assigne equals to assigneId
        defaultTaskShouldBeFound("assigneId.equals=" + assigneId);

        // Get all the taskList where assigne equals to assigneId + 1
        defaultTaskShouldNotBeFound("assigneId.equals=" + (assigneId + 1));
    }


    @Test
    @Transactional
    public void getAllTasksByCourrierIsEqualToSomething() throws Exception {
        // Initialize the database
        Courrier courrier = CourrierResourceIT.createEntity(em);
        em.persist(courrier);
        em.flush();
        task.setCourrier(courrier);
        taskRepository.saveAndFlush(task);
        Long courrierId = courrier.getId();

        // Get all the taskList where courrier equals to courrierId
        defaultTaskShouldBeFound("courrierId.equals=" + courrierId);

        // Get all the taskList where courrier equals to courrierId + 1
        defaultTaskShouldNotBeFound("courrierId.equals=" + (courrierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTaskShouldBeFound(String filter) throws Exception {
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].assignedAt").value(hasItem(DEFAULT_ASSIGNED_AT.toString())))
            .andExpect(jsonPath("$.[*].processedAt").value(hasItem(DEFAULT_PROCESSED_AT.toString())))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].delai").value(hasItem(DEFAULT_DELAI.doubleValue())))
            .andExpect(jsonPath("$.[*].relance").value(hasItem(DEFAULT_RELANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accuse").value(hasItem(DEFAULT_ACCUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].reponse").value(hasItem(DEFAULT_REPONSE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateAccuse").value(hasItem(DEFAULT_DATE_ACCUSE.toString())))
            .andExpect(jsonPath("$.[*].dateReponse").value(hasItem(DEFAULT_DATE_REPONSE.toString())));

        // Check, that the count call also returns 1
        restTaskMockMvc.perform(get("/api/tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTaskShouldNotBeFound(String filter) throws Exception {
        restTaskMockMvc.perform(get("/api/tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTaskMockMvc.perform(get("/api/tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get("/api/tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTask() throws Exception {
        // Initialize the database
        taskService.save(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .assignedAt(UPDATED_ASSIGNED_AT)
            .processedAt(UPDATED_PROCESSED_AT)
            .observation(UPDATED_OBSERVATION)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .delai(UPDATED_DELAI)
            .relance(UPDATED_RELANCE)
            .accuse(UPDATED_ACCUSE)
            .reponse(UPDATED_REPONSE)
            .dateAccuse(UPDATED_DATE_ACCUSE)
            .dateReponse(UPDATED_DATE_REPONSE);

        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTask)))
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTask.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testTask.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testTask.getProcessedAt()).isEqualTo(UPDATED_PROCESSED_AT);
        assertThat(testTask.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTask.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTask.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTask.getDelai()).isEqualTo(UPDATED_DELAI);
        assertThat(testTask.getRelance()).isEqualTo(UPDATED_RELANCE);
        assertThat(testTask.isAccuse()).isEqualTo(UPDATED_ACCUSE);
        assertThat(testTask.isReponse()).isEqualTo(UPDATED_REPONSE);
        assertThat(testTask.getDateAccuse()).isEqualTo(UPDATED_DATE_ACCUSE);
        assertThat(testTask.getDateReponse()).isEqualTo(UPDATED_DATE_REPONSE);
    }

    @Test
    @Transactional
    public void updateNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Create the Task

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc.perform(put("/api/tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTask() throws Exception {
        // Initialize the database
        taskService.save(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc.perform(delete("/api/tasks/{id}", task.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Task.class);
        Task task1 = new Task();
        task1.setId(1L);
        Task task2 = new Task();
        task2.setId(task1.getId());
        assertThat(task1).isEqualTo(task2);
        task2.setId(2L);
        assertThat(task1).isNotEqualTo(task2);
        task1.setId(null);
        assertThat(task1).isNotEqualTo(task2);
    }
}
