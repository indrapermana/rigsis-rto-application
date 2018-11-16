package com.lakon.rto.repository;

import com.lakon.rto.domain.UnitType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnitType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {

}
