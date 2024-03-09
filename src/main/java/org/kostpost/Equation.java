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

    private double rootX;

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
}
