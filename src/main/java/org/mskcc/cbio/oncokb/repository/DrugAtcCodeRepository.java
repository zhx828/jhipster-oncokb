package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.DrugAtcCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DrugAtcCode entity.
 */
@SuppressWarnings("unused")
public interface DrugAtcCodeRepository extends JpaRepository<DrugAtcCode,Long> {

}
