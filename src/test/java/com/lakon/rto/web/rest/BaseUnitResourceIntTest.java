package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.BaseUnit;
import com.lakon.rto.repository.BaseUnitRepository;
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
 * Test class for the BaseUnitResource REST controller.
 *
 * @see BaseUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class BaseUnitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    @Autowired
    private BaseUnitRepository baseUnitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBaseUnitMockMvc;

    private BaseUnit baseUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BaseUnitResource baseUnitResource = new BaseUnitResource(baseUnitRepository);
        this.restBaseUnitMockMvc = MockMvcBuilders.standaloneSetup(baseUnitResource)
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
    public static BaseUnit createEntity(EntityManager em) {
        BaseUnit baseUnit = new BaseUnit()
            .name(DEFAULT_NAME)
            .symbol(DEFAULT_SYMBOL)
            .description(DEFAULT_DESCRIPTION)
            .origin(DEFAULT_ORIGIN);
        return baseUnit;
    }

    @Before
    public void initTest() {
        baseUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createBaseUnit() throws Exception {
        int databaseSizeBeforeCreate = baseUnitRepository.findAll().size();

        // Create the BaseUnit
        restBaseUnitMockMvc.perform(post("/api/base-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseUnit)))
            .andExpect(status().isCreated());

        // Validate the BaseUnit in the database
        List<BaseUnit> baseUnitList = baseUnitRepository.findAll();
        assertThat(baseUnitList).hasSize(databaseSizeBeforeCreate + 1);
        BaseUnit testBaseUnit = baseUnitList.get(baseUnitList.size() - 1);
        assertThat(testBaseUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBaseUnit.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testBaseUnit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBaseUnit.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
    }

    @Test
    @Transactional
    public void createBaseUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baseUnitRepository.findAll().size();

        // Create the BaseUnit with an existing ID
        baseUnit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseUnitMockMvc.perform(post("/api/base-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseUnit)))
            .andExpect(status().isBadRequest());

        // Validate the BaseUnit in the database
        List<BaseUnit> baseUnitList = baseUnitRepository.findAll();
        assertThat(baseUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = baseUnitRepository.findAll().size();
        // set the field null
        baseUnit.setName(null);

        // Create the BaseUnit, which fails.

        restBaseUnitMockMvc.perform(post("/api/base-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseUnit)))
            .andExpect(status().isBadRequest());

        List<BaseUnit> baseUnitList = baseUnitRepository.findAll();
        assertThat(baseUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBaseUnits() throws Exception {
        // Initialize the database
        baseUnitRepository.saveAndFlush(baseUnit);

        // Get all the baseUnitList
        restBaseUnitMockMvc.perform(get("/api/base-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN.toString())));
    }
    
    @Test
    @Transactional
    public void getBaseUnit() throws Exception {
        // Initialize the database
        baseUnitRepository.saveAndFlush(baseUnit);

        // Get the baseUnit
        restBaseUnitMockMvc.perform(get("/api/base-units/{id}", baseUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(baseUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBaseUnit() throws Exception {
        // Get the baseUnit
        restBaseUnitMockMvc.perform(get("/api/base-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBaseUnit() throws Exception {
        // Initialize the database
        baseUnitRepository.saveAndFlush(baseUnit);

        int databaseSizeBeforeUpdate = baseUnitRepository.findAll().size();

        // Update the baseUnit
        BaseUnit updatedBaseUnit = baseUnitRepository.findById(baseUnit.getId()).get();
        // Disconnect from session so that the updates on updatedBaseUnit are not directly saved in db
        em.detach(updatedBaseUnit);
        updatedBaseUnit
            .name(UPDATED_NAME)
            .symbol(UPDATED_SYMBOL)
            .description(UPDATED_DESCRIPTION)
            .origin(UPDATED_ORIGIN);

        restBaseUnitMockMvc.perform(put("/api/base-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBaseUnit)))
            .andExpect(status().isOk());

        // Validate the BaseUnit in the database
        List<BaseUnit> baseUnitList = baseUnitRepository.findAll();
        assertThat(baseUnitList).hasSize(databaseSizeBeforeUpdate);
        BaseUnit testBaseUnit = baseUnitList.get(baseUnitList.size() - 1);
        assertThat(testBaseUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBaseUnit.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testBaseUnit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBaseUnit.getOrigin()).isEqualTo(UPDATED_ORIGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingBaseUnit() throws Exception {
        int databaseSizeBeforeUpdate = baseUnitRepository.findAll().size();

        // Create the BaseUnit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseUnitMockMvc.perform(put("/api/base-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(baseUnit)))
            .andExpect(status().isBadRequest());

        // Validate the BaseUnit in the database
        List<BaseUnit> baseUnitList = baseUnitRepository.findAll();
        assertThat(baseUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBaseUnit() throws Exception {
        // Initialize the database
        baseUnitRepository.saveAndFlush(baseUnit);

        int databaseSizeBeforeDelete = baseUnitRepository.findAll().size();

        // Get the baseUnit
        restBaseUnitMockMvc.perform(delete("/api/base-units/{id}", baseUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BaseUnit> baseUnitList = baseUnitRepository.findAll();
        assertThat(baseUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaseUnit.class);
        BaseUnit baseUnit1 = new BaseUnit();
        baseUnit1.setId(1L);
        BaseUnit baseUnit2 = new BaseUnit();
        baseUnit2.setId(baseUnit1.getId());
        assertThat(baseUnit1).isEqualTo(baseUnit2);
        baseUnit2.setId(2L);
        assertThat(baseUnit1).isNotEqualTo(baseUnit2);
        baseUnit1.setId(null);
        assertThat(baseUnit1).isNotEqualTo(baseUnit2);
    }
}
