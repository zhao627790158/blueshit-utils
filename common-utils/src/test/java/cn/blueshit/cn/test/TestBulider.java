package cn.blueshit.cn.test;

import lombok.Builder;
import lombok.Data;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

/**
 * Created by zhaoheng on 16/9/28.
 */
public class TestBulider {


    private String name;
    private int age;
    private String school;

    public TestBulider(Bulider bulider) {
        this.name = bulider.name;
        this.age = bulider.age;
        this.school = bulider.school;
    }

    public static class Bulider {
        private final String name;

        private final int age;

        private String school;

        public Bulider(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Bulider school(String val) {
            school = val;
            return this;
        }

        public TestBulider bulider() {
            System.out.println("init bulider");
            return new TestBulider(this);
        }

    }

    @Override
    public String toString() {
        return "TestBulider{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school='" + school + '\'' +
                '}';
    }
}

class test {
    public static void main(String[] args) {
        TestBulider bulider = new TestBulider.Bulider("name", 18).school("mySchool").bulider();
        System.out.println(bulider);

    }
}
