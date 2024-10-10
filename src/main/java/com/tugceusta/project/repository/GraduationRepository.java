package com.tugceusta.project.repository;

import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.domain.Graduation;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Graduation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GraduationRepository extends JpaRepository<Graduation, Long> {
    List<Graduation> findAllByGradId(Grad gradObj);
}
