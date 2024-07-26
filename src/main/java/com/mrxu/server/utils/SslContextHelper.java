package com.mrxu.server.utils;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Slf4j
public class SslContextHelper {

    private static final String keyStoreFormat = "JKS";
    private static final String sslProtocol = "SSLv3";
    private static final String keyStorePassword = "ZTo@fd389";

    public static SslContext getSslContext()
            throws IOException, NoSuchAlgorithmException, KeyManagementException, KeyStoreException, CertificateException, UnrecoverableKeyException {
        final InputStream jksFile = SslContextHelper.class.getResourceAsStream("/cert/SHA256withRSA__.zto.com.jks");
//        final InputStream keyFile = SslContextHelper.class.getClass().getResourceAsStream("/cert/_.zto.com.key");
//        final InputStream crtFile = SslContextHelper.class.getClass().getResourceAsStream("/cert/_.zto.com.cet");

        KeyStore ks = KeyStore.getInstance(keyStoreFormat);
        ks.load(jksFile, keyStorePassword.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, keyStorePassword.toCharArray());

//        SSLContext serverContext = SSLContext.getInstance(sslProtocol);
        SslContext sslContext = SslContextBuilder.forServer(kmf).build();
//        serverContext.init(kmf.getKeyManagers(), null, null);

//        SslContext sslContext = SslContextBuilder.forServer(crtFile, keyFile).build();
        return sslContext;
    }

}