package com.scmspain.domain.entities;

import javax.persistence.*;

/**
 * A tweet element is an element extracted from the original text, as links or emoticon codes.
 * Saving such external elements in a different table make us easy to analise them and, for instance, disable the prohibited ones; dangerous links for example.
 *
 * @author ricardmore
 */
@Entity
public class TweetElement {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TweetElementType type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int position;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    private boolean disabled;

    public TweetElement() {
        this.disabled = false;
    }

    public TweetElementType getType() {
        return type;
    }

    public void setType(TweetElementType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
