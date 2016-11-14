package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.VariantConsequence;
import org.mskcc.cbio.oncokb.repository.VariantConsequenceRepository;
import org.mskcc.cbio.oncokb.service.VariantConsequenceService;

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
 * Test class for the VariantConsequenceResource REST controller.
 *
 * @see VariantConsequenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class VariantConsequenceResourceIntTest {

    private static final String DEFAULT_TERM = "AAAAA";
    private static final String UPDATED_TERM = "BBBBB";

    private static final Boolean DEFAULT_IS_GENERALLY_TRUNCATING = false;
    private static final Boolean UPDATED_IS_GENERALLY_TRUNCATING = true;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private VariantConsequenceRepository variantConsequenceRepository;

    @Inject
    private VariantConsequenceService variantConsequenceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVariantConsequenceMockMvc;

    private VariantConsequence variantConsequence;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VariantConsequenceResource variantConsequenceResource = new VariantConsequenceResource();
        ReflectionTestUtils.setField(variantConsequenceResource, "variantConsequenceService", variantConsequenceService);
        this.restVariantConsequenceMockMvc = MockMvcBuilders.standaloneSetup(variantConsequenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariantConsequence createEntity(EntityManager em) {
        VariantConsequence variantConsequence = new VariantConsequence()
                .term(DEFAULT_TERM)
                .isGenerallyTruncating(DEFAULT_IS_GENERALLY_TRUNCATING)
                .description(DEFAULT_DESCRIPTION);
        return variantConsequence;
    }

    @Before
    public void initTest() {
        variantConsequence = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariantConsequence() throws Exception {
        int databaseSizeBeforeCreate = variantConsequenceRepository.findAll().size();

        // Create the VariantConsequence

        restVariantConsequenceMockMvc.perform(post("/api/variant-consequences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(variantConsequence)))
                .andExpect(status().isCreated());

        // Validate the VariantConsequence in the database
        List<VariantConsequence> variantConsequences = variantConsequenceRepository.findAll();
        assertThat(variantConsequences).hasSize(databaseSizeBeforeCreate + 1);
        VariantConsequence testVariantConsequence = variantConsequences.get(variantConsequences.size() - 1);
        assertThat(testVariantConsequence.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testVariantConsequence.isIsGenerallyTruncating()).isEqualTo(DEFAULT_IS_GENERALLY_TRUNCATING);
        assertThat(testVariantConsequence.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVariantConsequences() throws Exception {
        // Initialize the database
        variantConsequenceRepository.saveAndFlush(variantConsequence);

        // Get all the variantConsequences
        restVariantConsequenceMockMvc.perform(get("/api/variant-consequences?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(variantConsequence.getId().intValue())))
                .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM.toString())))
                .andExpect(jsonPath("$.[*].isGenerallyTruncating").value(hasItem(DEFAULT_IS_GENERALLY_TRUNCATING.booleanValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getVariantConsequence() throws Exception {
        // Initialize the database
        variantConsequenceRepository.saveAndFlush(variantConsequence);

        // Get the variantConsequence
        restVariantConsequenceMockMvc.perform(get("/api/variant-consequences/{id}", variantConsequence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variantConsequence.getId().intValue()))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM.toString()))
            .andExpect(jsonPath("$.isGenerallyTruncating").value(DEFAULT_IS_GENERALLY_TRUNCATING.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVariantConsequence() throws Exception {
        // Get the variantConsequence
        restVariantConsequenceMockMvc.perform(get("/api/variant-consequences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariantConsequence() throws Exception {
        // Initialize the database
        variantConsequenceService.save(variantConsequence);

        int databaseSizeBeforeUpdate = variantConsequenceRepository.findAll().size();

        // Update the variantConsequence
        VariantConsequence updatedVariantConsequence = variantConsequenceRepository.findOne(variantConsequence.getId());
        updatedVariantConsequence
                .term(UPDATED_TERM)
                .isGenerallyTruncating(UPDATED_IS_GENERALLY_TRUNCATING)
                .description(UPDATED_DESCRIPTION);

        restVariantConsequenceMockMvc.perform(put("/api/variant-consequences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVariantConsequence)))
                .andExpect(status().isOk());

        // Validate the VariantConsequence in the database
        List<VariantConsequence> variantConsequences = variantConsequenceRepository.findAll();
        assertThat(variantConsequences).hasSize(databaseSizeBeforeUpdate);
        VariantConsequence testVariantConsequence = variantConsequences.get(variantConsequences.size() - 1);
        assertThat(testVariantConsequence.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testVariantConsequence.isIsGenerallyTruncating()).isEqualTo(UPDATED_IS_GENERALLY_TRUNCATING);
        assertThat(testVariantConsequence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteVariantConsequence() throws Exception {
        // Initialize the database
        variantConsequenceService.save(variantConsequence);

        int databaseSizeBeforeDelete = variantConsequenceRepository.findAll().size();

        // Get the variantConsequence
        restVariantConsequenceMockMvc.perform(delete("/api/variant-consequences/{id}", variantConsequence.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VariantConsequence> variantConsequences = variantConsequenceRepository.findAll();
        assertThat(variantConsequences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
