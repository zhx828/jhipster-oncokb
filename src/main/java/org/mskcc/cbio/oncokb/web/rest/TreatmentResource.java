package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.Treatment;
import org.mskcc.cbio.oncokb.service.TreatmentService;
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
 * REST controller for managing Treatment.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);
        
    @Inject
    private TreatmentService treatmentService;

    /**
     * POST  /treatments : Create a new treatment.
     *
     * @param treatment the treatment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatment, or with status 400 (Bad Request) if the treatment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> createTreatment(@RequestBody Treatment treatment) throws URISyntaxException {
        log.debug("REST request to save Treatment : {}", treatment);
        if (treatment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("treatment", "idexists", "A new treatment cannot already have an ID")).body(null);
        }
        Treatment result = treatmentService.save(treatment);
        return ResponseEntity.created(new URI("/api/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("treatment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatments : Updates an existing treatment.
     *
     * @param treatment the treatment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatment,
     * or with status 400 (Bad Request) if the treatment is not valid,
     * or with status 500 (Internal Server Error) if the treatment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> updateTreatment(@RequestBody Treatment treatment) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatment);
        if (treatment.getId() == null) {
            return createTreatment(treatment);
        }
        Treatment result = treatmentService.save(treatment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("treatment", treatment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatments : get all the treatments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of treatments in body
     */
    @RequestMapping(value = "/treatments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Treatment> getAllTreatments() {
        log.debug("REST request to get all Treatments");
        return treatmentService.findAll();
    }

    /**
     * GET  /treatments/:id : get the "id" treatment.
     *
     * @param id the id of the treatment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/treatments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        Treatment treatment = treatmentService.findOne(id);
        return Optional.ofNullable(treatment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /treatments/:id : delete the "id" treatment.
     *
     * @param id the id of the treatment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/treatments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("treatment", id.toString())).build();
    }

}
