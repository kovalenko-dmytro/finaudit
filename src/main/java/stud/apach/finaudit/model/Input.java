package stud.apach.finaudit.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Table(name = "inputs")
@Entity
public class Input {

    @Column(name = "input_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inputId;

    @Column(name = "value")
    private Double value;

    @Column(name = "date")
    @NotNull
    private Date date;

    @ManyToOne
    Article article;

    @ManyToOne
    Enterprise enterprise;

    public Long getInputId() {
        return inputId;
    }

    public void setInputId(Long inputId) {
        this.inputId = inputId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    @Override
    public String toString() {
        return "Input{" +
                "value=" + value +
                ", date=" + date +
                ", article=" + article +
                ", enterprise=" + enterprise +
                '}';
    }
}
