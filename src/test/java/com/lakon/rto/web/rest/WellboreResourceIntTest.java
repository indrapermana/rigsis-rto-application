package com.lakon.rto.web.rest;

import com.lakon.rto.RtoAppApp;

import com.lakon.rto.domain.Wellbore;
import com.lakon.rto.repository.WellboreRepository;
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
import com.lakon.rto.domain.enumeration.BooleanType;
/**
 * Test class for the WellboreResource REST controller.
 *
 * @see WellboreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RtoAppApp.class)
public class WellboreResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WELL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WELL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_WELLBORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_WELLBORE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOVERMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GOVERMENT_NUMBER = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.Active;
    private static final Status UPDATED_STATUS = Status.Inactive;

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SHAPE = "AAAAAAAAAA";
    private static final String UPDATED_SHAPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAY_TARGET = 1;
    private static final Integer UPDATED_DAY_TARGET = 2;

    private static final ZonedDateTime DEFAULT_KICK_OFF_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_KICK_OFF_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BooleanType DEFAULT_ACHIEVED_TD = BooleanType.Yes;
    private static final BooleanType UPDATED_ACHIEVED_TD = BooleanType.No;

    private static final Double DEFAULT_MD_CURRENT = 1D;
    private static final Double UPDATED_MD_CURRENT = 2D;

    private static final Double DEFAULT_TVD_CURRENT = 1D;
    private static final Double UPDATED_TVD_CURRENT = 2D;

    private static final Double DEFAULT_MD_BIT_CURRENT = 1D;
    private static final Double UPDATED_MD_BIT_CURRENT = 2D;

    private static final Double DEFAULT_TVD_BIT_CURRENT = 1D;
    private static final Double UPDATED_TVD_BIT_CURRENT = 2D;

    private static final Double DEFAULT_MD_KICK_OFF = 1D;
    private static final Double UPDATED_MD_KICK_OFF = 2D;

    private static final Double DEFAULT_TVD_KICK_OFF = 1D;
    private static final Double UPDATED_TVD_KICK_OFF = 2D;

    private static final Double DEFAULT_MD_PLANNED = 1D;
    private static final Double UPDATED_MD_PLANNED = 2D;

    private static final Double DEFAULT_TVD_PLANNED = 1D;
    private static final Double UPDATED_TVD_PLANNED = 2D;

    private static final Double DEFAULT_MD_SUB_SEA = 1D;
    private static final Double UPDATED_MD_SUB_SEA = 2D;

    private static final Double DEFAULT_TVD_SUB_SEA = 1D;
    private static final Double UPDATED_TVD_SUB_SEA = 2D;

    @Autowired
    private WellboreRepository wellboreRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWellboreMockMvc;

    private Wellbore wellbore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WellboreResource wellboreResource = new WellboreResource(wellboreRepository);
        this.restWellboreMockMvc = MockMvcBuilders.standaloneSetup(wellboreResource)
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
    public static Wellbore createEntity(EntityManager em) {
        Wellbore wellbore = new Wellbore()
            .name(DEFAULT_NAME)
            .wellName(DEFAULT_WELL_NAME)
            .parentWellboreName(DEFAULT_PARENT_WELLBORE_NAME)
            .govermentNumber(DEFAULT_GOVERMENT_NUMBER)
            .status(DEFAULT_STATUS)
            .purpose(DEFAULT_PURPOSE)
            .type(DEFAULT_TYPE)
            .shape(DEFAULT_SHAPE)
            .dayTarget(DEFAULT_DAY_TARGET)
            .kickOffDate(DEFAULT_KICK_OFF_DATE)
            .achievedTD(DEFAULT_ACHIEVED_TD)
            .mdCurrent(DEFAULT_MD_CURRENT)
            .tvdCurrent(DEFAULT_TVD_CURRENT)
            .mdBitCurrent(DEFAULT_MD_BIT_CURRENT)
            .tvdBitCurrent(DEFAULT_TVD_BIT_CURRENT)
            .mdKickOff(DEFAULT_MD_KICK_OFF)
            .tvdKickOff(DEFAULT_TVD_KICK_OFF)
            .mdPlanned(DEFAULT_MD_PLANNED)
            .tvdPlanned(DEFAULT_TVD_PLANNED)
            .mdSubSea(DEFAULT_MD_SUB_SEA)
            .tvdSubSea(DEFAULT_TVD_SUB_SEA);
        return wellbore;
    }

    @Before
    public void initTest() {
        wellbore = createEntity(em);
    }

    @Test
    @Transactional
    public void createWellbore() throws Exception {
        int databaseSizeBeforeCreate = wellboreRepository.findAll().size();

        // Create the Wellbore
        restWellboreMockMvc.perform(post("/api/wellbores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wellbore)))
            .andExpect(status().isCreated());

        // Validate the Wellbore in the database
        List<Wellbore> wellboreList = wellboreRepository.findAll();
        assertThat(wellboreList).hasSize(databaseSizeBeforeCreate + 1);
        Wellbore testWellbore = wellboreList.get(wellboreList.size() - 1);
        assertThat(testWellbore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWellbore.getWellName()).isEqualTo(DEFAULT_WELL_NAME);
        assertThat(testWellbore.getParentWellboreName()).isEqualTo(DEFAULT_PARENT_WELLBORE_NAME);
        assertThat(testWellbore.getGovermentNumber()).isEqualTo(DEFAULT_GOVERMENT_NUMBER);
        assertThat(testWellbore.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWellbore.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testWellbore.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWellbore.getShape()).isEqualTo(DEFAULT_SHAPE);
        assertThat(testWellbore.getDayTarget()).isEqualTo(DEFAULT_DAY_TARGET);
        assertThat(testWellbore.getKickOffDate()).isEqualTo(DEFAULT_KICK_OFF_DATE);
        assertThat(testWellbore.getAchievedTD()).isEqualTo(DEFAULT_ACHIEVED_TD);
        assertThat(testWellbore.getMdCurrent()).isEqualTo(DEFAULT_MD_CURRENT);
        assertThat(testWellbore.getTvdCurrent()).isEqualTo(DEFAULT_TVD_CURRENT);
        assertThat(testWellbore.getMdBitCurrent()).isEqualTo(DEFAULT_MD_BIT_CURRENT);
        assertThat(testWellbore.getTvdBitCurrent()).isEqualTo(DEFAULT_TVD_BIT_CURRENT);
        assertThat(testWellbore.getMdKickOff()).isEqualTo(DEFAULT_MD_KICK_OFF);
        assertThat(testWellbore.getTvdKickOff()).isEqualTo(DEFAULT_TVD_KICK_OFF);
        assertThat(testWellbore.getMdPlanned()).isEqualTo(DEFAULT_MD_PLANNED);
        assertThat(testWellbore.getTvdPlanned()).isEqualTo(DEFAULT_TVD_PLANNED);
        assertThat(testWellbore.getMdSubSea()).isEqualTo(DEFAULT_MD_SUB_SEA);
        assertThat(testWellbore.getTvdSubSea()).isEqualTo(DEFAULT_TVD_SUB_SEA);
    }

    @Test
    @Transactional
    public void createWellboreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wellboreRepository.findAll().size();

        // Create the Wellbore with an existing ID
        wellbore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWellboreMockMvc.perform(post("/api/wellbores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wellbore)))
            .andExpect(status().isBadRequest());

        // Validate the Wellbore in the database
        List<Wellbore> wellboreList = wellboreRepository.findAll();
        assertThat(wellboreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = wellboreRepository.findAll().size();
        // set the field null
        wellbore.setName(null);

        // Create the Wellbore, which fails.

        restWellboreMockMvc.perform(post("/api/wellbores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wellbore)))
            .andExpect(status().isBadRequest());

        List<Wellbore> wellboreList = wellboreRepository.findAll();
        assertThat(wellboreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWellbores() throws Exception {
        // Initialize the database
        wellboreRepository.saveAndFlush(wellbore);

        // Get all the wellboreList
        restWellboreMockMvc.perform(get("/api/wellbores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wellbore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].wellName").value(hasItem(DEFAULT_WELL_NAME.toString())))
            .andExpect(jsonPath("$.[*].parentWellboreName").value(hasItem(DEFAULT_PARENT_WELLBORE_NAME.toString())))
            .andExpect(jsonPath("$.[*].govermentNumber").value(hasItem(DEFAULT_GOVERMENT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].shape").value(hasItem(DEFAULT_SHAPE.toString())))
            .andExpect(jsonPath("$.[*].dayTarget").value(hasItem(DEFAULT_DAY_TARGET)))
            .andExpect(jsonPath("$.[*].kickOffDate").value(hasItem(sameInstant(DEFAULT_KICK_OFF_DATE))))
            .andExpect(jsonPath("$.[*].achievedTD").value(hasItem(DEFAULT_ACHIEVED_TD.toString())))
            .andExpect(jsonPath("$.[*].mdCurrent").value(hasItem(DEFAULT_MD_CURRENT.doubleValue())))
            .andExpect(jsonPath("$.[*].tvdCurrent").value(hasItem(DEFAULT_TVD_CURRENT.doubleValue())))
            .andExpect(jsonPath("$.[*].mdBitCurrent").value(hasItem(DEFAULT_MD_BIT_CURRENT.doubleValue())))
            .andExpect(jsonPath("$.[*].tvdBitCurrent").value(hasItem(DEFAULT_TVD_BIT_CURRENT.doubleValue())))
            .andExpect(jsonPath("$.[*].mdKickOff").value(hasItem(DEFAULT_MD_KICK_OFF.doubleValue())))
            .andExpect(jsonPath("$.[*].tvdKickOff").value(hasItem(DEFAULT_TVD_KICK_OFF.doubleValue())))
            .andExpect(jsonPath("$.[*].mdPlanned").value(hasItem(DEFAULT_MD_PLANNED.doubleValue())))
            .andExpect(jsonPath("$.[*].tvdPlanned").value(hasItem(DEFAULT_TVD_PLANNED.doubleValue())))
            .andExpect(jsonPath("$.[*].mdSubSea").value(hasItem(DEFAULT_MD_SUB_SEA.doubleValue())))
            .andExpect(jsonPath("$.[*].tvdSubSea").value(hasItem(DEFAULT_TVD_SUB_SEA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWellbore() throws Exception {
        // Initialize the database
        wellboreRepository.saveAndFlush(wellbore);

        // Get the wellbore
        restWellboreMockMvc.perform(get("/api/wellbores/{id}", wellbore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(wellbore.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.wellName").value(DEFAULT_WELL_NAME.toString()))
            .andExpect(jsonPath("$.parentWellboreName").value(DEFAULT_PARENT_WELLBORE_NAME.toString()))
            .andExpect(jsonPath("$.govermentNumber").value(DEFAULT_GOVERMENT_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.shape").value(DEFAULT_SHAPE.toString()))
            .andExpect(jsonPath("$.dayTarget").value(DEFAULT_DAY_TARGET))
            .andExpect(jsonPath("$.kickOffDate").value(sameInstant(DEFAULT_KICK_OFF_DATE)))
            .andExpect(jsonPath("$.achievedTD").value(DEFAULT_ACHIEVED_TD.toString()))
            .andExpect(jsonPath("$.mdCurrent").value(DEFAULT_MD_CURRENT.doubleValue()))
            .andExpect(jsonPath("$.tvdCurrent").value(DEFAULT_TVD_CURRENT.doubleValue()))
            .andExpect(jsonPath("$.mdBitCurrent").value(DEFAULT_MD_BIT_CURRENT.doubleValue()))
            .andExpect(jsonPath("$.tvdBitCurrent").value(DEFAULT_TVD_BIT_CURRENT.doubleValue()))
            .andExpect(jsonPath("$.mdKickOff").value(DEFAULT_MD_KICK_OFF.doubleValue()))
            .andExpect(jsonPath("$.tvdKickOff").value(DEFAULT_TVD_KICK_OFF.doubleValue()))
            .andExpect(jsonPath("$.mdPlanned").value(DEFAULT_MD_PLANNED.doubleValue()))
            .andExpect(jsonPath("$.tvdPlanned").value(DEFAULT_TVD_PLANNED.doubleValue()))
            .andExpect(jsonPath("$.mdSubSea").value(DEFAULT_MD_SUB_SEA.doubleValue()))
            .andExpect(jsonPath("$.tvdSubSea").value(DEFAULT_TVD_SUB_SEA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWellbore() throws Exception {
        // Get the wellbore
        restWellboreMockMvc.perform(get("/api/wellbores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWellbore() throws Exception {
        // Initialize the database
        wellboreRepository.saveAndFlush(wellbore);

        int databaseSizeBeforeUpdate = wellboreRepository.findAll().size();

        // Update the wellbore
        Wellbore updatedWellbore = wellboreRepository.findById(wellbore.getId()).get();
        // Disconnect from session so that the updates on updatedWellbore are not directly saved in db
        em.detach(updatedWellbore);
        updatedWellbore
            .name(UPDATED_NAME)
            .wellName(UPDATED_WELL_NAME)
            .parentWellboreName(UPDATED_PARENT_WELLBORE_NAME)
            .govermentNumber(UPDATED_GOVERMENT_NUMBER)
            .status(UPDATED_STATUS)
            .purpose(UPDATED_PURPOSE)
            .type(UPDATED_TYPE)
            .shape(UPDATED_SHAPE)
            .dayTarget(UPDATED_DAY_TARGET)
            .kickOffDate(UPDATED_KICK_OFF_DATE)
            .achievedTD(UPDATED_ACHIEVED_TD)
            .mdCurrent(UPDATED_MD_CURRENT)
            .tvdCurrent(UPDATED_TVD_CURRENT)
            .mdBitCurrent(UPDATED_MD_BIT_CURRENT)
            .tvdBitCurrent(UPDATED_TVD_BIT_CURRENT)
            .mdKickOff(UPDATED_MD_KICK_OFF)
            .tvdKickOff(UPDATED_TVD_KICK_OFF)
            .mdPlanned(UPDATED_MD_PLANNED)
            .tvdPlanned(UPDATED_TVD_PLANNED)
            .mdSubSea(UPDATED_MD_SUB_SEA)
            .tvdSubSea(UPDATED_TVD_SUB_SEA);

        restWellboreMockMvc.perform(put("/api/wellbores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWellbore)))
            .andExpect(status().isOk());

        // Validate the Wellbore in the database
        List<Wellbore> wellboreList = wellboreRepository.findAll();
        assertThat(wellboreList).hasSize(databaseSizeBeforeUpdate);
        Wellbore testWellbore = wellboreList.get(wellboreList.size() - 1);
        assertThat(testWellbore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWellbore.getWellName()).isEqualTo(UPDATED_WELL_NAME);
        assertThat(testWellbore.getParentWellboreName()).isEqualTo(UPDATED_PARENT_WELLBORE_NAME);
        assertThat(testWellbore.getGovermentNumber()).isEqualTo(UPDATED_GOVERMENT_NUMBER);
        assertThat(testWellbore.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWellbore.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testWellbore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWellbore.getShape()).isEqualTo(UPDATED_SHAPE);
        assertThat(testWellbore.getDayTarget()).isEqualTo(UPDATED_DAY_TARGET);
        assertThat(testWellbore.getKickOffDate()).isEqualTo(UPDATED_KICK_OFF_DATE);
        assertThat(testWellbore.getAchievedTD()).isEqualTo(UPDATED_ACHIEVED_TD);
        assertThat(testWellbore.getMdCurrent()).isEqualTo(UPDATED_MD_CURRENT);
        assertThat(testWellbore.getTvdCurrent()).isEqualTo(UPDATED_TVD_CURRENT);
        assertThat(testWellbore.getMdBitCurrent()).isEqualTo(UPDATED_MD_BIT_CURRENT);
        assertThat(testWellbore.getTvdBitCurrent()).isEqualTo(UPDATED_TVD_BIT_CURRENT);
        assertThat(testWellbore.getMdKickOff()).isEqualTo(UPDATED_MD_KICK_OFF);
        assertThat(testWellbore.getTvdKickOff()).isEqualTo(UPDATED_TVD_KICK_OFF);
        assertThat(testWellbore.getMdPlanned()).isEqualTo(UPDATED_MD_PLANNED);
        assertThat(testWellbore.getTvdPlanned()).isEqualTo(UPDATED_TVD_PLANNED);
        assertThat(testWellbore.getMdSubSea()).isEqualTo(UPDATED_MD_SUB_SEA);
        assertThat(testWellbore.getTvdSubSea()).isEqualTo(UPDATED_TVD_SUB_SEA);
    }

    @Test
    @Transactional
    public void updateNonExistingWellbore() throws Exception {
        int databaseSizeBeforeUpdate = wellboreRepository.findAll().size();

        // Create the Wellbore

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWellboreMockMvc.perform(put("/api/wellbores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(wellbore)))
            .andExpect(status().isBadRequest());

        // Validate the Wellbore in the database
        List<Wellbore> wellboreList = wellboreRepository.findAll();
        assertThat(wellboreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWellbore() throws Exception {
        // Initialize the database
        wellboreRepository.saveAndFlush(wellbore);

        int databaseSizeBeforeDelete = wellboreRepository.findAll().size();

        // Get the wellbore
        restWellboreMockMvc.perform(delete("/api/wellbores/{id}", wellbore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Wellbore> wellboreList = wellboreRepository.findAll();
        assertThat(wellboreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wellbore.class);
        Wellbore wellbore1 = new Wellbore();
        wellbore1.setId(1L);
        Wellbore wellbore2 = new Wellbore();
        wellbore2.setId(wellbore1.getId());
        assertThat(wellbore1).isEqualTo(wellbore2);
        wellbore2.setId(2L);
        assertThat(wellbore1).isNotEqualTo(wellbore2);
        wellbore1.setId(null);
        assertThat(wellbore1).isNotEqualTo(wellbore2);
    }
}
