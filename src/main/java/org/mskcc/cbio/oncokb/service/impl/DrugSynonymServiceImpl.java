package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.DrugSynonymService;
import org.mskcc.cbio.oncokb.domain.DrugSynonym;
import org.mskcc.cbio.oncokb.repository.DrugSynonymRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing DrugSynonym.
 */
@Service
@Transactional
public class DrugSynonymServiceImpl implements DrugSynonymService{

    private final Logger log = LoggerFactory.getLogger(DrugSynonymServiceImpl.class);
    
    @Inject
    private DrugSynonymRepository drugSynonymRepository;

    /**
     * Save a drugSynonym.
     *
     * @param drugSynonym the entity to save
     * @return the persisted entity
     */
    public DrugSynonym save(DrugSynonym drugSynonym) {
        log.debug("Request to save DrugSynonym : {}", drugSynonym);
        DrugSynonym result = drugSynonymRepository.save(drugSynonym);
        return result;
    }

    /**
     *  Get all the drugSynonyms.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DrugSynonym> findAll() {
        log.debug("Request to get all DrugSynonyms");
        List<DrugSynonym> result = drugSynonymRepository.findAll();

        return result;
    }

    /**
     *  Get one drugSynonym by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DrugSynonym findOne(Long id) {
        log.debug("Request to get DrugSynonym : {}", id);
        DrugSynonym drugSynonym = drugSynonymRepository.findOne(id);
        return drugSynonym;
    }

    /**
     *  Delete the  drugSynonym by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DrugSynonym : {}", id);
        drugSynonymRepository.delete(id);
    }
}
