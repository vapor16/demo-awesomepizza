package com.adesso.project.awesomepizza.dto.response;

import lombok.Data;

@Data
public class GeneralResponse {
    private String message;

    public GeneralResponse(String message) {
        this.message = message;
    }
}
