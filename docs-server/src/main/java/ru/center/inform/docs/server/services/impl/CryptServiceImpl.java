package ru.center.inform.docs.server.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.*;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.springframework.util.Assert;
import ru.center.inform.docs.server.exception.DecryptDataException;
import ru.center.inform.docs.server.exception.EncryptDataException;
import ru.center.inform.docs.server.services.CryptService;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CryptServiceImpl implements CryptService {

    private final X509Certificate encryptionCertificate;
    private final PrivateKey decryptionKey;

    @Override
    public byte[] encryptData(byte[] data) {
        log.info("encryptData");

        try {
            return encrypt(data);
        } catch (CertificateEncodingException | CMSException | IOException e) {
            log.error(e.getMessage(), e);
            throw new EncryptDataException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] decryptData(byte[] encryptedData) {
        log.info("encryptData");

        try {
            return decrypt(encryptedData);
        } catch (CMSException e) {
            log.error(e.getMessage(), e);
            throw new DecryptDataException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] signData(byte[] data) {
        log.info("signData");

        try {
            return sign(data);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public boolean verifySignedData(byte[] signedData) {
        log.info("verifySignedData");

        try {
            return verify(signedData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private byte[] encrypt(byte[] data) throws CertificateEncodingException, CMSException, IOException {

        Assert.notNull(data, "byte array must not be null");

        CMSEnvelopedDataGenerator cmsEnvelopedDataGenerator = new CMSEnvelopedDataGenerator();
        JceKeyTransRecipientInfoGenerator transKeyGen = new JceKeyTransRecipientInfoGenerator(encryptionCertificate);
        cmsEnvelopedDataGenerator.addRecipientInfoGenerator(transKeyGen);
        CMSTypedData msg = new CMSProcessableByteArray(data);

        OutputEncryptor encryptor = new JceCMSContentEncryptorBuilder(CMSAlgorithm.AES128_CBC)
                .setProvider("BC").build();

        CMSEnvelopedData cmsEnvelopedData = cmsEnvelopedDataGenerator
                .generate(msg, encryptor);

        return cmsEnvelopedData.getEncoded();
    }

    private byte[] decrypt(byte[] encryptedData) throws CMSException {

        Assert.notNull(encryptedData, "byte array must not be null");

        CMSEnvelopedData envelopedData = new CMSEnvelopedData(encryptedData);
        Collection<RecipientInformation> recipients = envelopedData.getRecipientInfos().getRecipients();
        KeyTransRecipientInformation recipientInfo = (KeyTransRecipientInformation) recipients.iterator().next();
        JceKeyTransRecipient recipient = new JceKeyTransEnvelopedRecipient(decryptionKey);

        return recipientInfo.getContent(recipient);
    }

    public byte[] sign(byte[] data)
            throws CertificateEncodingException, OperatorCreationException, CMSException, IOException {

        List<X509Certificate> certList = new ArrayList<>();
        CMSTypedData cmsData = new CMSProcessableByteArray(data);
        certList.add(encryptionCertificate);
        Store certs = new JcaCertStore(certList);
        CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(decryptionKey);
        cmsGenerator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder()
                .setProvider("BC").build()).build(contentSigner, encryptionCertificate));
        cmsGenerator.addCertificates(certs);
        CMSSignedData cms = cmsGenerator.generate(cmsData, true);

        return cms.getEncoded();
    }

    public boolean verify(final byte[] signedData)
            throws CMSException, IOException, OperatorCreationException, CertificateException {

        ByteArrayInputStream bIn = new ByteArrayInputStream(signedData);
        ASN1InputStream aIn = new ASN1InputStream(bIn);
        CMSSignedData s = new CMSSignedData(ContentInfo.getInstance(aIn.readObject()));
        aIn.close();
        bIn.close();
        Store<X509CertificateHolder> certs = s.getCertificates();
        SignerInformationStore signers = s.getSignerInfos();
        Collection<SignerInformation> c = signers.getSigners();
        SignerInformation signer = c.iterator().next();
        Collection<X509CertificateHolder> certCollection = certs.getMatches(signer.getSID());
        Iterator<X509CertificateHolder> certIt = certCollection.iterator();
        X509CertificateHolder certHolder = certIt.next();

        return signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certHolder));
    }

}
