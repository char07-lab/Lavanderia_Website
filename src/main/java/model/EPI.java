package model;

import java.time.LocalDate;

public class EPI {
    private int id_epi;
    private String funcionario_matricula;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    
    // Variáveis das quantidades de EPIs
    private int bone, camiseta, calca, jaleco, moleton, macacao, calca_pvc, capa_pvc, sapatenis, botina;

    // Construtor vazio corrigido (sem o throw new UnsupportedOperationException)
    public EPI() {
        // Pode ficar totalmente em branco aqui dentro!
    }

    // --- GETTERS E SETTERS ---
    public int getId_epi() { return id_epi; }
    public void setId_epi(int id_epi) { this.id_epi = id_epi; }

    public String getFuncionario_matricula() { return funcionario_matricula; }
    public void setFuncionario_matricula(String funcionario_matricula) { this.funcionario_matricula = funcionario_matricula; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public int getBone() { return bone; }
    public void setBone(int bone) { this.bone = bone; }

    public int getCamiseta() { return camiseta; }
    public void setCamiseta(int camiseta) { this.camiseta = camiseta; }

    public int getCalca() { return calca; }
    public void setCalca(int calca) { this.calca = calca; }

    public int getJaleco() { return jaleco; }
    public void setJaleco(int jaleco) { this.jaleco = jaleco; }

    public int getMoleton() { return moleton; }
    public void setMoleton(int moleton) { this.moleton = moleton; }

    public int getMacacao() { return macacao; }
    public void setMacacao(int macacao) { this.macacao = macacao; }

    public int getCalca_pvc() { return calca_pvc; }
    public void setCalca_pvc(int calca_pvc) { this.calca_pvc = calca_pvc; }

    public int getCapa_pvc() { return capa_pvc; }
    public void setCapa_pvc(int capa_pvc) { this.capa_pvc = capa_pvc; }

    public int getSapatenis() { return sapatenis; }
    public void setSapatenis(int sapatenis) { this.sapatenis = sapatenis; }

    public int getBotina() { return botina; }
    public void setBotina(int botina) { this.botina = botina; }

    public String getDataFormatada() {
    java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
    if (this.dataEntrada != null) {
        return this.dataEntrada.format(formato);
    } else if (this.dataSaida != null) {
        return this.dataSaida.format(formato);
    }
    return "Sem data";
}
}