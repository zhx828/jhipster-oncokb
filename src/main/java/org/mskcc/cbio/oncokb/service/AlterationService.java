package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.Alteration;

import java.util.List;

/**
 * Service Interface for managing Alteration.
 */
public interface AlterationService {

    /**
     * Save a alteration.
     *
     * @param alteration the entity to save
     * @return the persisted entity
     */
    Alteration save(Alteration alteration);

    /**
     *  Get all the alterations.
     *  
     *  @return the list of entities
     */
    List<Alteration> findAll();

    /**
     *  Get the "id" alteration.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Alteration findOne(Long id);

    /**
     *  Delete the "id" alteration.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
