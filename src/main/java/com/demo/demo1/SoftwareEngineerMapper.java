package com.demo.demo1;

import org.springframework.stereotype.Component;

@Component
public class SoftwareEngineerMapper {


    public SoftwareEngineer toEntity(SoftwareEngineerCreateDTO dto){
        SoftwareEngineer engineer=new SoftwareEngineer();
        engineer.setFirstName(dto.getFirstName());
        engineer.setLastName(dto.getLastName());
        engineer.setEmail(dto.getEmail());
        engineer.setPhoneNumber(dto.getPhoneNumber());
        engineer.setDateOfBirth(dto.getDateOfBirth());        
        engineer.setTechstack(dto.getTechstack());
        return engineer;
    }

    public SoftwareEngineer updateEntity(SoftwareEngineer engineer ,SoftwareEngineerUpdateWithPutDTO dto){
        engineer.setFirstName(dto.getFirstName());
        engineer.setLastName(dto.getLastName());
        engineer.setEmail(dto.getEmail());
        engineer.setPhoneNumber(dto.getPhoneNumber());
        engineer.setDateOfBirth(dto.getDateOfBirth());        
        engineer.setTechstack(dto.getTechstack());
        return engineer;
    }

    public SoftwareEngineer updateEntity(SoftwareEngineer engineer,SoftwareEngineerUpdateWithPatchDTO dto) {

    if (dto.getFirstName() != null) {
        engineer.setFirstName(dto.getFirstName());
    }

    if (dto.getLastName() != null) {
        engineer.setLastName(dto.getLastName());
    }

    if (dto.getEmail() != null) {
        engineer.setEmail(dto.getEmail());
    }

    if (dto.getPhoneNumber() != null) {
        engineer.setPhoneNumber(dto.getPhoneNumber());
    }

    if (dto.getDateOfBirth() != null) {
        engineer.setDateOfBirth(dto.getDateOfBirth());
    }

    if (dto.getTechstack() != null) {
        engineer.setTechstack(dto.getTechstack());
    }
    return engineer;
}

    public SoftwareEngineerResponseDTO toResponseDTO(SoftwareEngineer engineer){
        SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
        responseDTO.setFirstName(engineer.getFirstName()); 
        responseDTO.setLastName(engineer.getLastName());
        responseDTO.setEmail(engineer.getEmail());
        responseDTO.setPhoneNumber(engineer.getPhoneNumber());
        responseDTO.setDateOfBirth(engineer.getDateOfBirth());
        responseDTO.setTechstack(engineer.getTechstack());
        return responseDTO;
    }

}
