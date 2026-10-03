package com.demo.demo1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.demo1.dto.TechnologyCreateDTO;
import com.demo.demo1.dto.TechnologyResponseDTO;
import com.demo.demo1.dto.TechnologyUpdateWithPatchDTO;
import com.demo.demo1.dto.TechnologyUpdateWithPutDTO;
import com.demo.demo1.service.TechnologyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/technologies")
public class TechnologyController {
    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping("/insert")
    public ResponseEntity<TechnologyResponseDTO> addTechnology(@Valid @RequestBody TechnologyCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technologyService.addTechnology(request));
    }

    @GetMapping("/")
    public ResponseEntity<List<TechnologyResponseDTO>> getAllTechnologies() {
        return ResponseEntity.ok(technologyService.getAllTechnologies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechnologyResponseDTO> getTechnologyById(@PathVariable Integer id) {
        return ResponseEntity.ok(technologyService.getTechnologyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TechnologyResponseDTO> updateTechnologyPut(@PathVariable Integer id, @Valid @RequestBody TechnologyUpdateWithPutDTO request) {
        return ResponseEntity.ok(technologyService.updateTechnologyPut(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TechnologyResponseDTO> updateTechnologyPatch(@PathVariable Integer id, @Valid @RequestBody TechnologyUpdateWithPatchDTO request) {
        return ResponseEntity.ok(technologyService.updateTechnologyPatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnology(@PathVariable Integer id) {
        technologyService.deleteTechnology(id);
        return ResponseEntity.ok().build();
    }
}
