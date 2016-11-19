package com.netcracker.entities;

import javax.persistence.*;

/**
 * Created by Nick on 14.11.2016.
 */
@Entity
@Table(name = "USERS", schema = "NETCRACKER", catalog = "")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long userId;
    private String firstName;
    private String lastName;
    private String ifno;
    private long parentUserId;

    @Id
    @Column(name = "USER_ID")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "IFNO")
    public String getIfno() {
        return ifno;
    }

    public void setIfno(String ifno) {
        this.ifno = ifno;
    }

    @Basic
    @Column(name = "PARENT_USER_ID")
    public long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(long parentUserId) {
        this.parentUserId = parentUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (userId != that.userId) return false;
        if (parentUserId != that.parentUserId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (ifno != null ? !ifno.equals(that.ifno) : that.ifno != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (ifno != null ? ifno.hashCode() : 0);
        result = 31 * result + (int) (parentUserId ^ (parentUserId >>> 32));
        return result;
    }
}
