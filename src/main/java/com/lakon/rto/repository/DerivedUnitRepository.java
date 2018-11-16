package com.lakon.rto.repository;

import com.lakon.rto.domain.DerivedUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DerivedUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DerivedUnitRepository extends JpaRepository<DerivedUnit, Long> {

}
