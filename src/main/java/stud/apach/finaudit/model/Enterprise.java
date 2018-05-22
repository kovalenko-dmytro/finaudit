package stud.apach.finaudit.model;

import com.sun.istack.internal.Nullable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Table(name = "enterprises")
@Entity
public class Enterprise {

    @Column(name = "enterprise_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long enterpriseId;

    @Column(name = "name")
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @ManyToOne
    private User user;

    //@OneToOne(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;

    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Input> inputs;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "name='" + name + '\'' +
                ", user=" + user +
                ", address=" + address +
                '}';
    }
}
