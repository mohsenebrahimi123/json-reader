package com.example.jsonreader;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchPersonRequest {

    private String firstName;

    private String lastName;

    private int age;

    @Singular
    private List<Tag> tags = new ArrayList<>();
}
