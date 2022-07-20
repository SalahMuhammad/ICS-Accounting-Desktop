/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objectes;

/**
 *
 * @author AL MASRIA 4 COMP
 */
public class Transactions {
    
    private int code;
    private java.sql.Date date;
    private int credit;
    private int debt;
    private String description;

    public Transactions(int code, java.sql.Date date, int credit, int debt, String description) {
        this.code = code;
        this.date = date;
        this.credit = credit;
        this.debt = debt;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
