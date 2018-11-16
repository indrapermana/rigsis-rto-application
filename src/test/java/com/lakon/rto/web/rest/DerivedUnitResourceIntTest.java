package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.DerivedUnit;
import com.lakon.rto.repository.DerivedUnitRepository;
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
 * Test class for the DerivedUnitResource REST controller.
 *
 * @see DerivedUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class DerivedUnitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_CONVERSION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONVERSION_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_FACTOR = 1D;
    private static final Double UPDATED_FACTOR = 2D;

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    @Autowired
    private DerivedUnitRepository derivedUnitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDerivedUnitMockMvc;

    private DerivedUnit derivedUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DerivedUnitResource derivedUnitResource = new DerivedUnitResource(derivedUnitRepository);
        this.restDerivedUnitMockMvc = MockMvcBuilders.standaloneSetup(derivedUnitResource)
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
    public static DerivedUnit createEntity(EntityManager em) {
        DerivedUnit derivedUnit = new DerivedUnit()
            .name(DEFAULT_NAME)
            .symbol(DEFAULT_SYMBOL)
            .conversionType(DEFAULT_CONVERSION_TYPE)
            .factor(DEFAULT_FACTOR)
            .origin(DEFAULT_ORIGIN);
        return derivedUnit;
    }

    @Before
    public void initTest() {
        derivedUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createDerivedUnit() throws Exception {
        int databaseSizeBeforeCreate = derivedUnitRepository.findAll().size();

        // Create the DerivedUnit
        restDerivedUnitMockMvc.perform(post("/api/derived-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedUnit)))
            .andExpect(status().isCreated());

        // Validate the DerivedUnit in the database
        List<DerivedUnit> derivedUnitList = derivedUnitRepository.findAll();
        assertThat(derivedUnitList).hasSize(databaseSizeBeforeCreate + 1);
        DerivedUnit testDerivedUnit = derivedUnitList.get(derivedUnitList.size() - 1);
        assertThat(testDerivedUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDerivedUnit.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testDerivedUnit.getConversionType()).isEqualTo(DEFAULT_CONVERSION_TYPE);
        assertThat(testDerivedUnit.getFactor()).isEqualTo(DEFAULT_FACTOR);
        assertThat(testDerivedUnit.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
    }

    @Test
    @Transactional
    public void createDerivedUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = derivedUnitRepository.findAll().size();

        // Create the DerivedUnit with an existing ID
        derivedUnit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDerivedUnitMockMvc.perform(post("/api/derived-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedUnit)))
            .andExpect(status().isBadRequest());

        // Validate the DerivedUnit in the database
        List<DerivedUnit> derivedUnitList = derivedUnitRepository.findAll();
        assertThat(derivedUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = derivedUnitRepository.findAll().size();
        // set the field null
        derivedUnit.setName(null);

        // Create the DerivedUnit, which fails.

        restDerivedUnitMockMvc.perform(post("/api/derived-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedUnit)))
            .andExpect(status().isBadRequest());

        List<DerivedUnit> derivedUnitList = derivedUnitRepository.findAll();
        assertThat(derivedUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDerivedUnits() throws Exception {
        // Initialize the database
        derivedUnitRepository.saveAndFlush(derivedUnit);

        // Get all the derivedUnitList
        restDerivedUnitMockMvc.perform(get("/api/derived-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(derivedUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].conversionType").value(hasItem(DEFAULT_CONVERSION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].factor").value(hasItem(DEFAULT_FACTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())));
    }
    
    @Test
    @Transactional
    public void getDerivedUnit() throws Exception {
        // Initialize the database
        derivedUnitRepository.saveAndFlush(derivedUnit);

        // Get the derivedUnit
        restDerivedUnitMockMvc.perform(get("/api/derived-units/{id}", derivedUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(derivedUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.conversionType").value(DEFAULT_CONVERSION_TYPE.toString()))
            .andExpect(jsonPath("$.factor").value(DEFAULT_FACTOR.doubleValue()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDerivedUnit() throws Exception {
        // Get the derivedUnit
        restDerivedUnitMockMvc.perform(get("/api/derived-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDerivedUnit() throws Exception {
        // Initialize the database
        derivedUnitRepository.saveAndFlush(derivedUnit);

        int databaseSizeBeforeUpdate = derivedUnitRepository.findAll().size();

        // Update the derivedUnit
        DerivedUnit updatedDerivedUnit = derivedUnitRepository.findById(derivedUnit.getId()).get();
        // Disconnect from session so that the updates on updatedDerivedUnit are not directly saved in db
        em.detach(updatedDerivedUnit);
        updatedDerivedUnit
            .name(UPDATED_NAME)
            .symbol(UPDATED_SYMBOL)
            .conversionType(UPDATED_CONVERSION_TYPE)
            .factor(UPDATED_FACTOR)
            .origin(UPDATED_ORIGIN);

        restDerivedUnitMockMvc.perform(put("/api/derived-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDerivedUnit)))
            .andExpect(status().isOk());

        // Validate the DerivedUnit in the database
        List<DerivedUnit> derivedUnitList = derivedUnitRepository.findAll();
        assertThat(derivedUnitList).hasSize(databaseSizeBeforeUpdate);
        DerivedUnit testDerivedUnit = derivedUnitList.get(derivedUnitList.size() - 1);
        assertThat(testDerivedUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDerivedUnit.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testDerivedUnit.getConversionType()).isEqualTo(UPDATED_CONVERSION_TYPE);
        assertThat(testDerivedUnit.getFactor()).isEqualTo(UPDATED_FACTOR);
        assertThat(testDerivedUnit.getOrigin()).isEqualTo(UPDATED_ORIGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingDerivedUnit() throws Exception {
        int databaseSizeBeforeUpdate = derivedUnitRepository.findAll().size();

        // Create the DerivedUnit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDerivedUnitMockMvc.perform(put("/api/derived-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(derivedUnit)))
            .andExpect(status().isBadRequest());

        // Validate the DerivedUnit in the database
        List<DerivedUnit> derivedUnitList = derivedUnitRepository.findAll();
        assertThat(derivedUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDerivedUnit() throws Exception {
        // Initialize the database
        derivedUnitRepository.saveAndFlush(derivedUnit);

        int databaseSizeBeforeDelete = derivedUnitRepository.findAll().size();

        // Get the derivedUnit
        restDerivedUnitMockMvc.perform(delete("/api/derived-units/{id}", derivedUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DerivedUnit> derivedUnitList = derivedUnitRepository.findAll();
        assertThat(derivedUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DerivedUnit.class);
        DerivedUnit derivedUnit1 = new DerivedUnit();
        derivedUnit1.setId(1L);
        DerivedUnit derivedUnit2 = new DerivedUnit();
        derivedUnit2.setId(derivedUnit1.getId());
        assertThat(derivedUnit1).isEqualTo(derivedUnit2);
        derivedUnit2.setId(2L);
        assertThat(derivedUnit1).isNotEqualTo(derivedUnit2);
        derivedUnit1.setId(null);
        assertThat(derivedUnit1).isNotEqualTo(derivedUnit2);
    }
}
