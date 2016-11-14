package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.ClinicalTrial;
import org.mskcc.cbio.oncokb.repository.ClinicalTrialRepository;
import org.mskcc.cbio.oncokb.service.ClinicalTrialService;

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
 * Test class for the ClinicalTrialResource REST controller.
 *
 * @see ClinicalTrialResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class ClinicalTrialResourceIntTest {

    private static final String DEFAULT_NCT_ID = "AAAAA";
    private static final String UPDATED_NCT_ID = "BBBBB";
    private static final String DEFAULT_CDR_ID = "AAAAA";
    private static final String UPDATED_CDR_ID = "BBBBB";
    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_PURPOSE = "AAAAA";
    private static final String UPDATED_PURPOSE = "BBBBB";
    private static final String DEFAULT_RECRUITING_STATUS = "AAAAA";
    private static final String UPDATED_RECRUITING_STATUS = "BBBBB";
    private static final String DEFAULT_ELIGIBILITY_CRITERIA = "AAAAA";
    private static final String UPDATED_ELIGIBILITY_CRITERIA = "BBBBB";
    private static final String DEFAULT_PHASE = "AAAAA";
    private static final String UPDATED_PHASE = "BBBBB";
    private static final String DEFAULT_DISEASE_CONDITION = "AAAAA";
    private static final String UPDATED_DISEASE_CONDITION = "BBBBB";
    private static final String DEFAULT_LAST_CHANGED_DATE = "AAAAA";
    private static final String UPDATED_LAST_CHANGED_DATE = "BBBBB";

    @Inject
    private ClinicalTrialRepository clinicalTrialRepository;

    @Inject
    private ClinicalTrialService clinicalTrialService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restClinicalTrialMockMvc;

    private ClinicalTrial clinicalTrial;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClinicalTrialResource clinicalTrialResource = new ClinicalTrialResource();
        ReflectionTestUtils.setField(clinicalTrialResource, "clinicalTrialService", clinicalTrialService);
        this.restClinicalTrialMockMvc = MockMvcBuilders.standaloneSetup(clinicalTrialResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClinicalTrial createEntity(EntityManager em) {
        ClinicalTrial clinicalTrial = new ClinicalTrial()
                .nctId(DEFAULT_NCT_ID)
                .cdrId(DEFAULT_CDR_ID)
                .title(DEFAULT_TITLE)
                .purpose(DEFAULT_PURPOSE)
                .recruitingStatus(DEFAULT_RECRUITING_STATUS)
                .eligibilityCriteria(DEFAULT_ELIGIBILITY_CRITERIA)
                .phase(DEFAULT_PHASE)
                .diseaseCondition(DEFAULT_DISEASE_CONDITION)
                .lastChangedDate(DEFAULT_LAST_CHANGED_DATE);
        return clinicalTrial;
    }

    @Before
    public void initTest() {
        clinicalTrial = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinicalTrial() throws Exception {
        int databaseSizeBeforeCreate = clinicalTrialRepository.findAll().size();

        // Create the ClinicalTrial

        restClinicalTrialMockMvc.perform(post("/api/clinical-trials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(clinicalTrial)))
                .andExpect(status().isCreated());

        // Validate the ClinicalTrial in the database
        List<ClinicalTrial> clinicalTrials = clinicalTrialRepository.findAll();
        assertThat(clinicalTrials).hasSize(databaseSizeBeforeCreate + 1);
        ClinicalTrial testClinicalTrial = clinicalTrials.get(clinicalTrials.size() - 1);
        assertThat(testClinicalTrial.getNctId()).isEqualTo(DEFAULT_NCT_ID);
        assertThat(testClinicalTrial.getCdrId()).isEqualTo(DEFAULT_CDR_ID);
        assertThat(testClinicalTrial.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClinicalTrial.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testClinicalTrial.getRecruitingStatus()).isEqualTo(DEFAULT_RECRUITING_STATUS);
        assertThat(testClinicalTrial.getEligibilityCriteria()).isEqualTo(DEFAULT_ELIGIBILITY_CRITERIA);
        assertThat(testClinicalTrial.getPhase()).isEqualTo(DEFAULT_PHASE);
        assertThat(testClinicalTrial.getDiseaseCondition()).isEqualTo(DEFAULT_DISEASE_CONDITION);
        assertThat(testClinicalTrial.getLastChangedDate()).isEqualTo(DEFAULT_LAST_CHANGED_DATE);
    }

    @Test
    @Transactional
    public void getAllClinicalTrials() throws Exception {
        // Initialize the database
        clinicalTrialRepository.saveAndFlush(clinicalTrial);

        // Get all the clinicalTrials
        restClinicalTrialMockMvc.perform(get("/api/clinical-trials?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(clinicalTrial.getId().intValue())))
                .andExpect(jsonPath("$.[*].nctId").value(hasItem(DEFAULT_NCT_ID.toString())))
                .andExpect(jsonPath("$.[*].cdrId").value(hasItem(DEFAULT_CDR_ID.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
                .andExpect(jsonPath("$.[*].recruitingStatus").value(hasItem(DEFAULT_RECRUITING_STATUS.toString())))
                .andExpect(jsonPath("$.[*].eligibilityCriteria").value(hasItem(DEFAULT_ELIGIBILITY_CRITERIA.toString())))
                .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())))
                .andExpect(jsonPath("$.[*].diseaseCondition").value(hasItem(DEFAULT_DISEASE_CONDITION.toString())))
                .andExpect(jsonPath("$.[*].lastChangedDate").value(hasItem(DEFAULT_LAST_CHANGED_DATE.toString())));
    }

    @Test
    @Transactional
    public void getClinicalTrial() throws Exception {
        // Initialize the database
        clinicalTrialRepository.saveAndFlush(clinicalTrial);

        // Get the clinicalTrial
        restClinicalTrialMockMvc.perform(get("/api/clinical-trials/{id}", clinicalTrial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clinicalTrial.getId().intValue()))
            .andExpect(jsonPath("$.nctId").value(DEFAULT_NCT_ID.toString()))
            .andExpect(jsonPath("$.cdrId").value(DEFAULT_CDR_ID.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.recruitingStatus").value(DEFAULT_RECRUITING_STATUS.toString()))
            .andExpect(jsonPath("$.eligibilityCriteria").value(DEFAULT_ELIGIBILITY_CRITERIA.toString()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()))
            .andExpect(jsonPath("$.diseaseCondition").value(DEFAULT_DISEASE_CONDITION.toString()))
            .andExpect(jsonPath("$.lastChangedDate").value(DEFAULT_LAST_CHANGED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinicalTrial() throws Exception {
        // Get the clinicalTrial
        restClinicalTrialMockMvc.perform(get("/api/clinical-trials/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinicalTrial() throws Exception {
        // Initialize the database
        clinicalTrialService.save(clinicalTrial);

        int databaseSizeBeforeUpdate = clinicalTrialRepository.findAll().size();

        // Update the clinicalTrial
        ClinicalTrial updatedClinicalTrial = clinicalTrialRepository.findOne(clinicalTrial.getId());
        updatedClinicalTrial
                .nctId(UPDATED_NCT_ID)
                .cdrId(UPDATED_CDR_ID)
                .title(UPDATED_TITLE)
                .purpose(UPDATED_PURPOSE)
                .recruitingStatus(UPDATED_RECRUITING_STATUS)
                .eligibilityCriteria(UPDATED_ELIGIBILITY_CRITERIA)
                .phase(UPDATED_PHASE)
                .diseaseCondition(UPDATED_DISEASE_CONDITION)
                .lastChangedDate(UPDATED_LAST_CHANGED_DATE);

        restClinicalTrialMockMvc.perform(put("/api/clinical-trials")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedClinicalTrial)))
                .andExpect(status().isOk());

        // Validate the ClinicalTrial in the database
        List<ClinicalTrial> clinicalTrials = clinicalTrialRepository.findAll();
        assertThat(clinicalTrials).hasSize(databaseSizeBeforeUpdate);
        ClinicalTrial testClinicalTrial = clinicalTrials.get(clinicalTrials.size() - 1);
        assertThat(testClinicalTrial.getNctId()).isEqualTo(UPDATED_NCT_ID);
        assertThat(testClinicalTrial.getCdrId()).isEqualTo(UPDATED_CDR_ID);
        assertThat(testClinicalTrial.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClinicalTrial.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testClinicalTrial.getRecruitingStatus()).isEqualTo(UPDATED_RECRUITING_STATUS);
        assertThat(testClinicalTrial.getEligibilityCriteria()).isEqualTo(UPDATED_ELIGIBILITY_CRITERIA);
        assertThat(testClinicalTrial.getPhase()).isEqualTo(UPDATED_PHASE);
        assertThat(testClinicalTrial.getDiseaseCondition()).isEqualTo(UPDATED_DISEASE_CONDITION);
        assertThat(testClinicalTrial.getLastChangedDate()).isEqualTo(UPDATED_LAST_CHANGED_DATE);
    }

    @Test
    @Transactional
    public void deleteClinicalTrial() throws Exception {
        // Initialize the database
        clinicalTrialService.save(clinicalTrial);

        int databaseSizeBeforeDelete = clinicalTrialRepository.findAll().size();

        // Get the clinicalTrial
        restClinicalTrialMockMvc.perform(delete("/api/clinical-trials/{id}", clinicalTrial.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClinicalTrial> clinicalTrials = clinicalTrialRepository.findAll();
        assertThat(clinicalTrials).hasSize(databaseSizeBeforeDelete - 1);
    }
}
