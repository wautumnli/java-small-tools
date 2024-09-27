package com.ql.tools.check;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Autumn Li
 * @date 2024/09/27
 */

public class Sample {

    public static void main(String[] args) {
        User user = User.create(1, null, new Date(),  new ArrayList<>());
        String checkFields = "age,name,date,strList";
        CheckUtils.check(user, checkFields);
    }
}
