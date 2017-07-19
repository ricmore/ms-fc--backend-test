package com.scmspain.services.digester;

import com.scmspain.domain.entities.Tweet;
import com.scmspain.domain.entities.TweetElement;
import com.scmspain.domain.entities.TweetElementType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ricardmore on 04/07/2017.
 */
public class TweetLinksComposer implements TweetComposer {

    @Override
    public void compose(Tweet tweet) {
        if (tweet.isComposable()) {
            String composedText = tweet.getTweet();
            List<TweetElement> elements =
                    tweet.getElements().stream()
                            .filter(
                                    e -> e.getType().equals(TweetElementType.LINK)
                            ).sorted(
                                (tweet1, tweet2) -> tweet2.getPosition() - tweet1.getPosition()
                            ).collect(Collectors.toList());

            for (TweetElement element : elements) {
                StringBuilder builder = new StringBuilder();
                composedText =
                        builder
                        .append(composedText.substring(0, element.getPosition()))
                        .append(element.getContent())
                        .append(composedText.substring(element.getPosition()))
                        .toString();
            }
            tweet.setTweet(composedText);
        }
    }


}
