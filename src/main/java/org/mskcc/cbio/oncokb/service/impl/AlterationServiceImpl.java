package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.AlterationService;
import org.mskcc.cbio.oncokb.domain.Alteration;
import org.mskcc.cbio.oncokb.repository.AlterationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Alteration.
 */
@Service
@Transactional
public class AlterationServiceImpl implements AlterationService{

    private final Logger log = LoggerFactory.getLogger(AlterationServiceImpl.class);
    
    @Inject
    private AlterationRepository alterationRepository;

    /**
     * Save a alteration.
     *
     * @param alteration the entity to save
     * @return the persisted entity
     */
    public Alteration save(Alteration alteration) {
        log.debug("Request to save Alteration : {}", alteration);
        Alteration result = alterationRepository.save(alteration);
        return result;
    }

    /**
     *  Get all the alterations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Alteration> findAll() {
        log.debug("Request to get all Alterations");
        List<Alteration> result = alterationRepository.findAllWithEagerRelationships();

        return result;
    }

    /**
     *  Get one alteration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Alteration findOne(Long id) {
        log.debug("Request to get Alteration : {}", id);
        Alteration alteration = alterationRepository.findOneWithEagerRelationships(id);
        return alteration;
    }

    /**
     *  Delete the  alteration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Alteration : {}", id);
        alterationRepository.delete(id);
    }
}
