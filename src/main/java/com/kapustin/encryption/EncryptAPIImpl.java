package com.kapustin.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

/**
 * Created by v.kapustin on Sep 9, 2015.
 * Facade for {@link AESBouncyCastle}
 */
@Service
public class EncryptAPIImpl implements EncryptAPI {

	@Autowired
	private AESBouncyCastle abc;

	private byte[] keyValue = new byte[] { -63, 95, -123, 53, 47, -91, -74, 114, 71, -42, -79, 68, -107, -100, -53, 119, 88, -126, -71, 34, 125, -78, -21, -62, -71, 83, -72, -127, -124, 125, -12, 122 };
	
	@PostConstruct
	public void init() {		
		abc.setPadding(new PKCS7Padding());
		abc.setKey(keyValue);		
	}	
	
	public byte[] encrypt(Object input) {		
		try {
			return abc.encrypt(object2ByteArray(input));
		} catch (DataLengthException | InvalidCipherTextException | IOException e) { 
			e.printStackTrace();
		}
		return null;
	}

	public Object decrypt(byte[] input) {
		try {
			return byteArray2Object(abc.decrypt(input));
		} catch (DataLengthException | ClassNotFoundException | InvalidCipherTextException | IOException e) {		
			e.printStackTrace();
		}			
		return null;
	}

	private byte[] object2ByteArray(Object input) throws IOException, DataLengthException, InvalidCipherTextException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream objectOut = null;
		try {
			baos = new ByteArrayOutputStream();
			objectOut = new ObjectOutputStream(baos);
			objectOut.writeObject(input);
			return baos.toByteArray();			
		} finally {
			if (objectOut != null) {
				objectOut.close();
			}
			if (baos != null) {
				baos.close();
			}
		}
	}

	private Object byteArray2Object(byte[] input) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(input);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} finally {
			if (ois != null) {
				ois.close();
			}
			if (bais != null) {
				bais.close();
			}
		}
	}
}