package ru.center.inform.docs.client.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.center.inform.docs.client.domain.Role;
import ru.center.inform.docs.client.domain.User;
import ru.center.inform.docs.client.repository.UserRepository;

import javax.annotation.PostConstruct;

@Slf4j
@EnableFeignClients(basePackages = {"ru.center.inform.docs.client.feign"})
@Configuration
public class AppConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User save = userRepository.save(new User(null, "admin", "admin",
                passwordEncoder.encode("p@ssw0rd"), Role.ROLE_ADMIN, true));
        log.info(save.toString());
    }
}
