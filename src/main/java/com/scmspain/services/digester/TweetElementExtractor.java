package com.scmspain.services.digester;

import com.scmspain.domain.entities.Tweet;

/**
 * Object tied to a {@link TweetDigester}. Will be the one in charge of the extraction step for the incoming tweets.
 *
 * @see TweetDigester
 * @see TweetComposer
 * @author ricardmore
 */
public interface TweetElementExtractor {

    /**
     * The tweet text should be cleared of external elements as links or emoticon codes.
     *
     * @param tweet Tweet to be modified. This method must inform the {@link com.scmspain.domain.entities.TweetElement} list.
     */
    void extract(Tweet tweet);

}
