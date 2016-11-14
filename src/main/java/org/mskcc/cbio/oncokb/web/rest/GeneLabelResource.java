package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.GeneLabel;
import org.mskcc.cbio.oncokb.service.GeneLabelService;
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
 * REST controller for managing GeneLabel.
 */
@RestController
@RequestMapping("/api")
public class GeneLabelResource {

    private final Logger log = LoggerFactory.getLogger(GeneLabelResource.class);
        
    @Inject
    private GeneLabelService geneLabelService;

    /**
     * POST  /gene-labels : Create a new geneLabel.
     *
     * @param geneLabel the geneLabel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geneLabel, or with status 400 (Bad Request) if the geneLabel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gene-labels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneLabel> createGeneLabel(@RequestBody GeneLabel geneLabel) throws URISyntaxException {
        log.debug("REST request to save GeneLabel : {}", geneLabel);
        if (geneLabel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("geneLabel", "idexists", "A new geneLabel cannot already have an ID")).body(null);
        }
        GeneLabel result = geneLabelService.save(geneLabel);
        return ResponseEntity.created(new URI("/api/gene-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("geneLabel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gene-labels : Updates an existing geneLabel.
     *
     * @param geneLabel the geneLabel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geneLabel,
     * or with status 400 (Bad Request) if the geneLabel is not valid,
     * or with status 500 (Internal Server Error) if the geneLabel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gene-labels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneLabel> updateGeneLabel(@RequestBody GeneLabel geneLabel) throws URISyntaxException {
        log.debug("REST request to update GeneLabel : {}", geneLabel);
        if (geneLabel.getId() == null) {
            return createGeneLabel(geneLabel);
        }
        GeneLabel result = geneLabelService.save(geneLabel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("geneLabel", geneLabel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gene-labels : get all the geneLabels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of geneLabels in body
     */
    @RequestMapping(value = "/gene-labels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GeneLabel> getAllGeneLabels() {
        log.debug("REST request to get all GeneLabels");
        return geneLabelService.findAll();
    }

    /**
     * GET  /gene-labels/:id : get the "id" geneLabel.
     *
     * @param id the id of the geneLabel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geneLabel, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/gene-labels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneLabel> getGeneLabel(@PathVariable Long id) {
        log.debug("REST request to get GeneLabel : {}", id);
        GeneLabel geneLabel = geneLabelService.findOne(id);
        return Optional.ofNullable(geneLabel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gene-labels/:id : delete the "id" geneLabel.
     *
     * @param id the id of the geneLabel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/gene-labels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGeneLabel(@PathVariable Long id) {
        log.debug("REST request to delete GeneLabel : {}", id);
        geneLabelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("geneLabel", id.toString())).build();
    }

}
