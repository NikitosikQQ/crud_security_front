package com.example.demo.VK;

import com.example.demo.VK.VKResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VKJson {
    private VKResponse response;

    public VKResponse getResponse() {
        return response;
    }

    public void setResponse(VKResponse response) {
        this.response = response;
    }
}
