package org.mskcc.cbio.oncokb.service;

import org.mskcc.cbio.oncokb.domain.TimeStamp;

import java.util.List;

/**
 * Service Interface for managing TimeStamp.
 */
public interface TimeStampService {

    /**
     * Save a timeStamp.
     *
     * @param timeStamp the entity to save
     * @return the persisted entity
     */
    TimeStamp save(TimeStamp timeStamp);

    /**
     *  Get all the timeStamps.
     *  
     *  @return the list of entities
     */
    List<TimeStamp> findAll();

    /**
     *  Get the "id" timeStamp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TimeStamp findOne(Long id);

    /**
     *  Delete the "id" timeStamp.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
