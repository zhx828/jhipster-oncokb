package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.NccnGuideline;

import java.util.List;

/**
 * Service Interface for managing NccnGuideline.
 */
public interface NccnGuidelineService {

    /**
     * Save a nccnGuideline.
     *
     * @param nccnGuideline the entity to save
     * @return the persisted entity
     */
    NccnGuideline save(NccnGuideline nccnGuideline);

    /**
     *  Get all the nccnGuidelines.
     *  
     *  @return the list of entities
     */
    List<NccnGuideline> findAll();

    /**
     *  Get the "id" nccnGuideline.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NccnGuideline findOne(Long id);

    /**
     *  Delete the "id" nccnGuideline.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
