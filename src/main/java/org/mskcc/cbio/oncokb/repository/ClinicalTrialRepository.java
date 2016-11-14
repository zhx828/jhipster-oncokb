package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.ClinicalTrial;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ClinicalTrial entity.
 */
@SuppressWarnings("unused")
public interface ClinicalTrialRepository extends JpaRepository<ClinicalTrial,Long> {

    @Query("select distinct clinicalTrial from ClinicalTrial clinicalTrial left join fetch clinicalTrial.drugs left join fetch clinicalTrial.countries")
    List<ClinicalTrial> findAllWithEagerRelationships();

    @Query("select clinicalTrial from ClinicalTrial clinicalTrial left join fetch clinicalTrial.drugs left join fetch clinicalTrial.countries where clinicalTrial.id =:id")
    ClinicalTrial findOneWithEagerRelationships(@Param("id") Long id);

}
