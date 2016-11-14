package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.NccnGuideline;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NccnGuideline entity.
 */
@SuppressWarnings("unused")
public interface NccnGuidelineRepository extends JpaRepository<NccnGuideline,Long> {

}
