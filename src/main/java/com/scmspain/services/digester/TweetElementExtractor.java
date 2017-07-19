package com.scmspain.services.digester;

import com.scmspain.domain.entities.Tweet;

/**
 * Object tied to a {@link TweetDigester}. Will be the one in charge of the extraction step for the incoming tweets.<br/>
 * A {@link TweetElementExtractor} implementation has to extract all the external elements from the tweet's text; for instance remove all the links.
 * So this extractor is going to modify:<ul>
 *     <li>Text: removing external elements</li>
 *     <li>TweetElement list: Such extracted elements will be added as elements for the same tweet: content and position in the original text.</li>
 * </ul>
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
