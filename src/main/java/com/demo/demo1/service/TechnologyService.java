package com.demo.demo1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.demo1.dto.TechnologyCreateDTO;
import com.demo.demo1.dto.TechnologyResponseDTO;
import com.demo.demo1.dto.TechnologyUpdateWithPatchDTO;
import com.demo.demo1.dto.TechnologyUpdateWithPutDTO;
import com.demo.demo1.entity.Technology;
import com.demo.demo1.exception.TechnologyNotFoundException;
import com.demo.demo1.mapper.TechnologyMapper;
import com.demo.demo1.repository.TechnologyRepository;

@Service
public class TechnologyService {
    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;

    public TechnologyService(TechnologyRepository technologyRepository, TechnologyMapper technologyMapper) {
        this.technologyRepository = technologyRepository;
        this.technologyMapper = technologyMapper;
    }

    public TechnologyResponseDTO addTechnology(TechnologyCreateDTO dto) {
        Technology tech = technologyMapper.toEntity(dto);
        Technology saved = technologyRepository.save(tech);
        return technologyMapper.toResponseDTO(saved);
    }

    public List<TechnologyResponseDTO> getAllTechnologies() {
        return technologyRepository.findAll().stream()
                .map(technologyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TechnologyResponseDTO getTechnologyById(Integer id) {
        Technology tech = technologyRepository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException("Technology not found with id " + id));
        return technologyMapper.toResponseDTO(tech);
    }

    public TechnologyResponseDTO updateTechnologyPut(Integer id, TechnologyUpdateWithPutDTO dto) {
        Technology existing = technologyRepository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException("Technology not found with id " + id));
        technologyMapper.updateEntity(existing, dto);

        technologyRepository.save(existing);
        return technologyMapper.toResponseDTO(existing);
    }

    public TechnologyResponseDTO updateTechnologyPatch(Integer id, TechnologyUpdateWithPatchDTO dto) {
        Technology existing = technologyRepository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException("Technology not found with id " + id));
        technologyMapper.updateEntity(existing, dto);

        technologyRepository.save(existing);
        return technologyMapper.toResponseDTO(existing);
    }

    public void deleteTechnology(Integer id) {
        if (!technologyRepository.existsById(id)) {
            throw new TechnologyNotFoundException("Technology not found with id " + id);
        }
        technologyRepository.deleteById(id);
    }
}