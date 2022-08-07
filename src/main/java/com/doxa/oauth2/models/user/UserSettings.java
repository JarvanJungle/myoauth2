//package com.doxa.oauth2.models.user;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "user_settings", schema = "public")
//public class UserSettings {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "is_2fa")
//    private boolean is2Fa;
//
//    @Column(name = "must_set_password")
//    private boolean mustSetPassword;
//
//    @JoinColumn(name = "language", referencedColumnName = "id")
//    @ManyToOne(targetEntity = Language.class, fetch = FetchType.EAGER)
//    private Language language;
//
//    @JsonIgnore
//    @Column(name = "two_fa_secret")
//    private String secret;
//
//    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Override
//    public String toString() {
//        return "UserSettings [id=" + id + ", is2FA=" + is2Fa + ", mustSetPassword=" + mustSetPassword + ", language="
//                + language + ", secret=" + secret + "]";
//    }
//
//}
