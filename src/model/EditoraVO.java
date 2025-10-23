/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WILLIAN
 */
public class EditoraVO {
    private int ideditora;
    private String nomeeditora;

    public int getIdeditora() {
        return ideditora;
    }

    public void setIdeditora(int ideditora) {
        this.ideditora = ideditora;
    }

    public String getNomeeditora() {
        return nomeeditora;
    }

    public void setNomeeditora(String nomeeditora) {
        this.nomeeditora = nomeeditora;
    }
    
    @Override
    public String toString() {
        return nomeeditora; 
    }
}
