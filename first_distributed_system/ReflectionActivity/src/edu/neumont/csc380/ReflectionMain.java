package edu.neumont.csc380;

import java.lang.String;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionMain {
    public static void main(String[] args) {

        try {
            Class reflectActivity = Class.forName("edu.neumont.csc380.ReflectionActivity");

            System.out.println("CONSTRUCTORS");

            for (Constructor constructor : reflectActivity.getConstructors()) {
                System.out.println(constructor);
            }

            Object objFirst = reflectActivity.getConstructor(java.lang.String.class, double.class).newInstance("Hello", 4.2);

            ReflectionActivity activity = (ReflectionActivity) objFirst;

            System.out.println("FIELDS");

            for (Field field : reflectActivity.getDeclaredFields()) {
                System.out.println(field.getType() + ": " + field.getName());
            }

            reflectActivity.getMethod("AddToNumber", double.class).invoke(2.3);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
