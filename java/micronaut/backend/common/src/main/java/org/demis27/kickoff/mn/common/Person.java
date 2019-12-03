package org.demis27.kickoff.mn.common;

import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Person {

    private String id;

    private String firstname;

    private String lastname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
