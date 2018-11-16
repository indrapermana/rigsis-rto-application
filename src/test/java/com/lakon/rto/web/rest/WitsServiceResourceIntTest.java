package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.WitsService;
import com.lakon.rto.repository.WitsServiceRepository;
import com.lakon.rto.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lakon.rto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lakon.rto.domain.enumeration.Status;
/**
 * Test class for the WitsServiceResource REST controller.
 *
 * @see WitsServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class WitsServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_HOSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PORT = "AAAAAAAAAA";
    private static final String UPDATED_PORT = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    @Autowired
    private WitsServiceRepository witsServiceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWitsServiceMockMvc;

    private WitsService witsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WitsServiceResource witsServiceResource = new WitsServiceResource(witsServiceRepository);
        this.restWitsServiceMockMvc = MockMvcBuilders.standaloneSetup(witsServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WitsService createEntity(EntityManager em) {
        WitsService witsService = new WitsService()
            .name(DEFAULT_NAME)
            .serviceType(DEFAULT_SERVICE_TYPE)
            .hostname(DEFAULT_HOSTNAME)
            .port(DEFAULT_PORT)
            .status(DEFAULT_STATUS);
        return witsService;
    }

    @Before
    public void initTest() {
        witsService = createEntity(em);
    }

    @Test
    @Transactional
    public void createWitsService() throws Exception {
        int databaseSizeBeforeCreate = witsServiceRepository.findAll().size();

        // Create the WitsService
        restWitsServiceMockMvc.perform(post("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isCreated());

        // Validate the WitsService in the database
        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeCreate + 1);
        WitsService testWitsService = witsServiceList.get(witsServiceList.size() - 1);
        assertThat(testWitsService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWitsService.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testWitsService.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testWitsService.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testWitsService.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWitsServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = witsServiceRepository.findAll().size();

        // Create the WitsService with an existing ID
        witsService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWitsServiceMockMvc.perform(post("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isBadRequest());

        // Validate the WitsService in the database
        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = witsServiceRepository.findAll().size();
        // set the field null
        witsService.setName(null);

        // Create the WitsService, which fails.

        restWitsServiceMockMvc.perform(post("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isBadRequest());

        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = witsServiceRepository.findAll().size();
        // set the field null
        witsService.setServiceType(null);

        // Create the WitsService, which fails.

        restWitsServiceMockMvc.perform(post("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isBadRequest());

        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHostnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = witsServiceRepository.findAll().size();
        // set the field null
        witsService.setHostname(null);

        // Create the WitsService, which fails.

        restWitsServiceMockMvc.perform(post("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isBadRequest());

        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = witsServiceRepository.findAll().size();
        // set the field null
        witsService.setPort(null);

        // Create the WitsService, which fails.

        restWitsServiceMockMvc.perform(post("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isBadRequest());

        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWitsServices() throws Exception {
        // Initialize the database
        witsServiceRepository.saveAndFlush(witsService);

        // Get all the witsServiceList
        restWitsServiceMockMvc.perform(get("/api/wits-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(witsService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME.toString())))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getWitsService() throws Exception {
        // Initialize the database
        witsServiceRepository.saveAndFlush(witsService);

        // Get the witsService
        restWitsServiceMockMvc.perform(get("/api/wits-services/{id}", witsService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(witsService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWitsService() throws Exception {
        // Get the witsService
        restWitsServiceMockMvc.perform(get("/api/wits-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWitsService() throws Exception {
        // Initialize the database
        witsServiceRepository.saveAndFlush(witsService);

        int databaseSizeBeforeUpdate = witsServiceRepository.findAll().size();

        // Update the witsService
        WitsService updatedWitsService = witsServiceRepository.findById(witsService.getId()).get();
        // Disconnect from session so that the updates on updatedWitsService are not directly saved in db
        em.detach(updatedWitsService);
        updatedWitsService
            .name(UPDATED_NAME)
            .serviceType(UPDATED_SERVICE_TYPE)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .status(UPDATED_STATUS);

        restWitsServiceMockMvc.perform(put("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWitsService)))
            .andExpect(status().isOk());

        // Validate the WitsService in the database
        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeUpdate);
        WitsService testWitsService = witsServiceList.get(witsServiceList.size() - 1);
        assertThat(testWitsService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWitsService.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testWitsService.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testWitsService.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testWitsService.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWitsService() throws Exception {
        int databaseSizeBeforeUpdate = witsServiceRepository.findAll().size();

        // Create the WitsService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWitsServiceMockMvc.perform(put("/api/wits-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(witsService)))
            .andExpect(status().isBadRequest());

        // Validate the WitsService in the database
        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWitsService() throws Exception {
        // Initialize the database
        witsServiceRepository.saveAndFlush(witsService);

        int databaseSizeBeforeDelete = witsServiceRepository.findAll().size();

        // Get the witsService
        restWitsServiceMockMvc.perform(delete("/api/wits-services/{id}", witsService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WitsService> witsServiceList = witsServiceRepository.findAll();
        assertThat(witsServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WitsService.class);
        WitsService witsService1 = new WitsService();
        witsService1.setId(1L);
        WitsService witsService2 = new WitsService();
        witsService2.setId(witsService1.getId());
        assertThat(witsService1).isEqualTo(witsService2);
        witsService2.setId(2L);
        assertThat(witsService1).isNotEqualTo(witsService2);
        witsService1.setId(null);
        assertThat(witsService1).isNotEqualTo(witsService2);
    }
}
