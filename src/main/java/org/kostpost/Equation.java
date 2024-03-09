package org.kostpost;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Equation {

    @Id
    @GeneratedValue
    private Long id;
    private String equation;

    private String rootX;

    private int quantityX;

    public Equation(String equation){
        this.equation = equation;
    }

    public Equation() {
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getRootX() {
        return rootX;
    }

    public void setRootX(String rootX) {
        this.rootX = rootX;
    }

    public int getQuantityX() {
        return quantityX;
    }

    public void setQuantityX(int quantityX) {
        this.quantityX = quantityX;
    }
}
