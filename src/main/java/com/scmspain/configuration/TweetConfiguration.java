package com.scmspain.configuration;

import com.scmspain.services.digester.TweetComposer;
import com.scmspain.services.digester.TweetDigester;
import com.scmspain.services.digester.TweetLinksComposer;
import com.scmspain.services.digester.TweetLinksExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class TweetConfiguration {
//    @Bean
//    public TweetServiceImpl getTweetService(EntityManager entityManager, MetricWriter metricWriter) {
//        return new TweetServiceImpl(entityManager, metricWriter);
//    }
//
//    @Bean
//    public TweetController getTweetConfiguration(TweetServiceImpl tweetService) {
//        return new TweetController(tweetService);
//    }

    /**
     * We generate {@link TweetDigester} instances based on a {@link TweetLinksExtractor} and the related {@link TweetLinksComposer}
     *
     * @return Default tweets digester to both, extract and add elements in the tweets are not considered as text (link, emojis,...).
     */
    @Bean
    public TweetDigester getTweetDigester() {

        return new TweetDigester(
                new TweetLinksExtractor[] {new TweetLinksExtractor()},
                new TweetComposer[] {new TweetLinksComposer()}
                );
    }
}
