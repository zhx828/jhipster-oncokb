package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.GeneLabelService;
import org.mskcc.cbio.oncokb.domain.GeneLabel;
import org.mskcc.cbio.oncokb.repository.GeneLabelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing GeneLabel.
 */
@Service
@Transactional
public class GeneLabelServiceImpl implements GeneLabelService{

    private final Logger log = LoggerFactory.getLogger(GeneLabelServiceImpl.class);
    
    @Inject
    private GeneLabelRepository geneLabelRepository;

    /**
     * Save a geneLabel.
     *
     * @param geneLabel the entity to save
     * @return the persisted entity
     */
    public GeneLabel save(GeneLabel geneLabel) {
        log.debug("Request to save GeneLabel : {}", geneLabel);
        GeneLabel result = geneLabelRepository.save(geneLabel);
        return result;
    }

    /**
     *  Get all the geneLabels.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<GeneLabel> findAll() {
        log.debug("Request to get all GeneLabels");
        List<GeneLabel> result = geneLabelRepository.findAll();

        return result;
    }

    /**
     *  Get one geneLabel by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GeneLabel findOne(Long id) {
        log.debug("Request to get GeneLabel : {}", id);
        GeneLabel geneLabel = geneLabelRepository.findOne(id);
        return geneLabel;
    }

    /**
     *  Delete the  geneLabel by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeneLabel : {}", id);
        geneLabelRepository.delete(id);
    }
}
