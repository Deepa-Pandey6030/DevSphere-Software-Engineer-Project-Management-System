package com.demo.demo1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.demo.demo1.entity.ProficiencyLevel;

public class SkillProficiencyCreateDTO {
    @NotNull(message="Engineer ID is required")
    @Positive(message="Engineer ID must be positive")
    private Integer engineerId;
    
    @NotNull(message="Technology ID is required")
    @Positive(message="Technology ID must be positive")
    private Integer technologyId;
    
    @NotNull(message="Proficiency level is required")
    private ProficiencyLevel proficiencyLevel;
    
    private LocalDate acquiredDate;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public LocalDate getAcquiredDate() {
        return acquiredDate;
    }

    public void setAcquiredDate(LocalDate acquiredDate) {
        this.acquiredDate = acquiredDate;
    }
}
