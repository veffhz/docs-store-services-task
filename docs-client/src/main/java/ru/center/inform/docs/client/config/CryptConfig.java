package ru.center.inform.docs.client.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import ru.center.inform.docs.client.service.CryptService;
import ru.center.inform.docs.client.service.impl.CryptServiceImpl;

import java.io.IOException;

import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class CryptConfig {

    public static final String CRYPTO_CRT = "crypto.crt";
    public static final String CRYPTO_P12 = "crypto.p12";
    public static final String PASSWORD = "123456";
    public static final String ALIAS = "crypto";

    @Bean
    public CryptService documentService() throws GeneralSecurityException, IOException {

        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");

        ClassPathResource classPathCrt = new ClassPathResource(CRYPTO_CRT);

        X509Certificate certificate = (X509Certificate) certFactory
                .generateCertificate(classPathCrt.getInputStream());

        char[] keystorePassword = PASSWORD.toCharArray();
        char[] keyPassword = PASSWORD.toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12");
        ClassPathResource classPathP12 = new ClassPathResource(CRYPTO_P12);

        keystore.load(classPathP12.getInputStream(), keystorePassword);
        PrivateKey key = (PrivateKey) keystore.getKey(ALIAS, keyPassword);

        return new CryptServiceImpl(certificate, key);
    }
}
