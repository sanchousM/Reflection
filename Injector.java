package edu.lab5;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.lang.reflect.*;


public class Injector {

    private Properties propertie;

    public Injector() {
        this.propertie=new Properties();
        File file = new File("C:\\Users\\Aleksandra\\IdeaProjects\\Reflection\\src\\edu\\lab5\\Data.properties");
        try {
            propertie.load(new FileReader(file));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public <T> T inject(T object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        inject_object(object);
        return object;
    }

    private <T> void inject_object(T object) {
        try {
            Class<?> clazz = object.getClass();
            for (Field field: clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    Object createdObject = produce(field.getType());
                    if (createdObject != null) {
                        field.setAccessible(true);
                        field.set(object, createdObject);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object produce(Class<?> type) {
        try {
            String className = propertie.getProperty(type.getName());
            Class<?> creatingClass = Class.forName(className);
            Constructor constructor = creatingClass.getDeclaredConstructor();
            return constructor.newInstance();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}