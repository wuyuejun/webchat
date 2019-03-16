package com.xcl.webchat.core.utils;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author :xiaochanglu
 * @Description :对象之间的属性拷贝工具
 * BeanUtils.copyProperties("转换后的类", "要转换的类");
 * PropertyUtils.copyProperties("转换后的类", "要转换的类");
 * @date :2018/12/17 17:20
 */
public class PropertiesCopyUtil {
    /**
     * @Description  ：拷贝属性到指定对象
     * @author       : xcl
     * @param        : [source, clazz] [被拷贝属性的对象,带处理对象]
     * @return       : T 指定对象(带处理对象)
     * @date         : 2019/1/9 9:22
     */
    public static <T> T copyProperties(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Description  ：指定不拷贝属性,剩余属性拷贝
     * @author       : xcl
     * @param        : [source, clazz, ignoreProperties] [被拷贝属性的对象,带处理对象,不拷贝的属性]
     * @return       : T
     * @date         : 2019/1/9 9:25
     */
    public static <T> T copyPropertiesIgnore(Object source, Class<T> clazz, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {}
        return null;
    }
    /**
     * @Description  ：指定拷贝属性
     * @author       : xcl
     * @param        : [source, clazz, specificProperties]
     * @return       : T
     * @date         : 2019/1/9 9:27
     */
    public static <T> T copyPropertiesSpecific(Object source, Class<T> clazz, String... specificProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = clazz.newInstance();
            if (specificProperties == null) {
                return target;
            }
            List<String> specificList = Arrays.asList(specificProperties);
            copySpecificProperties(source, target, specificList);
            return target;
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * @Description  ：拷贝 final修饰的对象属性值
     * @author       : xcl
     * @param        : [source, target, properties]
     * @return       : void
     * @date         : 2019/1/9 9:30
     */
    private static void copySpecificProperties(final Object source,final Object target, final Iterable<String> properties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);
        for (final String propertyName : properties) {
            trg.setPropertyValue(propertyName,src.getPropertyValue(propertyName));
        }
    }

    //    public static void main(String[] args) {
    //        Test test = new Test("张三","1234",18);
    //        User user = copyProperties(test, User.class);
    //        User user = copyPropertiesIgnore(test, User.class,"username","password");
    //        User user = copyPropertiesSpecific(test, User.class,"username","password");
    //        System.out.println(user.toString());
    //    }


}
