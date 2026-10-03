package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;
import com.demo.demo1.dto.TechnologyCreateDTO;
import com.demo.demo1.dto.TechnologyResponseDTO;
import com.demo.demo1.dto.TechnologyUpdateWithPutDTO;
import com.demo.demo1.dto.TechnologyUpdateWithPatchDTO;
import com.demo.demo1.entity.Technology;

@Component
public class TechnologyMapper {

    public Technology toEntity(TechnologyCreateDTO dto) {
        Technology tech = new Technology();
        tech.setName(dto.getName());
        tech.setCategory(dto.getCategory());
        tech.setDescription(dto.getDescription());
        return tech;
    }

    public void updateEntity(Technology tech, TechnologyUpdateWithPutDTO dto) {
        tech.setName(dto.getName());
        tech.setCategory(dto.getCategory());
        tech.setDescription(dto.getDescription());
    }

    public void updateEntity(Technology tech, TechnologyUpdateWithPatchDTO dto) {
        if (dto.getName() != null) tech.setName(dto.getName());
        if (dto.getCategory() != null) tech.setCategory(dto.getCategory());
        if (dto.getDescription() != null) tech.setDescription(dto.getDescription());
    }

    public TechnologyResponseDTO toResponseDTO(Technology tech) {
        TechnologyResponseDTO dto = new TechnologyResponseDTO();
        dto.setId(tech.getId());
        dto.setName(tech.getName());
        dto.setCategory(tech.getCategory());
        dto.setDescription(tech.getDescription());
        return dto;
    }
}
