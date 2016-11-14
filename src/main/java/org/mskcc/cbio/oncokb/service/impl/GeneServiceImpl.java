package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.GeneService;
import org.mskcc.cbio.oncokb.domain.Gene;
import org.mskcc.cbio.oncokb.repository.GeneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Gene.
 */
@Service
@Transactional
public class GeneServiceImpl implements GeneService{

    private final Logger log = LoggerFactory.getLogger(GeneServiceImpl.class);
    
    @Inject
    private GeneRepository geneRepository;

    /**
     * Save a gene.
     *
     * @param gene the entity to save
     * @return the persisted entity
     */
    public Gene save(Gene gene) {
        log.debug("Request to save Gene : {}", gene);
        Gene result = geneRepository.save(gene);
        return result;
    }

    /**
     *  Get all the genes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Gene> findAll() {
        log.debug("Request to get all Genes");
        List<Gene> result = geneRepository.findAll();

        return result;
    }

    /**
     *  Get one gene by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Gene findOne(Long id) {
        log.debug("Request to get Gene : {}", id);
        Gene gene = geneRepository.findOne(id);
        return gene;
    }

    /**
     *  Delete the  gene by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Gene : {}", id);
        geneRepository.delete(id);
    }
}
