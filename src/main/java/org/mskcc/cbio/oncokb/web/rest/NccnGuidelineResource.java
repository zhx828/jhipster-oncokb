package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.NccnGuideline;
import org.mskcc.cbio.oncokb.service.NccnGuidelineService;
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
 * REST controller for managing NccnGuideline.
 */
@RestController
@RequestMapping("/api")
public class NccnGuidelineResource {

    private final Logger log = LoggerFactory.getLogger(NccnGuidelineResource.class);
        
    @Inject
    private NccnGuidelineService nccnGuidelineService;

    /**
     * POST  /nccn-guidelines : Create a new nccnGuideline.
     *
     * @param nccnGuideline the nccnGuideline to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nccnGuideline, or with status 400 (Bad Request) if the nccnGuideline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/nccn-guidelines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NccnGuideline> createNccnGuideline(@RequestBody NccnGuideline nccnGuideline) throws URISyntaxException {
        log.debug("REST request to save NccnGuideline : {}", nccnGuideline);
        if (nccnGuideline.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("nccnGuideline", "idexists", "A new nccnGuideline cannot already have an ID")).body(null);
        }
        NccnGuideline result = nccnGuidelineService.save(nccnGuideline);
        return ResponseEntity.created(new URI("/api/nccn-guidelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("nccnGuideline", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nccn-guidelines : Updates an existing nccnGuideline.
     *
     * @param nccnGuideline the nccnGuideline to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nccnGuideline,
     * or with status 400 (Bad Request) if the nccnGuideline is not valid,
     * or with status 500 (Internal Server Error) if the nccnGuideline couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/nccn-guidelines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NccnGuideline> updateNccnGuideline(@RequestBody NccnGuideline nccnGuideline) throws URISyntaxException {
        log.debug("REST request to update NccnGuideline : {}", nccnGuideline);
        if (nccnGuideline.getId() == null) {
            return createNccnGuideline(nccnGuideline);
        }
        NccnGuideline result = nccnGuidelineService.save(nccnGuideline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("nccnGuideline", nccnGuideline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nccn-guidelines : get all the nccnGuidelines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nccnGuidelines in body
     */
    @RequestMapping(value = "/nccn-guidelines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NccnGuideline> getAllNccnGuidelines() {
        log.debug("REST request to get all NccnGuidelines");
        return nccnGuidelineService.findAll();
    }

    /**
     * GET  /nccn-guidelines/:id : get the "id" nccnGuideline.
     *
     * @param id the id of the nccnGuideline to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nccnGuideline, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/nccn-guidelines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NccnGuideline> getNccnGuideline(@PathVariable Long id) {
        log.debug("REST request to get NccnGuideline : {}", id);
        NccnGuideline nccnGuideline = nccnGuidelineService.findOne(id);
        return Optional.ofNullable(nccnGuideline)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nccn-guidelines/:id : delete the "id" nccnGuideline.
     *
     * @param id the id of the nccnGuideline to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/nccn-guidelines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNccnGuideline(@PathVariable Long id) {
        log.debug("REST request to delete NccnGuideline : {}", id);
        nccnGuidelineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("nccnGuideline", id.toString())).build();
    }

}
