package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.GeneAlias;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GeneAlias entity.
 */
@SuppressWarnings("unused")
public interface GeneAliasRepository extends JpaRepository<GeneAlias,Long> {

}
