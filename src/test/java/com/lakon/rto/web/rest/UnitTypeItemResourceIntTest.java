package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.UnitTypeItem;
import com.lakon.rto.repository.UnitTypeItemRepository;
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
 * Test class for the UnitTypeItemResource REST controller.
 *
 * @see UnitTypeItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class UnitTypeItemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONVERSION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONVERSION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final Double DEFAULT_FACTOR = 1D;
    private static final Double UPDATED_FACTOR = 2D;

    @Autowired
    private UnitTypeItemRepository unitTypeItemRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnitTypeItemMockMvc;

    private UnitTypeItem unitTypeItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitTypeItemResource unitTypeItemResource = new UnitTypeItemResource(unitTypeItemRepository);
        this.restUnitTypeItemMockMvc = MockMvcBuilders.standaloneSetup(unitTypeItemResource)
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
    public static UnitTypeItem createEntity(EntityManager em) {
        UnitTypeItem unitTypeItem = new UnitTypeItem()
            .name(DEFAULT_NAME)
            .conversionType(DEFAULT_CONVERSION_TYPE)
            .symbol(DEFAULT_SYMBOL)
            .factor(DEFAULT_FACTOR);
        return unitTypeItem;
    }

    @Before
    public void initTest() {
        unitTypeItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitTypeItem() throws Exception {
        int databaseSizeBeforeCreate = unitTypeItemRepository.findAll().size();

        // Create the UnitTypeItem
        restUnitTypeItemMockMvc.perform(post("/api/unit-type-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeItem)))
            .andExpect(status().isCreated());

        // Validate the UnitTypeItem in the database
        List<UnitTypeItem> unitTypeItemList = unitTypeItemRepository.findAll();
        assertThat(unitTypeItemList).hasSize(databaseSizeBeforeCreate + 1);
        UnitTypeItem testUnitTypeItem = unitTypeItemList.get(unitTypeItemList.size() - 1);
        assertThat(testUnitTypeItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUnitTypeItem.getConversionType()).isEqualTo(DEFAULT_CONVERSION_TYPE);
        assertThat(testUnitTypeItem.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testUnitTypeItem.getFactor()).isEqualTo(DEFAULT_FACTOR);
    }

    @Test
    @Transactional
    public void createUnitTypeItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitTypeItemRepository.findAll().size();

        // Create the UnitTypeItem with an existing ID
        unitTypeItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitTypeItemMockMvc.perform(post("/api/unit-type-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeItem)))
            .andExpect(status().isBadRequest());

        // Validate the UnitTypeItem in the database
        List<UnitTypeItem> unitTypeItemList = unitTypeItemRepository.findAll();
        assertThat(unitTypeItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitTypeItemRepository.findAll().size();
        // set the field null
        unitTypeItem.setName(null);

        // Create the UnitTypeItem, which fails.

        restUnitTypeItemMockMvc.perform(post("/api/unit-type-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeItem)))
            .andExpect(status().isBadRequest());

        List<UnitTypeItem> unitTypeItemList = unitTypeItemRepository.findAll();
        assertThat(unitTypeItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnitTypeItems() throws Exception {
        // Initialize the database
        unitTypeItemRepository.saveAndFlush(unitTypeItem);

        // Get all the unitTypeItemList
        restUnitTypeItemMockMvc.perform(get("/api/unit-type-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitTypeItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].conversionType").value(hasItem(DEFAULT_CONVERSION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].factor").value(hasItem(DEFAULT_FACTOR.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getUnitTypeItem() throws Exception {
        // Initialize the database
        unitTypeItemRepository.saveAndFlush(unitTypeItem);

        // Get the unitTypeItem
        restUnitTypeItemMockMvc.perform(get("/api/unit-type-items/{id}", unitTypeItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unitTypeItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.conversionType").value(DEFAULT_CONVERSION_TYPE.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.factor").value(DEFAULT_FACTOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUnitTypeItem() throws Exception {
        // Get the unitTypeItem
        restUnitTypeItemMockMvc.perform(get("/api/unit-type-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitTypeItem() throws Exception {
        // Initialize the database
        unitTypeItemRepository.saveAndFlush(unitTypeItem);

        int databaseSizeBeforeUpdate = unitTypeItemRepository.findAll().size();

        // Update the unitTypeItem
        UnitTypeItem updatedUnitTypeItem = unitTypeItemRepository.findById(unitTypeItem.getId()).get();
        // Disconnect from session so that the updates on updatedUnitTypeItem are not directly saved in db
        em.detach(updatedUnitTypeItem);
        updatedUnitTypeItem
            .name(UPDATED_NAME)
            .conversionType(UPDATED_CONVERSION_TYPE)
            .symbol(UPDATED_SYMBOL)
            .factor(UPDATED_FACTOR);

        restUnitTypeItemMockMvc.perform(put("/api/unit-type-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnitTypeItem)))
            .andExpect(status().isOk());

        // Validate the UnitTypeItem in the database
        List<UnitTypeItem> unitTypeItemList = unitTypeItemRepository.findAll();
        assertThat(unitTypeItemList).hasSize(databaseSizeBeforeUpdate);
        UnitTypeItem testUnitTypeItem = unitTypeItemList.get(unitTypeItemList.size() - 1);
        assertThat(testUnitTypeItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUnitTypeItem.getConversionType()).isEqualTo(UPDATED_CONVERSION_TYPE);
        assertThat(testUnitTypeItem.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testUnitTypeItem.getFactor()).isEqualTo(UPDATED_FACTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitTypeItem() throws Exception {
        int databaseSizeBeforeUpdate = unitTypeItemRepository.findAll().size();

        // Create the UnitTypeItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitTypeItemMockMvc.perform(put("/api/unit-type-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitTypeItem)))
            .andExpect(status().isBadRequest());

        // Validate the UnitTypeItem in the database
        List<UnitTypeItem> unitTypeItemList = unitTypeItemRepository.findAll();
        assertThat(unitTypeItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitTypeItem() throws Exception {
        // Initialize the database
        unitTypeItemRepository.saveAndFlush(unitTypeItem);

        int databaseSizeBeforeDelete = unitTypeItemRepository.findAll().size();

        // Get the unitTypeItem
        restUnitTypeItemMockMvc.perform(delete("/api/unit-type-items/{id}", unitTypeItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnitTypeItem> unitTypeItemList = unitTypeItemRepository.findAll();
        assertThat(unitTypeItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitTypeItem.class);
        UnitTypeItem unitTypeItem1 = new UnitTypeItem();
        unitTypeItem1.setId(1L);
        UnitTypeItem unitTypeItem2 = new UnitTypeItem();
        unitTypeItem2.setId(unitTypeItem1.getId());
        assertThat(unitTypeItem1).isEqualTo(unitTypeItem2);
        unitTypeItem2.setId(2L);
        assertThat(unitTypeItem1).isNotEqualTo(unitTypeItem2);
        unitTypeItem1.setId(null);
        assertThat(unitTypeItem1).isNotEqualTo(unitTypeItem2);
    }
}
