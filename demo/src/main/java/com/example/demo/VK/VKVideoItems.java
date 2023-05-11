package com.example.demo.VK;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
@JsonIgnoreProperties(ignoreUnknown = true)
public class VKVideoItems {
    private long date;
    private String ownerId;

    public VKVideoItems(@JsonProperty("date") long date, @JsonProperty("owner_id") String ownerId) {
        this.date = date;
        this.ownerId = ownerId;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "VKVideoItems{" +
                "date=" + date +
                ", owner_id='" + ownerId + '\'' +
                '}';
    }
}
