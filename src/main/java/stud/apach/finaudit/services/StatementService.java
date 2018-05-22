package stud.apach.finaudit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.repositories.StatementRepository;
import stud.apach.finaudit.services.interfaces.IStatementService;
import stud.apach.finaudit.validate.StatementForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StatementService implements IStatementService {

    @Autowired
    StatementRepository statementRepo;

    @Override
    public Statement selectByName(String name) {
        return statementRepo.findByName(name);
    }

    @Override
    public Statement addStatement(StatementForm statementForm) {

        Statement statement = new Statement();
        statement.setName(statementForm.getName());

       return statementRepo.save(statement);
    }

    @Override
    public List<Statement> selectAll() {

        return (List<Statement>) statementRepo.findAll();
    }

    @Override
    public Statement selectById(long statementId) {

        return statementRepo.findOne(statementId);
    }

    @Override
    public void deleteStatement(long statementId) {

        statementRepo.delete(statementId);
    }

    @Override
    public Statement updateStatement(StatementForm statementForm, long statementId) {

        Statement editStatement = statementRepo.findOne(statementId);

        editStatement.setName(statementForm.getName());

        return statementRepo.save(editStatement);
    }
}
