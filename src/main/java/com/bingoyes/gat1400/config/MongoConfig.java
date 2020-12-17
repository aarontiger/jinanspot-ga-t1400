package com.bingoyes.gat1400.config;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;


    @Bean
    public MongoMappingContext mongoMappingContext() {
        return new MongoMappingContext();
    }
    /**
     * 配置mongodb1
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter() throws UnknownHostException {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.mongoDbFactory1());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Bean
    @Primary
    public MongoDbFactory mongoDbFactory1() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(mongodbUri));
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(this.mongoDbFactory1(), mappingMongoConverter());
    }
}
