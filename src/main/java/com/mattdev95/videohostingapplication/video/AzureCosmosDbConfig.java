package com.mattdev95.videohostingapplication.video;

import com.azure.data.cosmos.CosmosKeyCredential;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import com.microsoft.azure.spring.data.cosmosdb.config.CosmosDBConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCosmosRepositories
public class AzureCosmosDbConfig extends AbstractCosmosConfiguration {

    @Value("${spring.cloud.azure.cosmos.endpoint}")
    private String uri;

    @Value("${spring.cloud.azure.cosmos.key}")
    private String key;

    @Value("${spring.cloud.azure.cosmos.database}")
    private String dbName;

    private CosmosKeyCredential cosmosKeyCredential;

    @Bean
    public CosmosDBConfig getConfig() {
        this.cosmosKeyCredential = new CosmosKeyCredential(key);
        CosmosDBConfig cosmosdbConfig = CosmosDBConfig.builder(uri, this.cosmosKeyCredential, dbName)
                .build();
        return cosmosdbConfig;
    }


    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}
