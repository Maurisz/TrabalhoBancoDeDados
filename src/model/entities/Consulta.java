import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(dataHora, consulta.dataHora) && Objects.equals(animal, consulta.animal) && Objects.equals(veterinario, consulta.veterinario) && Objects.equals(diagnostico, consulta.diagnostico) && Objects.equals(valor, consulta.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataHora, animal, veterinario, diagnostico, valor);
    }
}
