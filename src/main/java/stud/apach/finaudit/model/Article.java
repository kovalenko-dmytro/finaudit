package stud.apach.finaudit.model;

import javax.persistence.*;
import java.util.List;

@Table(name = "articles")
@Entity
public class Article {

    @Column(name = "article_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleId;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Input> inputs;

    @ManyToOne
    Statement statement;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
