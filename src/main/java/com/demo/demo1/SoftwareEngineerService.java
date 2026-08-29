package com.demo.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class SoftwareEngineerService {

private final SoftwareEngineerRepository softwareEngineerRepository;
private final SoftwareEngineerMapper softwareEngineerMapper;

public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository,SoftwareEngineerMapper softwareEngineerMapper) {
    this.softwareEngineerMapper=softwareEngineerMapper;
    this.softwareEngineerRepository = softwareEngineerRepository;
}


public SoftwareEngineerResponseDTO addSoftwareEngineer(SoftwareEngineerCreateDTO dto) {
    SoftwareEngineer engineer =softwareEngineerMapper.toEntity(dto);
    engineer.setEmployeeId("EMP-" + UUID.randomUUID());
    SoftwareEngineer saved =softwareEngineerRepository.save(engineer);
    return softwareEngineerMapper.toResponseDTO(saved);
}

public List<SoftwareEngineerResponseDTO> SoftwareEngineerResponseDTOAll(){
    List<SoftwareEngineer> engineers=getAllSoftwareEngineers();
    List<SoftwareEngineerResponseDTO> responseDTOs=new ArrayList<>();
    for(SoftwareEngineer engineer:engineers){
        SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
        responseDTOs.add(responseDTO);
    }
    return responseDTOs;
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOById(Integer id){
    SoftwareEngineer engineer=getSoftwareEngineerById(id);
    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
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

public SoftwareEngineerResponseDTO updateSoftwareEngineerwithPut(Integer id,SoftwareEngineerUpdateWithPutDTO dto){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+id));

    existingSoftwareEngineer= softwareEngineerMapper.updateEntity(existingSoftwareEngineer,dto);

    softwareEngineerRepository.save(existingSoftwareEngineer);

    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(existingSoftwareEngineer);
    return responseDTO;
}

public SoftwareEngineerResponseDTO updateSoftwareEngineerWithPatch(Integer id,SoftwareEngineerUpdateWithPatchDTO dto){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+id));
    existingSoftwareEngineer=softwareEngineerMapper.updateEntity(existingSoftwareEngineer, dto);

    softwareEngineerRepository.save(existingSoftwareEngineer);

    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(existingSoftwareEngineer);
    return responseDTO;
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOByEmialId(String email){
    SoftwareEngineer engineer=softwareEngineerRepository.findByEmail(email).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+email));
    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
    return responseDTO;
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOByFirstName(String firstName){
    SoftwareEngineer engineer=softwareEngineerRepository.findByFirstName(firstName).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+firstName));
    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
    return responseDTO;
}

}
