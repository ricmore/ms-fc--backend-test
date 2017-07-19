package com.scmspain.services.digester;

import com.scmspain.domain.entities.Tweet;

/**
 * This digester is used by the {@link com.scmspain.services.TweetService} to:<ul>
 *     <li>incoming tweets: Extract elements from the tweet, as links or emoticon codes.</li>
 *     <li>outgoing tweets: Take the extracted elements and compose again original text.</li>
 * </ul>
 *
 * @author ricardmore
 */
public class TweetDigester {

    private TweetElementExtractor[] extractors;
    private TweetComposer[] composers;

    /**
     * Constructor based on the extractors and converters.
     *
     * @param extractors In charge of extracting elements from a tweet.
     * @param composers In charge of taking the extracted elements and adding it back to the text.
     */
    public TweetDigester(TweetElementExtractor[] extractors, TweetComposer[] composers) {
        this.extractors = extractors;
        this.composers = composers;
    }

    /**
     * Incoming step. Extract elements. The text of the tweet will be modified, as we are extracting the elements. Also the list of {@link com.scmspain.domain.entities.TweetElement}
     *
     * @param tweet Tweet being modified
     */
    public void digest(Tweet tweet) {
        for (TweetElementExtractor extractor : this.extractors) {
            extractor.extract(tweet);
        }
    }

    /**
     * Outgoing step. This step will take all the {@link com.scmspain.domain.entities.TweetElement} and add them to the original text in the same position they were
     * before extracting them.
     *
     * @param tweet Tweet being modified
     */
    public void compose(Tweet tweet) {
        for (TweetComposer composer : this.composers) {
            composer.compose(tweet);
        }
    }

}
