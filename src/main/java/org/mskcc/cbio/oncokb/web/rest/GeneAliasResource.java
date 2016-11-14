package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.GeneAlias;
import org.mskcc.cbio.oncokb.service.GeneAliasService;
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
 * REST controller for managing GeneAlias.
 */
@RestController
@RequestMapping("/api")
public class GeneAliasResource {

    private final Logger log = LoggerFactory.getLogger(GeneAliasResource.class);
        
    @Inject
    private GeneAliasService geneAliasService;

    /**
     * POST  /gene-aliases : Create a new geneAlias.
     *
     * @param geneAlias the geneAlias to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geneAlias, or with status 400 (Bad Request) if the geneAlias has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gene-aliases",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneAlias> createGeneAlias(@RequestBody GeneAlias geneAlias) throws URISyntaxException {
        log.debug("REST request to save GeneAlias : {}", geneAlias);
        if (geneAlias.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("geneAlias", "idexists", "A new geneAlias cannot already have an ID")).body(null);
        }
        GeneAlias result = geneAliasService.save(geneAlias);
        return ResponseEntity.created(new URI("/api/gene-aliases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("geneAlias", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gene-aliases : Updates an existing geneAlias.
     *
     * @param geneAlias the geneAlias to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geneAlias,
     * or with status 400 (Bad Request) if the geneAlias is not valid,
     * or with status 500 (Internal Server Error) if the geneAlias couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/gene-aliases",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneAlias> updateGeneAlias(@RequestBody GeneAlias geneAlias) throws URISyntaxException {
        log.debug("REST request to update GeneAlias : {}", geneAlias);
        if (geneAlias.getId() == null) {
            return createGeneAlias(geneAlias);
        }
        GeneAlias result = geneAliasService.save(geneAlias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("geneAlias", geneAlias.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gene-aliases : get all the geneAliases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of geneAliases in body
     */
    @RequestMapping(value = "/gene-aliases",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GeneAlias> getAllGeneAliases() {
        log.debug("REST request to get all GeneAliases");
        return geneAliasService.findAll();
    }

    /**
     * GET  /gene-aliases/:id : get the "id" geneAlias.
     *
     * @param id the id of the geneAlias to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geneAlias, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/gene-aliases/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GeneAlias> getGeneAlias(@PathVariable Long id) {
        log.debug("REST request to get GeneAlias : {}", id);
        GeneAlias geneAlias = geneAliasService.findOne(id);
        return Optional.ofNullable(geneAlias)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gene-aliases/:id : delete the "id" geneAlias.
     *
     * @param id the id of the geneAlias to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/gene-aliases/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGeneAlias(@PathVariable Long id) {
        log.debug("REST request to delete GeneAlias : {}", id);
        geneAliasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("geneAlias", id.toString())).build();
    }

}
