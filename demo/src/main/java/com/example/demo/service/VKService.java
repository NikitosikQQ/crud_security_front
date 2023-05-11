package com.example.demo.service;

import com.example.demo.dto.VKJson;
import com.example.demo.dto.VKVideoItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class VKService {


    public int getCountOfVideosByOwnerId(long vkId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder requestToVk = new StringBuilder();
        requestToVk
                .append("https://api.vk.com/method/video.get")
                .append("?owner_id=")
                .append(vkId)
                .append("&access_token=")
                .append(System.getenv("VK_TOKEN"))
                .append("&v=5.167");
        ObjectMapper mapper = new ObjectMapper();
        VKJson vkJson = mapper.readValue(restTemplate.getForObject(requestToVk.toString(), String.class), VKJson.class);
        int countOfVideo = vkJson.getResponse().getCount(); //проверка наличия видео на странице
        if (countOfVideo == 0) {
            return 0;
        }
        return filterVKVideo(vkJson.getResponse().getItems(), vkId).size();
    }

    private List<VKVideoItem> filterVKVideo(List<VKVideoItem> list, long vkId) {
        long minimalDateOfAddVideo = System.currentTimeMillis() / 1000 - 2592000; //минимальная дозволенная дата создания видео в секундах(последние 30 дней)
        return list.stream()
                .filter(video -> video.getOwnerId().equals(String.valueOf(vkId)))
                .filter(video -> video.getDate() >= minimalDateOfAddVideo)
                .toList();

    }
}
