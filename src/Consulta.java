import java.math.BigDecimal;
import java.sql.Timestamp;

public class Consulta {
    private Timestamp dataHora;
    private Animal animal;
    private Veterinario veterinario;
    private String diagnostico;
    private BigDecimal valor;

    public Consulta(Timestamp dataHora, Animal animal, Veterinario veterinario, String diagnostico, BigDecimal valor){
        this.dataHora=dataHora;
        this.animal=animal;
        this.veterinario=veterinario;
        this.diagnostico=diagnostico;
        this.valor=valor;
    }

    public Animal getAnimal() {
        return animal;
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
    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void Remarcar(Timestamp data) {
        this.dataHora=data;
    }
}
