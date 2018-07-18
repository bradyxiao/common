package com.tencent.qcloud.javatrain;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void collect_test() throws Exception{
        Collection<Integer> collection = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        System.out.println(collection.size());
        for(Integer integer : collection){
            System.out.println(integer);
        }
        multiParameters(3,4,6);
        iterator(collection.iterator());

        Template<String> template = new Template<>();
        template.setA("template<T>");
        System.out.println(template.getA());
        template.setB(Integer.valueOf(34));

        TemplateB<String, Integer> templateB = new TemplateB<>();
        templateB.setA("Template<T, E>");
        System.out.println(templateB.getA());
        templateB.setB(56);
        System.out.println(templateB.getB());

    }

    public<T> void multiParameters(T... a){
        System.out.println(a.getClass().isArray());
        for(T e : a){
            System.out.println(e);
        }
    }

    public<E> void iterator(Iterator<E> iterator){
        if(iterator != null){
            while (iterator.hasNext()){
                System.out.println(iterator.next());
                iterator.remove();
            }
        }
    }

    @Test
    public void class_test() throws Exception{
        setClass(Template.class);
        setClass2(TemplateB.class);
    }

    public void setClass(Class<?> clc) throws IllegalAccessException, InstantiationException {
        System.out.println(clc.newInstance().toString());
        System.out.println(clc.isArray());
    }

    public<T> void setClass2(Class<T> clc) throws IllegalAccessException, InstantiationException {
        T object = clc.newInstance();
        System.out.println(object.toString());
    }

    class Template<T>{
        T a;
        public T getA(){
            return a;
        }

        public void setA(T a){
            this.a = a;
        }

        public<E> void setB(E b){
            System.out.println(b);
        }
    }

    class TemplateB<T, E>{
        T a;
        E b;
        public T getA(){
            return a;
        }

        public void setA(T a){
            this.a = a;
        }

        public void setB(E b){
            this.b = b;
        }

        public E getB() {
            return b;
        }
    }
    @MetaData(id = 1, name = "yao")
    public void testMetadata(){

    }

    @Target(value={ElementType.METHOD, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface MetaData{
        int id() default  0;
        String name() default "xiao";
    }
}