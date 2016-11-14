package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.DrugSynonym;

import java.util.List;

/**
 * Service Interface for managing DrugSynonym.
 */
public interface DrugSynonymService {

    /**
     * Save a drugSynonym.
     *
     * @param drugSynonym the entity to save
     * @return the persisted entity
     */
    DrugSynonym save(DrugSynonym drugSynonym);

    /**
     *  Get all the drugSynonyms.
     *  
     *  @return the list of entities
     */
    List<DrugSynonym> findAll();

    /**
     *  Get the "id" drugSynonym.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DrugSynonym findOne(Long id);

    /**
     *  Delete the "id" drugSynonym.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
