package com.lakon.rto.repository;

import com.lakon.rto.domain.Well;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Well entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WellRepository extends JpaRepository<Well, Long> {

}
