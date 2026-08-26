package com.demo.demo1;

import java.util.ArrayList;
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

public SoftwareEngineerResponseDTO addSoftwareEngineer(SoftwareEngineerCreateDTO dto){
    SoftwareEngineer engineer=new SoftwareEngineer();
    engineer.setName(dto.getName());
    engineer.setTechstack(dto.getTechstack());
    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setName(dto.getName());
    responseDTO.setTechstack(dto.getTechstack());
    return responseDTO;
}

public SoftwareEngineer getSoftwareEngineerById(Integer id){
    return softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException("Software Engineer not found with "+id));
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOById(Integer id){
    SoftwareEngineer engineer=getSoftwareEngineerById(id);
    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setName(engineer.getName());
    responseDTO.setTechstack(engineer.getTechstack());
    return responseDTO;
}

public List<SoftwareEngineerResponseDTO> SoftwareEngineerResponseDTOAll(){
    List<SoftwareEngineer> engineers=getAllSoftwareEngineers();
    List<SoftwareEngineerResponseDTO> responseDTOs=new ArrayList<>();
    for(SoftwareEngineer engineer:engineers){
        SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
        responseDTO.setName(engineer.getName());
        responseDTO.setTechstack(engineer.getTechstack());

        responseDTOs.add(responseDTO);
    }
    return responseDTOs;
}

public void deleteSoftwareEngineerById(Integer id){
    softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException("Software Engineer not found with "+id));
    softwareEngineerRepository.deleteById(id);
}

public SoftwareEngineer updateSoftwareEngineerById(Integer id,SoftwareEngineer updatedEngineer){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+id));
    existingSoftwareEngineer.setName(updatedEngineer.name);
    existingSoftwareEngineer.setTechstack(updatedEngineer.techstack);
    return softwareEngineerRepository.save(existingSoftwareEngineer);
}


}
