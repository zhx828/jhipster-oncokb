package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.VariantConsequence;
import org.mskcc.cbio.oncokb.service.VariantConsequenceService;
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
 * REST controller for managing VariantConsequence.
 */
@RestController
@RequestMapping("/api")
public class VariantConsequenceResource {

    private final Logger log = LoggerFactory.getLogger(VariantConsequenceResource.class);
        
    @Inject
    private VariantConsequenceService variantConsequenceService;

    /**
     * POST  /variant-consequences : Create a new variantConsequence.
     *
     * @param variantConsequence the variantConsequence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variantConsequence, or with status 400 (Bad Request) if the variantConsequence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/variant-consequences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VariantConsequence> createVariantConsequence(@RequestBody VariantConsequence variantConsequence) throws URISyntaxException {
        log.debug("REST request to save VariantConsequence : {}", variantConsequence);
        if (variantConsequence.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("variantConsequence", "idexists", "A new variantConsequence cannot already have an ID")).body(null);
        }
        VariantConsequence result = variantConsequenceService.save(variantConsequence);
        return ResponseEntity.created(new URI("/api/variant-consequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("variantConsequence", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variant-consequences : Updates an existing variantConsequence.
     *
     * @param variantConsequence the variantConsequence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variantConsequence,
     * or with status 400 (Bad Request) if the variantConsequence is not valid,
     * or with status 500 (Internal Server Error) if the variantConsequence couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/variant-consequences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VariantConsequence> updateVariantConsequence(@RequestBody VariantConsequence variantConsequence) throws URISyntaxException {
        log.debug("REST request to update VariantConsequence : {}", variantConsequence);
        if (variantConsequence.getId() == null) {
            return createVariantConsequence(variantConsequence);
        }
        VariantConsequence result = variantConsequenceService.save(variantConsequence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("variantConsequence", variantConsequence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variant-consequences : get all the variantConsequences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of variantConsequences in body
     */
    @RequestMapping(value = "/variant-consequences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VariantConsequence> getAllVariantConsequences() {
        log.debug("REST request to get all VariantConsequences");
        return variantConsequenceService.findAll();
    }

    /**
     * GET  /variant-consequences/:id : get the "id" variantConsequence.
     *
     * @param id the id of the variantConsequence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variantConsequence, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/variant-consequences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VariantConsequence> getVariantConsequence(@PathVariable Long id) {
        log.debug("REST request to get VariantConsequence : {}", id);
        VariantConsequence variantConsequence = variantConsequenceService.findOne(id);
        return Optional.ofNullable(variantConsequence)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /variant-consequences/:id : delete the "id" variantConsequence.
     *
     * @param id the id of the variantConsequence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/variant-consequences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVariantConsequence(@PathVariable Long id) {
        log.debug("REST request to delete VariantConsequence : {}", id);
        variantConsequenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("variantConsequence", id.toString())).build();
    }

}
