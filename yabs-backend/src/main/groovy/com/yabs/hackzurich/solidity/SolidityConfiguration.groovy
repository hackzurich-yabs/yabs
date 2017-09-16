package com.yabs.hackzurich.solidity

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:solidityConfiguration.properties")
class SolidityConfiguration {

    @Autowired
    private Environment environment

    String getYabsAddress() {
        return environment.getProperty('yabsAddress')
    }

}
