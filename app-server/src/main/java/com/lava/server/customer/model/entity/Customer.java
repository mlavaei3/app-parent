package com.lava.server.customer.model.entity;

import com.lava.utils.autoconfigure.LdbcColumn;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name="customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @LdbcColumn(columnName = "id")
    private String id;

    @LdbcColumn(columnName = "name")
    private String name;
    @LdbcColumn(columnName = "family")
    private String family;
    @LdbcColumn(columnName = "birthDate")
    private LocalDate birthDate;

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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", family='" + family + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}