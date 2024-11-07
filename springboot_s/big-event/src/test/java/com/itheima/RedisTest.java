//package com.itheima;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//
//import java.util.concurrent.TimeUnit;
//
//@SpringBootTest // 如果在测试类上添加了这个注解,那么将来单元测试方法执行之前,会先初始化Spring容器
//public class RedisTest {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Test
//    public void testSet() {
//        //往red1s中存一个键值对 itringRedisTemolake
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//
//        operations.set("username", "zhangsan");
//        operations.set("id", "1", 15, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testGet() {
//        //从redis中取出一个键值对
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//        String username = operations.get("username");
//        System.out.println(username);
//    }
//}
//