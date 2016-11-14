package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.ClinicalTrial;

import java.util.List;

/**
 * Service Interface for managing ClinicalTrial.
 */
public interface ClinicalTrialService {

    /**
     * Save a clinicalTrial.
     *
     * @param clinicalTrial the entity to save
     * @return the persisted entity
     */
    ClinicalTrial save(ClinicalTrial clinicalTrial);

    /**
     *  Get all the clinicalTrials.
     *  
     *  @return the list of entities
     */
    List<ClinicalTrial> findAll();

    /**
     *  Get the "id" clinicalTrial.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClinicalTrial findOne(Long id);

    /**
     *  Delete the "id" clinicalTrial.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
