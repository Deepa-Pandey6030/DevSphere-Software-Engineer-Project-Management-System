package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;
import com.demo.demo1.dto.SkillProficiencyCreateDTO;
import com.demo.demo1.dto.SkillProficiencyResponseDTO;
import com.demo.demo1.dto.SkillProficiencyUpdateWithPutDTO;
import com.demo.demo1.dto.SkillProficiencyUpdateWithPatchDTO;
import com.demo.demo1.entity.SkillProficiency;

@Component
public class SkillProficiencyMapper {

    public SkillProficiency toEntity(SkillProficiencyCreateDTO dto) {
        SkillProficiency skill = new SkillProficiency();
        skill.setProficiencyLevel(dto.getProficiencyLevel());
        skill.setAcquiredDate(dto.getAcquiredDate());
        return skill;
    }

    public void updateEntity(SkillProficiency skill, SkillProficiencyUpdateWithPutDTO dto) {
        skill.setProficiencyLevel(dto.getProficiencyLevel());
        skill.setAcquiredDate(dto.getAcquiredDate());
    }

    public void updateEntity(SkillProficiency skill, SkillProficiencyUpdateWithPatchDTO dto) {
        if (dto.getProficiencyLevel() != null) skill.setProficiencyLevel(dto.getProficiencyLevel());
        if (dto.getAcquiredDate() != null) skill.setAcquiredDate(dto.getAcquiredDate());
    }

    public SkillProficiencyResponseDTO toResponseDTO(SkillProficiency skill) {
        SkillProficiencyResponseDTO dto = new SkillProficiencyResponseDTO();
        dto.setId(skill.getId());
        if (skill.getEngineer() != null) {
            dto.setEngineerId(skill.getEngineer().getId());
            dto.setEngineerName(skill.getEngineer().getFirstName() + " " + skill.getEngineer().getLastName());
        }
        if (skill.getTechnology() != null) {
            dto.setTechnologyId(skill.getTechnology().getId());
            dto.setTechnologyName(skill.getTechnology().getName());
        }
        dto.setProficiencyLevel(skill.getProficiencyLevel());
        dto.setAcquiredDate(skill.getAcquiredDate());
        return dto;
    }
}
