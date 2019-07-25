package com.useradministration.webapp.dao.entity;

import com.useradministration.webapp.dao.entity.enumeration.Role;
import com.useradministration.webapp.dao.entity.enumeration.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_account", schema = "management")
public class UserAccount extends BaseEntity<Long> {

    @Pattern(regexp = "^[a-zA-Z]+$")
    @Size(min = 3, max = 16, message = "username must be between 3 and 16 characters!")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Pattern(regexp = "^[a-zA-Z]+$")
    @Size(min = 1, max = 16, message = "First Name must be between 1 and 16 characters!")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$")
    @Size(min = 1, max = 16, message = "Last Name must be between 1 and 16 characters!")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

}