package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.VariantConsequence;

import java.util.List;

/**
 * Service Interface for managing VariantConsequence.
 */
public interface VariantConsequenceService {

    /**
     * Save a variantConsequence.
     *
     * @param variantConsequence the entity to save
     * @return the persisted entity
     */
    VariantConsequence save(VariantConsequence variantConsequence);

    /**
     *  Get all the variantConsequences.
     *  
     *  @return the list of entities
     */
    List<VariantConsequence> findAll();

    /**
     *  Get the "id" variantConsequence.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VariantConsequence findOne(Long id);

    /**
     *  Delete the "id" variantConsequence.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
