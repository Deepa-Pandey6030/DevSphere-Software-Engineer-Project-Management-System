package com.demo.demo1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SoftwareEngineerController {
    private final SoftwareEngineerService softwareEngineerService;
    
    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService){
        this.softwareEngineerService=softwareEngineerService;
    }

    @GetMapping("/software-engineers")
    public ResponseEntity<List<SoftwareEngineerResponseDTO>> getAllEngineers(){
        List<SoftwareEngineerResponseDTO> engineers= softwareEngineerService.SoftwareEngineerResponseDTOAll();
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(engineers);
    }
    
    @PostMapping("/insert-software-engineer")
    public ResponseEntity<SoftwareEngineerResponseDTO> addEngineer(@RequestBody SoftwareEngineerCreateDTO request){
        SoftwareEngineerResponseDTO savedEngineer=softwareEngineerService.addSoftwareEngineer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEngineer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareEngineerResponseDTO> getEngineerById(@PathVariable Integer id){
        SoftwareEngineerResponseDTO engineer= softwareEngineerService.SoftwareEngineerResponseDTOById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(engineer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngineerById(@PathVariable Integer id){
        softwareEngineerService.deleteSoftwareEngineerById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoftwareEngineerResponseDTO> updateRngineerById(@PathVariable Integer id,@RequestBody SoftwareEngineerCreateDTO request){
        SoftwareEngineerResponseDTO savedResponseDTO=softwareEngineerService.updateSoftwareEngineerById(id,request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(savedResponseDTO);
    }
    
}
