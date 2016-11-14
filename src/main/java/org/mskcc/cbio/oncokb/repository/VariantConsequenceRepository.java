package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.VariantConsequence;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VariantConsequence entity.
 */
@SuppressWarnings("unused")
public interface VariantConsequenceRepository extends JpaRepository<VariantConsequence,Long> {

}
