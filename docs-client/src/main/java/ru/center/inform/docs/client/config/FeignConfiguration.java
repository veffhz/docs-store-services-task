package ru.center.inform.docs.client.config;

import feign.*;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.InputStream;
import java.security.*;


@Slf4j
@Configuration
public class FeignConfiguration {

    public static final String JKS_PASSWORD = "123456";
    public static final String CLIENT_JKS = "pki/client.jks";

//    @Bean
//    public RestTemplate getRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        KeyStore keyStore;
//        HttpComponentsClientHttpRequestFactory requestFactory = null;
//
//        try {
//            keyStore = KeyStore.getInstance("jks");
//            ClassPathResource classPathResource = new ClassPathResource("pki/client.jks");
//            InputStream inputStream = classPathResource.getInputStream();
//            keyStore.load(inputStream, "123456".toCharArray());
//
//            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(new SSLContextBuilder()
//                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                    .loadKeyMaterial(keyStore, "123456".toCharArray()).build(),
//                    NoopHostnameVerifier.INSTANCE);
//
//            HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
//                    .setMaxConnTotal(5)
//                    .setMaxConnPerRoute(5)
//                    .build();
//
//            requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//            requestFactory.setReadTimeout(10000);
//            requestFactory.setConnectTimeout(10000);
//
//            restTemplate.setRequestFactory(requestFactory);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return restTemplate;
//    }

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .retryer(Retryer.NEVER_RETRY)
                .client(new Client.Default(getSSLSocketFactory(), NoopHostnameVerifier.INSTANCE));
    }

    private SSLSocketFactory getSSLSocketFactory() {
        SSLContext sslContext = null;

        try {
            char[] allPassword = JKS_PASSWORD.toCharArray();
            KeyStore keyStore;

            keyStore = KeyStore.getInstance("jks");
            ClassPathResource classPathResource = new ClassPathResource(CLIENT_JKS);
            InputStream inputStream = classPathResource.getInputStream();
            keyStore.load(inputStream, allPassword);

            sslContext = SSLContextBuilder
                    .create()
                    .setKeyStoreType("jks")
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .loadKeyMaterial(keyStore, allPassword)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return sslContext.getSocketFactory();
    }

}
