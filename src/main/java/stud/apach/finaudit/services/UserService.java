package stud.apach.finaudit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stud.apach.finaudit.model.Role;
import stud.apach.finaudit.model.User;
import stud.apach.finaudit.repositories.RoleRepository;
import stud.apach.finaudit.repositories.UserRepository;
import stud.apach.finaudit.services.interfaces.IUserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public User registerNewUser(User user) {


        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepo.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepo.save(user);

        return userRepo.save(user);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
