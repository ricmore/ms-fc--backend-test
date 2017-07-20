package com.scmspain.controller.converter;

import com.scmspain.controller.TweetDTO;
import com.scmspain.domain.entities.Tweet;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Conversion service component for a tweet. From the DTO to the entity.
 *
 * @author ricardmore
 */
@Component
public class TweetDTOToEntity implements Converter<TweetDTO, Tweet> {

    @Override
    public Tweet convert(TweetDTO source) {
        Tweet t = new Tweet();
        t.setId(source.getId());
        t.setPublisher(source.getPublisher());
        t.setTweet(source.getTweet());
        return t;
    }
}
