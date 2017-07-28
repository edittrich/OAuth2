package de.edittrich.oauth2.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;

@Entity
public class Customers {
	
    @Id
    @GeneratedValue
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getConfirmationCode() {
        return confirmationCode;
    }
    
    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public DateTime getLastChanged() {
        return lastChanged;
    }
    
    public void setLastChanged(DateTime lastChanged) {
        this.lastChanged = lastChanged;
    }
    
    private String customerId;
    private String confirmationCode;
    @Column(length = 1024)
    private String accessToken;
    @Column(length = 1024)
    private String refreshToken;
    @Column(length = 512)
    private DateTime lastChanged;
    
    public Customers(String customerId) {
        this.customerId = customerId;    	
    }

    Customers() {
    }

}