package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.Rig;
import com.lakon.rto.repository.RigRepository;
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

import com.lakon.rto.domain.enumeration.BooleanType;
/**
 * Test class for the RigResource REST controller.
 *
 * @see RigResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class RigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BooleanType DEFAULT_OFFSHORE = BooleanType.Yes;
    private static final BooleanType UPDATED_OFFSHORE = BooleanType.No;

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RIG_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_RIG_CLASS = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_SERVICE = 1;
    private static final Integer UPDATED_YEAR_SERVICE = 2;

    private static final String DEFAULT_APPROVALS = "AAAAAAAAAA";
    private static final String UPDATED_APPROVALS = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final Double DEFAULT_DRILL_DEPTH = 1D;
    private static final Double UPDATED_DRILL_DEPTH = 2D;

    private static final Double DEFAULT_WATER_DEPTH = 1D;
    private static final Double UPDATED_WATER_DEPTH = 2D;

    private static final Double DEFAULT_AIR_GAP = 1D;
    private static final Double UPDATED_AIR_GAP = 2D;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RigRepository rigRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRigMockMvc;

    private Rig rig;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RigResource rigResource = new RigResource(rigRepository);
        this.restRigMockMvc = MockMvcBuilders.standaloneSetup(rigResource)
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
    public static Rig createEntity(EntityManager em) {
        Rig rig = new Rig()
            .name(DEFAULT_NAME)
            .offshore(DEFAULT_OFFSHORE)
            .owner(DEFAULT_OWNER)
            .type(DEFAULT_TYPE)
            .rigClass(DEFAULT_RIG_CLASS)
            .manufacturer(DEFAULT_MANUFACTURER)
            .yearService(DEFAULT_YEAR_SERVICE)
            .approvals(DEFAULT_APPROVALS)
            .registration(DEFAULT_REGISTRATION)
            .contact(DEFAULT_CONTACT)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .drillDepth(DEFAULT_DRILL_DEPTH)
            .waterDepth(DEFAULT_WATER_DEPTH)
            .airGap(DEFAULT_AIR_GAP)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return rig;
    }

    @Before
    public void initTest() {
        rig = createEntity(em);
    }

    @Test
    @Transactional
    public void createRig() throws Exception {
        int databaseSizeBeforeCreate = rigRepository.findAll().size();

        // Create the Rig
        restRigMockMvc.perform(post("/api/rigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rig)))
            .andExpect(status().isCreated());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeCreate + 1);
        Rig testRig = rigList.get(rigList.size() - 1);
        assertThat(testRig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRig.getOffshore()).isEqualTo(DEFAULT_OFFSHORE);
        assertThat(testRig.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testRig.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRig.getRigClass()).isEqualTo(DEFAULT_RIG_CLASS);
        assertThat(testRig.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testRig.getYearService()).isEqualTo(DEFAULT_YEAR_SERVICE);
        assertThat(testRig.getApprovals()).isEqualTo(DEFAULT_APPROVALS);
        assertThat(testRig.getRegistration()).isEqualTo(DEFAULT_REGISTRATION);
        assertThat(testRig.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testRig.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testRig.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testRig.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testRig.getDrillDepth()).isEqualTo(DEFAULT_DRILL_DEPTH);
        assertThat(testRig.getWaterDepth()).isEqualTo(DEFAULT_WATER_DEPTH);
        assertThat(testRig.getAirGap()).isEqualTo(DEFAULT_AIR_GAP);
        assertThat(testRig.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRig.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createRigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rigRepository.findAll().size();

        // Create the Rig with an existing ID
        rig.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRigMockMvc.perform(post("/api/rigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rig)))
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rigRepository.findAll().size();
        // set the field null
        rig.setName(null);

        // Create the Rig, which fails.

        restRigMockMvc.perform(post("/api/rigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rig)))
            .andExpect(status().isBadRequest());

        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRigs() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        // Get all the rigList
        restRigMockMvc.perform(get("/api/rigs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].offshore").value(hasItem(DEFAULT_OFFSHORE.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].rigClass").value(hasItem(DEFAULT_RIG_CLASS.toString())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].yearService").value(hasItem(DEFAULT_YEAR_SERVICE)))
            .andExpect(jsonPath("$.[*].approvals").value(hasItem(DEFAULT_APPROVALS.toString())))
            .andExpect(jsonPath("$.[*].registration").value(hasItem(DEFAULT_REGISTRATION.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].drillDepth").value(hasItem(DEFAULT_DRILL_DEPTH.doubleValue())))
            .andExpect(jsonPath("$.[*].waterDepth").value(hasItem(DEFAULT_WATER_DEPTH.doubleValue())))
            .andExpect(jsonPath("$.[*].airGap").value(hasItem(DEFAULT_AIR_GAP.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }
    
    @Test
    @Transactional
    public void getRig() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        // Get the rig
        restRigMockMvc.perform(get("/api/rigs/{id}", rig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.offshore").value(DEFAULT_OFFSHORE.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.rigClass").value(DEFAULT_RIG_CLASS.toString()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.yearService").value(DEFAULT_YEAR_SERVICE))
            .andExpect(jsonPath("$.approvals").value(DEFAULT_APPROVALS.toString()))
            .andExpect(jsonPath("$.registration").value(DEFAULT_REGISTRATION.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.drillDepth").value(DEFAULT_DRILL_DEPTH.doubleValue()))
            .andExpect(jsonPath("$.waterDepth").value(DEFAULT_WATER_DEPTH.doubleValue()))
            .andExpect(jsonPath("$.airGap").value(DEFAULT_AIR_GAP.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingRig() throws Exception {
        // Get the rig
        restRigMockMvc.perform(get("/api/rigs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRig() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        int databaseSizeBeforeUpdate = rigRepository.findAll().size();

        // Update the rig
        Rig updatedRig = rigRepository.findById(rig.getId()).get();
        // Disconnect from session so that the updates on updatedRig are not directly saved in db
        em.detach(updatedRig);
        updatedRig
            .name(UPDATED_NAME)
            .offshore(UPDATED_OFFSHORE)
            .owner(UPDATED_OWNER)
            .type(UPDATED_TYPE)
            .rigClass(UPDATED_RIG_CLASS)
            .manufacturer(UPDATED_MANUFACTURER)
            .yearService(UPDATED_YEAR_SERVICE)
            .approvals(UPDATED_APPROVALS)
            .registration(UPDATED_REGISTRATION)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .drillDepth(UPDATED_DRILL_DEPTH)
            .waterDepth(UPDATED_WATER_DEPTH)
            .airGap(UPDATED_AIR_GAP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restRigMockMvc.perform(put("/api/rigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRig)))
            .andExpect(status().isOk());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
        Rig testRig = rigList.get(rigList.size() - 1);
        assertThat(testRig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRig.getOffshore()).isEqualTo(UPDATED_OFFSHORE);
        assertThat(testRig.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testRig.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRig.getRigClass()).isEqualTo(UPDATED_RIG_CLASS);
        assertThat(testRig.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testRig.getYearService()).isEqualTo(UPDATED_YEAR_SERVICE);
        assertThat(testRig.getApprovals()).isEqualTo(UPDATED_APPROVALS);
        assertThat(testRig.getRegistration()).isEqualTo(UPDATED_REGISTRATION);
        assertThat(testRig.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testRig.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testRig.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testRig.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testRig.getDrillDepth()).isEqualTo(UPDATED_DRILL_DEPTH);
        assertThat(testRig.getWaterDepth()).isEqualTo(UPDATED_WATER_DEPTH);
        assertThat(testRig.getAirGap()).isEqualTo(UPDATED_AIR_GAP);
        assertThat(testRig.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRig.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRig() throws Exception {
        int databaseSizeBeforeUpdate = rigRepository.findAll().size();

        // Create the Rig

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRigMockMvc.perform(put("/api/rigs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rig)))
            .andExpect(status().isBadRequest());

        // Validate the Rig in the database
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRig() throws Exception {
        // Initialize the database
        rigRepository.saveAndFlush(rig);

        int databaseSizeBeforeDelete = rigRepository.findAll().size();

        // Get the rig
        restRigMockMvc.perform(delete("/api/rigs/{id}", rig.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rig> rigList = rigRepository.findAll();
        assertThat(rigList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rig.class);
        Rig rig1 = new Rig();
        rig1.setId(1L);
        Rig rig2 = new Rig();
        rig2.setId(rig1.getId());
        assertThat(rig1).isEqualTo(rig2);
        rig2.setId(2L);
        assertThat(rig1).isNotEqualTo(rig2);
        rig1.setId(null);
        assertThat(rig1).isNotEqualTo(rig2);
    }
}
