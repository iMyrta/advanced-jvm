package com.epam.advancedjvm.annotationsprocessing.data;

import com.google.protobuf.InvalidProtocolBufferException;

public class PersonConverter {

    public byte[] toBytes(Person person) {
        PersonProtos.Person.Builder builder = PersonProtos.Person.newBuilder();
        if (person.getFirstName() != null) {
            builder.setFirstName(person.getFirstName());
        }
        if (person.getLastName() != null) {
            builder.setLastName(person.getLastName());
        }
        builder.setAge(person.getAge());
        return builder.build().toByteArray();
    }

    public Person fromBytes(byte[] bytes) {
        try {
            PersonProtos.Person proto = PersonProtos.Person.parseFrom(bytes);

            Person person = new Person();
            person.setFirstName(proto.getFirstName());
            person.setLastName(proto.getLastName());
            person.setAge(proto.getAge());

            return person;

        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
