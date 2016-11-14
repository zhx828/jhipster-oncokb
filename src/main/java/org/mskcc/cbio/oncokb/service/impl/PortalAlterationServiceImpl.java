package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.PortalAlterationService;
import org.mskcc.cbio.oncokb.domain.PortalAlteration;
import org.mskcc.cbio.oncokb.repository.PortalAlterationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PortalAlteration.
 */
@Service
@Transactional
public class PortalAlterationServiceImpl implements PortalAlterationService{

    private final Logger log = LoggerFactory.getLogger(PortalAlterationServiceImpl.class);
    
    @Inject
    private PortalAlterationRepository portalAlterationRepository;

    /**
     * Save a portalAlteration.
     *
     * @param portalAlteration the entity to save
     * @return the persisted entity
     */
    public PortalAlteration save(PortalAlteration portalAlteration) {
        log.debug("Request to save PortalAlteration : {}", portalAlteration);
        PortalAlteration result = portalAlterationRepository.save(portalAlteration);
        return result;
    }

    /**
     *  Get all the portalAlterations.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PortalAlteration> findAll() {
        log.debug("Request to get all PortalAlterations");
        List<PortalAlteration> result = portalAlterationRepository.findAll();

        return result;
    }

    /**
     *  Get one portalAlteration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PortalAlteration findOne(Long id) {
        log.debug("Request to get PortalAlteration : {}", id);
        PortalAlteration portalAlteration = portalAlterationRepository.findOne(id);
        return portalAlteration;
    }

    /**
     *  Delete the  portalAlteration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PortalAlteration : {}", id);
        portalAlterationRepository.delete(id);
    }
}
