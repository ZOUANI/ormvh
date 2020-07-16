package com.ormvah.web.rest;

import com.ormvah.OrmvahApp;
import com.ormvah.domain.Bordereau;
import com.ormvah.repository.BordereauRepository;
import com.ormvah.service.BordereauService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BordereauResource} REST controller.
 */
@SpringBootTest(classes = OrmvahApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BordereauResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_PIECE_JOINTE = 1;
    private static final Integer UPDATED_NOMBRE_PIECE_JOINTE = 2;

    private static final LocalDate DEFAULT_DATE_BORDEREAUX = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BORDEREAUX = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BordereauRepository bordereauRepository;

    @Autowired
    private BordereauService bordereauService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBordereauMockMvc;

    private Bordereau bordereau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bordereau createEntity(EntityManager em) {
        Bordereau bordereau = new Bordereau()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .nombrePieceJointe(DEFAULT_NOMBRE_PIECE_JOINTE)
            .dateBordereaux(DEFAULT_DATE_BORDEREAUX);
        return bordereau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bordereau createUpdatedEntity(EntityManager em) {
        Bordereau bordereau = new Bordereau()
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .nombrePieceJointe(UPDATED_NOMBRE_PIECE_JOINTE)
            .dateBordereaux(UPDATED_DATE_BORDEREAUX);
        return bordereau;
    }

    @BeforeEach
    public void initTest() {
        bordereau = createEntity(em);
    }

    @Test
    @Transactional
    public void createBordereau() throws Exception {
        int databaseSizeBeforeCreate = bordereauRepository.findAll().size();
        // Create the Bordereau
        restBordereauMockMvc.perform(post("/api/bordereaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bordereau)))
            .andExpect(status().isCreated());

        // Validate the Bordereau in the database
        List<Bordereau> bordereauList = bordereauRepository.findAll();
        assertThat(bordereauList).hasSize(databaseSizeBeforeCreate + 1);
        Bordereau testBordereau = bordereauList.get(bordereauList.size() - 1);
        assertThat(testBordereau.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testBordereau.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBordereau.getNombrePieceJointe()).isEqualTo(DEFAULT_NOMBRE_PIECE_JOINTE);
        assertThat(testBordereau.getDateBordereaux()).isEqualTo(DEFAULT_DATE_BORDEREAUX);
    }

    @Test
    @Transactional
    public void createBordereauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bordereauRepository.findAll().size();

        // Create the Bordereau with an existing ID
        bordereau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBordereauMockMvc.perform(post("/api/bordereaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bordereau)))
            .andExpect(status().isBadRequest());

        // Validate the Bordereau in the database
        List<Bordereau> bordereauList = bordereauRepository.findAll();
        assertThat(bordereauList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bordereauRepository.findAll().size();
        // set the field null
        bordereau.setLibelle(null);

        // Create the Bordereau, which fails.


        restBordereauMockMvc.perform(post("/api/bordereaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bordereau)))
            .andExpect(status().isBadRequest());

        List<Bordereau> bordereauList = bordereauRepository.findAll();
        assertThat(bordereauList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBordereaus() throws Exception {
        // Initialize the database
        bordereauRepository.saveAndFlush(bordereau);

        // Get all the bordereauList
        restBordereauMockMvc.perform(get("/api/bordereaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bordereau.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nombrePieceJointe").value(hasItem(DEFAULT_NOMBRE_PIECE_JOINTE)))
            .andExpect(jsonPath("$.[*].dateBordereaux").value(hasItem(DEFAULT_DATE_BORDEREAUX.toString())));
    }
    
    @Test
    @Transactional
    public void getBordereau() throws Exception {
        // Initialize the database
        bordereauRepository.saveAndFlush(bordereau);

        // Get the bordereau
        restBordereauMockMvc.perform(get("/api/bordereaus/{id}", bordereau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bordereau.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.nombrePieceJointe").value(DEFAULT_NOMBRE_PIECE_JOINTE))
            .andExpect(jsonPath("$.dateBordereaux").value(DEFAULT_DATE_BORDEREAUX.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBordereau() throws Exception {
        // Get the bordereau
        restBordereauMockMvc.perform(get("/api/bordereaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBordereau() throws Exception {
        // Initialize the database
        bordereauService.save(bordereau);

        int databaseSizeBeforeUpdate = bordereauRepository.findAll().size();

        // Update the bordereau
        Bordereau updatedBordereau = bordereauRepository.findById(bordereau.getId()).get();
        // Disconnect from session so that the updates on updatedBordereau are not directly saved in db
        em.detach(updatedBordereau);
        updatedBordereau
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .nombrePieceJointe(UPDATED_NOMBRE_PIECE_JOINTE)
            .dateBordereaux(UPDATED_DATE_BORDEREAUX);

        restBordereauMockMvc.perform(put("/api/bordereaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBordereau)))
            .andExpect(status().isOk());

        // Validate the Bordereau in the database
        List<Bordereau> bordereauList = bordereauRepository.findAll();
        assertThat(bordereauList).hasSize(databaseSizeBeforeUpdate);
        Bordereau testBordereau = bordereauList.get(bordereauList.size() - 1);
        assertThat(testBordereau.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testBordereau.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBordereau.getNombrePieceJointe()).isEqualTo(UPDATED_NOMBRE_PIECE_JOINTE);
        assertThat(testBordereau.getDateBordereaux()).isEqualTo(UPDATED_DATE_BORDEREAUX);
    }

    @Test
    @Transactional
    public void updateNonExistingBordereau() throws Exception {
        int databaseSizeBeforeUpdate = bordereauRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBordereauMockMvc.perform(put("/api/bordereaus").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bordereau)))
            .andExpect(status().isBadRequest());

        // Validate the Bordereau in the database
        List<Bordereau> bordereauList = bordereauRepository.findAll();
        assertThat(bordereauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBordereau() throws Exception {
        // Initialize the database
        bordereauService.save(bordereau);

        int databaseSizeBeforeDelete = bordereauRepository.findAll().size();

        // Delete the bordereau
        restBordereauMockMvc.perform(delete("/api/bordereaus/{id}", bordereau.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bordereau> bordereauList = bordereauRepository.findAll();
        assertThat(bordereauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
