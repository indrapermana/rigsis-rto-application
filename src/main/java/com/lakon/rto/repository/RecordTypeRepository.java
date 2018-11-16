package com.lakon.rto.repository;

import com.lakon.rto.domain.RecordType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecordType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordTypeRepository extends JpaRepository<RecordType, Long> {

}
