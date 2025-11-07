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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    
    private List<Integer> idsLogins = new ArrayList<>(); // para o primeiro painel
    private List<Integer> idsGerenciarLogins = new ArrayList<>();
    
    // Modos de operação
    public static final int MODO_CADASTRO_FUNCIONARIO = 1;
    public static final int MODO_ALTERAR_PROPRIOS_DADOS = 2;
    public static final int MODO_CADASTRO_GENERICO = 3;
    
    private int modoOperacao;
    private String loginInicial = null;
    private boolean abertoPorAdmin = false;
    private int idLoginEmEdicao = -1;
    
    // Nova constante para identificar origem
    public static final int ORIGEM_LOGIN = 1;
    public static final int ORIGEM_MENU = 2;

    private int origemChamada = ORIGEM_MENU; // padrão
    private GUILogin guiLoginOrigem = null;

    // Campos adicionai
    private int perfilFixo = -1;
    private boolean perfilEditavel = true;
    
    DefaultTableModel dtm = new DefaultTableModel(
            new Object [][]{},
            new Object[]{"Login", "Perfil"}
    );

    /**
     * Creates new form GUICadLogin
     */
    public GUICadManuLogin() {
        initComponents();
        
        restaurarPerfilComboBox();
    }
    
    public GUICadManuLogin(GUIMenuPrincipal menu) {
        this();
        this.menuPrincipal = menu;

        if (SessaoUsuario.isUsuarioLogado()) {
            LoginVO usuario = SessaoUsuario.getUsuarioLogado();
            this.loginInicial = usuario.getLogin();
            this.idLoginEmEdicao = usuario.getIdLogin();
            this.abertoPorAdmin = SessaoUsuario.isAdministrador();
            this.modoOperacao = MODO_ALTERAR_PROPRIOS_DADOS;
        }

        configurarInterface();

        // ✅ Só mostra aba de gerenciamento se for admin
        if (!SessaoUsuario.isAdministrador()) {
            jTabbedPane1.remove(1); // Remove a aba "Gerenciar Logins"
        } else {
            preencherTabela();
        }
    }
    
    public GUICadManuLogin(GUIMenuPrincipal menu, String emailNovoFuncionario, boolean isAdmin) {
        this();
        this.menuPrincipal = menu;
        this.loginInicial = emailNovoFuncionario;
        this.abertoPorAdmin = isAdmin;
        this.modoOperacao = MODO_CADASTRO_FUNCIONARIO;
        configurarInterface();
    }
    
    // Construtor para modo especial (primeiro acesso ou cliente)
    public GUICadManuLogin(GUIMenuPrincipalEspecial menuEspecial, int perfilFixo, boolean modoEspecial) {
        this();
        this.menuPrincipal = menuEspecial;
        this.loginInicial = "";
        this.modoOperacao = MODO_CADASTRO_GENERICO;
        this.perfilFixo = perfilFixo;
        this.perfilEditavel = false;

        if (perfilFixo == 3) {
            configurarParaPrimeiroAcesso();
        } else if (perfilFixo == 1) {
            configurarParaCliente();
        }

        // Remove aba de gerenciamento no modo especial
        if (jTabbedPane1.getTabCount() > 1) {
            jTabbedPane1.removeTabAt(1);
        }

        // Configura ação de fechamento para voltar ao login
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
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

        // ✅ Configura filtro automático para o campo de pesquisa
        if (jtfPesquisar != null) {
            jtfPesquisar.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) { filtrar(); }
                public void removeUpdate(DocumentEvent e) { filtrar(); }
                public void insertUpdate(DocumentEvent e) { filtrar(); }
            });
        }
    }
    
    private void configurarModoCadastroFuncionario() {
        jlTtitulo.setText("Cadastro de Login - Novo Funcionário");
        
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
        jbtnCadastrar.setText("Salvar Alterações");
        jbtnCadastrar.setAlignmentX(CENTER_ALIGNMENT);

        if (loginInicial != null && !loginInicial.trim().isEmpty()) {
            jtfLogin.setText(loginInicial.trim());
            // ✅ Funcionário NÃO pode alterar login
            jtfLogin.setEditable(false);
        }

        LoginVO usuario = SessaoUsuario.getUsuarioLogado();
        if (usuario != null) {
            selecionarPerfilPorId(usuario.getPerfil());
        }
        jcbPerfil.setEnabled(false);
        jpfSenhaAtual.setVisible(true);
        jLabelSenhaAtual.setVisible(true);
        jbtnDeletar4.setVisible(false);
        jbtnLimpar4.setVisible(false);
        jbtnPromover.setVisible(false);
        jbtnRebaixar.setVisible(false);
        
        if(SessaoUsuario.isAdministrador()){
            jcbPerfil.setEnabled(false);
            jpfSenhaAtual.setVisible(true);
            jLabelSenhaAtual.setVisible(true);
            jbtnDeletar4.setVisible(true);
            jbtnLimpar4.setVisible(true);
            jbtnPromover.setVisible(true);
            jbtnRebaixar.setVisible(true);
        }
    }
    
    private void configurarModoCadastroGenerico() {
        jtfLogin.setText("");
        jtfLogin.setEditable(true);
        
        if (jcbPerfil.getItemCount() > 0) {
            jcbPerfil.setSelectedIndex(0);
        }
        
        jcbPerfil.setEnabled(abertoPorAdmin);
        
        jpfSenhaAtual.setVisible(false);
        jLabelSenhaAtual.setVisible(false);
    }
    
    private void configurarParaPrimeiroAcesso() {
    setTitle("Primeiro Acesso - Criar Administrador");
    jbtnCadastrar.setText("Criar Administrador");
    jbtnDeletar4.setVisible(false);
    jbtnLimpar4.setVisible(false);
    jbtnPromover.setVisible(false);
    jbtnRebaixar.setVisible(false);
    
    // Força perfil Admin
    selecionarPerfilPorId(3);
    jcbPerfil.setEnabled(false); // Travado como Admin
    
    // Login editável (primeiro usuário define seu login)
    jtfLogin.setEditable(true);
    jtfLogin.setText("");
    
    // Esconde campos não necessários
    jpfSenhaAtual.setVisible(false);
    jLabelSenhaAtual.setVisible(false);
}

    private void configurarParaCliente() {
        setTitle("Cadastro de Cliente");
        jbtnCadastrar.setText("Criar Conta");
        jbtnDeletar4.setVisible(false);
        jbtnLimpar4.setVisible(false);
        jbtnPromover.setVisible(false);
        jbtnRebaixar.setVisible(false);

        // Força perfil Cliente
        selecionarPerfilPorId(1);
        jcbPerfil.setEnabled(false); // Travado como Cliente

        // Login editável
        jtfLogin.setEditable(true);
        jtfLogin.setText("");

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
        jlTtitulo = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jcbPerfil = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jpfConfirmacao = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jpfSenhaAtual = new javax.swing.JPasswordField();
        jLabel14 = new javax.swing.JLabel();
        jLabelSenhaAtual = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jtfLogin = new javax.swing.JTextField();
        jcdMostrarSenha = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jtfPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtGerenciarLogins = new javax.swing.JTable();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jbtnCadastrar = new javax.swing.JButton();
        jbtnLimpar4 = new javax.swing.JButton();
        jbtnRebaixar = new javax.swing.JButton();
        jbtnDeletar4 = new javax.swing.JButton();
        jbtnPromover = new javax.swing.JButton();

        jLabel12.setText("jLabel12");

        jLabel13.setText("jLabel13");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Logins");

        jlTtitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jlTtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTtitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Login.png"))); // NOI18N
        jlTtitulo.setText("Login");
        jlTtitulo.setToolTipText("");

        jPanel3.setBackground(new java.awt.Color(255, 195, 65));

        jcbPerfil.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Confirmar Senha:");

        jpfConfirmacao.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Email: (Login)");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Senha:");

        jLabelSenhaAtual.setText("Senha Atual:");

        jpfSenha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jtfLogin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jcdMostrarSenha.setBackground(new java.awt.Color(255, 255, 255));
        jcdMostrarSenha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jcdMostrarSenha.setText("Mostrar Senha");
        jcdMostrarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcdMostrarSenhaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Perfil:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelSenhaAtual)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpfSenhaAtual)
                        .addGap(227, 227, 227))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jpfConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 128, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jcdMostrarSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSenhaAtual)
                    .addComponent(jpfSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jpfConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcdMostrarSenha)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jcbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62))
        );

        jTabbedPane1.addTab("Cadastro", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 195, 65));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Login:");

        jtfPesquisar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtfPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisarKeyReleased(evt);
            }
        });

        jtGerenciarLogins.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtGerenciarLogins);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Manutenção", jPanel4);

        jbtnCadastrar.setBackground(new java.awt.Color(255, 195, 65));
        jbtnCadastrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Cadastrar.png"))); // NOI18N
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

        jbtnLimpar4.setBackground(new java.awt.Color(255, 195, 65));
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

        jbtnRebaixar.setBackground(new java.awt.Color(255, 195, 65));
        jbtnRebaixar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnRebaixar.setText("Rebaixar Perfil");
        jbtnRebaixar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRebaixarActionPerformed(evt);
            }
        });

        jbtnDeletar4.setBackground(new java.awt.Color(255, 195, 65));
        jbtnDeletar4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDeletar4.setText("Deletar");
        jbtnDeletar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeletar4ActionPerformed(evt);
            }
        });

        jbtnPromover.setBackground(new java.awt.Color(255, 195, 65));
        jbtnPromover.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnPromover.setText("Promover Perfil");
        jbtnPromover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPromoverActionPerformed(evt);
            }
        });

        jLayeredPane5.setLayer(jbtnCadastrar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnLimpar4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnRebaixar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnDeletar4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane5.setLayer(jbtnPromover, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane5Layout = new javax.swing.GroupLayout(jLayeredPane5);
        jLayeredPane5.setLayout(jLayeredPane5Layout);
        jLayeredPane5Layout.setHorizontalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnCadastrar)
                .addGap(18, 18, 18)
                .addComponent(jbtnLimpar4)
                .addGap(18, 18, 18)
                .addComponent(jbtnPromover)
                .addGap(18, 18, 18)
                .addComponent(jbtnRebaixar)
                .addGap(18, 18, 18)
                .addComponent(jbtnDeletar4)
                .addGap(75, 75, 75))
        );
        jLayeredPane5Layout.setVerticalGroup(
            jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jLayeredPane5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCadastrar)
                    .addComponent(jbtnLimpar4)
                    .addComponent(jbtnRebaixar)
                    .addComponent(jbtnDeletar4)
                    .addComponent(jbtnPromover))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTtitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1)
                    .addComponent(jLayeredPane5))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jlTtitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
    
    private int obterPerfilSelecionado() throws Exception {
        int selectedIndex = jcbPerfil.getSelectedIndex();
        if (selectedIndex == -1) {
            throw new Exception("Selecione um perfil!");
        }
        return codperfil.get(selectedIndex);
    }
     
    private boolean validarSenha(String senha) {
        if (senha == null) {
            return false;
        }

        // Verifica tamanho mínimo
        if (senha.length() < 8) {
            JOptionPane.showMessageDialog(this, 
                "A senha deve ter no mínimo 8 caracteres.", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Verifica se tem pelo menos 1 número
        boolean temNumero = false;
        for (char c : senha.toCharArray()) {
            if (Character.isDigit(c)) {
                temNumero = true;
                break;
            }
        }

        if (!temNumero) {
            JOptionPane.showMessageDialog(this, 
                "A senha deve conter número(s).", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    
    private void cadastrarNovoLogin() {
        try {
            // ✅ 1. VALIDAÇÃO PRIMEIRO (antes de qualquer processamento)
            String login = jtfLogin.getText().trim();
            String senha = jpfSenha.getText().trim();
            String confirmacao = jpfConfirmacao.getText().trim();

            if (login.isEmpty() || senha.isEmpty() || confirmacao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return; // ❌ Sai antes de processar
            }

            if (!validarSenha(senha)) {
                return; // ❌ Sai antes de processar
            }

            if (!senha.equals(confirmacao)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
                return; // ❌ Sai antes de processar
            }

            // ✅ 2. PROCESSAMENTO APENAS SE TUDO ESTIVER VÁLIDO
            int idPerfil = (perfilFixo != -1) ? perfilFixo : obterPerfilSelecionado();

            LoginServicos ls = ServicosFactory.getLoginServicos();
            ls.cadastrarLogin(login, senha, idPerfil);

            String mensagem = (perfilFixo == 3) ? 
                "Administrador criado com sucesso!" : 
                "Conta de cliente criada com sucesso!";

            JOptionPane.showMessageDialog(this, mensagem);

            // ✅ 3. LIMPAR APENAS APÓS SUCESSO TOTAL
            limpar();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar login: " + e.getMessage());
            // ❌ NÃO limpa nada aqui - o erro pode ser de rede/banco
        }
    }

    private void alterarMeusDados() throws Exception {
        try {
            // ✅ 1. VALIDAÇÕES INICIAIS
            if (idLoginEmEdicao == -1) {
                JOptionPane.showMessageDialog(this, "Erro: ID de login inválido!");
                return; // ❌ Sai antes de processar
            }

            String senhaAtual = jpfSenhaAtual.getText();
            if (senhaAtual.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe sua senha atual!");
                return; // ❌ Sai antes de processar
            }

            LoginServicos ls = ServicosFactory.getLoginServicos();
            if (!ls.validarSenha(idLoginEmEdicao, senhaAtual)) {
                JOptionPane.showMessageDialog(this, "Senha atual incorreta!");
                return; // ❌ Sai antes de processar
            }

            String novoLogin = jtfLogin.getText().trim();
            String novaSenha = jpfSenha.getText().trim();
            String confirmacao = jpfConfirmacao.getText(); 

            if (novoLogin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O login não pode estar vazio!");
                return; // ❌ Sai antes de processar
            }

            // ✅ 2. VALIDAÇÕES DA NOVA SENHA
            if (!novaSenha.isEmpty() || !confirmacao.isEmpty()) {
                if (novaSenha.isEmpty() || confirmacao.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha ambos os campos de nova senha!");
                    return; // ❌ Sai antes de processar
                }
                if (!novaSenha.equals(confirmacao)) {
                    JOptionPane.showMessageDialog(this, "As novas senhas não coincidem!");
                    return; // ❌ Sai antes de processar
                }

                // ✅ Validação da nova senha - RETORNA IMEDIATAMENTE se inválida
                if (!validarSenha(novaSenha)) {
                    return; // ❌ Sai antes de processar
                }
            } else {
                novaSenha = senhaAtual; // Mantém senha atual se não for alterada
            }

            // ✅ 3. PROCESSAMENTO APENAS SE TUDO FOR VÁLIDO
            LoginVO usuario = SessaoUsuario.getUsuarioLogado();
            int perfilAtual = usuario.getPerfil();

            ls.atualizarLogin(idLoginEmEdicao, novoLogin, novaSenha, perfilAtual);

            // Atualizar sessão
            usuario.setLogin(novoLogin);
            SessaoUsuario.setUsuarioLogado(usuario);

            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");

            // ✅ 4. LIMPAR APENAS APÓS SUCESSO TOTAL
            limpar();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar dados: " + e.getMessage());
            // ❌ NÃO limpa nada aqui - o erro pode ser de rede/banco
        }
    }
    
    private void preencherTabela() {
        try {
            DefaultTableModel dtmGerenciar = (DefaultTableModel) jtGerenciarLogins.getModel();
            dtmGerenciar.setNumRows(0);
            idsGerenciarLogins.clear();

            LoginServicos ls = ServicosFactory.getLoginServicos();
            ArrayList<LoginVO> logins = ls.buscarTodosLogins();

            for (LoginVO l : logins) {
                // ✅ Verifica se há usuário logado antes de comparar
                if (SessaoUsuario.isUsuarioLogado()) {
                    int idUsuarioLogado = SessaoUsuario.getUsuarioLogado().getIdLogin();
                    if (l.getIdLogin() == idUsuarioLogado) {
                        continue; // Não mostra o próprio usuário
                    }
                }

                String nomePerfil = getNomePerfil(l.getPerfil());
                dtmGerenciar.addRow(new Object[]{
                    l.getLogin(),
                    nomePerfil
                });
                idsGerenciarLogins.add(l.getIdLogin());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar logins: " + e.getMessage());
        }
    }

    private String getNomePerfil(int idPerfil) {
        return switch (idPerfil) {
            case 1 -> "Cliente";
            case 2 -> "Funcionario";
            case 3 -> "Admin";
            default -> "Desconhecido";
        };
    }
    
    private void promoverLogin() {
        try {
            int linha = jtGerenciarLogins.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um login para promover!");
                return;
            }

            int idLogin = idsGerenciarLogins.get(linha);
            String loginSelecionado = (String) jtGerenciarLogins.getValueAt(linha, 0);
            String perfilAtual = (String) jtGerenciarLogins.getValueAt(linha, 1);

            // ✅ Só permite promover Funcionário para Admin
            if ("Funcionario".equals(perfilAtual)) {
                String novoPerfil = "Admin";
                int idNovoPerfil = 3;

                int confirma = JOptionPane.showConfirmDialog(
                    this,
                    "Promover '" + loginSelecionado + "' para '" + novoPerfil + "'?",
                    "Confirmar Promoção",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (confirma == JOptionPane.YES_OPTION) {
                    LoginServicos ls = ServicosFactory.getLoginServicos();
                    ls.atualizarPerfil(idLogin, idNovoPerfil);

                    JOptionPane.showMessageDialog(this, "Usuário promovido com sucesso!");
                    preencherTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Só é possível promover Funcionário para Admin.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao promover usuário: " + e.getMessage());
        }
    }
    
    private void rebaixarLogin() {
        try {
            int linha = jtGerenciarLogins.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um login para rebaixar!");
                return;
            }

            int idLogin = idsGerenciarLogins.get(linha);
            String loginSelecionado = (String) jtGerenciarLogins.getValueAt(linha, 0);
            String perfilAtual = (String) jtGerenciarLogins.getValueAt(linha, 1);

            // ✅ Só permite rebaixar Admin para Funcionário
            if ("Admin".equals(perfilAtual)) {
                String novoPerfil = "Funcionario";
                int idNovoPerfil = 2;

                int confirma = JOptionPane.showConfirmDialog(
                    this,
                    "Rebaixar '" + loginSelecionado + "' para '" + novoPerfil + "'?",
                    "Confirmar Rebaixamento",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirma == JOptionPane.YES_OPTION) {
                    LoginServicos ls = ServicosFactory.getLoginServicos();
                    ls.atualizarPerfil(idLogin, idNovoPerfil);

                    JOptionPane.showMessageDialog(this, "Usuário rebaixado com sucesso!");
                    preencherTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Só é possível rebaixar Admins para Funcionário.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao rebaixar usuário: " + e.getMessage());
        }
    }
    
    private void deletarLogin() {
        try {
            int linha = jtGerenciarLogins.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um login para excluir!");
                return;
            }

            int idLogin = idsGerenciarLogins.get(linha);
            String loginExcluido = (String) jtGerenciarLogins.getValueAt(linha, 1);

            int confirma = JOptionPane.showConfirmDialog(
                this,
                "Excluir login '" + loginExcluido + "' permanentemente?\n" +
                "⚠️ Esta ação não pode ser desfeita!",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirma == JOptionPane.YES_OPTION) {
                LoginServicos ls = ServicosFactory.getLoginServicos();
                ls.deletarLogin(idLogin);

                JOptionPane.showMessageDialog(this, "Login excluído com sucesso!");
                limpar();
                preencherTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir login: " + e.getMessage());
        }
    }
    
    private void filtrar() {
        try {
            String termo = jtfPesquisar.getText().trim();
            DefaultTableModel dtmGerenciar = (DefaultTableModel) jtGerenciarLogins.getModel();
            dtmGerenciar.setNumRows(0);
            idsGerenciarLogins.clear();

            if (termo.isEmpty()) {
                // Se não há termo, carrega todos os logins
                preencherTabela();
            } else {
                // Filtra pelo login (email)
                LoginServicos ls = ServicosFactory.getLoginServicos();
                ArrayList<LoginVO> logins = ls.filtrarLoginsPorLogin(termo);

                for (LoginVO l : logins) {
                    // Não mostrar o próprio admin na lista
                    if (l.getIdLogin() == SessaoUsuario.getUsuarioLogado().getIdLogin()) {
                        continue;
                    }

                    String nomePerfil = getNomePerfil(l.getPerfil());
                    dtmGerenciar.addRow(new Object[]{
                        l.getLogin(),
                        nomePerfil
                    });
                    idsGerenciarLogins.add(l.getIdLogin());
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar logins: " + e.getMessage());
        }
    }
    
    private void limpar(){
        jtfLogin.setText(null);
        jpfSenha.setText(null);
        jpfConfirmacao.setText(null);
        jpfSenhaAtual.setText(null);
        dtm.setNumRows(0);
        preencherTabela();
    }
    
    private void jbtnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCadastrarActionPerformed
        salvarLogin();
        jpfSenha.setText(null);
        jpfConfirmacao.setText(null);
        jpfSenhaAtual.setText(null);
    }//GEN-LAST:event_jbtnCadastrarActionPerformed

    private void jbtnCadastrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtnCadastrarKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            salvarLogin();
            jpfSenha.setText(null);
            jpfConfirmacao.setText(null);
            jpfSenhaAtual.setText(null);
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

    private void jbtnRebaixarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRebaixarActionPerformed
        rebaixarLogin();
    }//GEN-LAST:event_jbtnRebaixarActionPerformed

    private void jbtnDeletar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeletar4ActionPerformed
        deletarLogin();
    }//GEN-LAST:event_jbtnDeletar4ActionPerformed

    private void jbtnPromoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPromoverActionPerformed
        promoverLogin();
    }//GEN-LAST:event_jbtnPromoverActionPerformed

    private void jcdMostrarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcdMostrarSenhaActionPerformed
        if(jcdMostrarSenha.isSelected()){
            jpfSenha.setEchoChar((char)0);
            jpfConfirmacao.setEchoChar((char)0);
        }else{
            jpfSenha.setEchoChar('*');
            jpfConfirmacao.setEchoChar('*');
        }
    }//GEN-LAST:event_jcdMostrarSenhaActionPerformed

    private void jtfPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisarKeyReleased
        limpar();
        filtrar();
    }//GEN-LAST:event_jtfPesquisarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelSenhaAtual;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbtnCadastrar;
    private javax.swing.JButton jbtnDeletar4;
    private javax.swing.JButton jbtnLimpar4;
    private javax.swing.JButton jbtnPromover;
    private javax.swing.JButton jbtnRebaixar;
    private javax.swing.JComboBox<String> jcbPerfil;
    private javax.swing.JCheckBox jcdMostrarSenha;
    private javax.swing.JLabel jlTtitulo;
    private javax.swing.JPasswordField jpfConfirmacao;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JPasswordField jpfSenhaAtual;
    private javax.swing.JTable jtGerenciarLogins;
    private javax.swing.JTextField jtfLogin;
    private javax.swing.JTextField jtfPesquisar;
    // End of variables declaration//GEN-END:variables
}
