package model;

import java.util.Date;

public class LoyaltyProgram {
    private final int id;
    private String nomePrograma;
    private int milhasAcumuladas;
    private String status;
    private Date dataValidade;
    private Company companhia;

    public LoyaltyProgram() {
        this.id = 0; // usado em inserções
    }

    public LoyaltyProgram(int id) {
        this.id = id; // usado para update e delete
    }

    public int getId() {
        return id;
    }

    public String getNomePrograma() {
        return nomePrograma;
    }

    public void setNomePrograma(String nomePrograma) {
        this.nomePrograma = nomePrograma;
    }

    public int getMilhasAcumuladas() {
        return milhasAcumuladas;
    }

    public void setMilhasAcumuladas(int milhasAcumuladas) {
        this.milhasAcumuladas = milhasAcumuladas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Company getCompanhia() {
        return companhia;
    }

    public void setCompanhia(Company companhia) {
        this.companhia = companhia;
    }
}
