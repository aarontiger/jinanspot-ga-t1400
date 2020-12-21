package com.bingoyes.gat1400.config;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource(value = {"classpath:/efs_mongo.yml"})
@ConfigurationProperties(prefix = "mongo")
public class MongoConfigEfs {

    private static Logger logger = LoggerFactory.getLogger(MongoConfigEfs.class);

    List<ServerAddress> mongoServerList;

    @Value("${auth}")
    Boolean auth;
    @Value("${user}")
    String user;
    @Value("${password}")
    String password;

    String databaseName = "efs";
    @Value("${uri}")
    String uri;




    private  void prepareEfsMongoDbConfig() throws Exception {

        String hostName = null;
        int port = -1;

        mongoServerList = new ArrayList<ServerAddress>();

        String[] tempList =uri.split(":");
        hostName = tempList[0];
        port = Integer.parseInt(tempList[1]);
        mongoServerList.add(new ServerAddress(hostName,port));

    }


    public MongoDbFactory efsMongoDbFactory() throws UnknownHostException {

        if(auth) {
           MongoCredential mongoCredential = MongoCredential.createCredential(user, databaseName, password.toCharArray());

            return new SimpleMongoDbFactory(new MongoClient(this.mongoServerList,mongoCredential, MongoClientOptions.builder().build()), this.databaseName);
        }else
        {
            return new SimpleMongoDbFactory(new MongoClient(mongoServerList, MongoClientOptions.builder().build()), databaseName);
        }
    }

    @Bean(name="efsMongoTemplate")
    @Primary
    @Lazy
    public MongoTemplate efsMongoTemplate() throws Exception {

        prepareEfsMongoDbConfig();
        return new MongoTemplate(efsMongoDbFactory());
    }

}
