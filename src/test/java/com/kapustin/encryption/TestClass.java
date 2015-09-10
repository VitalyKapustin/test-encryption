package com.kapustin.encryption;

import java.io.Serializable;

import com.kapustin.encryption.annotation.Encrypted;

/**
 * Created by v.kapustin on Sep 9, 2015.
 */
public class TestClass implements Serializable {
	
	@Encrypted
	private Integer objectInt;
	
	private byte[] objectIntEncrypted;
	
	@Encrypted(target = "objectFloatBytes")
	private Float objectFloat;
	
	private byte[] objectFloatEncrypted;
		
	public Integer getObjectInt() {
		return objectInt;
	}
	public void setObjectInt(Integer objectInt) {
		this.objectInt = objectInt;
	}	
	public Float getObjectFloat() {
		return objectFloat;
	}
	public void setObjectFloat(Float objectFloat) {
		this.objectFloat = objectFloat;
	}		
}
