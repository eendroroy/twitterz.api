package com.github.eendroroy.twitterz.api.entity

import lombok.Data
import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

/**
 *
 * @author indrajit
 */

@Entity
@Table(name = "users")
@Data
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_seq")
    @SequenceGenerator(name="user_id_seq", sequenceName="users_user_id_seq", allocationSize=1)
    @Column(name = "user_id")
    private long id

    @Column(name = "email", unique = true)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email

    @Column(name = "password_salt")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password

    @Column(name = "access_token")
    private String accessToken

    @Column(name = "full_name")
    private String fullName

    @Column(name = "user_name", unique = true)
    @NotEmpty(message = "*Please provide your username")
    private String userName

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "*Please provide your date of birth")
    @Past(message = "*Date of birth can not be in future")
    private Date dateOfBirth

    @Column(name = "active")
    private int active

    Date getDateOfBirth() {
        if (dateOfBirth == null) {
            return null
        } else {
            return new Date(dateOfBirth.getTime())
        }
    }

    void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = new Date(dateOfBirth.getTime())
    }
}