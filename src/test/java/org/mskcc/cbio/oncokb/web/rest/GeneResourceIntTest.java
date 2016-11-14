package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.Gene;
import org.mskcc.cbio.oncokb.repository.GeneRepository;
import org.mskcc.cbio.oncokb.service.GeneService;

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
 * Test class for the GeneResource REST controller.
 *
 * @see GeneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class GeneResourceIntTest {


    private static final Integer DEFAULT_ENTREZ_GENE_ID = 1;
    private static final Integer UPDATED_ENTREZ_GENE_ID = 2;
    private static final String DEFAULT_HUGO_SYMBOL = "AAAAA";
    private static final String UPDATED_HUGO_SYMBOL = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private GeneRepository geneRepository;

    @Inject
    private GeneService geneService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGeneMockMvc;

    private Gene gene;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GeneResource geneResource = new GeneResource();
        ReflectionTestUtils.setField(geneResource, "geneService", geneService);
        this.restGeneMockMvc = MockMvcBuilders.standaloneSetup(geneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gene createEntity(EntityManager em) {
        Gene gene = new Gene()
                .entrezGeneId(DEFAULT_ENTREZ_GENE_ID)
                .hugoSymbol(DEFAULT_HUGO_SYMBOL)
                .name(DEFAULT_NAME);
        return gene;
    }

    @Before
    public void initTest() {
        gene = createEntity(em);
    }

    @Test
    @Transactional
    public void createGene() throws Exception {
        int databaseSizeBeforeCreate = geneRepository.findAll().size();

        // Create the Gene

        restGeneMockMvc.perform(post("/api/genes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gene)))
                .andExpect(status().isCreated());

        // Validate the Gene in the database
        List<Gene> genes = geneRepository.findAll();
        assertThat(genes).hasSize(databaseSizeBeforeCreate + 1);
        Gene testGene = genes.get(genes.size() - 1);
        assertThat(testGene.getEntrezGeneId()).isEqualTo(DEFAULT_ENTREZ_GENE_ID);
        assertThat(testGene.getHugoSymbol()).isEqualTo(DEFAULT_HUGO_SYMBOL);
        assertThat(testGene.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllGenes() throws Exception {
        // Initialize the database
        geneRepository.saveAndFlush(gene);

        // Get all the genes
        restGeneMockMvc.perform(get("/api/genes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gene.getId().intValue())))
                .andExpect(jsonPath("$.[*].entrezGeneId").value(hasItem(DEFAULT_ENTREZ_GENE_ID)))
                .andExpect(jsonPath("$.[*].hugoSymbol").value(hasItem(DEFAULT_HUGO_SYMBOL.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGene() throws Exception {
        // Initialize the database
        geneRepository.saveAndFlush(gene);

        // Get the gene
        restGeneMockMvc.perform(get("/api/genes/{id}", gene.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gene.getId().intValue()))
            .andExpect(jsonPath("$.entrezGeneId").value(DEFAULT_ENTREZ_GENE_ID))
            .andExpect(jsonPath("$.hugoSymbol").value(DEFAULT_HUGO_SYMBOL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGene() throws Exception {
        // Get the gene
        restGeneMockMvc.perform(get("/api/genes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGene() throws Exception {
        // Initialize the database
        geneService.save(gene);

        int databaseSizeBeforeUpdate = geneRepository.findAll().size();

        // Update the gene
        Gene updatedGene = geneRepository.findOne(gene.getId());
        updatedGene
                .entrezGeneId(UPDATED_ENTREZ_GENE_ID)
                .hugoSymbol(UPDATED_HUGO_SYMBOL)
                .name(UPDATED_NAME);

        restGeneMockMvc.perform(put("/api/genes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGene)))
                .andExpect(status().isOk());

        // Validate the Gene in the database
        List<Gene> genes = geneRepository.findAll();
        assertThat(genes).hasSize(databaseSizeBeforeUpdate);
        Gene testGene = genes.get(genes.size() - 1);
        assertThat(testGene.getEntrezGeneId()).isEqualTo(UPDATED_ENTREZ_GENE_ID);
        assertThat(testGene.getHugoSymbol()).isEqualTo(UPDATED_HUGO_SYMBOL);
        assertThat(testGene.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteGene() throws Exception {
        // Initialize the database
        geneService.save(gene);

        int databaseSizeBeforeDelete = geneRepository.findAll().size();

        // Get the gene
        restGeneMockMvc.perform(delete("/api/genes/{id}", gene.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Gene> genes = geneRepository.findAll();
        assertThat(genes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
