package org.demis27.kickoff.mn.common;

import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Comicbook {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Comicbook comicbook = (Comicbook) o;
        return Objects.equals(id, comicbook.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }

    @Override public String toString() {
        return "Comicbook{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
