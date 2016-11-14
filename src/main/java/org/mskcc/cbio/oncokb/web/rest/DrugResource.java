package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.Drug;
import org.mskcc.cbio.oncokb.service.DrugService;
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
 * REST controller for managing Drug.
 */
@RestController
@RequestMapping("/api")
public class DrugResource {

    private final Logger log = LoggerFactory.getLogger(DrugResource.class);
        
    @Inject
    private DrugService drugService;

    /**
     * POST  /drugs : Create a new drug.
     *
     * @param drug the drug to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drug, or with status 400 (Bad Request) if the drug has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drugs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drug> createDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to save Drug : {}", drug);
        if (drug.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drug", "idexists", "A new drug cannot already have an ID")).body(null);
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.created(new URI("/api/drugs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drug", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugs : Updates an existing drug.
     *
     * @param drug the drug to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drug,
     * or with status 400 (Bad Request) if the drug is not valid,
     * or with status 500 (Internal Server Error) if the drug couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drugs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drug> updateDrug(@RequestBody Drug drug) throws URISyntaxException {
        log.debug("REST request to update Drug : {}", drug);
        if (drug.getId() == null) {
            return createDrug(drug);
        }
        Drug result = drugService.save(drug);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drug", drug.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugs : get all the drugs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugs in body
     */
    @RequestMapping(value = "/drugs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Drug> getAllDrugs() {
        log.debug("REST request to get all Drugs");
        return drugService.findAll();
    }

    /**
     * GET  /drugs/:id : get the "id" drug.
     *
     * @param id the id of the drug to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drug, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/drugs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drug> getDrug(@PathVariable Long id) {
        log.debug("REST request to get Drug : {}", id);
        Drug drug = drugService.findOne(id);
        return Optional.ofNullable(drug)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drugs/:id : delete the "id" drug.
     *
     * @param id the id of the drug to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/drugs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrug(@PathVariable Long id) {
        log.debug("REST request to delete Drug : {}", id);
        drugService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drug", id.toString())).build();
    }

}
