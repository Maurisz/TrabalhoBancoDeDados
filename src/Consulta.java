import java.sql.Date;
import java.sql.Time;

public class Consulta {
    private Date data;
    private Time horario;
    private Animal animal;
    private Veterinario veterinario;
    private String diagnostico;
    private double valor;

    public Consulta(Date data, Time horario, Animal animal, Veterinario veterinario, String diagnostico, double valor){
        this.data=data;
        this.horario=horario;
        this.animal=animal;
        this.veterinario=veterinario;
        this.diagnostico=diagnostico;
        this.valor=valor;
    }

    public Animal getAnimal() {
        return animal;
    }
    public Date getData() {
        return data;
    }
    public String getDiagnostico() {
        return diagnostico;
    }
    public Time getHorario() {
        return horario;
    }
    public double getValor() {
        return valor;
    }
    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void Remarcar(Date data,Time horario) {
        this.data=data;
        this.horario = horario;
    }
}
