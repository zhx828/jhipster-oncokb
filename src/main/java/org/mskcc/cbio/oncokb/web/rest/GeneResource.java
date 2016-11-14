package org.mskcc.cbio.oncokb.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.mskcc.cbio.oncokb.domain.Gene;
import org.mskcc.cbio.oncokb.service.GeneService;
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
 * REST controller for managing Gene.
 */
@RestController
@RequestMapping("/api")
public class GeneResource {

    private final Logger log = LoggerFactory.getLogger(GeneResource.class);
        
    @Inject
    private GeneService geneService;

    /**
     * POST  /genes : Create a new gene.
     *
     * @param gene the gene to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gene, or with status 400 (Bad Request) if the gene has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/genes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gene> createGene(@RequestBody Gene gene) throws URISyntaxException {
        log.debug("REST request to save Gene : {}", gene);
        if (gene.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gene", "idexists", "A new gene cannot already have an ID")).body(null);
        }
        Gene result = geneService.save(gene);
        return ResponseEntity.created(new URI("/api/genes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gene", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /genes : Updates an existing gene.
     *
     * @param gene the gene to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gene,
     * or with status 400 (Bad Request) if the gene is not valid,
     * or with status 500 (Internal Server Error) if the gene couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/genes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gene> updateGene(@RequestBody Gene gene) throws URISyntaxException {
        log.debug("REST request to update Gene : {}", gene);
        if (gene.getId() == null) {
            return createGene(gene);
        }
        Gene result = geneService.save(gene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gene", gene.getId().toString()))
            .body(result);
    }

    /**
     * GET  /genes : get all the genes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of genes in body
     */
    @RequestMapping(value = "/genes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Gene> getAllGenes() {
        log.debug("REST request to get all Genes");
        return geneService.findAll();
    }

    /**
     * GET  /genes/:id : get the "id" gene.
     *
     * @param id the id of the gene to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gene, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/genes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Gene> getGene(@PathVariable Long id) {
        log.debug("REST request to get Gene : {}", id);
        Gene gene = geneService.findOne(id);
        return Optional.ofNullable(gene)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /genes/:id : delete the "id" gene.
     *
     * @param id the id of the gene to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/genes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGene(@PathVariable Long id) {
        log.debug("REST request to delete Gene : {}", id);
        geneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gene", id.toString())).build();
    }

}
