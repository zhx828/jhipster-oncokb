package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.GeneAliasService;
import org.mskcc.cbio.oncokb.domain.GeneAlias;
import org.mskcc.cbio.oncokb.repository.GeneAliasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing GeneAlias.
 */
@Service
@Transactional
public class GeneAliasServiceImpl implements GeneAliasService{

    private final Logger log = LoggerFactory.getLogger(GeneAliasServiceImpl.class);
    
    @Inject
    private GeneAliasRepository geneAliasRepository;

    /**
     * Save a geneAlias.
     *
     * @param geneAlias the entity to save
     * @return the persisted entity
     */
    public GeneAlias save(GeneAlias geneAlias) {
        log.debug("Request to save GeneAlias : {}", geneAlias);
        GeneAlias result = geneAliasRepository.save(geneAlias);
        return result;
    }

    /**
     *  Get all the geneAliases.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<GeneAlias> findAll() {
        log.debug("Request to get all GeneAliases");
        List<GeneAlias> result = geneAliasRepository.findAll();

        return result;
    }

    /**
     *  Get one geneAlias by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public GeneAlias findOne(Long id) {
        log.debug("Request to get GeneAlias : {}", id);
        GeneAlias geneAlias = geneAliasRepository.findOne(id);
        return geneAlias;
    }

    /**
     *  Delete the  geneAlias by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeneAlias : {}", id);
        geneAliasRepository.delete(id);
    }
}
