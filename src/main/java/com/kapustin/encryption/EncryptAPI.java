package com.kapustin.encryption;

/**
 * Created by v.kapustin on Sep 10, 2015.
 */
public interface EncryptAPI {

	public byte[] encrypt(Object input);
	
	public Object decrypt(byte[] input);
}
