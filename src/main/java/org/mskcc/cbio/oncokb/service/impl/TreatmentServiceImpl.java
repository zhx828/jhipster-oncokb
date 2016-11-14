package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.TreatmentService;
import org.mskcc.cbio.oncokb.domain.Treatment;
import org.mskcc.cbio.oncokb.repository.TreatmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Treatment.
 */
@Service
@Transactional
public class TreatmentServiceImpl implements TreatmentService{

    private final Logger log = LoggerFactory.getLogger(TreatmentServiceImpl.class);
    
    @Inject
    private TreatmentRepository treatmentRepository;

    /**
     * Save a treatment.
     *
     * @param treatment the entity to save
     * @return the persisted entity
     */
    public Treatment save(Treatment treatment) {
        log.debug("Request to save Treatment : {}", treatment);
        Treatment result = treatmentRepository.save(treatment);
        return result;
    }

    /**
     *  Get all the treatments.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Treatment> findAll() {
        log.debug("Request to get all Treatments");
        List<Treatment> result = treatmentRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one treatment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Treatment findOne(Long id) {
        log.debug("Request to get Treatment : {}", id);
        Treatment treatment = treatmentRepository.findOneWithEagerRelationships(id);
        return treatment;
    }

    /**
     *  Delete the  treatment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Treatment : {}", id);
        treatmentRepository.delete(id);
    }
}
