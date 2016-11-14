package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.GeneAlias;
import org.mskcc.cbio.oncokb.repository.GeneAliasRepository;
import org.mskcc.cbio.oncokb.service.GeneAliasService;

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
 * Test class for the GeneAliasResource REST controller.
 *
 * @see GeneAliasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class GeneAliasResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private GeneAliasRepository geneAliasRepository;

    @Inject
    private GeneAliasService geneAliasService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restGeneAliasMockMvc;

    private GeneAlias geneAlias;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GeneAliasResource geneAliasResource = new GeneAliasResource();
        ReflectionTestUtils.setField(geneAliasResource, "geneAliasService", geneAliasService);
        this.restGeneAliasMockMvc = MockMvcBuilders.standaloneSetup(geneAliasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeneAlias createEntity(EntityManager em) {
        GeneAlias geneAlias = new GeneAlias()
                .name(DEFAULT_NAME);
        return geneAlias;
    }

    @Before
    public void initTest() {
        geneAlias = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneAlias() throws Exception {
        int databaseSizeBeforeCreate = geneAliasRepository.findAll().size();

        // Create the GeneAlias

        restGeneAliasMockMvc.perform(post("/api/gene-aliases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(geneAlias)))
                .andExpect(status().isCreated());

        // Validate the GeneAlias in the database
        List<GeneAlias> geneAliases = geneAliasRepository.findAll();
        assertThat(geneAliases).hasSize(databaseSizeBeforeCreate + 1);
        GeneAlias testGeneAlias = geneAliases.get(geneAliases.size() - 1);
        assertThat(testGeneAlias.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllGeneAliases() throws Exception {
        // Initialize the database
        geneAliasRepository.saveAndFlush(geneAlias);

        // Get all the geneAliases
        restGeneAliasMockMvc.perform(get("/api/gene-aliases?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(geneAlias.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGeneAlias() throws Exception {
        // Initialize the database
        geneAliasRepository.saveAndFlush(geneAlias);

        // Get the geneAlias
        restGeneAliasMockMvc.perform(get("/api/gene-aliases/{id}", geneAlias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geneAlias.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeneAlias() throws Exception {
        // Get the geneAlias
        restGeneAliasMockMvc.perform(get("/api/gene-aliases/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneAlias() throws Exception {
        // Initialize the database
        geneAliasService.save(geneAlias);

        int databaseSizeBeforeUpdate = geneAliasRepository.findAll().size();

        // Update the geneAlias
        GeneAlias updatedGeneAlias = geneAliasRepository.findOne(geneAlias.getId());
        updatedGeneAlias
                .name(UPDATED_NAME);

        restGeneAliasMockMvc.perform(put("/api/gene-aliases")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGeneAlias)))
                .andExpect(status().isOk());

        // Validate the GeneAlias in the database
        List<GeneAlias> geneAliases = geneAliasRepository.findAll();
        assertThat(geneAliases).hasSize(databaseSizeBeforeUpdate);
        GeneAlias testGeneAlias = geneAliases.get(geneAliases.size() - 1);
        assertThat(testGeneAlias.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteGeneAlias() throws Exception {
        // Initialize the database
        geneAliasService.save(geneAlias);

        int databaseSizeBeforeDelete = geneAliasRepository.findAll().size();

        // Get the geneAlias
        restGeneAliasMockMvc.perform(delete("/api/gene-aliases/{id}", geneAlias.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneAlias> geneAliases = geneAliasRepository.findAll();
        assertThat(geneAliases).hasSize(databaseSizeBeforeDelete - 1);
    }
}
