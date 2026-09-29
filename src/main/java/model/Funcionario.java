package model;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {
    private String nome, matricula, setor, box;
    private List<EPI> fichaEPIs;

    // Construtor completo
    public Funcionario(String nome, String matricula, String setor, String box) {
        this.nome = nome;
        this.matricula = matricula;
        this.setor = setor;
        this.box = box;
        this.fichaEPIs = new ArrayList<>();
    }

    // Construtor vazio (usado no botão salvar)
    public Funcionario() {
        this.fichaEPIs = new ArrayList<>(); // Boa prática inicializar a lista aqui também
    }

    public void registrarEntrega(EPI e) { 
        this.fichaEPIs.add(e); 
    }

    // Getters
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getSetor() { return setor; }
    public String getBox() { return box; }
  
    public List<EPI> getFichaEPIs() { return fichaEPIs; }

    // Setters corrigidos (sem os bloqueios do NetBeans)
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
     public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setBox(String box) {
        this.box = box;
    }
    
    private String atendente;

public String getAtendente() {
    return atendente;
}

public void setAtendente(String atendente) {
    this.atendente = atendente;
}

}