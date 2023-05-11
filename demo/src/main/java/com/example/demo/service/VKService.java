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

    private static final String VK_API_HOST = "https://api.vk.com";
    private static final String VK_API_METHOD_VIDEO_GET_BY_OWNER = "/method/video.get?owner_id=";
    private static final String VK_API_ACCESS_TOKEN = "&access_token=" + System.getenv("VK_TOKEN");
    private static final String VK_API_VERSION = "&v=5.167";

    public int getCountOfVideosByOwnerId(long vkId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder requestToVk = new StringBuilder();
        requestToVk
                .append(VK_API_HOST)
                .append(VK_API_METHOD_VIDEO_GET_BY_OWNER)
                .append(vkId)
                .append(VK_API_ACCESS_TOKEN)
                .append(VK_API_VERSION);
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
