package com.github.eendroroy.twitterz.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

/**
 *
 * @author indrajit
 */

@Entity
@Table(name = "tweets")
class Tweet {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tweet_id_seq")
    @SequenceGenerator(name="tweet_id_seq", sequenceName="tweets_tweet_id_seq", allocationSize=1)
    @Column(name = "tweet_id")
    var id: Long? = null

    @Column(name = "body", unique = false)
    @NotEmpty(message = "*Please provide a tweet")
    var body: String? = null

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    var user: User? = null

    fun getUserName(): String? {
        return user!!.userName
    }
}
