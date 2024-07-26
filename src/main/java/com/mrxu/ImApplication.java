package com.mrxu;

import com.mrxu.server.ZimServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImApplication.class, args);
    }

    @Autowired
    private ZimServer zimServer;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        zimServer.start();
    }
}
