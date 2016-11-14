package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.ApprovedIndicationService;
import org.mskcc.cbio.oncokb.domain.ApprovedIndication;
import org.mskcc.cbio.oncokb.repository.ApprovedIndicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ApprovedIndication.
 */
@Service
@Transactional
public class ApprovedIndicationServiceImpl implements ApprovedIndicationService{

    private final Logger log = LoggerFactory.getLogger(ApprovedIndicationServiceImpl.class);
    
    @Inject
    private ApprovedIndicationRepository approvedIndicationRepository;

    /**
     * Save a approvedIndication.
     *
     * @param approvedIndication the entity to save
     * @return the persisted entity
     */
    public ApprovedIndication save(ApprovedIndication approvedIndication) {
        log.debug("Request to save ApprovedIndication : {}", approvedIndication);
        ApprovedIndication result = approvedIndicationRepository.save(approvedIndication);
        return result;
    }

    /**
     *  Get all the approvedIndications.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<ApprovedIndication> findAll() {
        log.debug("Request to get all ApprovedIndications");
        List<ApprovedIndication> result = approvedIndicationRepository.findAll();

        return result;
    }

    /**
     *  Get one approvedIndication by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ApprovedIndication findOne(Long id) {
        log.debug("Request to get ApprovedIndication : {}", id);
        ApprovedIndication approvedIndication = approvedIndicationRepository.findOne(id);
        return approvedIndication;
    }

    /**
     *  Delete the  approvedIndication by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ApprovedIndication : {}", id);
        approvedIndicationRepository.delete(id);
    }
}
