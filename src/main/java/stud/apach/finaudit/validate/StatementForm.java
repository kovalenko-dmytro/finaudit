package stud.apach.finaudit.validate;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class StatementForm {

    @NotNull
    @NotEmpty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StatementForm{" +
                "name='" + name + '\'' +
                '}';
    }
}
