package com.scmspain.services.digester;

import com.scmspain.domain.entities.Tweet;

/**
 * A TweetComposer is exactly the mirror of a {@link TweetElementExtractor}. Is also tied to a {@link TweetDigester}, and manages the outgoing step:
 * composing the tweet's text adding back all the elements to it, in the correct position.
 *
 * @see TweetDigester
 * @see TweetElementExtractor
 * @author ricardmore
 */
public interface TweetComposer {

    /**
     * The text of the tweet must be composed, adding the previously extracted external elements.
     *
     * @param tweet Tweet being composed.
     */
    void compose(Tweet tweet);

}
