package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.Treatment;
import org.mskcc.cbio.oncokb.repository.TreatmentRepository;
import org.mskcc.cbio.oncokb.service.TreatmentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TreatmentResource REST controller.
 *
 * @see TreatmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class TreatmentResourceIntTest {


    @Inject
    private TreatmentRepository treatmentRepository;

    @Inject
    private TreatmentService treatmentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TreatmentResource treatmentResource = new TreatmentResource();
        ReflectionTestUtils.setField(treatmentResource, "treatmentService", treatmentService);
        this.restTreatmentMockMvc = MockMvcBuilders.standaloneSetup(treatmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Treatment createEntity(EntityManager em) {
        Treatment treatment = new Treatment();
        return treatment;
    }

    @Before
    public void initTest() {
        treatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment

        restTreatmentMockMvc.perform(post("/api/treatments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(treatment)))
                .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatments.get(treatments.size() - 1);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatments
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentService.save(treatment);

        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = treatmentRepository.findOne(treatment.getId());

        restTreatmentMockMvc.perform(put("/api/treatments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTreatment)))
                .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatments.get(treatments.size() - 1);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentService.save(treatment);

        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Get the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Treatment> treatments = treatmentRepository.findAll();
        assertThat(treatments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
