package com.scmspain.domain.repository;

import com.scmspain.domain.entities.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Tweets Spring JPA repository.
 *
 * @author ricardmore
 */
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    /**
     * We want the tweets to be ordered by creation date desc
     * @param page pageable atributes; page and size.
     * @param discarded select discarded or non discarded tweets.
     * @return Tweet's page
     */
    Page<Tweet> findByDiscardedOrderByPublicationDateDesc(Boolean discarded, Pageable page);

    /**
     * Fin ds all the discarded tweets. We want the tweets to be ordered by discarded date desc
     * @param page pageable atributes; page and size.
     * @param discarded select discarded or non discarded tweets.
     * @return Tweet's page
     */
    Page<Tweet> findByDiscardedOrderByDiscardedDateDesc(Boolean discarded, Pageable page);

}
