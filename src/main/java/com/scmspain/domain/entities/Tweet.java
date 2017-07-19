package com.scmspain.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
/**
 * Holds all the tweet fields:<ul>
 *     <li>Unique id</li>
 *     <li>publisher as String</li>
 *     <li>Tweet's text</li>
 *     <li>publication date</li>
 *     <li>discarded indicator</li>
 *     <li>discarded date</li>
 *     <li>List of the related {@link TweetElement} extracted form the original text.</li>
 * </ul>
 */
public class Tweet {

    /**
     * Tweet text max length.
     */
    public static final int MAX_LENGTH = 140;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false, length = MAX_LENGTH)
    private String tweet;

    @Column(nullable = false)
    private LocalDateTime publicationDate;

    @Column (nullable=false)
    private Boolean discarded;

    @Column(nullable = true)
    private LocalDateTime discardedDate;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="tweet")
    private List<TweetElement> elements;

    /**
     * Why is this field here? Should be null for most of the tweets.
     * Must be moved to a different table.
     */
    @Column (nullable=true)
    private Long pre2015MigrationStatus = 0L;

    public Tweet() {
        this.elements = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public Boolean getDiscarded() {
        return discarded;
    }

    public void setDiscarded(Boolean discarded) {
        this.discarded = discarded;
    }

    public void setPublisher(@NotNull @Size(min = 1) String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(@NotNull @Size(min = 1, max = Tweet.MAX_LENGTH) String tweet) {
        this.tweet = tweet;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
    }

    public void addElement(TweetElement element) {
        this.elements.add(element);
    }

    public List<TweetElement> getElements() {
        return elements;
    }

    public boolean isComposable() {
        return this.elements.size() > 0;
    }

    public void setDiscardedDate(LocalDateTime discardedDate) {
        this.discardedDate = discardedDate;
    }
}
