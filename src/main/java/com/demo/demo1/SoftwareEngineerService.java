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


public SoftwareEngineerResponseDTO addSoftwareEngineer(SoftwareEngineerCreateDTO dto){
    SoftwareEngineer engineer=new SoftwareEngineer();
    engineer.setName(dto.getName());
    engineer.setTechstack(dto.getTechstack());
    softwareEngineerRepository.save(engineer);
    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setName(dto.getName());
    responseDTO.setTechstack(dto.getTechstack());
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

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOById(Integer id){
    SoftwareEngineer engineer=getSoftwareEngineerById(id);
    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setName(engineer.getName());
    responseDTO.setTechstack(engineer.getTechstack());
    return responseDTO;
}


public List<SoftwareEngineer> getAllSoftwareEngineers(){
    return softwareEngineerRepository.findAll();
}

public SoftwareEngineer getSoftwareEngineerById(Integer id){
    return softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException("Software Engineer not found with "+id));
}

public void deleteSoftwareEngineerById(Integer id){
    softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException("Software Engineer not found with "+id));
    softwareEngineerRepository.deleteById(id);
}

public SoftwareEngineerResponseDTO updateSoftwareEngineerById(Integer id,SoftwareEngineerCreateDTO dto){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+id));
    existingSoftwareEngineer.setName(dto.getName());
    existingSoftwareEngineer.setTechstack(dto.getTechstack());
    softwareEngineerRepository.save(existingSoftwareEngineer);
    SoftwareEngineerResponseDTO engineer=new SoftwareEngineerResponseDTO();
    engineer.setName(existingSoftwareEngineer.getName());
    engineer.setTechstack(existingSoftwareEngineer.getTechstack());
    return engineer;
}


}
