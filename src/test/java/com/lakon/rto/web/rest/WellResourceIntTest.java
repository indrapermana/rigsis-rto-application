package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.Well;
import com.lakon.rto.repository.WellRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.lakon.rto.web.rest.TestUtil.sameInstant;
import static com.lakon.rto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lakon.rto.domain.enumeration.Status;
/**
 * Test class for the WellResource REST controller.
 *
 * @see WellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class WellResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEGAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LICENSE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LICENSE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LICENSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GOVERMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GOVERMENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_API_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_API_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCK = "AAAAAAAAAA";
    private static final String UPDATED_BLOCK = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_OPERATOR = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR = "BBBBBBBBBB";

    private static final Double DEFAULT_OPERATOR_INTEREST = 1D;
    private static final Double UPDATED_OPERATOR_INTEREST = 2D;

    private static final String DEFAULT_OPERATOR_DIVISION = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR_DIVISION = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIME_ZONE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTION = "BBBBBBBBBB";

    private static final String DEFAULT_FLUID = "AAAAAAAAAA";
    private static final String UPDATED_FLUID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_TIME_SPUD = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME_SPUD = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_TIME_PA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME_PA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_HEAD_ELEVATION = 1D;
    private static final Double UPDATED_HEAD_ELEVATION = 2D;

    private static final Double DEFAULT_GROUND_ELEVATION = 1D;
    private static final Double UPDATED_GROUND_ELEVATION = 2D;

    private static final Double DEFAULT_WATER_DEPTH = 1D;
    private static final Double UPDATED_WATER_DEPTH = 2D;

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWellMockMvc;

    private Well well;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WellResource wellResource = new WellResource(wellRepository);
        this.restWellMockMvc = MockMvcBuilders.standaloneSetup(wellResource)
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
    public static Well createEntity(EntityManager em) {
        Well well = new Well()
            .name(DEFAULT_NAME)
            .legalName(DEFAULT_LEGAL_NAME)
            .licenseDate(DEFAULT_LICENSE_DATE)
            .licenseNumber(DEFAULT_LICENSE_NUMBER)
            .govermentNumber(DEFAULT_GOVERMENT_NUMBER)
            .apiNumber(DEFAULT_API_NUMBER)
            .country(DEFAULT_COUNTRY)
            .block(DEFAULT_BLOCK)
            .field(DEFAULT_FIELD)
            .district(DEFAULT_DISTRICT)
            .county(DEFAULT_COUNTY)
            .state(DEFAULT_STATE)
            .region(DEFAULT_REGION)
            .operator(DEFAULT_OPERATOR)
            .operatorInterest(DEFAULT_OPERATOR_INTEREST)
            .operatorDivision(DEFAULT_OPERATOR_DIVISION)
            .timeZone(DEFAULT_TIME_ZONE)
            .status(DEFAULT_STATUS)
            .purpose(DEFAULT_PURPOSE)
            .direction(DEFAULT_DIRECTION)
            .fluid(DEFAULT_FLUID)
            .dateTimeSpud(DEFAULT_DATE_TIME_SPUD)
            .dateTimePA(DEFAULT_DATE_TIME_PA)
            .headElevation(DEFAULT_HEAD_ELEVATION)
            .groundElevation(DEFAULT_GROUND_ELEVATION)
            .waterDepth(DEFAULT_WATER_DEPTH)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return well;
    }

    @Before
    public void initTest() {
        well = createEntity(em);
    }

    @Test
    @Transactional
    public void createWell() throws Exception {
        int databaseSizeBeforeCreate = wellRepository.findAll().size();

        // Create the Well
        restWellMockMvc.perform(post("/api/wells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(well)))
            .andExpect(status().isCreated());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeCreate + 1);
        Well testWell = wellList.get(wellList.size() - 1);
        assertThat(testWell.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWell.getLegalName()).isEqualTo(DEFAULT_LEGAL_NAME);
        assertThat(testWell.getLicenseDate()).isEqualTo(DEFAULT_LICENSE_DATE);
        assertThat(testWell.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testWell.getGovermentNumber()).isEqualTo(DEFAULT_GOVERMENT_NUMBER);
        assertThat(testWell.getApiNumber()).isEqualTo(DEFAULT_API_NUMBER);
        assertThat(testWell.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWell.getBlock()).isEqualTo(DEFAULT_BLOCK);
        assertThat(testWell.getField()).isEqualTo(DEFAULT_FIELD);
        assertThat(testWell.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testWell.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testWell.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWell.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testWell.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testWell.getOperatorInterest()).isEqualTo(DEFAULT_OPERATOR_INTEREST);
        assertThat(testWell.getOperatorDivision()).isEqualTo(DEFAULT_OPERATOR_DIVISION);
        assertThat(testWell.getTimeZone()).isEqualTo(DEFAULT_TIME_ZONE);
        assertThat(testWell.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWell.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testWell.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testWell.getFluid()).isEqualTo(DEFAULT_FLUID);
        assertThat(testWell.getDateTimeSpud()).isEqualTo(DEFAULT_DATE_TIME_SPUD);
        assertThat(testWell.getDateTimePA()).isEqualTo(DEFAULT_DATE_TIME_PA);
        assertThat(testWell.getHeadElevation()).isEqualTo(DEFAULT_HEAD_ELEVATION);
        assertThat(testWell.getGroundElevation()).isEqualTo(DEFAULT_GROUND_ELEVATION);
        assertThat(testWell.getWaterDepth()).isEqualTo(DEFAULT_WATER_DEPTH);
        assertThat(testWell.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testWell.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createWellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wellRepository.findAll().size();

        // Create the Well with an existing ID
        well.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWellMockMvc.perform(post("/api/wells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(well)))
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wellRepository.findAll().size();
        // set the field null
        well.setName(null);

        // Create the Well, which fails.

        restWellMockMvc.perform(post("/api/wells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(well)))
            .andExpect(status().isBadRequest());

        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWells() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        // Get all the wellList
        restWellMockMvc.perform(get("/api/wells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(well.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].legalName").value(hasItem(DEFAULT_LEGAL_NAME.toString())))
            .andExpect(jsonPath("$.[*].licenseDate").value(hasItem(sameInstant(DEFAULT_LICENSE_DATE))))
            .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].govermentNumber").value(hasItem(DEFAULT_GOVERMENT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].apiNumber").value(hasItem(DEFAULT_API_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].block").value(hasItem(DEFAULT_BLOCK.toString())))
            .andExpect(jsonPath("$.[*].field").value(hasItem(DEFAULT_FIELD.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].operatorInterest").value(hasItem(DEFAULT_OPERATOR_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].operatorDivision").value(hasItem(DEFAULT_OPERATOR_DIVISION.toString())))
            .andExpect(jsonPath("$.[*].timeZone").value(hasItem(DEFAULT_TIME_ZONE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION.toString())))
            .andExpect(jsonPath("$.[*].fluid").value(hasItem(DEFAULT_FLUID.toString())))
            .andExpect(jsonPath("$.[*].dateTimeSpud").value(hasItem(sameInstant(DEFAULT_DATE_TIME_SPUD))))
            .andExpect(jsonPath("$.[*].dateTimePA").value(hasItem(sameInstant(DEFAULT_DATE_TIME_PA))))
            .andExpect(jsonPath("$.[*].headElevation").value(hasItem(DEFAULT_HEAD_ELEVATION.doubleValue())))
            .andExpect(jsonPath("$.[*].groundElevation").value(hasItem(DEFAULT_GROUND_ELEVATION.doubleValue())))
            .andExpect(jsonPath("$.[*].waterDepth").value(hasItem(DEFAULT_WATER_DEPTH.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())));
    }
    
    @Test
    @Transactional
    public void getWell() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        // Get the well
        restWellMockMvc.perform(get("/api/wells/{id}", well.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(well.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.legalName").value(DEFAULT_LEGAL_NAME.toString()))
            .andExpect(jsonPath("$.licenseDate").value(sameInstant(DEFAULT_LICENSE_DATE)))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.toString()))
            .andExpect(jsonPath("$.govermentNumber").value(DEFAULT_GOVERMENT_NUMBER.toString()))
            .andExpect(jsonPath("$.apiNumber").value(DEFAULT_API_NUMBER.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.block").value(DEFAULT_BLOCK.toString()))
            .andExpect(jsonPath("$.field").value(DEFAULT_FIELD.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()))
            .andExpect(jsonPath("$.operatorInterest").value(DEFAULT_OPERATOR_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.operatorDivision").value(DEFAULT_OPERATOR_DIVISION.toString()))
            .andExpect(jsonPath("$.timeZone").value(DEFAULT_TIME_ZONE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION.toString()))
            .andExpect(jsonPath("$.fluid").value(DEFAULT_FLUID.toString()))
            .andExpect(jsonPath("$.dateTimeSpud").value(sameInstant(DEFAULT_DATE_TIME_SPUD)))
            .andExpect(jsonPath("$.dateTimePA").value(sameInstant(DEFAULT_DATE_TIME_PA)))
            .andExpect(jsonPath("$.headElevation").value(DEFAULT_HEAD_ELEVATION.doubleValue()))
            .andExpect(jsonPath("$.groundElevation").value(DEFAULT_GROUND_ELEVATION.doubleValue()))
            .andExpect(jsonPath("$.waterDepth").value(DEFAULT_WATER_DEPTH.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWell() throws Exception {
        // Get the well
        restWellMockMvc.perform(get("/api/wells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWell() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        int databaseSizeBeforeUpdate = wellRepository.findAll().size();

        // Update the well
        Well updatedWell = wellRepository.findById(well.getId()).get();
        // Disconnect from session so that the updates on updatedWell are not directly saved in db
        em.detach(updatedWell);
        updatedWell
            .name(UPDATED_NAME)
            .legalName(UPDATED_LEGAL_NAME)
            .licenseDate(UPDATED_LICENSE_DATE)
            .licenseNumber(UPDATED_LICENSE_NUMBER)
            .govermentNumber(UPDATED_GOVERMENT_NUMBER)
            .apiNumber(UPDATED_API_NUMBER)
            .country(UPDATED_COUNTRY)
            .block(UPDATED_BLOCK)
            .field(UPDATED_FIELD)
            .district(UPDATED_DISTRICT)
            .county(UPDATED_COUNTY)
            .state(UPDATED_STATE)
            .region(UPDATED_REGION)
            .operator(UPDATED_OPERATOR)
            .operatorInterest(UPDATED_OPERATOR_INTEREST)
            .operatorDivision(UPDATED_OPERATOR_DIVISION)
            .timeZone(UPDATED_TIME_ZONE)
            .status(UPDATED_STATUS)
            .purpose(UPDATED_PURPOSE)
            .direction(UPDATED_DIRECTION)
            .fluid(UPDATED_FLUID)
            .dateTimeSpud(UPDATED_DATE_TIME_SPUD)
            .dateTimePA(UPDATED_DATE_TIME_PA)
            .headElevation(UPDATED_HEAD_ELEVATION)
            .groundElevation(UPDATED_GROUND_ELEVATION)
            .waterDepth(UPDATED_WATER_DEPTH)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restWellMockMvc.perform(put("/api/wells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWell)))
            .andExpect(status().isOk());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
        Well testWell = wellList.get(wellList.size() - 1);
        assertThat(testWell.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWell.getLegalName()).isEqualTo(UPDATED_LEGAL_NAME);
        assertThat(testWell.getLicenseDate()).isEqualTo(UPDATED_LICENSE_DATE);
        assertThat(testWell.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testWell.getGovermentNumber()).isEqualTo(UPDATED_GOVERMENT_NUMBER);
        assertThat(testWell.getApiNumber()).isEqualTo(UPDATED_API_NUMBER);
        assertThat(testWell.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWell.getBlock()).isEqualTo(UPDATED_BLOCK);
        assertThat(testWell.getField()).isEqualTo(UPDATED_FIELD);
        assertThat(testWell.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testWell.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testWell.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWell.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testWell.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testWell.getOperatorInterest()).isEqualTo(UPDATED_OPERATOR_INTEREST);
        assertThat(testWell.getOperatorDivision()).isEqualTo(UPDATED_OPERATOR_DIVISION);
        assertThat(testWell.getTimeZone()).isEqualTo(UPDATED_TIME_ZONE);
        assertThat(testWell.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWell.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testWell.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testWell.getFluid()).isEqualTo(UPDATED_FLUID);
        assertThat(testWell.getDateTimeSpud()).isEqualTo(UPDATED_DATE_TIME_SPUD);
        assertThat(testWell.getDateTimePA()).isEqualTo(UPDATED_DATE_TIME_PA);
        assertThat(testWell.getHeadElevation()).isEqualTo(UPDATED_HEAD_ELEVATION);
        assertThat(testWell.getGroundElevation()).isEqualTo(UPDATED_GROUND_ELEVATION);
        assertThat(testWell.getWaterDepth()).isEqualTo(UPDATED_WATER_DEPTH);
        assertThat(testWell.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testWell.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingWell() throws Exception {
        int databaseSizeBeforeUpdate = wellRepository.findAll().size();

        // Create the Well

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellMockMvc.perform(put("/api/wells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(well)))
            .andExpect(status().isBadRequest());

        // Validate the Well in the database
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWell() throws Exception {
        // Initialize the database
        wellRepository.saveAndFlush(well);

        int databaseSizeBeforeDelete = wellRepository.findAll().size();

        // Get the well
        restWellMockMvc.perform(delete("/api/wells/{id}", well.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Well> wellList = wellRepository.findAll();
        assertThat(wellList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Well.class);
        Well well1 = new Well();
        well1.setId(1L);
        Well well2 = new Well();
        well2.setId(well1.getId());
        assertThat(well1).isEqualTo(well2);
        well2.setId(2L);
        assertThat(well1).isNotEqualTo(well2);
        well1.setId(null);
        assertThat(well1).isNotEqualTo(well2);
    }
}
