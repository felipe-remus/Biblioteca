/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WILLIAN
 */
public class AutorVO {
    private int idautor;
    private String nomeautor, sobrenomeautor;

    public int getIdautor() {
        return idautor;
    }

    public void setIdautor(int idautor) {
        this.idautor = idautor;
    }

    public String getNomeautor() {
        return nomeautor;
    }

    public void setNomeautor(String nomeautor) {
        this.nomeautor = nomeautor;
    }

    public String getSobrenomeautor() {
        return sobrenomeautor;
    }

    public void setSobrenomeautor(String sobrenomeautor) {
        this.sobrenomeautor = sobrenomeautor;
    }
    
    @Override
    public String toString() {
        return nomeautor + " " + sobrenomeautor;
    }
}
