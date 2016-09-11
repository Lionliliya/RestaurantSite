package com.gmail.liliyayalovchenko.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.gmail.liliyayalovchenko.jsonViews.Views;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMPLOYEE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIgnoreProperties(ignoreUnknown = false)
@Proxy(lazy = false)

@DiscriminatorValue("Employee")
@DiscriminatorOptions(force = true, insert = true)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "dtype")
public class Employee {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    @JsonView(Views.Internal.class)
    private int id;

    @Column(name = "second_name")
    @JsonView(Views.Public.class)
    private String secondName;

    @Column(name = "first_name")
    @JsonView(Views.Public.class)
    private String firstName;

    @Column(name = "empl_date")
    @JsonView(Views.Internal.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "Europe/Kiev")
    private Date emplDate;

    @Column(name = "phone")
    @JsonView(Views.Internal.class)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    @JsonView(Views.Internal.class)
    private Position position;

    @Column(name = "salary")
    @JsonView(Views.Internal.class)
    private int salary;

    @Column(name = "photo")
    @JsonView(Views.Internal.class)
    private String photoLink;

    public Employee(int id, String secondName, String firstName, Date emplDate, String phone, Position position, int salary, String photoLink) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.emplDate = emplDate;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.photoLink = photoLink;
    }

    public Employee(int id, String secondName, String firstName, Date emplDate, String phone, Position position, int salary) {
        this.id = id;
        this.secondName = secondName;
        this.firstName = firstName;
        this.emplDate = emplDate;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
    }

    public Employee(String secondName, String firstName, Date emplDate, String phone, Position position, int salary) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.emplDate = emplDate;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
    }

    public Employee(String secondName, String firstName, Date emplDate, String phone, Position position, int salary, String photoLink) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.emplDate = emplDate;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.photoLink = photoLink;
    }

    public Employee(String secondName, String firstName, String phone, Position position, int salary, String photoLink) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.phone = phone;
        this.position = position;
        this.salary = salary;
        this.photoLink = photoLink;
    }

    public Employee() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmplDate(Date emplDate) {
        this.emplDate = emplDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public Date getEmplDate() {
        return emplDate;
    }

    public String getPhone() {
        return phone;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", secondName='" + secondName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", emplDate=" + emplDate +
                ", phone='" + phone + '\'' +
                ", position=" + position +
                ", salary=" + salary +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (salary != employee.salary) return false;
        if (emplDate != null ? !emplDate.equals(employee.emplDate) : employee.emplDate != null) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (phone != null ? !phone.equals(employee.phone) : employee.phone != null) return false;
        if (position != employee.position) return false;
        if (secondName != null ? !secondName.equals(employee.secondName) : employee.secondName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = secondName != null ? secondName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (emplDate != null ? emplDate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + salary;
        return result;
    }
}
