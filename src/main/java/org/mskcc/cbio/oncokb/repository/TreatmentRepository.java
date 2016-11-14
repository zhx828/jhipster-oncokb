package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.Treatment;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Treatment entity.
 */
@SuppressWarnings("unused")
public interface TreatmentRepository extends JpaRepository<Treatment,Long> {

    @Query("select distinct treatment from Treatment treatment left join fetch treatment.drugs left join fetch treatment.approvedIndications")
    List<Treatment> findAllWithEagerRelationships();

    @Query("select treatment from Treatment treatment left join fetch treatment.drugs left join fetch treatment.approvedIndications where treatment.id =:id")
    Treatment findOneWithEagerRelationships(@Param("id") Long id);

}
