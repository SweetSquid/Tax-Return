package com.finalproject.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class ActionReport {

    private int report_id;
    private Action action;
    private String message;
    private LocalDateTime date;
    private int taxReturnId;

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionReport that = (ActionReport) o;
        return report_id == that.report_id &&
                taxReturnId == that.taxReturnId &&
                action == that.action &&
                Objects.equals(message, that.message) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(report_id, action, message, date, taxReturnId);
    }

    @Override
    public String toString() {
        return "ActionReport{" +
                "report_id=" + report_id +
                ", action=" + action +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", taxReturnId=" + taxReturnId +
                '}';
    }

    public int getTaxReturnId() {
        return taxReturnId;
    }

    public void setTaxReturnId(int taxReturnId) {
        this.taxReturnId = taxReturnId;
    }

    public enum Action {
        APPROVED, EDIT
    }
}
