package com.lakon.rto.repository;

import com.lakon.rto.domain.Rig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Rig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RigRepository extends JpaRepository<Rig, Long> {

}
