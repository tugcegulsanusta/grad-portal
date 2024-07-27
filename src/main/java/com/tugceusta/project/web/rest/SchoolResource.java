package com.tugceusta.project.web.rest;

import com.tugceusta.project.domain.School;
import com.tugceusta.project.repository.SchoolRepository;
import com.tugceusta.project.service.SchoolService;
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
 * REST controller for managing {@link com.tugceusta.project.domain.School}.
 */
@RestController
@RequestMapping("/api/schools")
public class SchoolResource {

    private static final Logger log = LoggerFactory.getLogger(SchoolResource.class);

    private static final String ENTITY_NAME = "school";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolService schoolService;

    private final SchoolRepository schoolRepository;

    public SchoolResource(SchoolService schoolService, SchoolRepository schoolRepository) {
        this.schoolService = schoolService;
        this.schoolRepository = schoolRepository;
    }

    /**
     * {@code POST  /schools} : Create a new school.
     *
     * @param school the school to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new school, or with status {@code 400 (Bad Request)} if the school has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<School> createSchool(@Valid @RequestBody School school) throws URISyntaxException {
        log.debug("REST request to save School : {}", school);
        if (school.getId() != null) {
            throw new BadRequestAlertException("A new school cannot already have an ID", ENTITY_NAME, "idexists");
        }
        school = schoolService.save(school);
        return ResponseEntity.created(new URI("/api/schools/" + school.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, school.getId().toString()))
            .body(school);
    }

    /**
     * {@code PUT  /schools/:id} : Updates an existing school.
     *
     * @param id the id of the school to save.
     * @param school the school to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated school,
     * or with status {@code 400 (Bad Request)} if the school is not valid,
     * or with status {@code 500 (Internal Server Error)} if the school couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody School school
    ) throws URISyntaxException {
        log.debug("REST request to update School : {}, {}", id, school);
        if (school.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, school.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schoolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        school = schoolService.update(school);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, school.getId().toString()))
            .body(school);
    }

    /**
     * {@code PATCH  /schools/:id} : Partial updates given fields of an existing school, field will ignore if it is null
     *
     * @param id the id of the school to save.
     * @param school the school to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated school,
     * or with status {@code 400 (Bad Request)} if the school is not valid,
     * or with status {@code 404 (Not Found)} if the school is not found,
     * or with status {@code 500 (Internal Server Error)} if the school couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<School> partialUpdateSchool(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody School school
    ) throws URISyntaxException {
        log.debug("REST request to partial update School partially : {}, {}", id, school);
        if (school.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, school.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schoolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<School> result = schoolService.partialUpdate(school);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, school.getId().toString())
        );
    }

    /**
     * {@code GET  /schools} : get all the schools.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schools in body.
     */
    @GetMapping("")
    public ResponseEntity<List<School>> getAllSchools(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Schools");
        Page<School> page = schoolService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schools/:id} : get the "id" school.
     *
     * @param id the id of the school to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the school, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<School> getSchool(@PathVariable("id") Long id) {
        log.debug("REST request to get School : {}", id);
        Optional<School> school = schoolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(school);
    }

    /**
     * {@code DELETE  /schools/:id} : delete the "id" school.
     *
     * @param id the id of the school to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable("id") Long id) {
        log.debug("REST request to delete School : {}", id);
        schoolService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
