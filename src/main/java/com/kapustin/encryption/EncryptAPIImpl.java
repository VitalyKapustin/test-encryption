package com.kapustin.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.annotation.PostConstruct;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Created by v.kapustin on Sep 9, 2015.
 * Facade for {@link AESBouncyCastle}
 */
@Service
public class EncryptAPIImpl implements EncryptAPI {

	@Autowired
	private Kryo kryo;
	
	@Autowired
	private AESBouncyCastle abc;

	private byte[] keyValue = new byte[] { -63, 95, -123, 53, 47, -91, -74, 114, 71, -42, -79, 68, -107, -100, -53, 119, 88, -126, -71, 34, 125, -78, -21, -62, -71, 83, -72, -127, -124, 125, -12, 122 };
	
	@PostConstruct
	@SuppressWarnings("restriction")
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
		Output outputStream = null;		
		try {
			outputStream = new Output(new ByteArrayOutputStream());			
			kryo.writeClassAndObject(outputStream, input);
			return outputStream.toBytes();			
		} finally {			
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	private Object byteArray2Object(byte[] input) throws IOException, ClassNotFoundException {
		Input inputStream = null;		
		try {
			inputStream = new Input(new ByteArrayInputStream(input));			
			return kryo.readClassAndObject(inputStream);
		} finally {			
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}