package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.TimeStampService;
import org.mskcc.cbio.oncokb.domain.TimeStamp;
import org.mskcc.cbio.oncokb.repository.TimeStampRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing TimeStamp.
 */
@Service
@Transactional
public class TimeStampServiceImpl implements TimeStampService{

    private final Logger log = LoggerFactory.getLogger(TimeStampServiceImpl.class);
    
    @Inject
    private TimeStampRepository timeStampRepository;

    /**
     * Save a timeStamp.
     *
     * @param timeStamp the entity to save
     * @return the persisted entity
     */
    public TimeStamp save(TimeStamp timeStamp) {
        log.debug("Request to save TimeStamp : {}", timeStamp);
        TimeStamp result = timeStampRepository.save(timeStamp);
        return result;
    }

    /**
     *  Get all the timeStamps.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TimeStamp> findAll() {
        log.debug("Request to get all TimeStamps");
        List<TimeStamp> result = timeStampRepository.findAll();

        return result;
    }

    /**
     *  Get one timeStamp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TimeStamp findOne(Long id) {
        log.debug("Request to get TimeStamp : {}", id);
        TimeStamp timeStamp = timeStampRepository.findOne(id);
        return timeStamp;
    }

    /**
     *  Delete the  timeStamp by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TimeStamp : {}", id);
        timeStampRepository.delete(id);
    }
}
