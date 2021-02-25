package principal;

import java.util.ArrayList;
import java.util.Optional;


public class GerenciadorContatos {
    private ArrayList<Contato> contatos;
    private static int prox = 0;

    public GerenciadorContatos() {
        contatos = new ArrayList<>();
    }

    public boolean inserir(Contato contato) {
        if (contato != null) {
            contato.setCodigo(++prox);
            contatos.add(contato);
            return true;
        }
        return false;
    }

    public ArrayList<Contato> getContatos() {
        return contatos;
    }

    public boolean remover(int codigo) {
        Optional<Contato> contato = buscarPeloCodigo(codigo);
        if (contato.isPresent()) {
            contatos.remove(contato.get());
            return true;
        }
        return false;
    }

    public Optional<Contato> buscarPeloCodigo(int codigo) {
        Contato contato = null;
        for (Contato elemento : contatos) {
            if (elemento.getCodigo() == codigo) {
                contato = elemento;
            }
        }
        return Optional.ofNullable(contato);
    }

    public boolean editar(Contato contato) {
        if(contatos.contains(contato)){
            contatos.set(contatos.indexOf(contato), contato);
            return true;
        }
        return false;
    }
}