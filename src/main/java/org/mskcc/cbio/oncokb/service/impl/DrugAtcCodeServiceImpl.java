package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.DrugAtcCodeService;
import org.mskcc.cbio.oncokb.domain.DrugAtcCode;
import org.mskcc.cbio.oncokb.repository.DrugAtcCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing DrugAtcCode.
 */
@Service
@Transactional
public class DrugAtcCodeServiceImpl implements DrugAtcCodeService{

    private final Logger log = LoggerFactory.getLogger(DrugAtcCodeServiceImpl.class);
    
    @Inject
    private DrugAtcCodeRepository drugAtcCodeRepository;

    /**
     * Save a drugAtcCode.
     *
     * @param drugAtcCode the entity to save
     * @return the persisted entity
     */
    public DrugAtcCode save(DrugAtcCode drugAtcCode) {
        log.debug("Request to save DrugAtcCode : {}", drugAtcCode);
        DrugAtcCode result = drugAtcCodeRepository.save(drugAtcCode);
        return result;
    }

    /**
     *  Get all the drugAtcCodes.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DrugAtcCode> findAll() {
        log.debug("Request to get all DrugAtcCodes");
        List<DrugAtcCode> result = drugAtcCodeRepository.findAll();

        return result;
    }

    /**
     *  Get one drugAtcCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DrugAtcCode findOne(Long id) {
        log.debug("Request to get DrugAtcCode : {}", id);
        DrugAtcCode drugAtcCode = drugAtcCodeRepository.findOne(id);
        return drugAtcCode;
    }

    /**
     *  Delete the  drugAtcCode by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DrugAtcCode : {}", id);
        drugAtcCodeRepository.delete(id);
    }
}
