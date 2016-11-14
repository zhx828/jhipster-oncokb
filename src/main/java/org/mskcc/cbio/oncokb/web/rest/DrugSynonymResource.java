package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.DrugSynonym;
import org.mskcc.cbio.oncokb.service.DrugSynonymService;
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
 * REST controller for managing DrugSynonym.
 */
@RestController
@RequestMapping("/api")
public class DrugSynonymResource {

    private final Logger log = LoggerFactory.getLogger(DrugSynonymResource.class);
        
    @Inject
    private DrugSynonymService drugSynonymService;

    /**
     * POST  /drug-synonyms : Create a new drugSynonym.
     *
     * @param drugSynonym the drugSynonym to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugSynonym, or with status 400 (Bad Request) if the drugSynonym has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drug-synonyms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DrugSynonym> createDrugSynonym(@RequestBody DrugSynonym drugSynonym) throws URISyntaxException {
        log.debug("REST request to save DrugSynonym : {}", drugSynonym);
        if (drugSynonym.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("drugSynonym", "idexists", "A new drugSynonym cannot already have an ID")).body(null);
        }
        DrugSynonym result = drugSynonymService.save(drugSynonym);
        return ResponseEntity.created(new URI("/api/drug-synonyms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drugSynonym", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drug-synonyms : Updates an existing drugSynonym.
     *
     * @param drugSynonym the drugSynonym to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugSynonym,
     * or with status 400 (Bad Request) if the drugSynonym is not valid,
     * or with status 500 (Internal Server Error) if the drugSynonym couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/drug-synonyms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DrugSynonym> updateDrugSynonym(@RequestBody DrugSynonym drugSynonym) throws URISyntaxException {
        log.debug("REST request to update DrugSynonym : {}", drugSynonym);
        if (drugSynonym.getId() == null) {
            return createDrugSynonym(drugSynonym);
        }
        DrugSynonym result = drugSynonymService.save(drugSynonym);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drugSynonym", drugSynonym.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drug-synonyms : get all the drugSynonyms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugSynonyms in body
     */
    @RequestMapping(value = "/drug-synonyms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DrugSynonym> getAllDrugSynonyms() {
        log.debug("REST request to get all DrugSynonyms");
        return drugSynonymService.findAll();
    }

    /**
     * GET  /drug-synonyms/:id : get the "id" drugSynonym.
     *
     * @param id the id of the drugSynonym to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugSynonym, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/drug-synonyms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DrugSynonym> getDrugSynonym(@PathVariable Long id) {
        log.debug("REST request to get DrugSynonym : {}", id);
        DrugSynonym drugSynonym = drugSynonymService.findOne(id);
        return Optional.ofNullable(drugSynonym)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drug-synonyms/:id : delete the "id" drugSynonym.
     *
     * @param id the id of the drugSynonym to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/drug-synonyms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrugSynonym(@PathVariable Long id) {
        log.debug("REST request to delete DrugSynonym : {}", id);
        drugSynonymService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drugSynonym", id.toString())).build();
    }

}
