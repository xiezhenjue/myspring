package test;

import com.hd.framework.context.HadiApplicationContext;

/**
 * @creator 18051027
 * @create Date 2019/6/25
 * @description
 */
public class Test {

    public static void main(String[] args) {
        HadiApplicationContext context = new HadiApplicationContext("application.properties");
        System.out.println(context);
    }
}
