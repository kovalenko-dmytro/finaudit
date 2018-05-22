package stud.apach.finaudit.services.interfaces;

import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.validate.StatementForm;

import java.util.List;
import java.util.Set;

public interface IStatementService {

    Statement selectByName(String name);
    Statement addStatement(StatementForm statementForm);
    List<Statement> selectAll();
    Statement selectById(long statementId);
    void deleteStatement(long statementId);
    Statement updateStatement(StatementForm statementForm, long statementId);
}
