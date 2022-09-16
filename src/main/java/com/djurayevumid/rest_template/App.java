package com.djurayevumid.rest_template;

import com.djurayevumid.rest_template.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * Hello world!
 */
public class App {

    public static final String url = "http://94.198.50.185:7081/api/users";
    static RestTemplate restTemplate = new RestTemplate();


    public static void main(String[] args) {
        useRestTemplate();
    }

    private static void useRestTemplate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<User>> responseEntity = getAllUsers(requestEntity);
        headers.set("Cookie", String.join(";", Objects.requireNonNull(responseEntity.getHeaders().get("Set-Cookie"))));
        System.out.println("ALL USERS");
        System.out.println(getAllUsers(requestEntity));
        System.out.println();


        User user = new User();
        user.setId(3L);
        user.setName("John");
        user.setLastName("Brown");
        user.setAge((byte) 23);

        requestEntity = new HttpEntity<>(user, headers);
        addUser(requestEntity);
        System.out.println("NEW USER");
        System.out.println(user);
        System.out.println();
        System.out.println("NEW LIST");
        System.out.println(getAllUsers(requestEntity));
        System.out.println();

        user.setName("Thomas");
        user.setLastName("Shelby");
        updateUser(requestEntity);
        System.out.println("UPDATED LIST");
        System.out.println(getAllUsers(requestEntity));
        System.out.println();
        System.out.println("UPDATED USER");
        System.out.println(user);
        System.out.println();

        deleteUser(requestEntity);

        System.out.println("PREVIOUS LIST");
        System.out.println(getAllUsers(requestEntity));

    }



    private static ResponseEntity<List<User>> getAllUsers(HttpEntity<Object> entity) {

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<User>>() {
        });
        HttpHeaders headers = responseEntity.getHeaders();
        return responseEntity;
    }



    private static void addUser(HttpEntity<Object> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        String userInfo = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
    }




    private static void updateUser(HttpEntity<Object> entity){
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        String userInfo = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
    }



    private static void deleteUser(HttpEntity<Object> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + "/3", HttpMethod.DELETE, entity, String.class);
        String userInfo = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
    }
}
