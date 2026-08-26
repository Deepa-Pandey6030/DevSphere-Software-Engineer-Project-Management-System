package com.demo.demo1;

import java.util.List;

public class SoftwareEngineerResponseDTO {
    private String name;
    private List<String>techstack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTechstack() {
        return techstack;
    }

    public void setTechstack(List<String> techstack) {
        this.techstack = techstack;
    }
}
