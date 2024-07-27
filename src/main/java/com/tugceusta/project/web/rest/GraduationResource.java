package com.tugceusta.project.web.rest;

import com.tugceusta.project.domain.Graduation;
import com.tugceusta.project.repository.GraduationRepository;
import com.tugceusta.project.service.GraduationService;
import com.tugceusta.project.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.tugceusta.project.domain.Graduation}.
 */
@RestController
@RequestMapping("/api/graduations")
public class GraduationResource {

    private static final Logger log = LoggerFactory.getLogger(GraduationResource.class);

    private static final String ENTITY_NAME = "graduation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GraduationService graduationService;

    private final GraduationRepository graduationRepository;

    public GraduationResource(GraduationService graduationService, GraduationRepository graduationRepository) {
        this.graduationService = graduationService;
        this.graduationRepository = graduationRepository;
    }

    /**
     * {@code POST  /graduations} : Create a new graduation.
     *
     * @param graduation the graduation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new graduation, or with status {@code 400 (Bad Request)} if the graduation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Graduation> createGraduation(@Valid @RequestBody Graduation graduation) throws URISyntaxException {
        log.debug("REST request to save Graduation : {}", graduation);
        if (graduation.getId() != null) {
            throw new BadRequestAlertException("A new graduation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        graduation = graduationService.save(graduation);
        return ResponseEntity.created(new URI("/api/graduations/" + graduation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, graduation.getId().toString()))
            .body(graduation);
    }

    /**
     * {@code PUT  /graduations/:id} : Updates an existing graduation.
     *
     * @param id the id of the graduation to save.
     * @param graduation the graduation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated graduation,
     * or with status {@code 400 (Bad Request)} if the graduation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the graduation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Graduation> updateGraduation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Graduation graduation
    ) throws URISyntaxException {
        log.debug("REST request to update Graduation : {}, {}", id, graduation);
        if (graduation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, graduation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!graduationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        graduation = graduationService.update(graduation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, graduation.getId().toString()))
            .body(graduation);
    }

    /**
     * {@code PATCH  /graduations/:id} : Partial updates given fields of an existing graduation, field will ignore if it is null
     *
     * @param id the id of the graduation to save.
     * @param graduation the graduation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated graduation,
     * or with status {@code 400 (Bad Request)} if the graduation is not valid,
     * or with status {@code 404 (Not Found)} if the graduation is not found,
     * or with status {@code 500 (Internal Server Error)} if the graduation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Graduation> partialUpdateGraduation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Graduation graduation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Graduation partially : {}, {}", id, graduation);
        if (graduation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, graduation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!graduationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Graduation> result = graduationService.partialUpdate(graduation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, graduation.getId().toString())
        );
    }

    /**
     * {@code GET  /graduations} : get all the graduations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of graduations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Graduation>> getAllGraduations(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Graduations");
        Page<Graduation> page = graduationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /graduations/:id} : get the "id" graduation.
     *
     * @param id the id of the graduation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the graduation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Graduation> getGraduation(@PathVariable("id") Long id) {
        log.debug("REST request to get Graduation : {}", id);
        Optional<Graduation> graduation = graduationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(graduation);
    }

    /**
     * {@code DELETE  /graduations/:id} : delete the "id" graduation.
     *
     * @param id the id of the graduation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGraduation(@PathVariable("id") Long id) {
        log.debug("REST request to delete Graduation : {}", id);
        graduationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
