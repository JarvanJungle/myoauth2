//package com.doxa.oauth2.models.financialInstitution;
//
//import java.time.Instant;
//import java.util.List;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import org.hibernate.annotations.Generated;
//import org.hibernate.annotations.GenerationTime;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@Table(name = "fi_company")
//public class FiCompany {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @JsonIgnore
//        @Column(name = "id")
//        private Long id;
//        @Column(name = "uuid")
//        private String uuid;
//        @Column(name = "entity_name")
//        private String entityName;
//        @Column(name = "gst_no")
//        private String gstNo;
//        @Column(name = "is_active")
//        @JsonProperty
//        private boolean active;
//        @Column(name = "created_at")
//        @Generated(GenerationTime.INSERT)
//        private Instant createdAt;
//        @Column(name = "updated_at")
//        @Generated(GenerationTime.ALWAYS)
//        private Instant updatedAt;
//        @Column(name = "entity_registration_no")
//        private String companyRegistrationNumber;
//        @Column(name = "company_status")
//        private String companyStatus;
//        @Column(name = "fi_id")
//        private Long fiid;
//}