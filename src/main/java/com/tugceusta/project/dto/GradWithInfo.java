package com.tugceusta.project.dto;

import com.tugceusta.project.domain.Grad;
import com.tugceusta.project.domain.Graduation;
import com.tugceusta.project.domain.Skill;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GradWithInfo implements Serializable {

    private final Grad grad;
    private List<GraduationDto> graduationList = new ArrayList<>();
    private List<Skill> skillList = new ArrayList<>();

    public GradWithInfo(Grad grad) {
        this.grad = grad;
    }

    public Grad getGrad() {
        return grad;
    }

    public List<GraduationDto> getGraduationList() {
        return graduationList;
    }

    public void setGraduationList(List<Graduation> graduationList) {
        List<GraduationDto> graduationDtoList = graduationList
            .stream()
            .map(
                grad ->
                    new GraduationDto(
                        grad.getId(),
                        grad.getStartYear(),
                        grad.getGraduationYear(),
                        grad.getGpa(),
                        grad.getSchoolId().toSchoolFullName(),
                        grad.getGradId().getId()
                    )
            )
            .toList();

        this.graduationList = graduationDtoList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }
}
