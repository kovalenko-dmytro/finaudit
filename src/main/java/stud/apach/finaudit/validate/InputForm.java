package stud.apach.finaudit.validate;

import stud.apach.finaudit.model.Input;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

public class InputForm {

    @NotNull
    private Date date;

    private List<Input> inputs;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }
}
