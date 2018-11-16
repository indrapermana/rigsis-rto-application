package com.lakon.rto.repository;

import com.lakon.rto.domain.RecordItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecordItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordItemRepository extends JpaRepository<RecordItem, Long> {

}
