package com.example.dodo;

import com.example.dodo.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoTestRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        System.out.println("MongoDB connected: " + userRepository.count());
    }
//    @PostConstruct
//    public void checkMongoEnv() {
//        System.out.println("MONGODB_URI = " + System.getenv("MONGODB_URI"));
//    }


}
