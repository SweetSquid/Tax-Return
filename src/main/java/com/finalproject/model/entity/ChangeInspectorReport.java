package com.finalproject.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class ChangeInspectorReport {
    private int id;
    private int userId;
    private int previousInspectorId;
    private int newInspectorId;
    private String message;
    private Status status;
    private LocalDateTime date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPreviousInspectorId() {
        return previousInspectorId;
    }

    public void setPreviousInspectorId(int previousInspectorId) {
        this.previousInspectorId = previousInspectorId;
    }

    public int getNewInspectorId() {
        return newInspectorId;
    }

    public void setNewInspectorId(int newInspectorId) {
        this.newInspectorId = newInspectorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChangeInspectorReport{" +
                "id=" + id +
                ", userId=" + userId +
                ", previousInspectorId=" + previousInspectorId +
                ", newInspectorId=" + newInspectorId +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeInspectorReport that = (ChangeInspectorReport) o;
        return id == that.id &&
                userId == that.userId &&
                previousInspectorId == that.previousInspectorId &&
                newInspectorId == that.newInspectorId &&
                Objects.equals(message, that.message) &&
                status == that.status &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, previousInspectorId, newInspectorId, message, status, date);
    }

    public enum Status {
        APPROVED,
        CHANGE
    }
}
