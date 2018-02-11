package com.github.eendroroy.twitterz.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date
import java.util.TreeSet

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Past

/**
 *
 * @author indrajit
 */

@Entity
@Table(name = "users")
class User {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_seq")
    @SequenceGenerator(name="user_id_seq", sequenceName="users_user_id_seq", allocationSize=1)
    @Column(name = "user_id")
    var id: Long? = null

    @Column(name = "email", unique = true)
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    var email: String? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password_salt")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    var password: String? = null

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "access_token")
    var accessToken: String? = null

    @Column(name = "full_name")
    var fullName: String? = null

    @Column(name = "user_name", unique = true)
    @NotEmpty(message = "*Please provide your username")
    var userName: String? = null

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "*Date of birth can not be in future")
    private var dateOfBirth: Date? = null

    @Column(name = "active")
    var active: Int? = null

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    var tweets: Set<Tweet>? = TreeSet()

    fun getDateOfBirth(): Date? {
        if (dateOfBirth != null) {
            return Date(this.dateOfBirth!!.time)
        }
        return null
    }

    fun setDateOfBirth(dateOfBirth: Date) {
        this.dateOfBirth = Date(dateOfBirth.time)
    }
}
