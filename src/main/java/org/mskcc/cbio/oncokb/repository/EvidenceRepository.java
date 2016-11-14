package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.Evidence;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Evidence entity.
 */
@SuppressWarnings("unused")
public interface EvidenceRepository extends JpaRepository<Evidence,Long> {

    @Query("select distinct evidence from Evidence evidence left join fetch evidence.alterations left join fetch evidence.treatments left join fetch evidence.articles left join fetch evidence.clinicalTrials left join fetch evidence.nccnGuidelines")
    List<Evidence> findAllWithEagerRelationships();

    @Query("select evidence from Evidence evidence left join fetch evidence.alterations left join fetch evidence.treatments left join fetch evidence.articles left join fetch evidence.clinicalTrials left join fetch evidence.nccnGuidelines where evidence.id =:id")
    Evidence findOneWithEagerRelationships(@Param("id") Long id);

}
