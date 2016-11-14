package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.GeneLabel;
import org.mskcc.cbio.oncokb.repository.GeneLabelRepository;
import org.mskcc.cbio.oncokb.service.GeneLabelService;

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
 * Test class for the GeneLabelResource REST controller.
 *
 * @see GeneLabelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class GeneLabelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private GeneLabelRepository geneLabelRepository;

    @Inject
    private GeneLabelService geneLabelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGeneLabelMockMvc;

    private GeneLabel geneLabel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GeneLabelResource geneLabelResource = new GeneLabelResource();
        ReflectionTestUtils.setField(geneLabelResource, "geneLabelService", geneLabelService);
        this.restGeneLabelMockMvc = MockMvcBuilders.standaloneSetup(geneLabelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneLabel createEntity(EntityManager em) {
        GeneLabel geneLabel = new GeneLabel()
                .name(DEFAULT_NAME);
        return geneLabel;
    }

    @Before
    public void initTest() {
        geneLabel = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneLabel() throws Exception {
        int databaseSizeBeforeCreate = geneLabelRepository.findAll().size();

        // Create the GeneLabel

        restGeneLabelMockMvc.perform(post("/api/gene-labels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(geneLabel)))
                .andExpect(status().isCreated());

        // Validate the GeneLabel in the database
        List<GeneLabel> geneLabels = geneLabelRepository.findAll();
        assertThat(geneLabels).hasSize(databaseSizeBeforeCreate + 1);
        GeneLabel testGeneLabel = geneLabels.get(geneLabels.size() - 1);
        assertThat(testGeneLabel.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllGeneLabels() throws Exception {
        // Initialize the database
        geneLabelRepository.saveAndFlush(geneLabel);

        // Get all the geneLabels
        restGeneLabelMockMvc.perform(get("/api/gene-labels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(geneLabel.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGeneLabel() throws Exception {
        // Initialize the database
        geneLabelRepository.saveAndFlush(geneLabel);

        // Get the geneLabel
        restGeneLabelMockMvc.perform(get("/api/gene-labels/{id}", geneLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geneLabel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeneLabel() throws Exception {
        // Get the geneLabel
        restGeneLabelMockMvc.perform(get("/api/gene-labels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneLabel() throws Exception {
        // Initialize the database
        geneLabelService.save(geneLabel);

        int databaseSizeBeforeUpdate = geneLabelRepository.findAll().size();

        // Update the geneLabel
        GeneLabel updatedGeneLabel = geneLabelRepository.findOne(geneLabel.getId());
        updatedGeneLabel
                .name(UPDATED_NAME);

        restGeneLabelMockMvc.perform(put("/api/gene-labels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGeneLabel)))
                .andExpect(status().isOk());

        // Validate the GeneLabel in the database
        List<GeneLabel> geneLabels = geneLabelRepository.findAll();
        assertThat(geneLabels).hasSize(databaseSizeBeforeUpdate);
        GeneLabel testGeneLabel = geneLabels.get(geneLabels.size() - 1);
        assertThat(testGeneLabel.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteGeneLabel() throws Exception {
        // Initialize the database
        geneLabelService.save(geneLabel);

        int databaseSizeBeforeDelete = geneLabelRepository.findAll().size();

        // Get the geneLabel
        restGeneLabelMockMvc.perform(delete("/api/gene-labels/{id}", geneLabel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneLabel> geneLabels = geneLabelRepository.findAll();
        assertThat(geneLabels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
