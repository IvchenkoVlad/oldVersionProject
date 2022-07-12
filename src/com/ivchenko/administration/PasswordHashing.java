package com.ivchenko.administration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHashing {

	public User hashPassword(String passwordInput) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		KeySpec spec = new PBEKeySpec(passwordInput.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		Base64.Encoder enc = Base64.getEncoder();
		String passwordSalt = enc.encodeToString(salt);
		String passwordHash = enc.encodeToString(hash);
		User tempUser = new User(passwordHash, passwordSalt);
		
		return tempUser;
	}
	public boolean checkHash(String passwordInput, String correctPassword, String salty) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Base64.Decoder dec = Base64.getDecoder();
		byte[] salt = dec.decode(salty);
		KeySpec spec = new PBEKeySpec(passwordInput.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		byte[] hashToCheck = dec.decode(correctPassword);
		Base64.Encoder enc = Base64.getEncoder();
		
		
		return enc.encodeToString(hash).equals(enc.encodeToString(hashToCheck));
	}
}
