package com.dsec.collab.adaptor.repository.jpa.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tokens")
public class GithubAccessTokenSchema {

    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private UserSchema userSchema;

    @Convert(converter=TokenConvertor.class)
    private String accessToken;

    private LocalDateTime accessTokenExpiryDate;

    @Convert(converter=TokenConvertor.class)
    private String refreshToken;

    private LocalDateTime refreshTokenExpiryDate;

    private String scope;
    private String tokenType;
}
