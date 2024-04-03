package org.sopt;

import org.sopt.classes.Person;
import org.sopt.classes.PersonBuilder;

public class Main {
    public static void main(String[] args) {
        Person.run();
        //Person 클래스의 객체 생성
        Person person = new Person("나세빈", 24, "female");
        //Person 클래스 내부 메소드 호출
        person.walk();

        System.out.println(person.getName());

        person.setName("34기YB");

        System.out.println(person.getName()); //결과 : 서팟장

        Person personWithBuilder = new
                PersonBuilder()
                .name("나세반")
                .age(24)
                .sex("female")
                .build();

        Person personWithFactoryMethod = Person.create("나세빈", 24, "female");

    }
}