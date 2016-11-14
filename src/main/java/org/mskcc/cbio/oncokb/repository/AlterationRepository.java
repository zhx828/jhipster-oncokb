package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.Alteration;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Alteration entity.
 */
@SuppressWarnings("unused")
public interface AlterationRepository extends JpaRepository<Alteration,Long> {

    @Query("select distinct alteration from Alteration alteration left join fetch alteration.portalAlterations")
    List<Alteration> findAllWithEagerRelationships();

    @Query("select alteration from Alteration alteration left join fetch alteration.portalAlterations where alteration.id =:id")
    Alteration findOneWithEagerRelationships(@Param("id") Long id);

}
