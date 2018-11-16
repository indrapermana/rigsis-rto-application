package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.RecordType;
import com.lakon.rto.repository.RecordTypeRepository;
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

/**
 * Test class for the RecordTypeResource REST controller.
 *
 * @see RecordTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class RecordTypeResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRIGGER = "AAAAAAAAAA";
    private static final String UPDATED_TRIGGER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RecordTypeRepository recordTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecordTypeMockMvc;

    private RecordType recordType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecordTypeResource recordTypeResource = new RecordTypeResource(recordTypeRepository);
        this.restRecordTypeMockMvc = MockMvcBuilders.standaloneSetup(recordTypeResource)
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
    public static RecordType createEntity(EntityManager em) {
        RecordType recordType = new RecordType()
            .number(DEFAULT_NUMBER)
            .name(DEFAULT_NAME)
            .trigger(DEFAULT_TRIGGER)
            .description(DEFAULT_DESCRIPTION);
        return recordType;
    }

    @Before
    public void initTest() {
        recordType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecordType() throws Exception {
        int databaseSizeBeforeCreate = recordTypeRepository.findAll().size();

        // Create the RecordType
        restRecordTypeMockMvc.perform(post("/api/record-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordType)))
            .andExpect(status().isCreated());

        // Validate the RecordType in the database
        List<RecordType> recordTypeList = recordTypeRepository.findAll();
        assertThat(recordTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RecordType testRecordType = recordTypeList.get(recordTypeList.size() - 1);
        assertThat(testRecordType.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testRecordType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecordType.getTrigger()).isEqualTo(DEFAULT_TRIGGER);
        assertThat(testRecordType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRecordTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordTypeRepository.findAll().size();

        // Create the RecordType with an existing ID
        recordType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordTypeMockMvc.perform(post("/api/record-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordType)))
            .andExpect(status().isBadRequest());

        // Validate the RecordType in the database
        List<RecordType> recordTypeList = recordTypeRepository.findAll();
        assertThat(recordTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordTypeRepository.findAll().size();
        // set the field null
        recordType.setName(null);

        // Create the RecordType, which fails.

        restRecordTypeMockMvc.perform(post("/api/record-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordType)))
            .andExpect(status().isBadRequest());

        List<RecordType> recordTypeList = recordTypeRepository.findAll();
        assertThat(recordTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecordTypes() throws Exception {
        // Initialize the database
        recordTypeRepository.saveAndFlush(recordType);

        // Get all the recordTypeList
        restRecordTypeMockMvc.perform(get("/api/record-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recordType.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].trigger").value(hasItem(DEFAULT_TRIGGER.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRecordType() throws Exception {
        // Initialize the database
        recordTypeRepository.saveAndFlush(recordType);

        // Get the recordType
        restRecordTypeMockMvc.perform(get("/api/record-types/{id}", recordType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recordType.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.trigger").value(DEFAULT_TRIGGER.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecordType() throws Exception {
        // Get the recordType
        restRecordTypeMockMvc.perform(get("/api/record-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecordType() throws Exception {
        // Initialize the database
        recordTypeRepository.saveAndFlush(recordType);

        int databaseSizeBeforeUpdate = recordTypeRepository.findAll().size();

        // Update the recordType
        RecordType updatedRecordType = recordTypeRepository.findById(recordType.getId()).get();
        // Disconnect from session so that the updates on updatedRecordType are not directly saved in db
        em.detach(updatedRecordType);
        updatedRecordType
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .trigger(UPDATED_TRIGGER)
            .description(UPDATED_DESCRIPTION);

        restRecordTypeMockMvc.perform(put("/api/record-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecordType)))
            .andExpect(status().isOk());

        // Validate the RecordType in the database
        List<RecordType> recordTypeList = recordTypeRepository.findAll();
        assertThat(recordTypeList).hasSize(databaseSizeBeforeUpdate);
        RecordType testRecordType = recordTypeList.get(recordTypeList.size() - 1);
        assertThat(testRecordType.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testRecordType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecordType.getTrigger()).isEqualTo(UPDATED_TRIGGER);
        assertThat(testRecordType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRecordType() throws Exception {
        int databaseSizeBeforeUpdate = recordTypeRepository.findAll().size();

        // Create the RecordType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordTypeMockMvc.perform(put("/api/record-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordType)))
            .andExpect(status().isBadRequest());

        // Validate the RecordType in the database
        List<RecordType> recordTypeList = recordTypeRepository.findAll();
        assertThat(recordTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecordType() throws Exception {
        // Initialize the database
        recordTypeRepository.saveAndFlush(recordType);

        int databaseSizeBeforeDelete = recordTypeRepository.findAll().size();

        // Get the recordType
        restRecordTypeMockMvc.perform(delete("/api/record-types/{id}", recordType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecordType> recordTypeList = recordTypeRepository.findAll();
        assertThat(recordTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecordType.class);
        RecordType recordType1 = new RecordType();
        recordType1.setId(1L);
        RecordType recordType2 = new RecordType();
        recordType2.setId(recordType1.getId());
        assertThat(recordType1).isEqualTo(recordType2);
        recordType2.setId(2L);
        assertThat(recordType1).isNotEqualTo(recordType2);
        recordType1.setId(null);
        assertThat(recordType1).isNotEqualTo(recordType2);
    }
}
