package principal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class JanelaGrafica extends JFrame{
    
    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabelaContatos;
    private JScrollPane scroll;
    private JButton btnInserir;
    private JButton btnExcluir;
    private JButton btnEditar;
    private DefaultTableModel modelo;

    private GerenciadorContatos gerenciador;

    public JanelaGrafica() {
        gerenciador = new GerenciadorContatos();
        modelo = new DefaultTableModel();
        criarTabela();
        criarComponentes();
    }

    private void criarTabela() {
        tabelaContatos = new JTable(modelo);
        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Telefone");
        modelo.addColumn("Endereço");
        tabelaContatos.getColumnModel().getColumn(0)
                .setPreferredWidth(10);
        tabelaContatos.getColumnModel().getColumn(1)
                .setPreferredWidth(120);
        tabelaContatos.getColumnModel().getColumn(2)
                .setPreferredWidth(80);
        tabelaContatos.getColumnModel().getColumn(3)
                .setPreferredWidth(120);
        carregarDados(modelo);
    }

    private void criarComponentes() {
        btnInserir = new JButton("Inserir");
        btnInserir.addActionListener((e) -> {
            inserirContato();
        });
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener((e) -> {
            removerContato();
        });
        btnEditar = new JButton("Editar");
        btnEditar.addActionListener((e) -> {
            editarContato();
        });
        painelBotoes = new JPanel();
        painelBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));
        scroll = new JScrollPane(tabelaContatos);
        painelFundo = new JPanel();
        painelFundo.add(scroll);
        painelBotoes.add(btnInserir);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelFundo.add(painelBotoes);
        add(painelFundo);

        setTitle("Contatos");
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void carregarDados(DefaultTableModel modelo) {
        modelo.setNumRows(0);

        gerenciador.getContatos().forEach(c -> {
            modelo.addRow(new Object[]{
                c.getCodigo(),
                c.getNome(),
                c.getTelefone(),
                c.getEndereco()
            });
        });
    }

    public GerenciadorContatos getGerenciador() {
        return gerenciador;
    }
    
    private void inserirContato() {
        JanelaCadastroContato janelaCadastro = new JanelaCadastroContato(modelo, this);
        janelaCadastro.setVisible(true);
    }

    private void editarContato() {
        int linhaSelecionada = -1;
        linhaSelecionada = tabelaContatos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int codigo = (int) tabelaContatos.getValueAt(linhaSelecionada, 0);
            JanelaCadastroContato janelaCadastro = new JanelaCadastroContato(modelo, this, codigo, linhaSelecionada);
            janelaCadastro.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Selecione uma linha.");
        }
    }

    private void removerContato() {
        int linhaSelecionada = -1;
        linhaSelecionada = tabelaContatos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int codigo = (int) tabelaContatos.getValueAt(linhaSelecionada, 0);
            if(gerenciador.remover(codigo)){
                modelo.removeRow(linhaSelecionada);
                JOptionPane.showMessageDialog(null, "Contato removido!");
            }else{
                JOptionPane.showMessageDialog(null, "Contato não encontrado!");
            }
            
        } else {
            JOptionPane.showMessageDialog(null,
                    "Selecione uma linha.");
        }
    }
}