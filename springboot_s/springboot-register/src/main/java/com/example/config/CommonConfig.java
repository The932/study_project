package com.example.config;

import cn.itcast.pojo.Country;
import cn.itcast.pojo.Province;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    //注入Country对象
    //如果配置文件中配置了指定的信息，则注入，否则不注入
    @ConditionalOnProperty(prefix = "country", name = {"name", "system"})
    @Bean
    public Country country(@Value("${country.name}") String name, @Value("${country.system}") String system) {
        Country country = new Country();
        country.setName(name);
        country.setSystem(system);
        return country;
    }

//    @Bean
//    public Province province(Country country) {
//        System.out.println("province:" + country);
//        return new Province();
//    }
    //如果ioc容器中有Country对象，则注入，否则不注入
    @Bean
    //@ConditionalOnMissingBean(Country.class)
    //如果当前环境中存在DispatcherServlet对象，则注入Province，否则不注入
    //如果当前引入了web起步依赖，则环境中会存在DispatcherServlet对象，否则不会存在
    @ConditionalOnClass(name= {"org.springframework.web.servlet.DispatcherServlet"})
    public Province province() {
        return new Province();
    }
}
