package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.StatusService;
import org.mskcc.cbio.oncokb.domain.Status;
import org.mskcc.cbio.oncokb.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Status.
 */
@Service
@Transactional
public class StatusServiceImpl implements StatusService{

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);
    
    @Inject
    private StatusRepository statusRepository;

    /**
     * Save a status.
     *
     * @param status the entity to save
     * @return the persisted entity
     */
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        Status result = statusRepository.save(status);
        return result;
    }

    /**
     *  Get all the statuses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Status> findAll() {
        log.debug("Request to get all Statuses");
        List<Status> result = statusRepository.findAll();

        return result;
    }

    /**
     *  Get one status by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Status findOne(Long id) {
        log.debug("Request to get Status : {}", id);
        Status status = statusRepository.findOne(id);
        return status;
    }

    /**
     *  Delete the  status by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.delete(id);
    }
}
