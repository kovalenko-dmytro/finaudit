package stud.apach.finaudit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stud.apach.finaudit.model.Article;
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.Input;
import stud.apach.finaudit.model.Statement;
import stud.apach.finaudit.repositories.ArticleRepository;
import stud.apach.finaudit.repositories.EnterpriseRepository;
import stud.apach.finaudit.repositories.InputRepository;
import stud.apach.finaudit.repositories.StatementRepository;
import stud.apach.finaudit.services.interfaces.IInputService;
import stud.apach.finaudit.validate.InputForm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class InputService implements IInputService {

    @Autowired
    InputRepository inputRepo;

    @Autowired
    ArticleRepository articleRepo;

    @Autowired
    StatementRepository statementRepo;

    @Autowired
    EnterpriseRepository enterpriseRepo;


    @Override
    public InputForm createInputForm(Long statementId) {

        InputForm inputForm = new InputForm();

        Statement statement = statementRepo.findOne(statementId);
        List<Article> articles = articleRepo.findByStatement(statement);

        List<Input> inputs = new ArrayList<>();


        for (Article article : articles) {
            Input input = new Input();
            input.setArticle(article);
            inputs.add(input);
        }

        inputForm.setInputs(inputs);

        return inputForm;
    }

    @Override
    public Input selectByArticleAndEnterpriseAndDate(Article article, Enterprise enterprise, Date date) {

        return inputRepo.findByArticleAndEnterpriseAndDate(article, enterprise, date);
    }

    @Override
    public List<Input> addInputs(InputForm inputForm, Enterprise enterprise, Statement statement) {

        Input newInput;
        List<Input> inputs = new ArrayList<>();
        List<Article> articles = articleRepo.findByStatement(statement);

        for ( int i = 0; i < inputForm.getInputs().size(); i++ ) {
            newInput = new Input();
            newInput.setDate(inputForm.getDate());
            newInput.setValue(inputForm.getInputs().get(i).getValue());
            newInput.setArticle(articles.get(i));
            newInput.setEnterprise(enterprise);
            inputs.add(inputRepo.save(newInput));
        }

        return inputs;
    }

    @Override
    public List<Input> selectDistinctDateByArticleAndEnterpriseOrderByDateDesc(Article article, Enterprise enterprise) {
        return inputRepo.findDistinctDateByArticleAndEnterpriseOrderByDateDesc(article, enterprise);
    }

    @Override
    public List<Input> updateInputs(InputForm inputForm, Long enterpriseId, Long statementId) {

        Enterprise enterprise = enterpriseRepo.findOne(enterpriseId);
        Statement statement = statementRepo.findOne(statementId);
        List<Article> articles = articleRepo.findByStatement(statement);

        List<Input> inputs = new ArrayList<>();
        Input input;

        for (int i = 0; i < inputForm.getInputs().size(); i++) {
            input = inputRepo.findByArticleAndEnterpriseAndDate(articles.get(i), enterprise, inputForm.getDate());
            input.setValue(inputForm.getInputs().get(i).getValue());
            inputRepo.save(input);
            inputs.add(input);
        }

        return null;
    }

    @Override
    public void deleteInputs(Long enterpriseId, Long statementId, Date date) {

        Enterprise enterprise = enterpriseRepo.findOne(enterpriseId);
        Statement statement = statementRepo.findOne(statementId);
        List<Article> articles = articleRepo.findByStatement(statement);

        Input input;

        for (Article article : articles) {
            input = inputRepo.findByArticleAndEnterpriseAndDate(article, enterprise, date);
            inputRepo.delete(input);
        }
    }
}
