package com.kapustin.encryption;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kapustin.configuration.ApplicationConfig;
import com.kapustin.encryption.annotation.EncryptedAnnotationProcessor;

/**
 * Created by v.kapustin on Sep 9, 2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
public class EncryptionTest {
		
	private TestClass testClassObject = new TestClass();
	private TestClass2 testClass2Object = new TestClass2();
	
	@Autowired
	private EncryptedAnnotationProcessor encryptedAnnotationProcessor;

	@Before
	public void before() {		
		testClassObject.setObjectInt(20);		
		testClassObject.setObjectFloat(30f);
		testClass2Object.setObjectInt(20);		
		testClass2Object.setObjectFloat(30f);
	}
	
	@Test
	@Ignore
	public void separateFieldsEncryptionTest() {		
		encryptedAnnotationProcessor.process(testClassObject);
		
		Assert.assertNull(testClassObject.getObjectInt());		
		Assert.assertNull(testClassObject.getObjectFloat());		
	}
	
	@Test
	@Ignore
	public void separateFieldsDecriptionTest() {
		encryptedAnnotationProcessor.process(testClassObject);
		encryptedAnnotationProcessor.process(testClassObject);
		Assert.assertEquals(20, testClassObject.getObjectInt().intValue());
		Assert.assertEquals(30, testClassObject.getObjectFloat().intValue());
	}
	
	@Test
	public void allFieldsEncryptionTest() {
		encryptedAnnotationProcessor.process(testClass2Object);
		
		Assert.assertNull(testClass2Object.getObjectInt());		
		Assert.assertNull(testClass2Object.getObjectFloat());
	}
	
	@Test
	public void allFieldsDecriptionTest() {
		encryptedAnnotationProcessor.process(testClass2Object);
		encryptedAnnotationProcessor.process(testClass2Object);
		Assert.assertEquals(20, testClass2Object.getObjectInt().intValue());
		Assert.assertEquals(30, testClass2Object.getObjectFloat().intValue());
	}
}