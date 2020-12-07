package com.example.jsonreader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
public class PersonController {

    @PostMapping(value = "/persons",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public PersonSearchResponse searchPerson(@RequestBody final SearchPersonRequest searchPersonRequest) {
        log.info("Search a person based on the request: {}", searchPersonRequest);

        final String description = searchPersonRequest.getTags()
                .stream()
                .map(tag -> tag.getKey() + " : " + tag.getValue())
                .collect(Collectors.joining(","));

        return PersonSearchResponse.builder()
                .fullName(searchPersonRequest.getFirstName() + " " + searchPersonRequest.getLastName())
                .age(searchPersonRequest.getAge())
                .description(description)
                .build();
    }
}
