package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.NccnGuideline;
import org.mskcc.cbio.oncokb.repository.NccnGuidelineRepository;
import org.mskcc.cbio.oncokb.service.NccnGuidelineService;

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
 * Test class for the NccnGuidelineResource REST controller.
 *
 * @see NccnGuidelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class NccnGuidelineResourceIntTest {

    private static final String DEFAULT_DISEASE = "AAAAA";
    private static final String UPDATED_DISEASE = "BBBBB";
    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_PAGES = "AAAAA";
    private static final String UPDATED_PAGES = "BBBBB";
    private static final String DEFAULT_CATEGORY = "AAAAA";
    private static final String UPDATED_CATEGORY = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBB";

    @Inject
    private NccnGuidelineRepository nccnGuidelineRepository;

    @Inject
    private NccnGuidelineService nccnGuidelineService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restNccnGuidelineMockMvc;

    private NccnGuideline nccnGuideline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NccnGuidelineResource nccnGuidelineResource = new NccnGuidelineResource();
        ReflectionTestUtils.setField(nccnGuidelineResource, "nccnGuidelineService", nccnGuidelineService);
        this.restNccnGuidelineMockMvc = MockMvcBuilders.standaloneSetup(nccnGuidelineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NccnGuideline createEntity(EntityManager em) {
        NccnGuideline nccnGuideline = new NccnGuideline()
                .disease(DEFAULT_DISEASE)
                .version(DEFAULT_VERSION)
                .pages(DEFAULT_PAGES)
                .category(DEFAULT_CATEGORY)
                .description(DEFAULT_DESCRIPTION)
                .additionalInfo(DEFAULT_ADDITIONAL_INFO);
        return nccnGuideline;
    }

    @Before
    public void initTest() {
        nccnGuideline = createEntity(em);
    }

    @Test
    @Transactional
    public void createNccnGuideline() throws Exception {
        int databaseSizeBeforeCreate = nccnGuidelineRepository.findAll().size();

        // Create the NccnGuideline

        restNccnGuidelineMockMvc.perform(post("/api/nccn-guidelines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(nccnGuideline)))
                .andExpect(status().isCreated());

        // Validate the NccnGuideline in the database
        List<NccnGuideline> nccnGuidelines = nccnGuidelineRepository.findAll();
        assertThat(nccnGuidelines).hasSize(databaseSizeBeforeCreate + 1);
        NccnGuideline testNccnGuideline = nccnGuidelines.get(nccnGuidelines.size() - 1);
        assertThat(testNccnGuideline.getDisease()).isEqualTo(DEFAULT_DISEASE);
        assertThat(testNccnGuideline.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testNccnGuideline.getPages()).isEqualTo(DEFAULT_PAGES);
        assertThat(testNccnGuideline.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testNccnGuideline.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNccnGuideline.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void getAllNccnGuidelines() throws Exception {
        // Initialize the database
        nccnGuidelineRepository.saveAndFlush(nccnGuideline);

        // Get all the nccnGuidelines
        restNccnGuidelineMockMvc.perform(get("/api/nccn-guidelines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(nccnGuideline.getId().intValue())))
                .andExpect(jsonPath("$.[*].disease").value(hasItem(DEFAULT_DISEASE.toString())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].pages").value(hasItem(DEFAULT_PAGES.toString())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())));
    }

    @Test
    @Transactional
    public void getNccnGuideline() throws Exception {
        // Initialize the database
        nccnGuidelineRepository.saveAndFlush(nccnGuideline);

        // Get the nccnGuideline
        restNccnGuidelineMockMvc.perform(get("/api/nccn-guidelines/{id}", nccnGuideline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nccnGuideline.getId().intValue()))
            .andExpect(jsonPath("$.disease").value(DEFAULT_DISEASE.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.pages").value(DEFAULT_PAGES.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNccnGuideline() throws Exception {
        // Get the nccnGuideline
        restNccnGuidelineMockMvc.perform(get("/api/nccn-guidelines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNccnGuideline() throws Exception {
        // Initialize the database
        nccnGuidelineService.save(nccnGuideline);

        int databaseSizeBeforeUpdate = nccnGuidelineRepository.findAll().size();

        // Update the nccnGuideline
        NccnGuideline updatedNccnGuideline = nccnGuidelineRepository.findOne(nccnGuideline.getId());
        updatedNccnGuideline
                .disease(UPDATED_DISEASE)
                .version(UPDATED_VERSION)
                .pages(UPDATED_PAGES)
                .category(UPDATED_CATEGORY)
                .description(UPDATED_DESCRIPTION)
                .additionalInfo(UPDATED_ADDITIONAL_INFO);

        restNccnGuidelineMockMvc.perform(put("/api/nccn-guidelines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedNccnGuideline)))
                .andExpect(status().isOk());

        // Validate the NccnGuideline in the database
        List<NccnGuideline> nccnGuidelines = nccnGuidelineRepository.findAll();
        assertThat(nccnGuidelines).hasSize(databaseSizeBeforeUpdate);
        NccnGuideline testNccnGuideline = nccnGuidelines.get(nccnGuidelines.size() - 1);
        assertThat(testNccnGuideline.getDisease()).isEqualTo(UPDATED_DISEASE);
        assertThat(testNccnGuideline.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testNccnGuideline.getPages()).isEqualTo(UPDATED_PAGES);
        assertThat(testNccnGuideline.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testNccnGuideline.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNccnGuideline.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
    }

    @Test
    @Transactional
    public void deleteNccnGuideline() throws Exception {
        // Initialize the database
        nccnGuidelineService.save(nccnGuideline);

        int databaseSizeBeforeDelete = nccnGuidelineRepository.findAll().size();

        // Get the nccnGuideline
        restNccnGuidelineMockMvc.perform(delete("/api/nccn-guidelines/{id}", nccnGuideline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<NccnGuideline> nccnGuidelines = nccnGuidelineRepository.findAll();
        assertThat(nccnGuidelines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
