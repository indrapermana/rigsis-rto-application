package com.lakon.rto.repository;

import com.lakon.rto.domain.WitsService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WitsService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WitsServiceRepository extends JpaRepository<WitsService, Long> {

}
