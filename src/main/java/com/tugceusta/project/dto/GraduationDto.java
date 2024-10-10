package com.tugceusta.project.dto;

import java.io.Serializable;

public class GraduationDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer startYear;
    private Integer graduationYear;
    private Double gpa;
    private String schoolName;
    private Long gradId;

    public GraduationDto(Long id, Integer startYear, Integer graduationYear, Double gpa, String schoolName, Long gradId) {
        this.id = id;
        this.startYear = startYear;
        this.graduationYear = graduationYear;
        this.gpa = gpa;
        this.schoolName = schoolName;
        this.gradId = gradId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Integer graduationYear) {
        this.graduationYear = graduationYear;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getGradId() {
        return gradId;
    }

    public void setGradId(Long gradId) {
        this.gradId = gradId;
    }
}
