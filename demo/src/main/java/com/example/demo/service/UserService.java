package com.example.demo.service;

import com.example.demo.user.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static com.example.demo.config.SecurityConfig.passwordEncoder;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Transactional
    public HttpStatus saveUser(User user) throws JsonProcessingException {

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User checkUsername = userRepository.findUserByName(user.getName());
        if (checkUsername != null) {
            return HttpStatus.BAD_REQUEST;
        }
        user.setCountOfVideo(getCountOfVideosByOwnerId(user.getVkOwnerId()));
        userRepository.save(user);
        return HttpStatus.OK;
    }

    @Transactional
    public HttpStatus updateUser(User user) throws JsonProcessingException {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User checkUser = userRepository.findUserById(user.getId());
        if (checkUser != null) {
            user.setCountOfVideo(getCountOfVideosByOwnerId(user.getVkOwnerId()));
            userRepository.save(user);
            return HttpStatus.OK;

        }
        return HttpStatus.BAD_REQUEST;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Transactional
    public HttpStatus deleteUserById(Long id) {
        User checkUser = userRepository.findUserById(id);
        if (checkUser == null) {
            return HttpStatus.BAD_REQUEST;
        }
        userRepository.deleteById(id);
        return HttpStatus.OK;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:(");
        }
        return user;
    }

    private int getCountOfVideosByOwnerId(long vkId) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder();
        url
                .append("https://api.vk.com/method/video.get")
                .append("?owner_id=")
                .append(vkId)
                .append("&access_token=vk1.a.oAEBh-qRRdDilD7hU1rt7DplJJi57YJ9QosT2KIcHLRNYkQhOf7rmwz8DLbC0WIF8btNrT9d7r8F9i4pBOdYe839YutFygl0CF1c358UfN8dkzAQfs9q9dd0XCmyvZdl7-5t2p1clZAmPT6S_kmbNmYWiO7YUG1OD-rwsnD14jBsf2lJd9SOQpXmK7l3pYgl")
                .append("&v=5.167");

        String response = restTemplate.getForObject(url.toString(), String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        long minimalDateOfAddVideo = System.currentTimeMillis() / 1000 - 2592000; //минимальная дозволенная дата создания видео в секундах(последние 30 дней)
        int countOfVideo = jsonNode.get("response").get("count").asInt(); //проверка наличия видео на странице
        if (countOfVideo == 0) {
            return 0;
        }
        int countOwnerVideos = 0; //счетчик видосов, которые 1)принадлежат нужному пользователю, 2)были созданы за последние 30 дней
        int i = 0;
        while (jsonNode.get("response").get("items").get(i) != null) {
            boolean isOwner = jsonNode.get("response").get("items").get(i).get("owner_id").toString().equals(String.valueOf(vkId));
            boolean isCurrentDate = jsonNode.get("response").get("items").get(i).get("adding_date").asLong() >= minimalDateOfAddVideo;
            if (isOwner && isCurrentDate) {
                countOwnerVideos++;
            }
            i++;
        }
        return countOwnerVideos;
    }
}
