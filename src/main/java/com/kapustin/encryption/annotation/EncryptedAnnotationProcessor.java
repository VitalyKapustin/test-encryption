package com.kapustin.encryption.annotation;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kapustin.encryption.EncryptAPI;

/**
 * Created by v.kapustin on Sep 10, 2015.
 * Process {@link Encrypted} annotation
 */
@Component
public class EncryptedAnnotationProcessor {

	private static final String DEFAULT_TARGET_FIELD_SUFFIX = "Encrypted";
	
	@Autowired
	private EncryptAPI encryptAPI;
	
	public void process(Object input) {
		Class<?> clazz = input.getClass();
		List<Field> encryptedFields;
		Encrypted encryptedAnnotation = clazz.getAnnotation(Encrypted.class); 
		if (encryptedAnnotation != null) {
			String targetFieldNameSuffix = encryptedAnnotation.targetFieldNameSuffix();
			encryptedFields = FieldUtils.getAllFieldsList(clazz);
			encryptedFields.stream().forEach(f -> {
				if (!f.getName().contains(targetFieldNameSuffix)) {
					f.setAccessible(true);				
					String targetFieldName = f.getName() + targetFieldNameSuffix;				
					processFields(f, targetFieldName, input, clazz);					
				}				
			});
		} else {
			String targetFieldNameSuffix = DEFAULT_TARGET_FIELD_SUFFIX;
			encryptedFields = FieldUtils.getFieldsListWithAnnotation(clazz, Encrypted.class);
			encryptedFields.stream().forEach(f -> {		
					f.setAccessible(true);
					Encrypted annotation = f.getAnnotation(Encrypted.class);
					String targetFieldName = annotation.target();
					if (StringUtils.isBlank(targetFieldName)) {
						targetFieldName = f.getName() + targetFieldNameSuffix;
					}
					processFields(f, targetFieldName, input, clazz);			
			});
		}	
	}

	private void processFields(Field sourceField, String targetFieldName, Object input, Class<?> inputClazz) {
		try {
			Field targetField = FieldUtils.getField(inputClazz, targetFieldName, true);
			targetField.setAccessible(true);
			
			Object sourceFieldValue = sourceField.get(input);
			if (sourceFieldValue != null) {
				byte[] bytes = encryptAPI.encrypt(sourceFieldValue);
				targetField.set(input, bytes);
				sourceField.set(input, null);
			} else {
				sourceFieldValue = encryptAPI.decrypt((byte[])targetField.get(input));					
				sourceField.set(input, sourceFieldValue);						
				targetField.set(input, null);					
			}
		} catch (Exception e) {				
			e.printStackTrace();
		}
	}
}
