package com.github.edulook.look.core.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course { 
    // com.google.api.services.classroom.model.Course
    private String id;
    private String state;
    private String description;
    private String name;
    private String ownerId;
    private String room;
    private String section;
    private String updated;
}
