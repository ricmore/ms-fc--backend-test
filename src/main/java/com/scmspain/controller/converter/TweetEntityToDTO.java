package com.scmspain.controller.converter;

import com.scmspain.controller.TweetDTO;
import com.scmspain.domain.entities.Tweet;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Conversion service component for a tweet. From the entity to the DTO.
 *
 * @author ricardmore
 */
@Component
public class TweetEntityToDTO implements Converter<Tweet, TweetDTO> {

    @Override
    public TweetDTO convert(Tweet source) {
        return new TweetDTO(source.getId(), source.getPublisher(), source.getTweet());
    }
}
