/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Felipe Remus
 */
public class DevolucaoVO {
    private int id_devolucao;
    private int id_emprestimo;
    private Date dt_devolucao;
    private double multa;
    private String nome_cliente, nome_livro;

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getNome_livro() {
        return nome_livro;
    }

    public void setNome_livro(String nome_livro) {
        this.nome_livro = nome_livro;
    }

    public int getId_devolucao() {
        return id_devolucao;
    }

    public void setId_devolucao(int id_devolucao) {
        this.id_devolucao = id_devolucao;
    }

    public int getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(int id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public Date getDt_devolucao() {
        return dt_devolucao;
    }

    public void setDt_devolucao(Date dt_devolucao) {
        this.dt_devolucao = dt_devolucao;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }
    
}
