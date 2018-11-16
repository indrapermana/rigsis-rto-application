package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.UnitType;
import com.lakon.rto.repository.UnitTypeRepository;
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
 * Test class for the UnitTypeResource REST controller.
 *
 * @see UnitTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class UnitTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private UnitTypeRepository unitTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnitTypeMockMvc;

    private UnitType unitType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitTypeResource unitTypeResource = new UnitTypeResource(unitTypeRepository);
        this.restUnitTypeMockMvc = MockMvcBuilders.standaloneSetup(unitTypeResource)
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
    public static UnitType createEntity(EntityManager em) {
        UnitType unitType = new UnitType()
            .type(DEFAULT_TYPE);
        return unitType;
    }

    @Before
    public void initTest() {
        unitType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitType() throws Exception {
        int databaseSizeBeforeCreate = unitTypeRepository.findAll().size();

        // Create the UnitType
        restUnitTypeMockMvc.perform(post("/api/unit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitType)))
            .andExpect(status().isCreated());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UnitType testUnitType = unitTypeList.get(unitTypeList.size() - 1);
        assertThat(testUnitType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createUnitTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitTypeRepository.findAll().size();

        // Create the UnitType with an existing ID
        unitType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitTypeMockMvc.perform(post("/api/unit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitType)))
            .andExpect(status().isBadRequest());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitTypeRepository.findAll().size();
        // set the field null
        unitType.setType(null);

        // Create the UnitType, which fails.

        restUnitTypeMockMvc.perform(post("/api/unit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitType)))
            .andExpect(status().isBadRequest());

        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnitTypes() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        // Get all the unitTypeList
        restUnitTypeMockMvc.perform(get("/api/unit-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getUnitType() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        // Get the unitType
        restUnitTypeMockMvc.perform(get("/api/unit-types/{id}", unitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unitType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnitType() throws Exception {
        // Get the unitType
        restUnitTypeMockMvc.perform(get("/api/unit-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitType() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        int databaseSizeBeforeUpdate = unitTypeRepository.findAll().size();

        // Update the unitType
        UnitType updatedUnitType = unitTypeRepository.findById(unitType.getId()).get();
        // Disconnect from session so that the updates on updatedUnitType are not directly saved in db
        em.detach(updatedUnitType);
        updatedUnitType
            .type(UPDATED_TYPE);

        restUnitTypeMockMvc.perform(put("/api/unit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnitType)))
            .andExpect(status().isOk());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeUpdate);
        UnitType testUnitType = unitTypeList.get(unitTypeList.size() - 1);
        assertThat(testUnitType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitType() throws Exception {
        int databaseSizeBeforeUpdate = unitTypeRepository.findAll().size();

        // Create the UnitType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitTypeMockMvc.perform(put("/api/unit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitType)))
            .andExpect(status().isBadRequest());

        // Validate the UnitType in the database
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitType() throws Exception {
        // Initialize the database
        unitTypeRepository.saveAndFlush(unitType);

        int databaseSizeBeforeDelete = unitTypeRepository.findAll().size();

        // Get the unitType
        restUnitTypeMockMvc.perform(delete("/api/unit-types/{id}", unitType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnitType> unitTypeList = unitTypeRepository.findAll();
        assertThat(unitTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitType.class);
        UnitType unitType1 = new UnitType();
        unitType1.setId(1L);
        UnitType unitType2 = new UnitType();
        unitType2.setId(unitType1.getId());
        assertThat(unitType1).isEqualTo(unitType2);
        unitType2.setId(2L);
        assertThat(unitType1).isNotEqualTo(unitType2);
        unitType1.setId(null);
        assertThat(unitType1).isNotEqualTo(unitType2);
    }
}
