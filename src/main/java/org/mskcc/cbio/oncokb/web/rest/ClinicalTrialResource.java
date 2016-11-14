package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.ClinicalTrial;
import org.mskcc.cbio.oncokb.service.ClinicalTrialService;
import org.mskcc.cbio.oncokb.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClinicalTrial.
 */
@RestController
@RequestMapping("/api")
public class ClinicalTrialResource {

    private final Logger log = LoggerFactory.getLogger(ClinicalTrialResource.class);
        
    @Inject
    private ClinicalTrialService clinicalTrialService;

    /**
     * POST  /clinical-trials : Create a new clinicalTrial.
     *
     * @param clinicalTrial the clinicalTrial to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clinicalTrial, or with status 400 (Bad Request) if the clinicalTrial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/clinical-trials",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClinicalTrial> createClinicalTrial(@RequestBody ClinicalTrial clinicalTrial) throws URISyntaxException {
        log.debug("REST request to save ClinicalTrial : {}", clinicalTrial);
        if (clinicalTrial.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clinicalTrial", "idexists", "A new clinicalTrial cannot already have an ID")).body(null);
        }
        ClinicalTrial result = clinicalTrialService.save(clinicalTrial);
        return ResponseEntity.created(new URI("/api/clinical-trials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clinicalTrial", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clinical-trials : Updates an existing clinicalTrial.
     *
     * @param clinicalTrial the clinicalTrial to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clinicalTrial,
     * or with status 400 (Bad Request) if the clinicalTrial is not valid,
     * or with status 500 (Internal Server Error) if the clinicalTrial couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/clinical-trials",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClinicalTrial> updateClinicalTrial(@RequestBody ClinicalTrial clinicalTrial) throws URISyntaxException {
        log.debug("REST request to update ClinicalTrial : {}", clinicalTrial);
        if (clinicalTrial.getId() == null) {
            return createClinicalTrial(clinicalTrial);
        }
        ClinicalTrial result = clinicalTrialService.save(clinicalTrial);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clinicalTrial", clinicalTrial.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clinical-trials : get all the clinicalTrials.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clinicalTrials in body
     */
    @RequestMapping(value = "/clinical-trials",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClinicalTrial> getAllClinicalTrials() {
        log.debug("REST request to get all ClinicalTrials");
        return clinicalTrialService.findAll();
    }

    /**
     * GET  /clinical-trials/:id : get the "id" clinicalTrial.
     *
     * @param id the id of the clinicalTrial to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clinicalTrial, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/clinical-trials/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClinicalTrial> getClinicalTrial(@PathVariable Long id) {
        log.debug("REST request to get ClinicalTrial : {}", id);
        ClinicalTrial clinicalTrial = clinicalTrialService.findOne(id);
        return Optional.ofNullable(clinicalTrial)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /clinical-trials/:id : delete the "id" clinicalTrial.
     *
     * @param id the id of the clinicalTrial to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/clinical-trials/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClinicalTrial(@PathVariable Long id) {
        log.debug("REST request to delete ClinicalTrial : {}", id);
        clinicalTrialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clinicalTrial", id.toString())).build();
    }

}
