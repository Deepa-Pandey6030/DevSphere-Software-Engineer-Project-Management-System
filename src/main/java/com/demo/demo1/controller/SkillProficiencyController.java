package com.demo.demo1.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo1.dto.SkillProficiencyCreateDTO;
import com.demo.demo1.dto.SkillProficiencyResponseDTO;
import com.demo.demo1.dto.SkillProficiencyUpdateWithPatchDTO;
import com.demo.demo1.dto.SkillProficiencyUpdateWithPutDTO;
import com.demo.demo1.service.SkillProficiencyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillProficiencyController {
    private final SkillProficiencyService skillService;

    public SkillProficiencyController(SkillProficiencyService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/insert")
    public ResponseEntity<SkillProficiencyResponseDTO> addSkill(@Valid @RequestBody SkillProficiencyCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.addSkill(request));
    }

    @GetMapping("/")
    public ResponseEntity<Page<SkillProficiencyResponseDTO>> getAllSkills(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(skillService.getAllSkillsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillProficiencyResponseDTO> getSkillById(@PathVariable Integer id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillProficiencyResponseDTO> updateSkillPut(@PathVariable Integer id, @Valid @RequestBody SkillProficiencyUpdateWithPutDTO request) {
        return ResponseEntity.ok(skillService.updateSkillPut(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SkillProficiencyResponseDTO> updateSkillPatch(@PathVariable Integer id, @Valid @RequestBody SkillProficiencyUpdateWithPatchDTO request) {
        return ResponseEntity.ok(skillService.updateSkillPatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Integer id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/engineer/{engineerId}")
    public ResponseEntity<Page<SkillProficiencyResponseDTO>> getSkillsByEngineer(
            @PathVariable Integer engineerId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(skillService.getSkillsByEngineerPaged(engineerId, pageable));
    }
}