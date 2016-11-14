package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.ApprovedIndication;
import org.mskcc.cbio.oncokb.repository.ApprovedIndicationRepository;
import org.mskcc.cbio.oncokb.service.ApprovedIndicationService;

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
 * Test class for the ApprovedIndicationResource REST controller.
 *
 * @see ApprovedIndicationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class ApprovedIndicationResourceIntTest {

    private static final String DEFAULT_APPROVED_INDICATIONS = "AAAAA";
    private static final String UPDATED_APPROVED_INDICATIONS = "BBBBB";

    @Inject
    private ApprovedIndicationRepository approvedIndicationRepository;

    @Inject
    private ApprovedIndicationService approvedIndicationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restApprovedIndicationMockMvc;

    private ApprovedIndication approvedIndication;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ApprovedIndicationResource approvedIndicationResource = new ApprovedIndicationResource();
        ReflectionTestUtils.setField(approvedIndicationResource, "approvedIndicationService", approvedIndicationService);
        this.restApprovedIndicationMockMvc = MockMvcBuilders.standaloneSetup(approvedIndicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApprovedIndication createEntity(EntityManager em) {
        ApprovedIndication approvedIndication = new ApprovedIndication()
                .approvedIndications(DEFAULT_APPROVED_INDICATIONS);
        return approvedIndication;
    }

    @Before
    public void initTest() {
        approvedIndication = createEntity(em);
    }

    @Test
    @Transactional
    public void createApprovedIndication() throws Exception {
        int databaseSizeBeforeCreate = approvedIndicationRepository.findAll().size();

        // Create the ApprovedIndication

        restApprovedIndicationMockMvc.perform(post("/api/approved-indications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(approvedIndication)))
                .andExpect(status().isCreated());

        // Validate the ApprovedIndication in the database
        List<ApprovedIndication> approvedIndications = approvedIndicationRepository.findAll();
        assertThat(approvedIndications).hasSize(databaseSizeBeforeCreate + 1);
        ApprovedIndication testApprovedIndication = approvedIndications.get(approvedIndications.size() - 1);
        assertThat(testApprovedIndication.getApprovedIndications()).isEqualTo(DEFAULT_APPROVED_INDICATIONS);
    }

    @Test
    @Transactional
    public void getAllApprovedIndications() throws Exception {
        // Initialize the database
        approvedIndicationRepository.saveAndFlush(approvedIndication);

        // Get all the approvedIndications
        restApprovedIndicationMockMvc.perform(get("/api/approved-indications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(approvedIndication.getId().intValue())))
                .andExpect(jsonPath("$.[*].approvedIndications").value(hasItem(DEFAULT_APPROVED_INDICATIONS.toString())));
    }

    @Test
    @Transactional
    public void getApprovedIndication() throws Exception {
        // Initialize the database
        approvedIndicationRepository.saveAndFlush(approvedIndication);

        // Get the approvedIndication
        restApprovedIndicationMockMvc.perform(get("/api/approved-indications/{id}", approvedIndication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(approvedIndication.getId().intValue()))
            .andExpect(jsonPath("$.approvedIndications").value(DEFAULT_APPROVED_INDICATIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApprovedIndication() throws Exception {
        // Get the approvedIndication
        restApprovedIndicationMockMvc.perform(get("/api/approved-indications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApprovedIndication() throws Exception {
        // Initialize the database
        approvedIndicationService.save(approvedIndication);

        int databaseSizeBeforeUpdate = approvedIndicationRepository.findAll().size();

        // Update the approvedIndication
        ApprovedIndication updatedApprovedIndication = approvedIndicationRepository.findOne(approvedIndication.getId());
        updatedApprovedIndication
                .approvedIndications(UPDATED_APPROVED_INDICATIONS);

        restApprovedIndicationMockMvc.perform(put("/api/approved-indications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedApprovedIndication)))
                .andExpect(status().isOk());

        // Validate the ApprovedIndication in the database
        List<ApprovedIndication> approvedIndications = approvedIndicationRepository.findAll();
        assertThat(approvedIndications).hasSize(databaseSizeBeforeUpdate);
        ApprovedIndication testApprovedIndication = approvedIndications.get(approvedIndications.size() - 1);
        assertThat(testApprovedIndication.getApprovedIndications()).isEqualTo(UPDATED_APPROVED_INDICATIONS);
    }

    @Test
    @Transactional
    public void deleteApprovedIndication() throws Exception {
        // Initialize the database
        approvedIndicationService.save(approvedIndication);

        int databaseSizeBeforeDelete = approvedIndicationRepository.findAll().size();

        // Get the approvedIndication
        restApprovedIndicationMockMvc.perform(delete("/api/approved-indications/{id}", approvedIndication.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ApprovedIndication> approvedIndications = approvedIndicationRepository.findAll();
        assertThat(approvedIndications).hasSize(databaseSizeBeforeDelete - 1);
    }
}
