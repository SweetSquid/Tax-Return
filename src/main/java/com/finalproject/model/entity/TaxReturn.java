package com.finalproject.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class TaxReturn {
    private int id;
    private int userId;
    private int inspectorId;
    private double wage;
    private double militaryCollection;
    private double incomeTax;
    private String category;
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

    public int getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(int inspectorId) {
        this.inspectorId = inspectorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxReturn taxReturn = (TaxReturn) o;
        return id == taxReturn.id &&
                userId == taxReturn.userId &&
                inspectorId == taxReturn.inspectorId &&
                Double.compare(taxReturn.wage, wage) == 0 &&
                Double.compare(taxReturn.militaryCollection, militaryCollection) == 0 &&
                Double.compare(taxReturn.incomeTax, incomeTax) == 0 &&
                category.equals(taxReturn.category) &&
                Objects.equals(date, taxReturn.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, inspectorId, wage, militaryCollection, incomeTax, category, date);
    }

    @Override
    public String toString() {
        return "TaxReturn{" +
                "id=" + id +
                ", userId=" + userId +
                ", inspectorId=" + inspectorId +
                ", wage=" + wage +
                ", militaryCollection=" + militaryCollection +
                ", incomeTax=" + incomeTax +
                ", category=" + category +
                ", date=" + date +
                '}';
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }


    public double getMilitaryCollection() {
        return militaryCollection;
    }

    public void setMilitaryCollection(double militaryCollection) {
        this.militaryCollection = militaryCollection;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public enum Category {
        LAND_TAX("Land tax (3%)"),
        WATER_TAX("Water tax (1.5%)"),
        TRANSPORT_TAX("Transport tax (1%)"),
        EXCISE_TAX("Excise tax (3%)");

        String instance;

        Category(String instance) {
            this.instance = instance;
        }

        public String getInstance() {
            return instance;
        }

    }

}
