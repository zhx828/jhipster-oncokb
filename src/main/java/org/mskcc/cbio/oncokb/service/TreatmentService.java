package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.Treatment;

import java.util.List;

/**
 * Service Interface for managing Treatment.
 */
public interface TreatmentService {

    /**
     * Save a treatment.
     *
     * @param treatment the entity to save
     * @return the persisted entity
     */
    Treatment save(Treatment treatment);

    /**
     *  Get all the treatments.
     *  
     *  @return the list of entities
     */
    List<Treatment> findAll();

    /**
     *  Get the "id" treatment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Treatment findOne(Long id);

    /**
     *  Delete the "id" treatment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
