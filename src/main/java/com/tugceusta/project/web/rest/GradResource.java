package com.tugceusta.project.web.rest;

import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.repository.GradRepository;
import com.tugceusta.project.service.GradService;
import com.tugceusta.project.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tugceusta.project.domain.Grad}.
 */
@RestController
@RequestMapping("/api/grads")
public class GradResource {

    private static final Logger log = LoggerFactory.getLogger(GradResource.class);

    private static final String ENTITY_NAME = "grad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradService gradService;

    private final GradRepository gradRepository;

    public GradResource(GradService gradService, GradRepository gradRepository) {
        this.gradService = gradService;
        this.gradRepository = gradRepository;
    }

    /**
     * {@code POST  /grads} : Create a new grad.
     *
     * @param grad the grad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grad, or with status {@code 400 (Bad Request)} if the grad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Grad> createGrad(@RequestBody Grad grad) throws URISyntaxException {
        log.debug("REST request to save Grad : {}", grad);
        if (grad.getId() != null) {
            throw new BadRequestAlertException("A new grad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grad = gradService.save(grad);
        return ResponseEntity.created(new URI("/api/grads/" + grad.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, grad.getId().toString()))
            .body(grad);
    }

    /**
     * {@code PUT  /grads/:id} : Updates an existing grad.
     *
     * @param id the id of the grad to save.
     * @param grad the grad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grad,
     * or with status {@code 400 (Bad Request)} if the grad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Grad> updateGrad(@PathVariable(value = "id", required = false) final Long id, @RequestBody Grad grad)
        throws URISyntaxException {
        log.debug("REST request to update Grad : {}, {}", id, grad);
        if (grad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gradRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grad = gradService.update(grad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grad.getId().toString()))
            .body(grad);
    }

    /**
     * {@code PATCH  /grads/:id} : Partial updates given fields of an existing grad, field will ignore if it is null
     *
     * @param id the id of the grad to save.
     * @param grad the grad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grad,
     * or with status {@code 400 (Bad Request)} if the grad is not valid,
     * or with status {@code 404 (Not Found)} if the grad is not found,
     * or with status {@code 500 (Internal Server Error)} if the grad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Grad> partialUpdateGrad(@PathVariable(value = "id", required = false) final Long id, @RequestBody Grad grad)
        throws URISyntaxException {
        log.debug("REST request to partial update Grad partially : {}, {}", id, grad);
        if (grad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gradRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Grad> result = gradService.partialUpdate(grad);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grad.getId().toString())
        );
    }

    /**
     * {@code GET  /grads} : get all the grads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grads in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Grad>> getAllGrads(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Grads");
        Page<Grad> page = gradService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grads/:id} : get the "id" grad.
     *
     * @param id the id of the grad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grad> getGrad(@PathVariable("id") Long id) {
        log.debug("REST request to get Grad : {}", id);
        Optional<Grad> grad = gradService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grad);
    }

    /**
     * {@code DELETE  /grads/:id} : delete the "id" grad.
     *
     * @param id the id of the grad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrad(@PathVariable("id") Long id) {
        log.debug("REST request to delete Grad : {}", id);
        gradService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
