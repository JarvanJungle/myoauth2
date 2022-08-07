package com.doxa.oauth2.models.user;

import com.doxa.oauth2.models.companies.Company;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "user")
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    @Field("hashed_password")
    private String password;
    private String phone;
    @Field("avatar_url")
    private String avatarUrl;
    @Field("company_id")
    private String companyId;
    @DocumentReference(lazy = true, lookup = "{ 'userList' : ?#{#self._id} }")
    private Company company;
    private String status;
    @Field("role_list")
    private List<String> roleList;
    @DocumentReference(lazy = true, lookup = "{ 'userList' : ?#{#self._id} }")
    private List<Role> roles;
    @Field("created_at")
    private Instant createdAt;
    @Field("updated_at")
    private Instant updatedAt;

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.password = user.password;
        this.email = user.email;
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.status = user.getStatus();
        this.avatarUrl = user.avatarUrl;
    }
//    public String getRoles() {
//        Set<String> roles = new HashSet<>();
//        for (Role role : roles) {
//            roles.add(role.getName());
//        }
//        for (UserCompanies userCompany : companies) {
//            String key = userCompany.getCompanies().getUuid();
//            List<UserRole> roleList = userCompany.getUserRoles();
//            for (UserRole role : roleList) {
//                roles.add(role.getRole());
//            }
//        }
//        return roles.stream().map(Role::getRoleCode).collect(Collectors.joining(" "));
//    }

}
