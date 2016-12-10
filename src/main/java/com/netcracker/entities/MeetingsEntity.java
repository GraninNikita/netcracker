package com.netcracker.entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.*;

import java.sql.Time;
import java.util.Date;


/**
 * Created by Nick on 27.11.2016.
 */
@Entity
@Table(name = "MEETINGS", schema = "NETCRACKER", catalog = "")
public class MeetingsEntity {
    private long meetingId;
    private String name;
    private DateTime dateStart;
    private DateTime dateEnd;
    private Long adminId;
    private String place;
    private String summary;
    private Boolean state;
    private Integer notificationTime;
    private List<ContactsEntity> contactsEntities = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEETING_GEN")
    @SequenceGenerator(name = "MEETING_GEN", sequenceName = "MEETINGS_SEQ")
    @Column(name = "MEETING_ID")
    public long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(long meetingId) {
        this.meetingId = meetingId;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "DATE_START")
    public DateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(DateTime dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "DATE_END")
    public DateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(DateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Basic
    @Column(name = "ADMIN_ID")
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Basic
    @Column(name = "PLACE")
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "SUMMARY")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
    @Column(name = "NOTIFICATION_TIME")
    public Integer getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Integer notificationTime) {
        this.notificationTime = notificationTime;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "contacts_to_meeting", catalog = "", joinColumns = {
            @JoinColumn(name = "meeting_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "contact_id",
                    nullable = false, updatable = false)})
    public List<ContactsEntity> getContacts() {

        return this.contactsEntities;
    }

    public void setContacts(List<ContactsEntity> contactsEntities) {
        this.contactsEntities = contactsEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingsEntity that = (MeetingsEntity) o;

        if (meetingId != that.meetingId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (dateStart != null ? !dateStart.equals(that.dateStart) : that.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(that.dateEnd) : that.dateEnd != null) return false;
        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (notificationTime != null ? !notificationTime.equals(that.notificationTime) : that.notificationTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (meetingId ^ (meetingId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (adminId != null ? adminId.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (notificationTime != null ? notificationTime.hashCode() : 0);
        return result;
    }
}
