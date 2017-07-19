package com.scmspain.services.digester;

import com.scmspain.domain.entities.Tweet;
import com.scmspain.domain.entities.TweetElement;
import com.scmspain.domain.entities.TweetElementType;

import java.util.Arrays;

/**
 * Implentation of a {@link TweetElementExtractor} for the external links.
 *
 * @author ricardmore
 */
public class TweetLinksExtractor implements TweetElementExtractor {

    @Override
    public void extract(Tweet tweet) {
        String text = tweet.getTweet();
        int indexHttp = text.indexOf("http://");
        int indexHttps = text.indexOf("https://");

        while (indexHttp != -1 || indexHttps != -1) {
            // Flipada. Minimum index after filtering the -1. At least one of the index is > 0, so the optional always has a value.
            int index =
                    Arrays.asList(indexHttp, indexHttps).stream()
                            .filter(i -> i.intValue() != -1)
                            .mapToInt(Integer::valueOf)
                            .min().getAsInt();

            String link = text.substring(index);
            String newText = text.substring(0, index);
            int indexLast = link.indexOf(" ");
            if (indexLast != -1) {
                link = link.substring(0, indexLast);
                newText += text.substring(index + indexLast);
            }
            TweetElement element = new TweetElement();
            element.setType(TweetElementType.LINK);
            element.setContent(link);
            element.setPosition(index);
            element.setTweet(tweet);

            tweet.addElement(element);

            text = newText;
            indexHttp = text.indexOf("http://");
            indexHttps = text.indexOf("https://");
        }
        tweet.setTweet(text);
    }

}
