package com.example.demo.service;

import com.example.demo.VK.VKJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VKService {


    public int getCountOfVideosByOwnerId(long vkId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder();
        url
                .append("https://api.vk.com/method/video.get")
                .append("?owner_id=")
                .append(vkId)
                .append("&access_token=vk1.a.aCOgC6ishm0tQIvWDnAmhnD5TLZNTgx9p0OgwZ539H0qRE7SHmih6nF_XB0sq3RSimOTLIVdPw3wCm9n165lP8o4ipHfAdWKYOkl-l5UCMOZUDaH_3CVzx3MAMDrnv6Dzo844prgeloDYsE8qyNYFvG0NroBNkCRNZT8c7nRGXSqusr0u36V84aqFzb6urTD")
                .append("&v=5.167");
        System.out.println(url);
        String response = restTemplate.getForObject(url.toString(), String.class);
        ObjectMapper mapper = new ObjectMapper();
        VKJson vkJson = mapper.readValue(response, VKJson.class);
        long minimalDateOfAddVideo = System.currentTimeMillis() / 1000 - 2592000; //минимальная дозволенная дата создания видео в секундах(последние 30 дней)
        int countOfVideo = vkJson.getResponse().getCount(); //проверка наличия видео на странице
        if (countOfVideo == 0) {
            return 0;
        }
        int countOwnerVideos = 0; //счетчик видосов, которые 1)принадлежат нужному пользователю, 2)были созданы за последние 30 дней
        int i = 0;
        while (i < vkJson.getResponse().getItems().size()) {
            boolean isOwner = vkJson.getResponse().getItems().get(i).getOwnerId().equals(String.valueOf(vkId));
            boolean isCurrentDate = vkJson.getResponse().getItems().get(i).getDate() >= minimalDateOfAddVideo;
            if (isOwner && isCurrentDate) {
                countOwnerVideos++;
            }
            i++;
        }
        return countOwnerVideos;
    }

}
