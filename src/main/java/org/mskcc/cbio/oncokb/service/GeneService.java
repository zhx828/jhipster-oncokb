package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.Gene;

import java.util.List;

/**
 * Service Interface for managing Gene.
 */
public interface GeneService {

    /**
     * Save a gene.
     *
     * @param gene the entity to save
     * @return the persisted entity
     */
    Gene save(Gene gene);

    /**
     *  Get all the genes.
     *  
     *  @return the list of entities
     */
    List<Gene> findAll();

    /**
     *  Get the "id" gene.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Gene findOne(Long id);

    /**
     *  Delete the "id" gene.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
