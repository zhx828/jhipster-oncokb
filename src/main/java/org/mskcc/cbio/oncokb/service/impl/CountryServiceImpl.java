package org.mskcc.cbio.oncokb.service.impl;

import org.mskcc.cbio.oncokb.service.CountryService;
import org.mskcc.cbio.oncokb.domain.Country;
import org.mskcc.cbio.oncokb.repository.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService{

    private final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);
    
    @Inject
    private CountryRepository countryRepository;

    /**
     * Save a country.
     *
     * @param country the entity to save
     * @return the persisted entity
     */
    public Country save(Country country) {
        log.debug("Request to save Country : {}", country);
        Country result = countryRepository.save(country);
        return result;
    }

    /**
     *  Get all the countries.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Country> findAll() {
        log.debug("Request to get all Countries");
        List<Country> result = countryRepository.findAll();

        return result;
    }

    /**
     *  Get one country by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Country findOne(Long id) {
        log.debug("Request to get Country : {}", id);
        Country country = countryRepository.findOne(id);
        return country;
    }

    /**
     *  Delete the  country by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Country : {}", id);
        countryRepository.delete(id);
    }
}
