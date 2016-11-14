package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.PortalAlteration;
import org.mskcc.cbio.oncokb.service.PortalAlterationService;
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
 * REST controller for managing PortalAlteration.
 */
@RestController
@RequestMapping("/api")
public class PortalAlterationResource {

    private final Logger log = LoggerFactory.getLogger(PortalAlterationResource.class);
        
    @Inject
    private PortalAlterationService portalAlterationService;

    /**
     * POST  /portal-alterations : Create a new portalAlteration.
     *
     * @param portalAlteration the portalAlteration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new portalAlteration, or with status 400 (Bad Request) if the portalAlteration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/portal-alterations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortalAlteration> createPortalAlteration(@RequestBody PortalAlteration portalAlteration) throws URISyntaxException {
        log.debug("REST request to save PortalAlteration : {}", portalAlteration);
        if (portalAlteration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("portalAlteration", "idexists", "A new portalAlteration cannot already have an ID")).body(null);
        }
        PortalAlteration result = portalAlterationService.save(portalAlteration);
        return ResponseEntity.created(new URI("/api/portal-alterations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("portalAlteration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /portal-alterations : Updates an existing portalAlteration.
     *
     * @param portalAlteration the portalAlteration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated portalAlteration,
     * or with status 400 (Bad Request) if the portalAlteration is not valid,
     * or with status 500 (Internal Server Error) if the portalAlteration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/portal-alterations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortalAlteration> updatePortalAlteration(@RequestBody PortalAlteration portalAlteration) throws URISyntaxException {
        log.debug("REST request to update PortalAlteration : {}", portalAlteration);
        if (portalAlteration.getId() == null) {
            return createPortalAlteration(portalAlteration);
        }
        PortalAlteration result = portalAlterationService.save(portalAlteration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("portalAlteration", portalAlteration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /portal-alterations : get all the portalAlterations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of portalAlterations in body
     */
    @RequestMapping(value = "/portal-alterations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PortalAlteration> getAllPortalAlterations() {
        log.debug("REST request to get all PortalAlterations");
        return portalAlterationService.findAll();
    }

    /**
     * GET  /portal-alterations/:id : get the "id" portalAlteration.
     *
     * @param id the id of the portalAlteration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the portalAlteration, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/portal-alterations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PortalAlteration> getPortalAlteration(@PathVariable Long id) {
        log.debug("REST request to get PortalAlteration : {}", id);
        PortalAlteration portalAlteration = portalAlterationService.findOne(id);
        return Optional.ofNullable(portalAlteration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /portal-alterations/:id : delete the "id" portalAlteration.
     *
     * @param id the id of the portalAlteration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/portal-alterations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePortalAlteration(@PathVariable Long id) {
        log.debug("REST request to delete PortalAlteration : {}", id);
        portalAlterationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("portalAlteration", id.toString())).build();
    }

}
