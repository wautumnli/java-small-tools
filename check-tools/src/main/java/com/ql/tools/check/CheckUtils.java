package com.ql.tools.check;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 参数校验工具
 *
 * @author Autumn Li
 * @date 2024/09/27
 */
public class CheckUtils {

    /**
     * 校验参数函数
     *
     * @param t           待校验对象
     * @param checkFields 校验字段
     */
    public static <T> void check(T t, String checkFields) {
        // 空校验返回
        if (StrUtil.isBlank(checkFields)) {
            return;
        }
        // 存储校验结果
        List<String> checkResult = CollectionUtil.newArrayList();
        // 校验每个字段并存储结果
        for (String field : checkFields.split(",")) {
            Object value = getFieldValue(t, field);
            if (checkEmpty(value)) {
                checkResult.add(field);
            }
        }
        // 返回异常结果
        returnResult(checkResult);
    }

    /**
     * 字段校验结果拼接
     *
     * @param checkResult 字段检查结果集合
     */
    private static void returnResult(List<String> checkResult) {
        if (CollUtil.isNotEmpty(checkResult)) {
            String msg = MessageFormat.format(
                    "字段: {0}为空，请检查",
                    StrUtil.join(",", checkResult)
            );
            throw new RuntimeException(msg);
        }
    }

    private static boolean checkEmpty(Object value) {
        if (value instanceof Collection) {
            return checkValue(value, Objects::nonNull)
                    && checkValue(value, v -> ((Collection<?>) v).isEmpty());
        }
        if (value instanceof String) {
            return checkValue(String.valueOf(value), StrUtil::isBlank);
        }
        return checkValue(value, Objects::isNull);
    }

    /**
     * 检查对象是否空
     *
     * @param value    值
     * @param validate 校验函数
     * @return 是否空值
     */
    private static Boolean checkValue(Object value, Function<Object, Boolean> validate) {
        return validate.apply(value);
    }

    /**
     * 检查字符串是否空
     *
     * @param value    值
     * @param validate 校验函数
     * @return 是否空值
     */
    private static Boolean checkValue(String value, Function<String, Boolean> validate) {
        return validate.apply(value);
    }

    private static <T> Object getFieldValue(T t, String field) {
        try {
            field = field.substring(0, 1).toUpperCase() + field.substring(1);
            String methodName = "get" + field;
            Method method = t.getClass().getMethod(methodName);
            return method.invoke(t);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
