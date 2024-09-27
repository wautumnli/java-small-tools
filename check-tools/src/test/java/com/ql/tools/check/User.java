package com.ql.tools.check;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Autumn Li
 * @date 2024/09/27
 */
@Data
public class User {
    private Integer age;
    private String name;
    private Date date;
    private List<String> strList;

    public static User create(Integer age, String name, Date date, List<String> stringList) {
        User user = new User();
        user.setAge(age);
        user.setName(name);
        user.setDate(date);
        user.setStrList(stringList);
        return user;
    }
}
