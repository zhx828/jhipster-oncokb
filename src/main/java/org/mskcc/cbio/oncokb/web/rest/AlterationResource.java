package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.Alteration;
import org.mskcc.cbio.oncokb.service.AlterationService;
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
 * REST controller for managing Alteration.
 */
@RestController
@RequestMapping("/api")
public class AlterationResource {

    private final Logger log = LoggerFactory.getLogger(AlterationResource.class);
        
    @Inject
    private AlterationService alterationService;

    /**
     * POST  /alterations : Create a new alteration.
     *
     * @param alteration the alteration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alteration, or with status 400 (Bad Request) if the alteration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/alterations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alteration> createAlteration(@RequestBody Alteration alteration) throws URISyntaxException {
        log.debug("REST request to save Alteration : {}", alteration);
        if (alteration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alteration", "idexists", "A new alteration cannot already have an ID")).body(null);
        }
        Alteration result = alterationService.save(alteration);
        return ResponseEntity.created(new URI("/api/alterations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alteration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alterations : Updates an existing alteration.
     *
     * @param alteration the alteration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alteration,
     * or with status 400 (Bad Request) if the alteration is not valid,
     * or with status 500 (Internal Server Error) if the alteration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/alterations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alteration> updateAlteration(@RequestBody Alteration alteration) throws URISyntaxException {
        log.debug("REST request to update Alteration : {}", alteration);
        if (alteration.getId() == null) {
            return createAlteration(alteration);
        }
        Alteration result = alterationService.save(alteration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alteration", alteration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alterations : get all the alterations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alterations in body
     */
    @RequestMapping(value = "/alterations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Alteration> getAllAlterations() {
        log.debug("REST request to get all Alterations");
        return alterationService.findAll();
    }

    /**
     * GET  /alterations/:id : get the "id" alteration.
     *
     * @param id the id of the alteration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alteration, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/alterations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alteration> getAlteration(@PathVariable Long id) {
        log.debug("REST request to get Alteration : {}", id);
        Alteration alteration = alterationService.findOne(id);
        return Optional.ofNullable(alteration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alterations/:id : delete the "id" alteration.
     *
     * @param id the id of the alteration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/alterations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlteration(@PathVariable Long id) {
        log.debug("REST request to delete Alteration : {}", id);
        alterationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alteration", id.toString())).build();
    }

}
