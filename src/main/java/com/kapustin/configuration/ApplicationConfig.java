package com.kapustin.configuration;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.esotericsoftware.kryo.Kryo;
import com.kapustin.encryption.TestClass;
import com.kapustin.encryption.TestClass2;
import com.kapustin.encryption.annotation.Encrypted;

/**
 * Created by v.kapustin on Sep 10, 2015.
 */
@Configuration
@ComponentScan("com.kapustin")
public class ApplicationConfig {
	
	@Bean
	public Kryo getKryo() {
		return new Kryo();
	}
}
