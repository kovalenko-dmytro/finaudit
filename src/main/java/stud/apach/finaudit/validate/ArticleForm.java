package stud.apach.finaudit.validate;

import javax.validation.constraints.Size;

public class ArticleForm {

    private String name;

    @Size(min = 4, max = 4)
    private String code;

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
}
