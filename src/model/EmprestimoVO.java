/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class EmprestimoVO {
    private int idEmprestimo;
    private Date dtRetirada;
    private Date dtPrazo;
    private String status; 
    private int idCliente;
    private int idLivro;

    // Para exibição
    private String nomeCliente;
    private String nomeLivro;

    // Getters e Setters
    public int getIdEmprestimo() { return idEmprestimo; }
    public void setIdEmprestimo(int idEmprestimo) { this.idEmprestimo = idEmprestimo; }

    public Date getDtRetirada() { return dtRetirada; }
    public void setDtRetirada(Date dtRetirada) { this.dtRetirada = dtRetirada; }

    public Date getDtPrazo() { return dtPrazo; }
    public void setDtPrazo(Date dtPrazo) { this.dtPrazo = dtPrazo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdLivro() { return idLivro; }
    public void setIdLivro(int idLivro) { this.idLivro = idLivro; }

    // Exibição
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getNomeLivro() { return nomeLivro; }
    public void setNomeLivro(String nomeLivro) { this.nomeLivro = nomeLivro; }
}