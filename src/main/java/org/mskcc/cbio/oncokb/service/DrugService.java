package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.Drug;

import java.util.List;

/**
 * Service Interface for managing Drug.
 */
public interface DrugService {

    /**
     * Save a drug.
     *
     * @param drug the entity to save
     * @return the persisted entity
     */
    Drug save(Drug drug);

    /**
     *  Get all the drugs.
     *  
     *  @return the list of entities
     */
    List<Drug> findAll();

    /**
     *  Get the "id" drug.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Drug findOne(Long id);

    /**
     *  Delete the "id" drug.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
