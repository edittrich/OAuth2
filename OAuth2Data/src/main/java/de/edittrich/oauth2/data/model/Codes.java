package de.edittrich.oauth2.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Codes {
	
    @Id
    @GeneratedValue
    private Long id;
    
    public Long getId() {
        return id;
    }
    
    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }    
    
    public String getConfirmationCode() {
        return confirmationCode;
    }
    
    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
    
    private String code;
    @Column(length = 2048)
    private String state;
    private String confirmationCode;

    public Codes(String code) {
        this.code = code;    	
    }

    Codes() {
    }

}