package stud.apach.finaudit.services.interfaces;

import stud.apach.finaudit.model.User;

public interface IUserService {

    User findByEmail(String email);
    User registerNewUser(User user);
}
