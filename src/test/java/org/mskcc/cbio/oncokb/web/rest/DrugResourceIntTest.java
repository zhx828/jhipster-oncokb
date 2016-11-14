package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.Drug;
import org.mskcc.cbio.oncokb.repository.DrugRepository;
import org.mskcc.cbio.oncokb.service.DrugService;

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
 * Test class for the DrugResource REST controller.
 *
 * @see DrugResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class DrugResourceIntTest {

    private static final String DEFAULT_DRUG_NAME = "AAAAA";
    private static final String UPDATED_DRUG_NAME = "BBBBB";

    private static final Boolean DEFAULT_FDA_APPROVED = false;
    private static final Boolean UPDATED_FDA_APPROVED = true;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private DrugRepository drugRepository;

    @Inject
    private DrugService drugService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDrugMockMvc;

    private Drug drug;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrugResource drugResource = new DrugResource();
        ReflectionTestUtils.setField(drugResource, "drugService", drugService);
        this.restDrugMockMvc = MockMvcBuilders.standaloneSetup(drugResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drug createEntity(EntityManager em) {
        Drug drug = new Drug()
                .drugName(DEFAULT_DRUG_NAME)
                .fdaApproved(DEFAULT_FDA_APPROVED)
                .description(DEFAULT_DESCRIPTION);
        return drug;
    }

    @Before
    public void initTest() {
        drug = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug

        restDrugMockMvc.perform(post("/api/drugs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drug)))
                .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugs = drugRepository.findAll();
        assertThat(drugs).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugs.get(drugs.size() - 1);
        assertThat(testDrug.getDrugName()).isEqualTo(DEFAULT_DRUG_NAME);
        assertThat(testDrug.isFdaApproved()).isEqualTo(DEFAULT_FDA_APPROVED);
        assertThat(testDrug.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDrugs() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugs
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
                .andExpect(jsonPath("$.[*].drugName").value(hasItem(DEFAULT_DRUG_NAME.toString())))
                .andExpect(jsonPath("$.[*].fdaApproved").value(hasItem(DEFAULT_FDA_APPROVED.booleanValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", drug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drug.getId().intValue()))
            .andExpect(jsonPath("$.drugName").value(DEFAULT_DRUG_NAME.toString()))
            .andExpect(jsonPath("$.fdaApproved").value(DEFAULT_FDA_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrug() throws Exception {
        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrug() throws Exception {
        // Initialize the database
        drugService.save(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug
        Drug updatedDrug = drugRepository.findOne(drug.getId());
        updatedDrug
                .drugName(UPDATED_DRUG_NAME)
                .fdaApproved(UPDATED_FDA_APPROVED)
                .description(UPDATED_DESCRIPTION);

        restDrugMockMvc.perform(put("/api/drugs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDrug)))
                .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugs = drugRepository.findAll();
        assertThat(drugs).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugs.get(drugs.size() - 1);
        assertThat(testDrug.getDrugName()).isEqualTo(UPDATED_DRUG_NAME);
        assertThat(testDrug.isFdaApproved()).isEqualTo(UPDATED_FDA_APPROVED);
        assertThat(testDrug.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteDrug() throws Exception {
        // Initialize the database
        drugService.save(drug);

        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Get the drug
        restDrugMockMvc.perform(delete("/api/drugs/{id}", drug.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Drug> drugs = drugRepository.findAll();
        assertThat(drugs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
