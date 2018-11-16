package com.lakon.rto.repository;

import com.lakon.rto.domain.Wellbore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wellbore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WellboreRepository extends JpaRepository<Wellbore, Long> {

}
