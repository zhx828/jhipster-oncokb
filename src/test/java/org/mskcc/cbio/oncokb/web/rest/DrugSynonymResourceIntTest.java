package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.DrugSynonym;
import org.mskcc.cbio.oncokb.repository.DrugSynonymRepository;
import org.mskcc.cbio.oncokb.service.DrugSynonymService;

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
 * Test class for the DrugSynonymResource REST controller.
 *
 * @see DrugSynonymResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class DrugSynonymResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private DrugSynonymRepository drugSynonymRepository;

    @Inject
    private DrugSynonymService drugSynonymService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDrugSynonymMockMvc;

    private DrugSynonym drugSynonym;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DrugSynonymResource drugSynonymResource = new DrugSynonymResource();
        ReflectionTestUtils.setField(drugSynonymResource, "drugSynonymService", drugSynonymService);
        this.restDrugSynonymMockMvc = MockMvcBuilders.standaloneSetup(drugSynonymResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugSynonym createEntity(EntityManager em) {
        DrugSynonym drugSynonym = new DrugSynonym()
                .name(DEFAULT_NAME);
        return drugSynonym;
    }

    @Before
    public void initTest() {
        drugSynonym = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugSynonym() throws Exception {
        int databaseSizeBeforeCreate = drugSynonymRepository.findAll().size();

        // Create the DrugSynonym

        restDrugSynonymMockMvc.perform(post("/api/drug-synonyms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(drugSynonym)))
                .andExpect(status().isCreated());

        // Validate the DrugSynonym in the database
        List<DrugSynonym> drugSynonyms = drugSynonymRepository.findAll();
        assertThat(drugSynonyms).hasSize(databaseSizeBeforeCreate + 1);
        DrugSynonym testDrugSynonym = drugSynonyms.get(drugSynonyms.size() - 1);
        assertThat(testDrugSynonym.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugSynonyms() throws Exception {
        // Initialize the database
        drugSynonymRepository.saveAndFlush(drugSynonym);

        // Get all the drugSynonyms
        restDrugSynonymMockMvc.perform(get("/api/drug-synonyms?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drugSynonym.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDrugSynonym() throws Exception {
        // Initialize the database
        drugSynonymRepository.saveAndFlush(drugSynonym);

        // Get the drugSynonym
        restDrugSynonymMockMvc.perform(get("/api/drug-synonyms/{id}", drugSynonym.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugSynonym.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDrugSynonym() throws Exception {
        // Get the drugSynonym
        restDrugSynonymMockMvc.perform(get("/api/drug-synonyms/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugSynonym() throws Exception {
        // Initialize the database
        drugSynonymService.save(drugSynonym);

        int databaseSizeBeforeUpdate = drugSynonymRepository.findAll().size();

        // Update the drugSynonym
        DrugSynonym updatedDrugSynonym = drugSynonymRepository.findOne(drugSynonym.getId());
        updatedDrugSynonym
                .name(UPDATED_NAME);

        restDrugSynonymMockMvc.perform(put("/api/drug-synonyms")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDrugSynonym)))
                .andExpect(status().isOk());

        // Validate the DrugSynonym in the database
        List<DrugSynonym> drugSynonyms = drugSynonymRepository.findAll();
        assertThat(drugSynonyms).hasSize(databaseSizeBeforeUpdate);
        DrugSynonym testDrugSynonym = drugSynonyms.get(drugSynonyms.size() - 1);
        assertThat(testDrugSynonym.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteDrugSynonym() throws Exception {
        // Initialize the database
        drugSynonymService.save(drugSynonym);

        int databaseSizeBeforeDelete = drugSynonymRepository.findAll().size();

        // Get the drugSynonym
        restDrugSynonymMockMvc.perform(delete("/api/drug-synonyms/{id}", drugSynonym.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DrugSynonym> drugSynonyms = drugSynonymRepository.findAll();
        assertThat(drugSynonyms).hasSize(databaseSizeBeforeDelete - 1);
    }
}
