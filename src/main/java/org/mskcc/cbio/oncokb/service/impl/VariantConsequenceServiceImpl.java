package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.VariantConsequenceService;
import org.mskcc.cbio.oncokb.domain.VariantConsequence;
import org.mskcc.cbio.oncokb.repository.VariantConsequenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing VariantConsequence.
 */
@Service
@Transactional
public class VariantConsequenceServiceImpl implements VariantConsequenceService{

    private final Logger log = LoggerFactory.getLogger(VariantConsequenceServiceImpl.class);
    
    @Inject
    private VariantConsequenceRepository variantConsequenceRepository;

    /**
     * Save a variantConsequence.
     *
     * @param variantConsequence the entity to save
     * @return the persisted entity
     */
    public VariantConsequence save(VariantConsequence variantConsequence) {
        log.debug("Request to save VariantConsequence : {}", variantConsequence);
        VariantConsequence result = variantConsequenceRepository.save(variantConsequence);
        return result;
    }

    /**
     *  Get all the variantConsequences.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<VariantConsequence> findAll() {
        log.debug("Request to get all VariantConsequences");
        List<VariantConsequence> result = variantConsequenceRepository.findAll();

        return result;
    }

    /**
     *  Get one variantConsequence by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VariantConsequence findOne(Long id) {
        log.debug("Request to get VariantConsequence : {}", id);
        VariantConsequence variantConsequence = variantConsequenceRepository.findOne(id);
        return variantConsequence;
    }

    /**
     *  Delete the  variantConsequence by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VariantConsequence : {}", id);
        variantConsequenceRepository.delete(id);
    }
}
