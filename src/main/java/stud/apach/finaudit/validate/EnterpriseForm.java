package stud.apach.finaudit.validate;


import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EnterpriseForm {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @Size(min = 5, max = 5, message = "Индекс должен состоять из 5 цифр")
    private String index;

    @Size(max = 45, message = "Длина поля не более 45 символов")
    private String city;

    @Size(max = 45, message = "Длина поля не более 45 символов")
    private String street;

    @Size(max = 10, message = "Длина поля не более 10 символов")
    private String building;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "EnterpriseForm{" +
                "name='" + name + '\'' +
                ", index='" + index + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                '}';
    }
}
