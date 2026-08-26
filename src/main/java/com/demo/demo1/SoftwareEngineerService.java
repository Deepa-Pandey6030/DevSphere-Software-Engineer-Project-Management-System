package com.demo.demo1;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class SoftwareEngineerService {

private final SoftwareEngineerRepository softwareEngineerRepository;

public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
    this.softwareEngineerRepository = softwareEngineerRepository;
}
public List<SoftwareEngineer> getAllSoftwareEngineers(){
    return softwareEngineerRepository.findAll();
}

public SoftwareEngineer addSoftwareEngineer(SoftwareEngineer softwareEngineer){
    return softwareEngineerRepository.save(softwareEngineer);
}

public SoftwareEngineer getSoftwareEngineerById(Integer id){
    return softwareEngineerRepository.findById(id).orElseThrow();
}

public void deleteSoftwareEngineerById(Integer id){
    softwareEngineerRepository.deleteById(id);
}

public SoftwareEngineer updateSoftwareEngineerById(Integer id,SoftwareEngineer updatedEngineer){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow();
    existingSoftwareEngineer.setName(updatedEngineer.name);
    existingSoftwareEngineer.setTechstack(updatedEngineer.techstack);
    return softwareEngineerRepository.save(existingSoftwareEngineer);
}

}
