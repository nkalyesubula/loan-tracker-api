package com.knoah.loantrackerapi.domain;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class ApiMetrics {
    private int totalRequests;
    private int failedValidations;

    private int positiveRequests;
    private int negativeRequests;

    public int getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }

    public int getFailedValidations() {
        return failedValidations;
    }

    public void setFailedValidations(int failedValidations) {
        this.failedValidations = failedValidations;
    }

    public int getPositiveRequests() {
        return positiveRequests;
    }

    public void setPositiveRequests(int positiveRequests) {
        this.positiveRequests = positiveRequests;
    }

    public int getNegativeRequests() {
        return negativeRequests;
    }

    public void setNegativeRequests(int negativeRequests) {
        this.negativeRequests = negativeRequests;
    }
    public void incrementTotalRequests() {
        this.totalRequests = getTotalRequests() +1 ;
    }

    public void incrementFailedValidations() {
        this.failedValidations= getFailedValidations() +1 ;
    }

    public void incrementPositiveRequests() {
        this.positiveRequests = getPositiveRequests() +1 ;
    }

    public void incrementNegativeRequests() {
        this.negativeRequests = getNegativeRequests() +1 ;
    }

}
