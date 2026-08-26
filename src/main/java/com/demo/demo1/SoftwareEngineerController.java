package com.demo.demo1;

import java.util.List;

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
    public List<SoftwareEngineer> getAllEngineers(){
        return softwareEngineerService.getAllSoftwareEngineers();
    }
    
    @PostMapping("/insert-software-engineer")
    public SoftwareEngineer addEngineer(@RequestBody SoftwareEngineer softwareEngineer){
        return softwareEngineerService.addSoftwareEngineer(softwareEngineer);
    }

    @GetMapping("/{id}")
    public SoftwareEngineer getEngineerById(@PathVariable Integer id){
        return softwareEngineerService.getSoftwareEngineerById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEngineerById(@PathVariable Integer id){
        softwareEngineerService.deleteSoftwareEngineerById(id);
    }

    @PutMapping("/{id}")
    public SoftwareEngineer updateRngineerById(@PathVariable Integer id,@RequestBody SoftwareEngineer updatedEngineer){
        return softwareEngineerService.updateSoftwareEngineerById(id,updatedEngineer);
    }
    
}
