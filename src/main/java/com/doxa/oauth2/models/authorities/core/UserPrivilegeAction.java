//package com.doxa.oauth2.models.authorities.core;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "user_privilege_action", schema = "authority")
//public class UserPrivilegeAction {
//
//    @Id
//    @JsonIgnore
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @JsonIgnore
//    private String uuid;
//
//    @JsonIgnore
//    @OneToOne(targetEntity = UserPrivilege.class, cascade = CascadeType.ALL)
//    @JoinColumn(referencedColumnName = "id", name = "user_privilege_id")
//    private UserPrivilege userPrivilege;
//
//    private boolean read;
//
//    private boolean write;
//
//    private boolean approve;
//
//    public void setAdminPrivilege() {
//        read = true;
//        write = true;
//        approve = true;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserPrivilegeAction action = (UserPrivilegeAction) o;
//        return read == action.read && write == action.write && approve == action.approve;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(read, write, approve);
//    }
//}
