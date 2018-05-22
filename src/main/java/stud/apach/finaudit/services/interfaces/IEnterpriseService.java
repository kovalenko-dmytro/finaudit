package stud.apach.finaudit.services.interfaces;

import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.User;
import stud.apach.finaudit.validate.EnterpriseForm;

public interface IEnterpriseService {

    Enterprise addEnterprise(EnterpriseForm form);
    Enterprise selectByName(String name);
    Iterable<Enterprise> selectByUser(User user);
    Enterprise selectById(long enterpriseId);
    Enterprise updateEnterprise(EnterpriseForm form, Long enterpriseId);
    void deleteEnterprise(long enterpriseId);
    EnterpriseForm getEnterpriseForm(Enterprise enterprise);
}
