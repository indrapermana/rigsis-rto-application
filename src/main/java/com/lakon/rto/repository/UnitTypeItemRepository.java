package com.lakon.rto.repository;

import com.lakon.rto.domain.UnitTypeItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnitTypeItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitTypeItemRepository extends JpaRepository<UnitTypeItem, Long> {

}
