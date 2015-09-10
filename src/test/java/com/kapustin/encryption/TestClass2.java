package com.kapustin.encryption;

import java.io.Serializable;

import com.kapustin.encryption.annotation.Encrypted;

/**
 * Created by v.kapustin on Sep 10, 2015.
 */
@Encrypted(targetFieldNameSuffix="Bytes")
public class TestClass2 implements Serializable {
		
	private Integer objectInt;
	
	private byte[] objectIntBytes;
		
	private Float objectFloat;
	
	private byte[] objectFloatBytes;
		
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
