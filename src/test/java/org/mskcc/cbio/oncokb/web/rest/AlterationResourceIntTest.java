package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.Alteration;
import org.mskcc.cbio.oncokb.repository.AlterationRepository;
import org.mskcc.cbio.oncokb.service.AlterationService;

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

import org.mskcc.cbio.oncokb.domain.enumeration.AlterationType;
/**
 * Test class for the AlterationResource REST controller.
 *
 * @see AlterationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class AlterationResourceIntTest {

    private static final String DEFAULT_ALTERATION = "AAAAA";
    private static final String UPDATED_ALTERATION = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final AlterationType DEFAULT_ALTERATION_TYPE = AlterationType.MUTATION;
    private static final AlterationType UPDATED_ALTERATION_TYPE = AlterationType.COPY_NUMBER_ALTERATION;
    private static final String DEFAULT_REF_RESIDUES = "AAAAA";
    private static final String UPDATED_REF_RESIDUES = "BBBBB";

    private static final Integer DEFAULT_PROTEIN_START = 1;
    private static final Integer UPDATED_PROTEIN_START = 2;

    private static final Integer DEFAULT_POTEIN_END = 1;
    private static final Integer UPDATED_POTEIN_END = 2;
    private static final String DEFAULT_VARIANT_RESIDUES = "AAAAA";
    private static final String UPDATED_VARIANT_RESIDUES = "BBBBB";

    @Inject
    private AlterationRepository alterationRepository;

    @Inject
    private AlterationService alterationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlterationMockMvc;

    private Alteration alteration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlterationResource alterationResource = new AlterationResource();
        ReflectionTestUtils.setField(alterationResource, "alterationService", alterationService);
        this.restAlterationMockMvc = MockMvcBuilders.standaloneSetup(alterationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alteration createEntity(EntityManager em) {
        Alteration alteration = new Alteration()
                .alteration(DEFAULT_ALTERATION)
                .name(DEFAULT_NAME)
                .alterationType(DEFAULT_ALTERATION_TYPE)
                .refResidues(DEFAULT_REF_RESIDUES)
                .proteinStart(DEFAULT_PROTEIN_START)
                .poteinEnd(DEFAULT_POTEIN_END)
                .variantResidues(DEFAULT_VARIANT_RESIDUES);
        return alteration;
    }

    @Before
    public void initTest() {
        alteration = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlteration() throws Exception {
        int databaseSizeBeforeCreate = alterationRepository.findAll().size();

        // Create the Alteration

        restAlterationMockMvc.perform(post("/api/alterations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(alteration)))
                .andExpect(status().isCreated());

        // Validate the Alteration in the database
        List<Alteration> alterations = alterationRepository.findAll();
        assertThat(alterations).hasSize(databaseSizeBeforeCreate + 1);
        Alteration testAlteration = alterations.get(alterations.size() - 1);
        assertThat(testAlteration.getAlteration()).isEqualTo(DEFAULT_ALTERATION);
        assertThat(testAlteration.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlteration.getAlterationType()).isEqualTo(DEFAULT_ALTERATION_TYPE);
        assertThat(testAlteration.getRefResidues()).isEqualTo(DEFAULT_REF_RESIDUES);
        assertThat(testAlteration.getProteinStart()).isEqualTo(DEFAULT_PROTEIN_START);
        assertThat(testAlteration.getPoteinEnd()).isEqualTo(DEFAULT_POTEIN_END);
        assertThat(testAlteration.getVariantResidues()).isEqualTo(DEFAULT_VARIANT_RESIDUES);
    }

    @Test
    @Transactional
    public void getAllAlterations() throws Exception {
        // Initialize the database
        alterationRepository.saveAndFlush(alteration);

        // Get all the alterations
        restAlterationMockMvc.perform(get("/api/alterations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(alteration.getId().intValue())))
                .andExpect(jsonPath("$.[*].alteration").value(hasItem(DEFAULT_ALTERATION.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].alterationType").value(hasItem(DEFAULT_ALTERATION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].refResidues").value(hasItem(DEFAULT_REF_RESIDUES.toString())))
                .andExpect(jsonPath("$.[*].proteinStart").value(hasItem(DEFAULT_PROTEIN_START)))
                .andExpect(jsonPath("$.[*].poteinEnd").value(hasItem(DEFAULT_POTEIN_END)))
                .andExpect(jsonPath("$.[*].variantResidues").value(hasItem(DEFAULT_VARIANT_RESIDUES.toString())));
    }

    @Test
    @Transactional
    public void getAlteration() throws Exception {
        // Initialize the database
        alterationRepository.saveAndFlush(alteration);

        // Get the alteration
        restAlterationMockMvc.perform(get("/api/alterations/{id}", alteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alteration.getId().intValue()))
            .andExpect(jsonPath("$.alteration").value(DEFAULT_ALTERATION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.alterationType").value(DEFAULT_ALTERATION_TYPE.toString()))
            .andExpect(jsonPath("$.refResidues").value(DEFAULT_REF_RESIDUES.toString()))
            .andExpect(jsonPath("$.proteinStart").value(DEFAULT_PROTEIN_START))
            .andExpect(jsonPath("$.poteinEnd").value(DEFAULT_POTEIN_END))
            .andExpect(jsonPath("$.variantResidues").value(DEFAULT_VARIANT_RESIDUES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlteration() throws Exception {
        // Get the alteration
        restAlterationMockMvc.perform(get("/api/alterations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlteration() throws Exception {
        // Initialize the database
        alterationService.save(alteration);

        int databaseSizeBeforeUpdate = alterationRepository.findAll().size();

        // Update the alteration
        Alteration updatedAlteration = alterationRepository.findOne(alteration.getId());
        updatedAlteration
                .alteration(UPDATED_ALTERATION)
                .name(UPDATED_NAME)
                .alterationType(UPDATED_ALTERATION_TYPE)
                .refResidues(UPDATED_REF_RESIDUES)
                .proteinStart(UPDATED_PROTEIN_START)
                .poteinEnd(UPDATED_POTEIN_END)
                .variantResidues(UPDATED_VARIANT_RESIDUES);

        restAlterationMockMvc.perform(put("/api/alterations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAlteration)))
                .andExpect(status().isOk());

        // Validate the Alteration in the database
        List<Alteration> alterations = alterationRepository.findAll();
        assertThat(alterations).hasSize(databaseSizeBeforeUpdate);
        Alteration testAlteration = alterations.get(alterations.size() - 1);
        assertThat(testAlteration.getAlteration()).isEqualTo(UPDATED_ALTERATION);
        assertThat(testAlteration.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlteration.getAlterationType()).isEqualTo(UPDATED_ALTERATION_TYPE);
        assertThat(testAlteration.getRefResidues()).isEqualTo(UPDATED_REF_RESIDUES);
        assertThat(testAlteration.getProteinStart()).isEqualTo(UPDATED_PROTEIN_START);
        assertThat(testAlteration.getPoteinEnd()).isEqualTo(UPDATED_POTEIN_END);
        assertThat(testAlteration.getVariantResidues()).isEqualTo(UPDATED_VARIANT_RESIDUES);
    }

    @Test
    @Transactional
    public void deleteAlteration() throws Exception {
        // Initialize the database
        alterationService.save(alteration);

        int databaseSizeBeforeDelete = alterationRepository.findAll().size();

        // Get the alteration
        restAlterationMockMvc.perform(delete("/api/alterations/{id}", alteration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Alteration> alterations = alterationRepository.findAll();
        assertThat(alterations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
