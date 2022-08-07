//package com.doxa.oauth2.models.companies;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Generated;
//import org.hibernate.annotations.GenerationTime;
//
//import javax.persistence.*;
//import java.time.Instant;
//
//@Data
//@NoArgsConstructor
//@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@Table(name = "documents_meta_data", schema = "public")
//public class DocumentsMetaData {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "guid")
//    private String guid;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "file_name")
//    private String fileName;
//
//    @Column(name = "created_at")
//    @Generated(GenerationTime.INSERT)
//    private Instant createdAt;
//
//    @Generated(GenerationTime.ALWAYS)
//    @Column(name = "updated_at")
//    private Instant updatedAt;
//
//    @Column(name = "company_id")
//    private Long companyId;
//
//    @Column(name = "delete_from")
//    private Long deleteFrom;
//}
