package com.tugceusta.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Graduation.
 */
@Entity
@Table(name = "graduation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Graduation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_year")
    private Integer startYear;

    @NotNull
    @Column(name = "graduation_year", nullable = false)
    private Integer graduationYear;

    @Column(name = "gpa")
    private Double gpa;

    @ManyToOne(fetch = FetchType.LAZY)
    private School schoolId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Grad gradId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Graduation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartYear() {
        return this.startYear;
    }

    public Graduation startYear(Integer startYear) {
        this.setStartYear(startYear);
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getGraduationYear() {
        return this.graduationYear;
    }

    public Graduation graduationYear(Integer graduationYear) {
        this.setGraduationYear(graduationYear);
        return this;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Double getGpa() {
        return this.gpa;
    }

    public Graduation gpa(Double gpa) {
        this.setGpa(gpa);
        return this;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public School getSchoolId() {
        return this.schoolId;
    }

    public void setSchoolId(School school) {
        this.schoolId = school;
    }

    public Graduation schoolId(School school) {
        this.setSchoolId(school);
        return this;
    }

    public Grad getGradId() {
        return this.gradId;
    }

    public void setGradId(Grad grad) {
        this.gradId = grad;
    }

    public Graduation gradId(Grad grad) {
        this.setGradId(grad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graduation)) {
            return false;
        }
        return getId() != null && getId().equals(((Graduation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Graduation{" +
            "id=" + getId() +
            ", startYear=" + getStartYear() +
            ", graduationYear=" + getGraduationYear() +
            ", gpa=" + getGpa() +
            "}";
    }
}
