package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.ApprovedIndication;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ApprovedIndication entity.
 */
@SuppressWarnings("unused")
public interface ApprovedIndicationRepository extends JpaRepository<ApprovedIndication,Long> {

}
