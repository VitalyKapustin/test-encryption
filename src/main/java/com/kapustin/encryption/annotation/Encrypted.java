package com.kapustin.encryption.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by v.kapustin on Sep 9, 2015.
 * Indicates class or field which should be encrypted
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface Encrypted {
	
	/**
	 * Field of the same class which will get encrypted data. Only applicable for field marked
	 * @return
	 */
	String target() default "";
	
	/**
	 * Suffix of field of the same class which will get encrypted data. Only applicable for field marked. Only applicable for class marked
	 */
	String targetFieldNameSuffix() default "Encrypted";
}
