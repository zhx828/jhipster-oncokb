package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.ApprovedIndication;

import java.util.List;

/**
 * Service Interface for managing ApprovedIndication.
 */
public interface ApprovedIndicationService {

    /**
     * Save a approvedIndication.
     *
     * @param approvedIndication the entity to save
     * @return the persisted entity
     */
    ApprovedIndication save(ApprovedIndication approvedIndication);

    /**
     *  Get all the approvedIndications.
     *  
     *  @return the list of entities
     */
    List<ApprovedIndication> findAll();

    /**
     *  Get the "id" approvedIndication.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ApprovedIndication findOne(Long id);

    /**
     *  Delete the "id" approvedIndication.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
