package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.ClinicalTrialService;
import org.mskcc.cbio.oncokb.domain.ClinicalTrial;
import org.mskcc.cbio.oncokb.repository.ClinicalTrialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ClinicalTrial.
 */
@Service
@Transactional
public class ClinicalTrialServiceImpl implements ClinicalTrialService{

    private final Logger log = LoggerFactory.getLogger(ClinicalTrialServiceImpl.class);
    
    @Inject
    private ClinicalTrialRepository clinicalTrialRepository;

    /**
     * Save a clinicalTrial.
     *
     * @param clinicalTrial the entity to save
     * @return the persisted entity
     */
    public ClinicalTrial save(ClinicalTrial clinicalTrial) {
        log.debug("Request to save ClinicalTrial : {}", clinicalTrial);
        ClinicalTrial result = clinicalTrialRepository.save(clinicalTrial);
        return result;
    }

    /**
     *  Get all the clinicalTrials.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ClinicalTrial> findAll() {
        log.debug("Request to get all ClinicalTrials");
        List<ClinicalTrial> result = clinicalTrialRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one clinicalTrial by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ClinicalTrial findOne(Long id) {
        log.debug("Request to get ClinicalTrial : {}", id);
        ClinicalTrial clinicalTrial = clinicalTrialRepository.findOneWithEagerRelationships(id);
        return clinicalTrial;
    }

    /**
     *  Delete the  clinicalTrial by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ClinicalTrial : {}", id);
        clinicalTrialRepository.delete(id);
    }
}
