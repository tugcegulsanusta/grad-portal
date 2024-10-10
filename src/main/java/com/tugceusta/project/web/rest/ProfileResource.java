package com.tugceusta.project.web.rest;

import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.domain.Skill;
import com.tugceusta.project.dto.GradWithInfo;
import com.tugceusta.project.repository.GradRepository;
import com.tugceusta.project.repository.GraduationRepository;
import com.tugceusta.project.repository.SkillRepository;
import com.tugceusta.project.service.GradService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link Grad}.
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileResource {

    private static final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GradService gradService;

    private final GradRepository gradRepository;

    private final SkillRepository skillRepository;

    private final GraduationRepository graduationRepository;

    public ProfileResource(
        GradService gradService,
        GradRepository gradRepository,
        SkillRepository skillRepository,
        GraduationRepository graduationRepository
    ) {
        this.gradService = gradService;
        this.gradRepository = gradRepository;
        this.skillRepository = skillRepository;
        this.graduationRepository = graduationRepository;
    }

    /**
     * {@code GET  /profile/:id} : get the "id" grad.
     *
     * @param id the id of the grad profile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<GradWithInfo> getGradWithInfo(@PathVariable("id") Long id) {
        log.debug("REST request to get Grad : {}", id);
        Optional<Grad> grad = gradService.findOne(id);
        Optional<GradWithInfo> gradWithInfoOptional = Optional.empty();
        if (grad.isPresent()) {
            Grad gradObj = grad.get();
            GradWithInfo gradWithInfo = new GradWithInfo(gradObj);
            gradWithInfo.setSkillList(skillRepository.findAllByGradId(gradObj));
            gradWithInfo.setGraduationList(graduationRepository.findAllByGradId(gradObj));
            gradWithInfoOptional = Optional.of(gradWithInfo);
        }
        return ResponseUtil.wrapOrNotFound(gradWithInfoOptional);
    }
}
