package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.Evidence;
import org.mskcc.cbio.oncokb.service.EvidenceService;
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
 * REST controller for managing Evidence.
 */
@RestController
@RequestMapping("/api")
public class EvidenceResource {

    private final Logger log = LoggerFactory.getLogger(EvidenceResource.class);
        
    @Inject
    private EvidenceService evidenceService;

    /**
     * POST  /evidences : Create a new evidence.
     *
     * @param evidence the evidence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evidence, or with status 400 (Bad Request) if the evidence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/evidences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evidence> createEvidence(@RequestBody Evidence evidence) throws URISyntaxException {
        log.debug("REST request to save Evidence : {}", evidence);
        if (evidence.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evidence", "idexists", "A new evidence cannot already have an ID")).body(null);
        }
        Evidence result = evidenceService.save(evidence);
        return ResponseEntity.created(new URI("/api/evidences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evidence", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evidences : Updates an existing evidence.
     *
     * @param evidence the evidence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evidence,
     * or with status 400 (Bad Request) if the evidence is not valid,
     * or with status 500 (Internal Server Error) if the evidence couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/evidences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evidence> updateEvidence(@RequestBody Evidence evidence) throws URISyntaxException {
        log.debug("REST request to update Evidence : {}", evidence);
        if (evidence.getId() == null) {
            return createEvidence(evidence);
        }
        Evidence result = evidenceService.save(evidence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evidence", evidence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evidences : get all the evidences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evidences in body
     */
    @RequestMapping(value = "/evidences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Evidence> getAllEvidences() {
        log.debug("REST request to get all Evidences");
        return evidenceService.findAll();
    }

    /**
     * GET  /evidences/:id : get the "id" evidence.
     *
     * @param id the id of the evidence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evidence, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/evidences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evidence> getEvidence(@PathVariable Long id) {
        log.debug("REST request to get Evidence : {}", id);
        Evidence evidence = evidenceService.findOne(id);
        return Optional.ofNullable(evidence)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evidences/:id : delete the "id" evidence.
     *
     * @param id the id of the evidence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/evidences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEvidence(@PathVariable Long id) {
        log.debug("REST request to delete Evidence : {}", id);
        evidenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evidence", id.toString())).build();
    }

}
