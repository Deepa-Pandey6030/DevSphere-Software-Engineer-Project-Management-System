package com.demo.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class SoftwareEngineerService {

private final SoftwareEngineerRepository softwareEngineerRepository;

public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository) {
    this.softwareEngineerRepository = softwareEngineerRepository;
}


public SoftwareEngineerResponseDTO addSoftwareEngineer(SoftwareEngineerCreateDTO dto){
    SoftwareEngineer engineer=new SoftwareEngineer();

    engineer.setFirstName(dto.getFirstName()); 
    engineer.setLastName(dto.getLastName());
    engineer.setEmail(dto.getEmail());
    engineer.setPhoneNumber(dto.getPhoneNumber());
    engineer.setDateOfBirth(dto.getDateOfBirth());
    engineer.setTechstack(dto.getTechstack());
    engineer.setEmployeeId("EMP-" + UUID.randomUUID());
    softwareEngineerRepository.save(engineer);

    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setFirstName(dto.getFirstName()); 
    responseDTO.setLastName(dto.getLastName());
    responseDTO.setEmail(dto.getEmail());
    responseDTO.setPhoneNumber(dto.getPhoneNumber());
    responseDTO.setDateOfBirth(dto.getDateOfBirth());
    responseDTO.setTechstack(dto.getTechstack());
    return responseDTO;
}

public List<SoftwareEngineerResponseDTO> SoftwareEngineerResponseDTOAll(){
    List<SoftwareEngineer> engineers=getAllSoftwareEngineers();
    List<SoftwareEngineerResponseDTO> responseDTOs=new ArrayList<>();
    for(SoftwareEngineer engineer:engineers){
        SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
        responseDTO.setFirstName(engineer.getFirstName()); 
        responseDTO.setLastName(engineer.getLastName());
        responseDTO.setEmail(engineer.getEmail());
        responseDTO.setPhoneNumber(engineer.getPhoneNumber());
        responseDTO.setDateOfBirth(engineer.getDateOfBirth());
        responseDTO.setTechstack(engineer.getTechstack());

        responseDTOs.add(responseDTO);
    }
    return responseDTOs;
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOById(Integer id){
    SoftwareEngineer engineer=getSoftwareEngineerById(id);
    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setFirstName(engineer.getFirstName()); 
    responseDTO.setLastName(engineer.getLastName());
    responseDTO.setEmail(engineer.getEmail());
    responseDTO.setPhoneNumber(engineer.getPhoneNumber());
    responseDTO.setDateOfBirth(engineer.getDateOfBirth());
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
    existingSoftwareEngineer.setFirstName(dto.getFirstName());
    existingSoftwareEngineer.setLastName(dto.getLastName());
    existingSoftwareEngineer.setEmail(dto.getEmail());
    existingSoftwareEngineer.setPhoneNumber(dto.getPhoneNumber());
    existingSoftwareEngineer.setDateOfBirth(dto.getDateOfBirth());
    existingSoftwareEngineer.setTechstack(dto.getTechstack());

    softwareEngineerRepository.save(existingSoftwareEngineer);
    SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
    responseDTO.setFirstName(existingSoftwareEngineer.getFirstName()); 
    responseDTO.setLastName(existingSoftwareEngineer.getLastName());
    responseDTO.setEmail(existingSoftwareEngineer.getEmail());
    responseDTO.setPhoneNumber(existingSoftwareEngineer.getPhoneNumber());
    responseDTO.setDateOfBirth(existingSoftwareEngineer.getDateOfBirth());
    responseDTO.setTechstack(existingSoftwareEngineer.getTechstack());
    return responseDTO;
}


}
