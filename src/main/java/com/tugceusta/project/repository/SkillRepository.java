package com.tugceusta.project.repository;

import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.domain.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByGradId(Grad gradId);
}
