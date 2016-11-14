package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.EvidenceService;
import org.mskcc.cbio.oncokb.domain.Evidence;
import org.mskcc.cbio.oncokb.repository.EvidenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Evidence.
 */
@Service
@Transactional
public class EvidenceServiceImpl implements EvidenceService{

    private final Logger log = LoggerFactory.getLogger(EvidenceServiceImpl.class);
    
    @Inject
    private EvidenceRepository evidenceRepository;

    /**
     * Save a evidence.
     *
     * @param evidence the entity to save
     * @return the persisted entity
     */
    public Evidence save(Evidence evidence) {
        log.debug("Request to save Evidence : {}", evidence);
        Evidence result = evidenceRepository.save(evidence);
        return result;
    }

    /**
     *  Get all the evidences.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Evidence> findAll() {
        log.debug("Request to get all Evidences");
        List<Evidence> result = evidenceRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one evidence by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Evidence findOne(Long id) {
        log.debug("Request to get Evidence : {}", id);
        Evidence evidence = evidenceRepository.findOneWithEagerRelationships(id);
        return evidence;
    }

    /**
     *  Delete the  evidence by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Evidence : {}", id);
        evidenceRepository.delete(id);
    }
}
