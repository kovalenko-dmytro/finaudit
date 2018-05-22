package stud.apach.finaudit.services.interfaces;

import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.Input;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.validate.InputForm;

import java.sql.Date;
import java.util.List;

public interface IInputService {

    InputForm createInputForm(Long statementId);
    Input selectByArticleAndEnterpriseAndDate(Article article, Enterprise enterprise, Date date);
    List<Input> addInputs(InputForm inputForm, Enterprise enterprise, Statement statement);

    List<Input> selectDistinctDateByArticleAndEnterpriseOrderByDateDesc(Article article, Enterprise enterprise);

    List<Input> updateInputs(InputForm inputForm, Long enterpriseId, Long statementId);

    void deleteInputs(Long enterpriseId, Long statementId, Date date);
}
