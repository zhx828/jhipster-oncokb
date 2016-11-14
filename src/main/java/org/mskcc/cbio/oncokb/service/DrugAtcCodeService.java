package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.DrugAtcCode;

import java.util.List;

/**
 * Service Interface for managing DrugAtcCode.
 */
public interface DrugAtcCodeService {

    /**
     * Save a drugAtcCode.
     *
     * @param drugAtcCode the entity to save
     * @return the persisted entity
     */
    DrugAtcCode save(DrugAtcCode drugAtcCode);

    /**
     *  Get all the drugAtcCodes.
     *  
     *  @return the list of entities
     */
    List<DrugAtcCode> findAll();

    /**
     *  Get the "id" drugAtcCode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DrugAtcCode findOne(Long id);

    /**
     *  Delete the "id" drugAtcCode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
