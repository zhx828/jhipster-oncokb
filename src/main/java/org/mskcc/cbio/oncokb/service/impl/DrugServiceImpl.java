package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.DrugService;
import org.mskcc.cbio.oncokb.domain.Drug;
import org.mskcc.cbio.oncokb.repository.DrugRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Drug.
 */
@Service
@Transactional
public class DrugServiceImpl implements DrugService{

    private final Logger log = LoggerFactory.getLogger(DrugServiceImpl.class);
    
    @Inject
    private DrugRepository drugRepository;

    /**
     * Save a drug.
     *
     * @param drug the entity to save
     * @return the persisted entity
     */
    public Drug save(Drug drug) {
        log.debug("Request to save Drug : {}", drug);
        Drug result = drugRepository.save(drug);
        return result;
    }

    /**
     *  Get all the drugs.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Drug> findAll() {
        log.debug("Request to get all Drugs");
        List<Drug> result = drugRepository.findAll();

        return result;
    }

    /**
     *  Get one drug by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Drug findOne(Long id) {
        log.debug("Request to get Drug : {}", id);
        Drug drug = drugRepository.findOne(id);
        return drug;
    }

    /**
     *  Delete the  drug by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Drug : {}", id);
        drugRepository.delete(id);
    }
}
