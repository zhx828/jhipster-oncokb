package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.PortalAlteration;

import java.util.List;

/**
 * Service Interface for managing PortalAlteration.
 */
public interface PortalAlterationService {

    /**
     * Save a portalAlteration.
     *
     * @param portalAlteration the entity to save
     * @return the persisted entity
     */
    PortalAlteration save(PortalAlteration portalAlteration);

    /**
     *  Get all the portalAlterations.
     *  
     *  @return the list of entities
     */
    List<PortalAlteration> findAll();

    /**
     *  Get the "id" portalAlteration.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PortalAlteration findOne(Long id);

    /**
     *  Delete the "id" portalAlteration.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
