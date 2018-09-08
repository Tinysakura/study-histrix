package com.cfh.eurekaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//获得服务发现能力
@EnableDiscoveryClient
//获取容错能力
@EnableCircuitBreaker
@SpringBootApplication
public class EurekaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaConsumerApplication.class, args);
	}

	@Bean
	@LoadBalanced//获得客户端的负载均衡能力
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
