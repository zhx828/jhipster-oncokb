package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.Evidence;
import org.mskcc.cbio.oncokb.repository.EvidenceRepository;
import org.mskcc.cbio.oncokb.service.EvidenceService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mskcc.cbio.oncokb.domain.enumeration.EvidenceType;
import org.mskcc.cbio.oncokb.domain.enumeration.LevelOfEvidence;
import org.mskcc.cbio.oncokb.domain.enumeration.KnownEffect;
/**
 * Test class for the EvidenceResource REST controller.
 *
 * @see EvidenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class EvidenceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final EvidenceType DEFAULT_EVIDENCE_TYPE = EvidenceType.GENE_SUMMARY;
    private static final EvidenceType UPDATED_EVIDENCE_TYPE = EvidenceType.MUTATION_SUMMARY;

    private static final LevelOfEvidence DEFAULT_LEVEL_OF_EVIDENCE = LevelOfEvidence.LEVEL_0;
    private static final LevelOfEvidence UPDATED_LEVEL_OF_EVIDENCE = LevelOfEvidence.LEVEL_1;

    private static final KnownEffect DEFAULT_KNOWN_EFFECT = KnownEffect.YES;
    private static final KnownEffect UPDATED_KNOWN_EFFECT = KnownEffect.LIKELY;
    private static final String DEFAULT_CANCER_TYPE = "AAAAA";
    private static final String UPDATED_CANCER_TYPE = "BBBBB";
    private static final String DEFAULT_SUBTYPE = "AAAAA";
    private static final String UPDATED_SUBTYPE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ADDITIONAL_INFO = "AAAAA";
    private static final String UPDATED_ADDITIONAL_INFO = "BBBBB";

    private static final ZonedDateTime DEFAULT_LAST_EDIT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_EDIT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_EDIT_STR = dateTimeFormatter.format(DEFAULT_LAST_EDIT);
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";

    @Inject
    private EvidenceRepository evidenceRepository;

    @Inject
    private EvidenceService evidenceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEvidenceMockMvc;

    private Evidence evidence;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvidenceResource evidenceResource = new EvidenceResource();
        ReflectionTestUtils.setField(evidenceResource, "evidenceService", evidenceService);
        this.restEvidenceMockMvc = MockMvcBuilders.standaloneSetup(evidenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evidence createEntity(EntityManager em) {
        Evidence evidence = new Evidence()
                .evidenceType(DEFAULT_EVIDENCE_TYPE)
                .levelOfEvidence(DEFAULT_LEVEL_OF_EVIDENCE)
                .knownEffect(DEFAULT_KNOWN_EFFECT)
                .cancerType(DEFAULT_CANCER_TYPE)
                .subtype(DEFAULT_SUBTYPE)
                .description(DEFAULT_DESCRIPTION)
                .additionalInfo(DEFAULT_ADDITIONAL_INFO)
                .lastEdit(DEFAULT_LAST_EDIT)
                .status(DEFAULT_STATUS);
        return evidence;
    }

    @Before
    public void initTest() {
        evidence = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvidence() throws Exception {
        int databaseSizeBeforeCreate = evidenceRepository.findAll().size();

        // Create the Evidence

        restEvidenceMockMvc.perform(post("/api/evidences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evidence)))
                .andExpect(status().isCreated());

        // Validate the Evidence in the database
        List<Evidence> evidences = evidenceRepository.findAll();
        assertThat(evidences).hasSize(databaseSizeBeforeCreate + 1);
        Evidence testEvidence = evidences.get(evidences.size() - 1);
        assertThat(testEvidence.getEvidenceType()).isEqualTo(DEFAULT_EVIDENCE_TYPE);
        assertThat(testEvidence.getLevelOfEvidence()).isEqualTo(DEFAULT_LEVEL_OF_EVIDENCE);
        assertThat(testEvidence.getKnownEffect()).isEqualTo(DEFAULT_KNOWN_EFFECT);
        assertThat(testEvidence.getCancerType()).isEqualTo(DEFAULT_CANCER_TYPE);
        assertThat(testEvidence.getSubtype()).isEqualTo(DEFAULT_SUBTYPE);
        assertThat(testEvidence.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvidence.getAdditionalInfo()).isEqualTo(DEFAULT_ADDITIONAL_INFO);
        assertThat(testEvidence.getLastEdit()).isEqualTo(DEFAULT_LAST_EDIT);
        assertThat(testEvidence.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllEvidences() throws Exception {
        // Initialize the database
        evidenceRepository.saveAndFlush(evidence);

        // Get all the evidences
        restEvidenceMockMvc.perform(get("/api/evidences?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evidence.getId().intValue())))
                .andExpect(jsonPath("$.[*].evidenceType").value(hasItem(DEFAULT_EVIDENCE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].levelOfEvidence").value(hasItem(DEFAULT_LEVEL_OF_EVIDENCE.toString())))
                .andExpect(jsonPath("$.[*].knownEffect").value(hasItem(DEFAULT_KNOWN_EFFECT.toString())))
                .andExpect(jsonPath("$.[*].cancerType").value(hasItem(DEFAULT_CANCER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].subtype").value(hasItem(DEFAULT_SUBTYPE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].additionalInfo").value(hasItem(DEFAULT_ADDITIONAL_INFO.toString())))
                .andExpect(jsonPath("$.[*].lastEdit").value(hasItem(DEFAULT_LAST_EDIT_STR)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getEvidence() throws Exception {
        // Initialize the database
        evidenceRepository.saveAndFlush(evidence);

        // Get the evidence
        restEvidenceMockMvc.perform(get("/api/evidences/{id}", evidence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evidence.getId().intValue()))
            .andExpect(jsonPath("$.evidenceType").value(DEFAULT_EVIDENCE_TYPE.toString()))
            .andExpect(jsonPath("$.levelOfEvidence").value(DEFAULT_LEVEL_OF_EVIDENCE.toString()))
            .andExpect(jsonPath("$.knownEffect").value(DEFAULT_KNOWN_EFFECT.toString()))
            .andExpect(jsonPath("$.cancerType").value(DEFAULT_CANCER_TYPE.toString()))
            .andExpect(jsonPath("$.subtype").value(DEFAULT_SUBTYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.additionalInfo").value(DEFAULT_ADDITIONAL_INFO.toString()))
            .andExpect(jsonPath("$.lastEdit").value(DEFAULT_LAST_EDIT_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvidence() throws Exception {
        // Get the evidence
        restEvidenceMockMvc.perform(get("/api/evidences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvidence() throws Exception {
        // Initialize the database
        evidenceService.save(evidence);

        int databaseSizeBeforeUpdate = evidenceRepository.findAll().size();

        // Update the evidence
        Evidence updatedEvidence = evidenceRepository.findOne(evidence.getId());
        updatedEvidence
                .evidenceType(UPDATED_EVIDENCE_TYPE)
                .levelOfEvidence(UPDATED_LEVEL_OF_EVIDENCE)
                .knownEffect(UPDATED_KNOWN_EFFECT)
                .cancerType(UPDATED_CANCER_TYPE)
                .subtype(UPDATED_SUBTYPE)
                .description(UPDATED_DESCRIPTION)
                .additionalInfo(UPDATED_ADDITIONAL_INFO)
                .lastEdit(UPDATED_LAST_EDIT)
                .status(UPDATED_STATUS);

        restEvidenceMockMvc.perform(put("/api/evidences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEvidence)))
                .andExpect(status().isOk());

        // Validate the Evidence in the database
        List<Evidence> evidences = evidenceRepository.findAll();
        assertThat(evidences).hasSize(databaseSizeBeforeUpdate);
        Evidence testEvidence = evidences.get(evidences.size() - 1);
        assertThat(testEvidence.getEvidenceType()).isEqualTo(UPDATED_EVIDENCE_TYPE);
        assertThat(testEvidence.getLevelOfEvidence()).isEqualTo(UPDATED_LEVEL_OF_EVIDENCE);
        assertThat(testEvidence.getKnownEffect()).isEqualTo(UPDATED_KNOWN_EFFECT);
        assertThat(testEvidence.getCancerType()).isEqualTo(UPDATED_CANCER_TYPE);
        assertThat(testEvidence.getSubtype()).isEqualTo(UPDATED_SUBTYPE);
        assertThat(testEvidence.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvidence.getAdditionalInfo()).isEqualTo(UPDATED_ADDITIONAL_INFO);
        assertThat(testEvidence.getLastEdit()).isEqualTo(UPDATED_LAST_EDIT);
        assertThat(testEvidence.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteEvidence() throws Exception {
        // Initialize the database
        evidenceService.save(evidence);

        int databaseSizeBeforeDelete = evidenceRepository.findAll().size();

        // Get the evidence
        restEvidenceMockMvc.perform(delete("/api/evidences/{id}", evidence.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Evidence> evidences = evidenceRepository.findAll();
        assertThat(evidences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
