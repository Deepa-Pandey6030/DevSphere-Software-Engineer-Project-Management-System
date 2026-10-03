package com.demo.demo1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.demo1.dto.SkillProficiencyCreateDTO;
import com.demo.demo1.dto.SkillProficiencyResponseDTO;
import com.demo.demo1.dto.SkillProficiencyUpdateWithPatchDTO;
import com.demo.demo1.dto.SkillProficiencyUpdateWithPutDTO;
import com.demo.demo1.entity.SkillProficiency;
import com.demo.demo1.exception.SkillProficiencyNotFoundException;
import com.demo.demo1.exception.SoftwareEngineerNotFoundException;
import com.demo.demo1.exception.TechnologyNotFoundException;
import com.demo.demo1.mapper.SkillProficiencyMapper;
import com.demo.demo1.repository.SkillProficiencyRepository;
import com.demo.demo1.repository.SoftwareEngineerRepository;
import com.demo.demo1.repository.TechnologyRepository;

@Service
public class SkillProficiencyService {
    private final SkillProficiencyRepository skillRepository;
    private final SkillProficiencyMapper skillMapper;
    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final TechnologyRepository technologyRepository;

    public SkillProficiencyService(SkillProficiencyRepository skillRepository,
                                   SkillProficiencyMapper skillMapper,
                                   SoftwareEngineerRepository softwareEngineerRepository,
                                   TechnologyRepository technologyRepository) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.technologyRepository = technologyRepository;
    }

    public SkillProficiencyResponseDTO addSkill(SkillProficiencyCreateDTO dto) {
        SkillProficiency skill = skillMapper.toEntity(dto);
        skill.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        skill.setTechnology(technologyRepository.findById(dto.getTechnologyId())
            .orElseThrow(() -> new TechnologyNotFoundException("Technology not found with id " + dto.getTechnologyId())));

        SkillProficiency saved = skillRepository.save(skill);
        return skillMapper.toResponseDTO(saved);
    }

    public List<SkillProficiencyResponseDTO> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(skillMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<SkillProficiencyResponseDTO> getAllSkillsPaged(Pageable pageable) {
        return skillRepository.findAll(pageable)
                .map(skillMapper::toResponseDTO);
    }

    public SkillProficiencyResponseDTO getSkillById(Integer id) {
        SkillProficiency skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillProficiencyNotFoundException("Skill not found with id " + id));
        return skillMapper.toResponseDTO(skill);
    }

    public SkillProficiencyResponseDTO updateSkillPut(Integer id, SkillProficiencyUpdateWithPutDTO dto) {
        SkillProficiency existing = skillRepository.findById(id)
                .orElseThrow(() -> new SkillProficiencyNotFoundException("Skill not found with id " + id));
        skillMapper.updateEntity(existing, dto);

        if (dto.getEngineerId() != null) {
            existing.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
                .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        }
        if (dto.getTechnologyId() != null) {
            existing.setTechnology(technologyRepository.findById(dto.getTechnologyId())
                .orElseThrow(() -> new TechnologyNotFoundException("Technology not found with id " + dto.getTechnologyId())));
        }

        skillRepository.save(existing);
        return skillMapper.toResponseDTO(existing);
    }

    public SkillProficiencyResponseDTO updateSkillPatch(Integer id, SkillProficiencyUpdateWithPatchDTO dto) {
        SkillProficiency existing = skillRepository.findById(id)
                .orElseThrow(() -> new SkillProficiencyNotFoundException("Skill not found with id " + id));
        skillMapper.updateEntity(existing, dto);

        if (dto.getEngineerId() != null) {
            existing.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
                .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        }
        if (dto.getTechnologyId() != null) {
            existing.setTechnology(technologyRepository.findById(dto.getTechnologyId())
                .orElseThrow(() -> new TechnologyNotFoundException("Technology not found with id " + dto.getTechnologyId())));
        }

        skillRepository.save(existing);
        return skillMapper.toResponseDTO(existing);
    }

    public void deleteSkill(Integer id) {
        if (!skillRepository.existsById(id)) {
            throw new SkillProficiencyNotFoundException("Skill not found with id " + id);
        }
        skillRepository.deleteById(id);
    }

    public List<SkillProficiencyResponseDTO> getSkillsByEngineer(Integer engineerId) {
        return skillRepository.findByEngineerId(engineerId).stream()
                .map(skillMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<SkillProficiencyResponseDTO> getSkillsByEngineerPaged(Integer engineerId, Pageable pageable) {
        return skillRepository.findByEngineerId(engineerId, pageable)
                .map(skillMapper::toResponseDTO);
    }
}