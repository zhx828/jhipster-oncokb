package org.mskcc.cbio.oncokb.web.rest;

import org.mskcc.cbio.oncokb.OncokbApp;

import org.mskcc.cbio.oncokb.domain.TimeStamp;
import org.mskcc.cbio.oncokb.repository.TimeStampRepository;
import org.mskcc.cbio.oncokb.service.TimeStampService;

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

/**
 * Test class for the TimeStampResource REST controller.
 *
 * @see TimeStampResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OncokbApp.class)
public class TimeStampResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_TIME_STAMP_STR = dateTimeFormatter.format(DEFAULT_TIME_STAMP);

    @Inject
    private TimeStampRepository timeStampRepository;

    @Inject
    private TimeStampService timeStampService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTimeStampMockMvc;

    private TimeStamp timeStamp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeStampResource timeStampResource = new TimeStampResource();
        ReflectionTestUtils.setField(timeStampResource, "timeStampService", timeStampService);
        this.restTimeStampMockMvc = MockMvcBuilders.standaloneSetup(timeStampResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeStamp createEntity(EntityManager em) {
        TimeStamp timeStamp = new TimeStamp()
                .timeStamp(DEFAULT_TIME_STAMP);
        return timeStamp;
    }

    @Before
    public void initTest() {
        timeStamp = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeStamp() throws Exception {
        int databaseSizeBeforeCreate = timeStampRepository.findAll().size();

        // Create the TimeStamp

        restTimeStampMockMvc.perform(post("/api/time-stamps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeStamp)))
                .andExpect(status().isCreated());

        // Validate the TimeStamp in the database
        List<TimeStamp> timeStamps = timeStampRepository.findAll();
        assertThat(timeStamps).hasSize(databaseSizeBeforeCreate + 1);
        TimeStamp testTimeStamp = timeStamps.get(timeStamps.size() - 1);
        assertThat(testTimeStamp.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void getAllTimeStamps() throws Exception {
        // Initialize the database
        timeStampRepository.saveAndFlush(timeStamp);

        // Get all the timeStamps
        restTimeStampMockMvc.perform(get("/api/time-stamps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeStamp.getId().intValue())))
                .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(DEFAULT_TIME_STAMP_STR)));
    }

    @Test
    @Transactional
    public void getTimeStamp() throws Exception {
        // Initialize the database
        timeStampRepository.saveAndFlush(timeStamp);

        // Get the timeStamp
        restTimeStampMockMvc.perform(get("/api/time-stamps/{id}", timeStamp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeStamp.getId().intValue()))
            .andExpect(jsonPath("$.timeStamp").value(DEFAULT_TIME_STAMP_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTimeStamp() throws Exception {
        // Get the timeStamp
        restTimeStampMockMvc.perform(get("/api/time-stamps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeStamp() throws Exception {
        // Initialize the database
        timeStampService.save(timeStamp);

        int databaseSizeBeforeUpdate = timeStampRepository.findAll().size();

        // Update the timeStamp
        TimeStamp updatedTimeStamp = timeStampRepository.findOne(timeStamp.getId());
        updatedTimeStamp
                .timeStamp(UPDATED_TIME_STAMP);

        restTimeStampMockMvc.perform(put("/api/time-stamps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTimeStamp)))
                .andExpect(status().isOk());

        // Validate the TimeStamp in the database
        List<TimeStamp> timeStamps = timeStampRepository.findAll();
        assertThat(timeStamps).hasSize(databaseSizeBeforeUpdate);
        TimeStamp testTimeStamp = timeStamps.get(timeStamps.size() - 1);
        assertThat(testTimeStamp.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void deleteTimeStamp() throws Exception {
        // Initialize the database
        timeStampService.save(timeStamp);

        int databaseSizeBeforeDelete = timeStampRepository.findAll().size();

        // Get the timeStamp
        restTimeStampMockMvc.perform(delete("/api/time-stamps/{id}", timeStamp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeStamp> timeStamps = timeStampRepository.findAll();
        assertThat(timeStamps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
