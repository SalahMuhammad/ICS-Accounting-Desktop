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
public class Customers {
    
    private int id;
    private String name;
    private int inDebt;

    public Customers(int id, String name, int inDebt) {
        this.id = id;
        this.name = name;
        this.inDebt = inDebt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInDebt() {
        return inDebt;
    }

    public void setInDebt(int inDebt) {
        this.inDebt = inDebt;
    }
}
