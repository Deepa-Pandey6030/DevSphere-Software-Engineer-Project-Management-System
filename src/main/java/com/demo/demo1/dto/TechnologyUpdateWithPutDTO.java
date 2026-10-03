package com.demo.demo1.dto;

import jakarta.validation.constraints.NotBlank;

public class TechnologyUpdateWithPutDTO {
    @NotBlank(message="Technology name is required")
    private String name;
    
    @NotBlank(message="Category is required")
    private String category;
    
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
