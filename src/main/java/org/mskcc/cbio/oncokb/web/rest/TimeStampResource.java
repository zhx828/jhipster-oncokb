package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.TimeStamp;
import org.mskcc.cbio.oncokb.service.TimeStampService;
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
 * REST controller for managing TimeStamp.
 */
@RestController
@RequestMapping("/api")
public class TimeStampResource {

    private final Logger log = LoggerFactory.getLogger(TimeStampResource.class);
        
    @Inject
    private TimeStampService timeStampService;

    /**
     * POST  /time-stamps : Create a new timeStamp.
     *
     * @param timeStamp the timeStamp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeStamp, or with status 400 (Bad Request) if the timeStamp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/time-stamps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeStamp> createTimeStamp(@RequestBody TimeStamp timeStamp) throws URISyntaxException {
        log.debug("REST request to save TimeStamp : {}", timeStamp);
        if (timeStamp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("timeStamp", "idexists", "A new timeStamp cannot already have an ID")).body(null);
        }
        TimeStamp result = timeStampService.save(timeStamp);
        return ResponseEntity.created(new URI("/api/time-stamps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeStamp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-stamps : Updates an existing timeStamp.
     *
     * @param timeStamp the timeStamp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeStamp,
     * or with status 400 (Bad Request) if the timeStamp is not valid,
     * or with status 500 (Internal Server Error) if the timeStamp couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/time-stamps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeStamp> updateTimeStamp(@RequestBody TimeStamp timeStamp) throws URISyntaxException {
        log.debug("REST request to update TimeStamp : {}", timeStamp);
        if (timeStamp.getId() == null) {
            return createTimeStamp(timeStamp);
        }
        TimeStamp result = timeStampService.save(timeStamp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeStamp", timeStamp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-stamps : get all the timeStamps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timeStamps in body
     */
    @RequestMapping(value = "/time-stamps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeStamp> getAllTimeStamps() {
        log.debug("REST request to get all TimeStamps");
        return timeStampService.findAll();
    }

    /**
     * GET  /time-stamps/:id : get the "id" timeStamp.
     *
     * @param id the id of the timeStamp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeStamp, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/time-stamps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeStamp> getTimeStamp(@PathVariable Long id) {
        log.debug("REST request to get TimeStamp : {}", id);
        TimeStamp timeStamp = timeStampService.findOne(id);
        return Optional.ofNullable(timeStamp)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /time-stamps/:id : delete the "id" timeStamp.
     *
     * @param id the id of the timeStamp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/time-stamps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeStamp(@PathVariable Long id) {
        log.debug("REST request to delete TimeStamp : {}", id);
        timeStampService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeStamp", id.toString())).build();
    }

}
