package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.NccnGuidelineService;
import org.mskcc.cbio.oncokb.domain.NccnGuideline;
import org.mskcc.cbio.oncokb.repository.NccnGuidelineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing NccnGuideline.
 */
@Service
@Transactional
public class NccnGuidelineServiceImpl implements NccnGuidelineService{

    private final Logger log = LoggerFactory.getLogger(NccnGuidelineServiceImpl.class);
    
    @Inject
    private NccnGuidelineRepository nccnGuidelineRepository;

    /**
     * Save a nccnGuideline.
     *
     * @param nccnGuideline the entity to save
     * @return the persisted entity
     */
    public NccnGuideline save(NccnGuideline nccnGuideline) {
        log.debug("Request to save NccnGuideline : {}", nccnGuideline);
        NccnGuideline result = nccnGuidelineRepository.save(nccnGuideline);
        return result;
    }

    /**
     *  Get all the nccnGuidelines.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<NccnGuideline> findAll() {
        log.debug("Request to get all NccnGuidelines");
        List<NccnGuideline> result = nccnGuidelineRepository.findAll();

        return result;
    }

    /**
     *  Get one nccnGuideline by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public NccnGuideline findOne(Long id) {
        log.debug("Request to get NccnGuideline : {}", id);
        NccnGuideline nccnGuideline = nccnGuidelineRepository.findOne(id);
        return nccnGuideline;
    }

    /**
     *  Delete the  nccnGuideline by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NccnGuideline : {}", id);
        nccnGuidelineRepository.delete(id);
    }
}
