package com.essential.app.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *Update utility class (null fields ignored)
 */
public class UpdateUtil {

  /**
   *All properties with null values are not copied
   *
   * @param source
   * @param target
   */
  public static void copyNullProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullField(source));
  }

  public static void copyNotNullProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNotNullField(source));
  }

  /**
   *Get empty field in property
   *
   * @param target
   * @return
   */
  public static String[] getNullField(Object target) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(target);
    PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
    Set<String> notNullFieldSet = new HashSet<>();
    if (propertyDescriptors.length > 0) {
      for (PropertyDescriptor p : propertyDescriptors) {
        String name = p.getName();
        Object value = beanWrapper.getPropertyValue(name);
        if (Objects.isNull(value)) {
          notNullFieldSet.add(name);
        }
      }
    }
    String[] notNullField = new String[notNullFieldSet.size()];
    return notNullFieldSet.toArray(notNullField);
  }

  public static String[] getNotNullField(Object target) {
    BeanWrapper beanWrapper = new BeanWrapperImpl(target);
    PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
    Set<String> notNullFieldSet = new HashSet<>();
    if (propertyDescriptors.length > 0) {
      for (PropertyDescriptor p : propertyDescriptors) {
        String name = p.getName();
        Object value = beanWrapper.getPropertyValue(name);
        if (!Objects.isNull(value)) {
          notNullFieldSet.add(name);
        }
      }
    }
    String[] notNullField = new String[notNullFieldSet.size()];
    return notNullFieldSet.toArray(notNullField);
  }
}