package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.GeneLabel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GeneLabel entity.
 */
@SuppressWarnings("unused")
public interface GeneLabelRepository extends JpaRepository<GeneLabel,Long> {

}
