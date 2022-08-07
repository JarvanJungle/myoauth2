package com.doxa.oauth2.models.authorities.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
@Data
@Document(collection = "feature")
public class Feature {
    @Id
    private String id;
    private String name;
    private String code;
    private String category;
    @Field("module_id")
    private String moduleId;
//    private List<String> moduleIds;
//    @DocumentReference(lazy = true, lookup = "{ 'courseId' : ?#{#self._id} }")
//    List<Module> modules;
}
