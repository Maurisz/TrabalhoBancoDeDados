package Projeto.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Consulta {
    private int id;
    private Timestamp dataHora;
    private int idAnimal;
    private String idVeterinario;
    private String diagnostico;
    private BigDecimal valor;
    private String nomeAnimal;
    private String nomeVeterinario;

    public String getNomeVeterinario() {
        return nomeVeterinario;
    }

    public void setNomeVeterinario(String nomeVeterinario) {
        this.nomeVeterinario = nomeVeterinario;
    }

    public String getNomeAnimal() {
        return nomeAnimal;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public Consulta(Timestamp dataHora, int idAnimal, String idVeterinario, String diagnostico, BigDecimal valor){
        this.dataHora=dataHora;
        this.idAnimal= idAnimal;
        this.idVeterinario =idVeterinario;
        this.diagnostico=diagnostico;
        this.valor=valor;
    }

    public int getIdAnimal() {
        return idAnimal;
    }
    public Timestamp getDataHora() {
        return dataHora;
    }
    public String getDiagnostico() {
        return diagnostico;
    }
    public BigDecimal getValor() {
        return valor;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public void setIdVeterinario(String idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getIdVeterinario() {
        return idVeterinario;
    }

    public void Remarcar(Timestamp data) {
        this.dataHora=data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
