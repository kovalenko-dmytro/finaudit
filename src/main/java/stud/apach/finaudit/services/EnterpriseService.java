package stud.apach.finaudit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stud.apach.finaudit.model.Address;
import stud.apach.finaudit.model.Enterprise;
import stud.apach.finaudit.model.User;
import stud.apach.finaudit.repositories.AddressRepository;
import stud.apach.finaudit.repositories.EnterpriseRepository;
import stud.apach.finaudit.repositories.UserRepository;
import stud.apach.finaudit.services.interfaces.IEnterpriseService;
import stud.apach.finaudit.validate.EnterpriseForm;

@Service
public class EnterpriseService implements IEnterpriseService {

    @Autowired
    EnterpriseRepository enterpriseRepo;

    @Autowired
    AddressRepository addressRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public Enterprise addEnterprise(EnterpriseForm enterpriseForm) {

        Enterprise enterprise = new Enterprise();
        enterprise.setName(enterpriseForm.getName());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        enterprise.setUser(user);

        Address address = new Address(
                                        enterpriseForm.getIndex(),
                                        enterpriseForm.getCity(),
                                        enterpriseForm.getStreet(),
                                        enterpriseForm.getBuilding()
                                        );
        addressRepo.save(address);
        enterprise.setAddress(address);

        return enterpriseRepo.save(enterprise);
    }

    @Override
    public Enterprise selectByName(String name) {

        return enterpriseRepo.findByName(name);
    }

    @Override
    public Iterable<Enterprise> selectByUser(User user) {

        return enterpriseRepo.findByUser(user);
    }

    @Override
    public Enterprise selectById(long enterpriseId) {

        return enterpriseRepo.findOne(enterpriseId);

    }

    @Override
    public Enterprise updateEnterprise(EnterpriseForm enterpriseForm, Long enterpriseId) {

        Enterprise editEnterprise = enterpriseRepo.findOne(enterpriseId);

        editEnterprise.setName(enterpriseForm.getName());

        Address address = new Address(
                enterpriseForm.getIndex(),
                enterpriseForm.getCity(),
                enterpriseForm.getStreet(),
                enterpriseForm.getBuilding()
        );

        addressRepo.save(address);
        editEnterprise.setAddress(address);

        return enterpriseRepo.save(editEnterprise);
    }

    @Override
    public void deleteEnterprise(long enterpriseId) {

        enterpriseRepo.delete(enterpriseId);
    }

    @Override
    public EnterpriseForm getEnterpriseForm(Enterprise enterprise) {

        EnterpriseForm form = new EnterpriseForm();

        form.setName(enterprise.getName());
        if (enterprise.getAddress() != null) {
            form.setIndex(enterprise.getAddress().getIndex());
            form.setCity(enterprise.getAddress().getCity());
            form.setStreet(enterprise.getAddress().getStreet());
            form.setBuilding(enterprise.getAddress().getBuilding());
        }
        return form;
    }
}
