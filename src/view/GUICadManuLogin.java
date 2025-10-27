/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.SwingUtilities;
import services.LoginServicos;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import services.ServicosFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import model.LoginVO;
import util.SessaoUsuario;

/**
 *
 * @author Felipe Remus
 */
public class GUICadManuLogin extends javax.swing.JInternalFrame {
    private Vector<Integer> codperfil = new Vector<Integer>();
    private GUIMenuPrincipal menuPrincipal;
    
    // Modos de operação
    public static final int MODO_CADASTRO_FUNCIONARIO = 1;
    public static final int MODO_ALTERAR_PROPRIOS_DADOS = 2;
    public static final int MODO_CADASTRO_GENERICO = 3;
    
    private int modoOperacao;
    private String loginInicial = null;
    private boolean abertoPorAdmin = false;
    private int idLoginEmEdicao = -1;
    
    DefaultTableModel dtm = new DefaultTableModel(
            new Object [][]{},
            new Object[]{"Login"}
    );

    /**
     * Creates new form GUICadLogin
     */
    public GUICadManuLogin() {
        initComponents();
        
        restaurarPerfilComboBox();
    }
    
    // Construtor para cadastro de novo funcionário (da GUIFuncionario)
    public GUICadManuLogin(GUIMenuPrincipal menu, String emailNovoFuncionario, boolean isAdmin) {
        this();
        this.menuPrincipal = menu;
        this.loginInicial = emailNovoFuncionario;
        this.abertoPorAdmin = isAdmin;
        this.modoOperacao = MODO_CADASTRO_FUNCIONARIO;
        configurarInterface();
    }
    
    // Construtor para alterar próprios dados (do menu principal)
    public GUICadManuLogin(GUIMenuPrincipal menu, LoginVO usuarioLogado) {
        this();
        this.menuPrincipal = menu;
        this.loginInicial = usuarioLogado.getLogin();
        this.idLoginEmEdicao = usuarioLogado.getIdLogin();
        this.abertoPorAdmin = SessaoUsuario.isAdministrador();
        this.modoOperacao = MODO_ALTERAR_PROPRIOS_DADOS;
        configurarInterface();
    }
    
    // Construtor para cadastro genérico (só admin pelo menu)
    public GUICadManuLogin(GUIMenuPrincipal menu) {
        this();
        this.menuPrincipal = menu;
        this.abertoPorAdmin = SessaoUsuario.isAdministrador();
        this.modoOperacao = MODO_CADASTRO_GENERICO;
        configurarInterface();
    }
    
    private void configurarInterface() {
        restaurarPerfilComboBox();
        
        switch (modoOperacao) {
            case MODO_CADASTRO_FUNCIONARIO:
                configurarModoCadastroFuncionario();
                break;
            case MODO_ALTERAR_PROPRIOS_DADOS:
                configurarModoAlterarPropriosDados();
                break;
            case MODO_CADASTRO_GENERICO:
                configurarModoCadastroGenerico();
                break;
        }
    }
    
    private void configurarModoCadastroFuncionario() {
        setTitle("Cadastro de Login - Novo Funcionário");
        jbtnCadastrar.setText("Cadastrar Login");
        
        if (loginInicial != null && !loginInicial.trim().isEmpty()) {
            jtfLogin.setText(loginInicial.trim());
            jtfLogin.setEditable(false);
        }
        
        selecionarPerfilPorId(2); 
        jcbPerfil.setEnabled(abertoPorAdmin);
        
        jpfSenhaAtual.setVisible(false);
        jLabelSenhaAtual.setVisible(false);
    }
    
    private void configurarModoAlterarPropriosDados() {
        setTitle("Meus Dados");
        jbtnCadastrar.setText("Salvar Alterações");
        
        if (loginInicial != null && !loginInicial.trim().isEmpty()) {
            jtfLogin.setText(loginInicial.trim());
            jtfLogin.setEditable(true);
        }
        
        LoginVO usuario = SessaoUsuario.getUsuarioLogado();
        if (usuario != null) {
            selecionarPerfilPorId(usuario.getPerfil());
        }
        
        jcbPerfil.setEnabled(false); 
        
        jpfSenhaAtual.setVisible(true);
        jLabelSenhaAtual.setVisible(true);
    }
    
    private void configurarModoCadastroGenerico() {
        setTitle("Cadastro de Login");
        jbtnCadastrar.setText("Cadastrar");
        
        jtfLogin.setText("");
        jtfLogin.setEditable(true);
        
        if (jcbPerfil.getItemCount() > 0) {
            jcbPerfil.setSelectedIndex(0);
        }
        
        jcbPerfil.setEnabled(abertoPorAdmin);
        
        jpfSenhaAtual.setVisible(false);
        jLabelSenhaAtual.setVisible(false);
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jbtnCadastrar = new javax.swing.JButton();
        jbtnLimpar4 = new javax.swing.JButton();
        jbtnAlterar4 = new javax.swing.JButton();
        jbtnDeletar4 = new javax.swing.JButton();
        jbtnPreencher4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtfLogin = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jcbPerfil = new javax.swing.JComboBox<>();
        jLabelSenhaAtual = new javax.swing.JLabel();
        jpfSenhaAtual = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        jpfConfirmacao = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jtfPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtLogin = new javax.swing.JTable();

        jLabel12.setText("jLabel12");

        jLabel13.setText("jLabel13");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jbtnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCadastrar.setText("Cadastrar");
        jbtnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCadastrarActionPerformed(evt);
            }
        });
        jbtnCadastrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnCadastrarKeyPressed(evt);
            }
        });

        jbtnLimpar4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnLimpar4.setText("Limpar");
        jbtnLimpar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLimpar4ActionPerformed(evt);
            }
        });
        jbtnLimpar4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtnLimpar4KeyPressed(evt);
            }
        });

        jbtnAlterar4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnAlterar4.setText("Alterar");
        jbtnAlterar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAlterar4ActionPerformed(evt);
            }
        });

        jbtnDeletar4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDeletar4.setText("Deletar");
        jbtnDeletar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeletar4ActionPerformed(evt);
            }
        });

        jbtnPreencher4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnPreencher4.setText("Preencher");
        jbtnPreencher4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPreencher4ActionPerformed(evt);
            }
        });

        jLayeredPane5.setLayer(jbtnCadastrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnLimpar4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnAlterar4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnDeletar4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnPreencher4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane5Layout = new javax.swing.GroupLayout(jLayeredPane5);
        jLayeredPane5.setLayout(jLayeredPane5Layout);
        jLayeredPane5Layout.setHorizontalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane5Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addComponent(jbtnCadastrar)
                .addGap(18, 18, 18)
                .addComponent(jbtnLimpar4)
                .addGap(18, 18, 18)
                .addComponent(jbtnPreencher4)
                .addGap(18, 18, 18)
                .addComponent(jbtnAlterar4)
                .addGap(18, 18, 18)
                .addComponent(jbtnDeletar4)
                .addGap(36, 36, 36))
        );
        jLayeredPane5Layout.setVerticalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCadastrar)
                    .addComponent(jbtnLimpar4)
                    .addComponent(jbtnAlterar4)
                    .addComponent(jbtnDeletar4)
                    .addComponent(jbtnPreencher4))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Login");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Login:");

        jtfLogin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Senha:");

        jpfSenha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jcbPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabelSenhaAtual.setText("Senha Atual:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Confirmar Senha:");

        jpfConfirmacao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelSenhaAtual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                        .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jpfSenha)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpfConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelSenhaAtual)
                            .addComponent(jpfSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jpfConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cadastro", jPanel1);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Login:");

        jtfPesquisar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisarKeyReleased(evt);
            }
        });

        jtLogin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Login", "Perfil"
            }
        ));
        jScrollPane1.setViewportView(jtLogin);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Manutenção", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane5)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane1)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(jTabbedPane1)
                    .addGap(83, 83, 83)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selecionarPerfilPorId(int idPerfil) {
        for (int i = 0; i < jcbPerfil.getItemCount(); i++) {
            if (i < codperfil.size() && codperfil.get(i) == idPerfil) {
                jcbPerfil.setSelectedIndex(i);
                return;
            }
        }
        if (jcbPerfil.getItemCount() > 0) {
            jcbPerfil.setSelectedIndex(0);
        }
    }
    
    private void restaurarPerfilComboBox() {
        try {
            LoginServicos ls = services.ServicosFactory.getLoginServicos();
            List<Map<String, Object>> perfis = ls.listaPerfil();

            jcbPerfil.removeAllItems();
            codperfil.clear();

            for (Map<String, Object> perfil : perfis) {
                codperfil.addElement((Integer) perfil.get("id"));
                jcbPerfil.addItem((String) perfil.get("nome"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar perfis: " + e.getMessage());
        }
    }
    
     private void salvarLogin() {
        try {
            switch (modoOperacao) {
                case MODO_CADASTRO_FUNCIONARIO:
                case MODO_CADASTRO_GENERICO:
                    cadastrarNovoLogin();
                    break;
                case MODO_ALTERAR_PROPRIOS_DADOS:
                    alterarMeusDados();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }
     
    private void cadastrarNovoLogin() throws Exception {
        String login = jtfLogin.getText();
        String senha = jpfSenha.getText().trim();
        String confirmacao = jpfConfirmacao.getText().trim();
        
        if (login.isEmpty() || senha.isEmpty() || confirmacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }
        
        if (!senha.equals(confirmacao)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            return;
        }
        
        int idPerfil;
        if (modoOperacao == MODO_CADASTRO_FUNCIONARIO && !abertoPorAdmin) {
            idPerfil = 2; // Força Funcionário
        } else {
            int selectedIndex = jcbPerfil.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um perfil!");
                return;
            }
            idPerfil = codperfil.get(selectedIndex);
        }
        
        LoginServicos ls = ServicosFactory.getLoginServicos();
        ls.cadastrarLogin(login, senha, idPerfil);
        
        JOptionPane.showMessageDialog(this, "Login criado com sucesso!");
        dispose();
    }
     
    private void alterarMeusDados() throws Exception {
        if (idLoginEmEdicao == -1) {
            JOptionPane.showMessageDialog(this, "Erro: ID de login inválido!");
            return;
        }
        
        String senhaAtual = jpfSenhaAtual.getText();
        if (senhaAtual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe sua senha atual!");
            return;
        }
        
        LoginServicos ls = ServicosFactory.getLoginServicos();
        if (!ls.validarSenha(idLoginEmEdicao, senhaAtual)) {
            JOptionPane.showMessageDialog(this, "Senha atual incorreta!");
            return;
        }
        
        String novoLogin = jtfLogin.getText().trim();
        String novaSenha = jpfSenha.getText().trim();
        String confirmacao = jpfConfirmacao.getText(); 
        
        if (novoLogin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O login não pode estar vazio!");
            return;
        }
        
        if (!novaSenha.isEmpty() || !confirmacao.isEmpty()) {
            if (novaSenha.isEmpty() || confirmacao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha ambos os campos de nova senha!");
                return;
            }
            if (!novaSenha.equals(confirmacao)) {
                JOptionPane.showMessageDialog(this, "As novas senhas não coincidem!");
                return;
            }
        } else {
            novaSenha = senhaAtual;
        }
        
        // Manter perfil atual
        LoginVO usuario = SessaoUsuario.getUsuarioLogado();
        int perfilAtual = usuario.getPerfil();
        
        ls.atualizarLogin(idLoginEmEdicao, novoLogin, novaSenha, perfilAtual);
        
        // Atualizar sessão
        usuario.setLogin(novoLogin);
        SessaoUsuario.setUsuarioLogado(usuario);
        
        JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
        limpar();
        dispose();
    }
     
    private void limpar(){
        jtfLogin.setText(null);
        jpfSenha.setText(null);
        jpfConfirmacao.setText(null);
        jpfSenhaAtual.setText(null);
        dtm.setNumRows(0);
    }
    
    private void jbtnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCadastrarActionPerformed
        salvarLogin();
    }//GEN-LAST:event_jbtnCadastrarActionPerformed

    private void jbtnCadastrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnCadastrarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            salvarLogin();
        }
    }//GEN-LAST:event_jbtnCadastrarKeyPressed

    private void jbtnLimpar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLimpar4ActionPerformed
        limpar();
    }//GEN-LAST:event_jbtnLimpar4ActionPerformed

    private void jbtnLimpar4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnLimpar4KeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            limpar();
        }
    }//GEN-LAST:event_jbtnLimpar4KeyPressed

    private void jbtnAlterar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAlterar4ActionPerformed
        //confirmarAlterarFuncionario();
    }//GEN-LAST:event_jbtnAlterar4ActionPerformed

    private void jbtnDeletar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeletar4ActionPerformed
        //deletarFuncionario();
    }//GEN-LAST:event_jbtnDeletar4ActionPerformed

    private void jbtnPreencher4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPreencher4ActionPerformed
        //preencherAlterarFuncionario();
    }//GEN-LAST:event_jbtnPreencher4ActionPerformed

    private void jtfPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisarKeyReleased
        limpar();
        //filtrar();
    }//GEN-LAST:event_jtfPesquisarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelSenhaAtual;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnAlterar4;
    private javax.swing.JButton jbtnCadastrar;
    private javax.swing.JButton jbtnDeletar4;
    private javax.swing.JButton jbtnLimpar4;
    private javax.swing.JButton jbtnPreencher4;
    private javax.swing.JComboBox<String> jcbPerfil;
    private javax.swing.JPasswordField jpfConfirmacao;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JPasswordField jpfSenhaAtual;
    private javax.swing.JTable jtLogin;
    private javax.swing.JTextField jtfLogin;
    private javax.swing.JTextField jtfPesquisar;
    // End of variables declaration//GEN-END:variables
}
