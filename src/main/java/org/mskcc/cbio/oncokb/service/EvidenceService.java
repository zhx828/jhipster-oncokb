package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.Evidence;

import java.util.List;

/**
 * Service Interface for managing Evidence.
 */
public interface EvidenceService {

    /**
     * Save a evidence.
     *
     * @param evidence the entity to save
     * @return the persisted entity
     */
    Evidence save(Evidence evidence);

    /**
     *  Get all the evidences.
     *  
     *  @return the list of entities
     */
    List<Evidence> findAll();

    /**
     *  Get the "id" evidence.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Evidence findOne(Long id);

    /**
     *  Delete the "id" evidence.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
