package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.GeneAlias;

import java.util.List;

/**
 * Service Interface for managing GeneAlias.
 */
public interface GeneAliasService {

    /**
     * Save a geneAlias.
     *
     * @param geneAlias the entity to save
     * @return the persisted entity
     */
    GeneAlias save(GeneAlias geneAlias);

    /**
     *  Get all the geneAliases.
     *  
     *  @return the list of entities
     */
    List<GeneAlias> findAll();

    /**
     *  Get the "id" geneAlias.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GeneAlias findOne(Long id);

    /**
     *  Delete the "id" geneAlias.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
