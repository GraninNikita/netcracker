package com.netcracker.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick on 24.11.2016.
 */
@Entity
@Table(name = "CONTACTS", schema = "NETCRACKER", catalog = "")
public class ContactsEntity {
    private long contactId;
    private String type;
    private Boolean state;
    private String value;
    private List<MeetingsEntity> meetingsEntities = new ArrayList<>();
    private UsersEntity usersEntities;

    @Id
    @Column(name = "CONTACT_ID")
    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Basic
    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "STATE")
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Basic
    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "contacts_to_meeting", catalog = "", joinColumns = {
            @JoinColumn(name = "contact_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "meeting_id",
                    nullable = false, updatable = false)})
    public List<MeetingsEntity> getMeetings() {
        return this.meetingsEntities;
    }

    public void setMeetings(List<MeetingsEntity> meetingsEntities) {
        this.meetingsEntities = meetingsEntities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactsEntity that = (ContactsEntity) o;

        if (contactId != that.contactId) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (contactId ^ (contactId >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }


    @ManyToOne(optional = false)
    public UsersEntity getUsersEntities() {
        return usersEntities;
    }

    public void setUsersEntities(UsersEntity usersEntities) {
        this.usersEntities = usersEntities;
    }
}
