package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.Gene;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gene entity.
 */
@SuppressWarnings("unused")
public interface GeneRepository extends JpaRepository<Gene,Long> {

}
