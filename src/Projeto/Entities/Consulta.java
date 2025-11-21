package Projeto.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Consulta {
    private Timestamp dataHora;
    private int idAnimal;
    private String idVeterinario;
    private String diagnostico;
    private BigDecimal valor;

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
    public String getIdVeterinario() {
        return idVeterinario;
    }

    public void Remarcar(Timestamp data) {
        this.dataHora=data;
    }
}
