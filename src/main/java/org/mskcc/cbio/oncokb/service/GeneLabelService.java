package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.GeneLabel;

import java.util.List;

/**
 * Service Interface for managing GeneLabel.
 */
public interface GeneLabelService {

    /**
     * Save a geneLabel.
     *
     * @param geneLabel the entity to save
     * @return the persisted entity
     */
    GeneLabel save(GeneLabel geneLabel);

    /**
     *  Get all the geneLabels.
     *  
     *  @return the list of entities
     */
    List<GeneLabel> findAll();

    /**
     *  Get the "id" geneLabel.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GeneLabel findOne(Long id);

    /**
     *  Delete the "id" geneLabel.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
