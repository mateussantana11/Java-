package principal;

import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class JanelaCadastroContato extends JFrame {
    
    private DefaultTableModel modelo;
    private JPanel painelFundo;
    private JButton btnSalvar;
    private JButton btnLimpar;
    private JLabel lblCodigo;
    private JLabel lblNome;
    private JLabel lblTelefone;
    private JLabel lblEndereco;
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEndereco;

    private JanelaGrafica janelaGrafica;

    private int linhaSelecionada;

    public JanelaCadastroContato(DefaultTableModel modelo, JanelaGrafica janelaGrafica) {
        criarComponentesInsercao();
        this.modelo = modelo;
        this.janelaGrafica = janelaGrafica;
    }

    public JanelaCadastroContato(DefaultTableModel modelo, JanelaGrafica janelaGrafica, int codigo, int linha) {
        criarComponentesEdicao();
        this.modelo = modelo;
        this.janelaGrafica = janelaGrafica;
        this.linhaSelecionada = linha;
        carregarDados(codigo);
    }

    private void criarComponentesInsercao() {
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener((e) -> {
            inserirContato();
        });
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener((e) -> {
            txtNome.setText("");
            txtTelefone.setText("");
            txtEndereco.setText("");
        });
        lblNome = new JLabel("Nome:");
        lblTelefone = new JLabel("Telefone:");
        lblEndereco = new JLabel("Endereço:");
        txtNome = new JTextField(10);
        txtTelefone = new JTextField(5);
        txtEndereco = new JTextField(10);

        painelFundo = new JPanel();
        painelFundo.add(lblNome);
        painelFundo.add(txtNome);
        painelFundo.add(lblTelefone);
        painelFundo.add(txtTelefone);
        painelFundo.add(lblEndereco);
        painelFundo.add(txtEndereco);
        painelFundo.add(btnSalvar);
        painelFundo.add(btnLimpar);

        add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
    
    private void criarComponentesEdicao() {
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener((e) -> {
            editarContato();
        });
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener((e) -> {
            txtNome.setText("");
            txtTelefone.setText("");
            txtEndereco.setText("");
        });
        lblCodigo = new JLabel("Código:");
        lblNome = new JLabel("Nome:");
        lblTelefone = new JLabel("Telefone:");
        lblEndereco = new JLabel("E-mail:");
        txtCodigo = new JTextField(2);
        txtCodigo.setEditable(false);
        txtNome = new JTextField(10);
        txtTelefone = new JTextField(5);
        txtEndereco = new JTextField(10);

        painelFundo = new JPanel();
        painelFundo.add(lblCodigo);
        painelFundo.add(txtCodigo);
        painelFundo.add(lblNome);
        painelFundo.add(txtNome);
        painelFundo.add(lblTelefone);
        painelFundo.add(txtTelefone);
        painelFundo.add(lblEndereco);
        painelFundo.add(txtEndereco);
        painelFundo.add(btnSalvar);
        painelFundo.add(btnLimpar);

        add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void inserirContato() {
        Contato contato = new Contato();
        contato.setNome(txtNome.getText());
        contato.setTelefone(txtTelefone.getText());
        contato.setEndereco(txtEndereco.getText());

        setVisible(false);
        if (janelaGrafica.getGerenciador().inserir(contato)) {
            janelaGrafica.carregarDados(modelo);
            JOptionPane.showMessageDialog(null, "Contato inserido!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao inserir contato!");
        }  
    }

    private void carregarDados(int codigo) {
        Optional<Contato> op = janelaGrafica.getGerenciador().buscarPeloCodigo(codigo);
        if (op.isPresent()) {
            Contato contato = op.get();
            txtCodigo.setText(Integer.toString(contato.getCodigo()));
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            txtEndereco.setText(contato.getEndereco());
        }
    }

    private void editarContato() {
        Contato contato = new Contato();
        contato.setCodigo(Integer.parseInt(txtCodigo.getText()));
        contato.setNome(txtNome.getText());
        contato.setTelefone(txtTelefone.getText());
        contato.setEndereco(txtEndereco.getText());

        setVisible(false);
        if (janelaGrafica.getGerenciador().editar(contato)) {
            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{
                contato.getCodigo(),
                contato.getNome(), 
                contato.getTelefone(), 
                contato.getEndereco()
            });
            JOptionPane.showMessageDialog(null, "Contato editado!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao editar contato!");
        }
    }
}
