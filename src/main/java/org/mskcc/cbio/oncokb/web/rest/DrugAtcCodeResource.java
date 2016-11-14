package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.DrugAtcCode;
import org.mskcc.cbio.oncokb.service.DrugAtcCodeService;
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
 * REST controller for managing DrugAtcCode.
 */
@RestController
@RequestMapping("/api")
public class DrugAtcCodeResource {

    private final Logger log = LoggerFactory.getLogger(DrugAtcCodeResource.class);
        
    @Inject
    private DrugAtcCodeService drugAtcCodeService;

    /**
     * POST  /drug-atc-codes : Create a new drugAtcCode.
     *
     * @param drugAtcCode the drugAtcCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugAtcCode, or with status 400 (Bad Request) if the drugAtcCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drug-atc-codes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DrugAtcCode> createDrugAtcCode(@RequestBody DrugAtcCode drugAtcCode) throws URISyntaxException {
        log.debug("REST request to save DrugAtcCode : {}", drugAtcCode);
        if (drugAtcCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drugAtcCode", "idexists", "A new drugAtcCode cannot already have an ID")).body(null);
        }
        DrugAtcCode result = drugAtcCodeService.save(drugAtcCode);
        return ResponseEntity.created(new URI("/api/drug-atc-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drugAtcCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drug-atc-codes : Updates an existing drugAtcCode.
     *
     * @param drugAtcCode the drugAtcCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugAtcCode,
     * or with status 400 (Bad Request) if the drugAtcCode is not valid,
     * or with status 500 (Internal Server Error) if the drugAtcCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drug-atc-codes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DrugAtcCode> updateDrugAtcCode(@RequestBody DrugAtcCode drugAtcCode) throws URISyntaxException {
        log.debug("REST request to update DrugAtcCode : {}", drugAtcCode);
        if (drugAtcCode.getId() == null) {
            return createDrugAtcCode(drugAtcCode);
        }
        DrugAtcCode result = drugAtcCodeService.save(drugAtcCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drugAtcCode", drugAtcCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drug-atc-codes : get all the drugAtcCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugAtcCodes in body
     */
    @RequestMapping(value = "/drug-atc-codes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DrugAtcCode> getAllDrugAtcCodes() {
        log.debug("REST request to get all DrugAtcCodes");
        return drugAtcCodeService.findAll();
    }

    /**
     * GET  /drug-atc-codes/:id : get the "id" drugAtcCode.
     *
     * @param id the id of the drugAtcCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugAtcCode, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/drug-atc-codes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DrugAtcCode> getDrugAtcCode(@PathVariable Long id) {
        log.debug("REST request to get DrugAtcCode : {}", id);
        DrugAtcCode drugAtcCode = drugAtcCodeService.findOne(id);
        return Optional.ofNullable(drugAtcCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drug-atc-codes/:id : delete the "id" drugAtcCode.
     *
     * @param id the id of the drugAtcCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/drug-atc-codes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrugAtcCode(@PathVariable Long id) {
        log.debug("REST request to delete DrugAtcCode : {}", id);
        drugAtcCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drugAtcCode", id.toString())).build();
    }

}
