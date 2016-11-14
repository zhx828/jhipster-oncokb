package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.ApprovedIndication;
import org.mskcc.cbio.oncokb.service.ApprovedIndicationService;
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
 * REST controller for managing ApprovedIndication.
 */
@RestController
@RequestMapping("/api")
public class ApprovedIndicationResource {

    private final Logger log = LoggerFactory.getLogger(ApprovedIndicationResource.class);
        
    @Inject
    private ApprovedIndicationService approvedIndicationService;

    /**
     * POST  /approved-indications : Create a new approvedIndication.
     *
     * @param approvedIndication the approvedIndication to create
     * @return the ResponseEntity with status 201 (Created) and with body the new approvedIndication, or with status 400 (Bad Request) if the approvedIndication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/approved-indications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApprovedIndication> createApprovedIndication(@RequestBody ApprovedIndication approvedIndication) throws URISyntaxException {
        log.debug("REST request to save ApprovedIndication : {}", approvedIndication);
        if (approvedIndication.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("approvedIndication", "idexists", "A new approvedIndication cannot already have an ID")).body(null);
        }
        ApprovedIndication result = approvedIndicationService.save(approvedIndication);
        return ResponseEntity.created(new URI("/api/approved-indications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("approvedIndication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /approved-indications : Updates an existing approvedIndication.
     *
     * @param approvedIndication the approvedIndication to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated approvedIndication,
     * or with status 400 (Bad Request) if the approvedIndication is not valid,
     * or with status 500 (Internal Server Error) if the approvedIndication couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/approved-indications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApprovedIndication> updateApprovedIndication(@RequestBody ApprovedIndication approvedIndication) throws URISyntaxException {
        log.debug("REST request to update ApprovedIndication : {}", approvedIndication);
        if (approvedIndication.getId() == null) {
            return createApprovedIndication(approvedIndication);
        }
        ApprovedIndication result = approvedIndicationService.save(approvedIndication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("approvedIndication", approvedIndication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /approved-indications : get all the approvedIndications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of approvedIndications in body
     */
    @RequestMapping(value = "/approved-indications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ApprovedIndication> getAllApprovedIndications() {
        log.debug("REST request to get all ApprovedIndications");
        return approvedIndicationService.findAll();
    }

    /**
     * GET  /approved-indications/:id : get the "id" approvedIndication.
     *
     * @param id the id of the approvedIndication to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the approvedIndication, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/approved-indications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApprovedIndication> getApprovedIndication(@PathVariable Long id) {
        log.debug("REST request to get ApprovedIndication : {}", id);
        ApprovedIndication approvedIndication = approvedIndicationService.findOne(id);
        return Optional.ofNullable(approvedIndication)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /approved-indications/:id : delete the "id" approvedIndication.
     *
     * @param id the id of the approvedIndication to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/approved-indications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApprovedIndication(@PathVariable Long id) {
        log.debug("REST request to delete ApprovedIndication : {}", id);
        approvedIndicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("approvedIndication", id.toString())).build();
    }

}
