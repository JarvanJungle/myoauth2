//package com.doxa.oauth2.models.user;
//
//import com.doxa.oauth2.common.CompanyJWT;
//import com.doxa.oauth2.models.companies.DoxaEntity;
//import com.doxa.oauth2.models.financialInstitution.FinancialInstitution;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.Generated;
//import org.hibernate.annotations.GenerationTime;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "users", schema = "public")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//    private String name;
//    @JsonIgnore
//    @Column(name = "hashed_password")
//    private String password;
//    @JsonIgnore
//    @Column(name = "password_salt")
//    private String passwordSalt;
//    @ManyToOne(targetEntity = DoxaEntity.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "entity_id")
//    private DoxaEntity entity;
//    @OneToMany(targetEntity = UserCompanies.class, fetch = FetchType.LAZY, mappedBy = "user")
//    private List<UserCompanies> companies;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "created_at")
//    @Generated(GenerationTime.INSERT)
//    private Instant createdAt;
//    @Generated(GenerationTime.ALWAYS)
//    @Column(name = "updated_at")
//    private Instant updatedAt;
//    private boolean isActive;
//    private boolean isDeleted;
//    private String designation;
//    @Column(name = "work_number")
//    private String workNumber;
//    @Column(name = "uuid")
//    private String uuid;
//
//    @Column(name = "country_code")
//    private String countryCode;
//
//    @Column(name = "remarks")
//    private String remarks;
//
//    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "user")
//    @PrimaryKeyJoinColumn
//    private UserSettings settings;
//
//    @Column(name = "avatar_url")
//    private String avatarUrl;
//
//    @Column(name = "fi_uuid")
//    private String fiUuid;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "fi_id")
//	private FinancialInstitution financialInstitution;
//
//    public User(User user) {
//        this.id = user.id;
//        this.name = user.name;
//        this.password = user.password;
//        this.email = user.email;
//        this.createdAt = user.getCreatedAt();
//        this.updatedAt = user.getUpdatedAt();
//        this.isActive = user.isActive;
//        this.isDeleted = user.isDeleted;
//        this.entity = user.entity;
////        this.roles = user.roles;
//        this.uuid = user.uuid;
//        this.settings = user.settings;
//        this.avatarUrl = user.avatarUrl;
//    }
//
//    public User(String name, String password, String email) {
//        this.name = name;
//        this.password = password;
//        this.email = email;
//        this.isActive = true;
//        this.createdAt = Instant.now();
//        this.updatedAt = Instant.now();
//        this.isDeleted = false;
//    }
//
//    public UUID getId() {
//        return UUID.fromString(uuid);
//    }
//
//    public String getRoles() {
//        Set<Role> roles = new HashSet<>();
//        for (UserCompanies userCompany : companies) {
//            String key = userCompany.getCompanies().getUuid();
//            List<UserRole> roleList = userCompany.getUserRoles();
//            for (UserRole role : roleList) {
//                roles.add(role.getRole());
//            }
//        }
//        return roles.stream().map(Role::getRoleCode).collect(Collectors.joining(" "));
//    }
//
//    public boolean isDoxaAdmin() {
//        return getRoles().contains("DOXA_ADMIN");
//    }
//
//
//    public String getUserName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                ", isActive=" + isActive +
//                ", designation='" + designation + '\'' +
//                ", workNumber='" + workNumber + '\'' +
//                ", uuid='" + uuid + '\'' +
//                '}';
//    }
//}
