package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.DrugSynonym;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DrugSynonym entity.
 */
@SuppressWarnings("unused")
public interface DrugSynonymRepository extends JpaRepository<DrugSynonym,Long> {

}
