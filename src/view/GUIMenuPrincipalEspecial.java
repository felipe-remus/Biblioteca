/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Felipe Remus
 */

public class GUIMenuPrincipalEspecial extends GUIMenuPrincipal {
    private GUILogin guiLoginOrigem;
    private boolean primeiraExecucao;
    
    public GUIMenuPrincipalEspecial(GUILogin guiLogin, boolean primeiraExecucao) {
        this.guiLoginOrigem = guiLogin;
        this.primeiraExecucao = primeiraExecucao;
        configurarModoEspecial();
    }
    
    private void configurarModoEspecial() {
        // Remove todos os menus
        jmbMenu.removeAll();
        
        // Define título apropriado
        setTitle(primeiraExecucao ? 
            "Primeiro Acesso - Configuração do Sistema" : 
            "Cadastro de Cliente");
        
        // Centraliza e maximiza o desktop
        jdpAreaDeTrabalho.setBounds(0, 0, getWidth(), getHeight());
        
        // Abre automaticamente a tela de cadastro
        abrirTelaCadastroEspecial();
    }
    
    private void abrirTelaCadastroEspecial() {
        try {
            // Cria GUICadManuLogin no modo especial
            GUICadManuLogin cadastro = new GUICadManuLogin(this, primeiraExecucao ? 3 : 1, true);
            jdpAreaDeTrabalho.add(cadastro);
            
            // Maximiza a janela interna
            cadastro.setMaximum(true);
            cadastro.setVisible(true);
            
            // Fecha automaticamente quando o cadastro for concluído
            cadastro.addInternalFrameListener(new InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    voltarParaLogin();
                }
            });
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir cadastro: " + e.getMessage());
            voltarParaLogin();
        }
    }
    
    public void voltarParaLogin() {
        if (guiLoginOrigem != null) {
            guiLoginOrigem.setVisible(true);
            guiLoginOrigem.toFront();
        }
        this.dispose();
    }
}