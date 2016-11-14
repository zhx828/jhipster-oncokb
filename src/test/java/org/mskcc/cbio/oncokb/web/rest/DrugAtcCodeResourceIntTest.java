package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.DrugAtcCode;
import org.mskcc.cbio.oncokb.repository.DrugAtcCodeRepository;
import org.mskcc.cbio.oncokb.service.DrugAtcCodeService;

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
 * Test class for the DrugAtcCodeResource REST controller.
 *
 * @see DrugAtcCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class DrugAtcCodeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private DrugAtcCodeRepository drugAtcCodeRepository;

    @Inject
    private DrugAtcCodeService drugAtcCodeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDrugAtcCodeMockMvc;

    private DrugAtcCode drugAtcCode;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrugAtcCodeResource drugAtcCodeResource = new DrugAtcCodeResource();
        ReflectionTestUtils.setField(drugAtcCodeResource, "drugAtcCodeService", drugAtcCodeService);
        this.restDrugAtcCodeMockMvc = MockMvcBuilders.standaloneSetup(drugAtcCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugAtcCode createEntity(EntityManager em) {
        DrugAtcCode drugAtcCode = new DrugAtcCode()
                .name(DEFAULT_NAME);
        return drugAtcCode;
    }

    @Before
    public void initTest() {
        drugAtcCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugAtcCode() throws Exception {
        int databaseSizeBeforeCreate = drugAtcCodeRepository.findAll().size();

        // Create the DrugAtcCode

        restDrugAtcCodeMockMvc.perform(post("/api/drug-atc-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drugAtcCode)))
                .andExpect(status().isCreated());

        // Validate the DrugAtcCode in the database
        List<DrugAtcCode> drugAtcCodes = drugAtcCodeRepository.findAll();
        assertThat(drugAtcCodes).hasSize(databaseSizeBeforeCreate + 1);
        DrugAtcCode testDrugAtcCode = drugAtcCodes.get(drugAtcCodes.size() - 1);
        assertThat(testDrugAtcCode.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugAtcCodes() throws Exception {
        // Initialize the database
        drugAtcCodeRepository.saveAndFlush(drugAtcCode);

        // Get all the drugAtcCodes
        restDrugAtcCodeMockMvc.perform(get("/api/drug-atc-codes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drugAtcCode.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDrugAtcCode() throws Exception {
        // Initialize the database
        drugAtcCodeRepository.saveAndFlush(drugAtcCode);

        // Get the drugAtcCode
        restDrugAtcCodeMockMvc.perform(get("/api/drug-atc-codes/{id}", drugAtcCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugAtcCode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrugAtcCode() throws Exception {
        // Get the drugAtcCode
        restDrugAtcCodeMockMvc.perform(get("/api/drug-atc-codes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugAtcCode() throws Exception {
        // Initialize the database
        drugAtcCodeService.save(drugAtcCode);

        int databaseSizeBeforeUpdate = drugAtcCodeRepository.findAll().size();

        // Update the drugAtcCode
        DrugAtcCode updatedDrugAtcCode = drugAtcCodeRepository.findOne(drugAtcCode.getId());
        updatedDrugAtcCode
                .name(UPDATED_NAME);

        restDrugAtcCodeMockMvc.perform(put("/api/drug-atc-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDrugAtcCode)))
                .andExpect(status().isOk());

        // Validate the DrugAtcCode in the database
        List<DrugAtcCode> drugAtcCodes = drugAtcCodeRepository.findAll();
        assertThat(drugAtcCodes).hasSize(databaseSizeBeforeUpdate);
        DrugAtcCode testDrugAtcCode = drugAtcCodes.get(drugAtcCodes.size() - 1);
        assertThat(testDrugAtcCode.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteDrugAtcCode() throws Exception {
        // Initialize the database
        drugAtcCodeService.save(drugAtcCode);

        int databaseSizeBeforeDelete = drugAtcCodeRepository.findAll().size();

        // Get the drugAtcCode
        restDrugAtcCodeMockMvc.perform(delete("/api/drug-atc-codes/{id}", drugAtcCode.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DrugAtcCode> drugAtcCodes = drugAtcCodeRepository.findAll();
        assertThat(drugAtcCodes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
