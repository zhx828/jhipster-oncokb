package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.PortalAlteration;
import org.mskcc.cbio.oncokb.repository.PortalAlterationRepository;
import org.mskcc.cbio.oncokb.service.PortalAlterationService;

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
 * Test class for the PortalAlterationResource REST controller.
 *
 * @see PortalAlterationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class PortalAlterationResourceIntTest {

    private static final String DEFAULT_CANCER_TYPE = "AAAAA";
    private static final String UPDATED_CANCER_TYPE = "BBBBB";
    private static final String DEFAULT_CANCER_STUDY = "AAAAA";
    private static final String UPDATED_CANCER_STUDY = "BBBBB";
    private static final String DEFAULT_SAMPLE_ID = "AAAAA";
    private static final String UPDATED_SAMPLE_ID = "BBBBB";
    private static final String DEFAULT_PROTEIN_CHANGE = "AAAAA";
    private static final String UPDATED_PROTEIN_CHANGE = "BBBBB";

    private static final Integer DEFAULT_PROTEIN_START_POSITION = 1;
    private static final Integer UPDATED_PROTEIN_START_POSITION = 2;

    private static final Integer DEFAULT_PROTEIN_END_POSITION = 1;
    private static final Integer UPDATED_PROTEIN_END_POSITION = 2;
    private static final String DEFAULT_ALTERATION_TYPE = "AAAAA";
    private static final String UPDATED_ALTERATION_TYPE = "BBBBB";

    @Inject
    private PortalAlterationRepository portalAlterationRepository;

    @Inject
    private PortalAlterationService portalAlterationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPortalAlterationMockMvc;

    private PortalAlteration portalAlteration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PortalAlterationResource portalAlterationResource = new PortalAlterationResource();
        ReflectionTestUtils.setField(portalAlterationResource, "portalAlterationService", portalAlterationService);
        this.restPortalAlterationMockMvc = MockMvcBuilders.standaloneSetup(portalAlterationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortalAlteration createEntity(EntityManager em) {
        PortalAlteration portalAlteration = new PortalAlteration()
                .cancerType(DEFAULT_CANCER_TYPE)
                .cancerStudy(DEFAULT_CANCER_STUDY)
                .sampleId(DEFAULT_SAMPLE_ID)
                .proteinChange(DEFAULT_PROTEIN_CHANGE)
                .proteinStartPosition(DEFAULT_PROTEIN_START_POSITION)
                .proteinEndPosition(DEFAULT_PROTEIN_END_POSITION)
                .alterationType(DEFAULT_ALTERATION_TYPE);
        return portalAlteration;
    }

    @Before
    public void initTest() {
        portalAlteration = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortalAlteration() throws Exception {
        int databaseSizeBeforeCreate = portalAlterationRepository.findAll().size();

        // Create the PortalAlteration

        restPortalAlterationMockMvc.perform(post("/api/portal-alterations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(portalAlteration)))
                .andExpect(status().isCreated());

        // Validate the PortalAlteration in the database
        List<PortalAlteration> portalAlterations = portalAlterationRepository.findAll();
        assertThat(portalAlterations).hasSize(databaseSizeBeforeCreate + 1);
        PortalAlteration testPortalAlteration = portalAlterations.get(portalAlterations.size() - 1);
        assertThat(testPortalAlteration.getCancerType()).isEqualTo(DEFAULT_CANCER_TYPE);
        assertThat(testPortalAlteration.getCancerStudy()).isEqualTo(DEFAULT_CANCER_STUDY);
        assertThat(testPortalAlteration.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testPortalAlteration.getProteinChange()).isEqualTo(DEFAULT_PROTEIN_CHANGE);
        assertThat(testPortalAlteration.getProteinStartPosition()).isEqualTo(DEFAULT_PROTEIN_START_POSITION);
        assertThat(testPortalAlteration.getProteinEndPosition()).isEqualTo(DEFAULT_PROTEIN_END_POSITION);
        assertThat(testPortalAlteration.getAlterationType()).isEqualTo(DEFAULT_ALTERATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllPortalAlterations() throws Exception {
        // Initialize the database
        portalAlterationRepository.saveAndFlush(portalAlteration);

        // Get all the portalAlterations
        restPortalAlterationMockMvc.perform(get("/api/portal-alterations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(portalAlteration.getId().intValue())))
                .andExpect(jsonPath("$.[*].cancerType").value(hasItem(DEFAULT_CANCER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].cancerStudy").value(hasItem(DEFAULT_CANCER_STUDY.toString())))
                .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID.toString())))
                .andExpect(jsonPath("$.[*].proteinChange").value(hasItem(DEFAULT_PROTEIN_CHANGE.toString())))
                .andExpect(jsonPath("$.[*].proteinStartPosition").value(hasItem(DEFAULT_PROTEIN_START_POSITION)))
                .andExpect(jsonPath("$.[*].proteinEndPosition").value(hasItem(DEFAULT_PROTEIN_END_POSITION)))
                .andExpect(jsonPath("$.[*].alterationType").value(hasItem(DEFAULT_ALTERATION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPortalAlteration() throws Exception {
        // Initialize the database
        portalAlterationRepository.saveAndFlush(portalAlteration);

        // Get the portalAlteration
        restPortalAlterationMockMvc.perform(get("/api/portal-alterations/{id}", portalAlteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portalAlteration.getId().intValue()))
            .andExpect(jsonPath("$.cancerType").value(DEFAULT_CANCER_TYPE.toString()))
            .andExpect(jsonPath("$.cancerStudy").value(DEFAULT_CANCER_STUDY.toString()))
            .andExpect(jsonPath("$.sampleId").value(DEFAULT_SAMPLE_ID.toString()))
            .andExpect(jsonPath("$.proteinChange").value(DEFAULT_PROTEIN_CHANGE.toString()))
            .andExpect(jsonPath("$.proteinStartPosition").value(DEFAULT_PROTEIN_START_POSITION))
            .andExpect(jsonPath("$.proteinEndPosition").value(DEFAULT_PROTEIN_END_POSITION))
            .andExpect(jsonPath("$.alterationType").value(DEFAULT_ALTERATION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPortalAlteration() throws Exception {
        // Get the portalAlteration
        restPortalAlterationMockMvc.perform(get("/api/portal-alterations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortalAlteration() throws Exception {
        // Initialize the database
        portalAlterationService.save(portalAlteration);

        int databaseSizeBeforeUpdate = portalAlterationRepository.findAll().size();

        // Update the portalAlteration
        PortalAlteration updatedPortalAlteration = portalAlterationRepository.findOne(portalAlteration.getId());
        updatedPortalAlteration
                .cancerType(UPDATED_CANCER_TYPE)
                .cancerStudy(UPDATED_CANCER_STUDY)
                .sampleId(UPDATED_SAMPLE_ID)
                .proteinChange(UPDATED_PROTEIN_CHANGE)
                .proteinStartPosition(UPDATED_PROTEIN_START_POSITION)
                .proteinEndPosition(UPDATED_PROTEIN_END_POSITION)
                .alterationType(UPDATED_ALTERATION_TYPE);

        restPortalAlterationMockMvc.perform(put("/api/portal-alterations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPortalAlteration)))
                .andExpect(status().isOk());

        // Validate the PortalAlteration in the database
        List<PortalAlteration> portalAlterations = portalAlterationRepository.findAll();
        assertThat(portalAlterations).hasSize(databaseSizeBeforeUpdate);
        PortalAlteration testPortalAlteration = portalAlterations.get(portalAlterations.size() - 1);
        assertThat(testPortalAlteration.getCancerType()).isEqualTo(UPDATED_CANCER_TYPE);
        assertThat(testPortalAlteration.getCancerStudy()).isEqualTo(UPDATED_CANCER_STUDY);
        assertThat(testPortalAlteration.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testPortalAlteration.getProteinChange()).isEqualTo(UPDATED_PROTEIN_CHANGE);
        assertThat(testPortalAlteration.getProteinStartPosition()).isEqualTo(UPDATED_PROTEIN_START_POSITION);
        assertThat(testPortalAlteration.getProteinEndPosition()).isEqualTo(UPDATED_PROTEIN_END_POSITION);
        assertThat(testPortalAlteration.getAlterationType()).isEqualTo(UPDATED_ALTERATION_TYPE);
    }

    @Test
    @Transactional
    public void deletePortalAlteration() throws Exception {
        // Initialize the database
        portalAlterationService.save(portalAlteration);

        int databaseSizeBeforeDelete = portalAlterationRepository.findAll().size();

        // Get the portalAlteration
        restPortalAlterationMockMvc.perform(delete("/api/portal-alterations/{id}", portalAlteration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PortalAlteration> portalAlterations = portalAlterationRepository.findAll();
        assertThat(portalAlterations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
