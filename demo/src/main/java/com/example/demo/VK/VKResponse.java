package com.example.demo.VK;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "response")
public class VKResponse {

    private List<VKVideoItem> items;
    private int count;

        public List<VKVideoItem> getItems() {
                return items;
        }

        public void setItems(List<VKVideoItem> items) {
                this.items = items;
        }

        public int getCount() {
                return count;
        }

        public void setCount(int count) {
                this.count = count;
        }

    @Override
    public String toString() {
        return "VKResponse{" +
                "items=" + items +
                ", count=" + count +
                '}';
    }
}
