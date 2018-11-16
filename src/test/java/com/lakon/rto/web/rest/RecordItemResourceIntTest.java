package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.RecordItem;
import com.lakon.rto.repository.RecordItemRepository;
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
 * Test class for the RecordItemResource REST controller.
 *
 * @see RecordItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class RecordItemResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MNEMONIC = "AAAAAAAAAA";
    private static final String UPDATED_MNEMONIC = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIAL_CASE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIAL_CASE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NULL_VALUE = 1;
    private static final Integer UPDATED_NULL_VALUE = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RecordItemRepository recordItemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecordItemMockMvc;

    private RecordItem recordItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecordItemResource recordItemResource = new RecordItemResource(recordItemRepository);
        this.restRecordItemMockMvc = MockMvcBuilders.standaloneSetup(recordItemResource)
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
    public static RecordItem createEntity(EntityManager em) {
        RecordItem recordItem = new RecordItem()
            .number(DEFAULT_NUMBER)
            .name(DEFAULT_NAME)
            .mnemonic(DEFAULT_MNEMONIC)
            .specialCase(DEFAULT_SPECIAL_CASE)
            .unitType(DEFAULT_UNIT_TYPE)
            .unit(DEFAULT_UNIT)
            .dataType(DEFAULT_DATA_TYPE)
            .nullValue(DEFAULT_NULL_VALUE)
            .description(DEFAULT_DESCRIPTION);
        return recordItem;
    }

    @Before
    public void initTest() {
        recordItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecordItem() throws Exception {
        int databaseSizeBeforeCreate = recordItemRepository.findAll().size();

        // Create the RecordItem
        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isCreated());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeCreate + 1);
        RecordItem testRecordItem = recordItemList.get(recordItemList.size() - 1);
        assertThat(testRecordItem.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testRecordItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecordItem.getMnemonic()).isEqualTo(DEFAULT_MNEMONIC);
        assertThat(testRecordItem.getSpecialCase()).isEqualTo(DEFAULT_SPECIAL_CASE);
        assertThat(testRecordItem.getUnitType()).isEqualTo(DEFAULT_UNIT_TYPE);
        assertThat(testRecordItem.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testRecordItem.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testRecordItem.getNullValue()).isEqualTo(DEFAULT_NULL_VALUE);
        assertThat(testRecordItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRecordItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordItemRepository.findAll().size();

        // Create the RecordItem with an existing ID
        recordItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setName(null);

        // Create the RecordItem, which fails.

        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMnemonicIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setMnemonic(null);

        // Create the RecordItem, which fails.

        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setUnitType(null);

        // Create the RecordItem, which fails.

        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setUnit(null);

        // Create the RecordItem, which fails.

        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordItemRepository.findAll().size();
        // set the field null
        recordItem.setDataType(null);

        // Create the RecordItem, which fails.

        restRecordItemMockMvc.perform(post("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecordItems() throws Exception {
        // Initialize the database
        recordItemRepository.saveAndFlush(recordItem);

        // Get all the recordItemList
        restRecordItemMockMvc.perform(get("/api/record-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recordItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].mnemonic").value(hasItem(DEFAULT_MNEMONIC.toString())))
            .andExpect(jsonPath("$.[*].specialCase").value(hasItem(DEFAULT_SPECIAL_CASE.toString())))
            .andExpect(jsonPath("$.[*].unitType").value(hasItem(DEFAULT_UNIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nullValue").value(hasItem(DEFAULT_NULL_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRecordItem() throws Exception {
        // Initialize the database
        recordItemRepository.saveAndFlush(recordItem);

        // Get the recordItem
        restRecordItemMockMvc.perform(get("/api/record-items/{id}", recordItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recordItem.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mnemonic").value(DEFAULT_MNEMONIC.toString()))
            .andExpect(jsonPath("$.specialCase").value(DEFAULT_SPECIAL_CASE.toString()))
            .andExpect(jsonPath("$.unitType").value(DEFAULT_UNIT_TYPE.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.nullValue").value(DEFAULT_NULL_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecordItem() throws Exception {
        // Get the recordItem
        restRecordItemMockMvc.perform(get("/api/record-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecordItem() throws Exception {
        // Initialize the database
        recordItemRepository.saveAndFlush(recordItem);

        int databaseSizeBeforeUpdate = recordItemRepository.findAll().size();

        // Update the recordItem
        RecordItem updatedRecordItem = recordItemRepository.findById(recordItem.getId()).get();
        // Disconnect from session so that the updates on updatedRecordItem are not directly saved in db
        em.detach(updatedRecordItem);
        updatedRecordItem
            .number(UPDATED_NUMBER)
            .name(UPDATED_NAME)
            .mnemonic(UPDATED_MNEMONIC)
            .specialCase(UPDATED_SPECIAL_CASE)
            .unitType(UPDATED_UNIT_TYPE)
            .unit(UPDATED_UNIT)
            .dataType(UPDATED_DATA_TYPE)
            .nullValue(UPDATED_NULL_VALUE)
            .description(UPDATED_DESCRIPTION);

        restRecordItemMockMvc.perform(put("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecordItem)))
            .andExpect(status().isOk());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeUpdate);
        RecordItem testRecordItem = recordItemList.get(recordItemList.size() - 1);
        assertThat(testRecordItem.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testRecordItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecordItem.getMnemonic()).isEqualTo(UPDATED_MNEMONIC);
        assertThat(testRecordItem.getSpecialCase()).isEqualTo(UPDATED_SPECIAL_CASE);
        assertThat(testRecordItem.getUnitType()).isEqualTo(UPDATED_UNIT_TYPE);
        assertThat(testRecordItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testRecordItem.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testRecordItem.getNullValue()).isEqualTo(UPDATED_NULL_VALUE);
        assertThat(testRecordItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRecordItem() throws Exception {
        int databaseSizeBeforeUpdate = recordItemRepository.findAll().size();

        // Create the RecordItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordItemMockMvc.perform(put("/api/record-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recordItem)))
            .andExpect(status().isBadRequest());

        // Validate the RecordItem in the database
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecordItem() throws Exception {
        // Initialize the database
        recordItemRepository.saveAndFlush(recordItem);

        int databaseSizeBeforeDelete = recordItemRepository.findAll().size();

        // Get the recordItem
        restRecordItemMockMvc.perform(delete("/api/record-items/{id}", recordItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecordItem> recordItemList = recordItemRepository.findAll();
        assertThat(recordItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecordItem.class);
        RecordItem recordItem1 = new RecordItem();
        recordItem1.setId(1L);
        RecordItem recordItem2 = new RecordItem();
        recordItem2.setId(recordItem1.getId());
        assertThat(recordItem1).isEqualTo(recordItem2);
        recordItem2.setId(2L);
        assertThat(recordItem1).isNotEqualTo(recordItem2);
        recordItem1.setId(null);
        assertThat(recordItem1).isNotEqualTo(recordItem2);
    }
}
