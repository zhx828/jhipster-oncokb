package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.PortalAlteration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PortalAlteration entity.
 */
@SuppressWarnings("unused")
public interface PortalAlterationRepository extends JpaRepository<PortalAlteration,Long> {

}
