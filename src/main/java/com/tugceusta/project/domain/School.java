package com.tugceusta.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "university_name", nullable = false)
    private String universityName;

    @NotNull
    @Column(name = "faculty_name", nullable = false)
    private String facultyName;

    @NotNull
    @Column(name = "department_name", nullable = false)
    private String departmentName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public School id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversityName() {
        return this.universityName;
    }

    public School universityName(String universityName) {
        this.setUniversityName(universityName);
        return this;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getFacultyName() {
        return this.facultyName;
    }

    public School facultyName(String facultyName) {
        this.setFacultyName(facultyName);
        return this;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public School departmentName(String departmentName) {
        this.setDepartmentName(departmentName);
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof School)) {
            return false;
        }
        return getId() != null && getId().equals(((School) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", universityName='" + getUniversityName() + "'" +
            ", facultyName='" + getFacultyName() + "'" +
            ", departmentName='" + getDepartmentName() + "'" +
            "}";
    }
}
