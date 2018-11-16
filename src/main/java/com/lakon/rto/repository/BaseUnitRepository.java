package com.lakon.rto.repository;

import com.lakon.rto.domain.BaseUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseUnitRepository extends JpaRepository<BaseUnit, Long> {

}
