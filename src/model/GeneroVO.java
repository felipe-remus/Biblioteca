/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WILLIAN
 */
public class GeneroVO {
    private int idgenero;
    private String nomegenero;

    public int getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(int idgenero) {
        this.idgenero = idgenero;
    }

    public String getNomegenero() {
        return nomegenero;
    }

    public void setNomegenero(String nomegenero) {
        this.nomegenero = nomegenero;
    }
    
    @Override
    public String toString() {
        return nomegenero;
    }
}
