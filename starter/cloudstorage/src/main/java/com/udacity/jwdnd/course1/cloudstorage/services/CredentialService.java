package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialsMapper;
    private EncryptionService encryptionService;
    private UserService userService;


    Logger logger = LoggerFactory.getLogger(CredentialService.class);

    public CredentialService(CredentialMapper credentialsMapper, EncryptionService encryptionService, UserService userService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    public Integer createCredential(Credential credential){
        String encodedKey = generateEncodedKey();
        encryptPassword(credential, encodedKey);

        return credentialsMapper.insert(credential);
    }

    public Boolean updateCredential(Credential credential){
        String encodedKey = generateEncodedKey();
        encryptPassword(credential, encodedKey);
        return credentialsMapper.update(credential);
    }

    public Boolean deleteCredential(Integer credentialId){
        return credentialsMapper.delete(credentialId);
    }

    public String generateEncodedKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public void encryptPassword(Credential credential, String encodedKey) {
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
    }

    public void decryptPassword(Credential credential){
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
    }

    public String getDecryptedPassword(Credential credential){
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public List<Credential> getCredentialByUserId(Integer userId){
        return credentialsMapper.getCredentialListByUserId(userId);
    }

    public List<Credential> getCredentialListByLoggedInUserId() {
        return getCredentialByUserId(userService.getLoggedInUserId());
    }
}
