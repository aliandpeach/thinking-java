package chapter01_basic.xstream;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-24
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates
 */
public class Person {
    private String firstName;
    private String lastName;
    private PhoneNumber phone;
    private PhoneNumber fax;

    public Person() {
    }

    public Person(String firstname, String lastname) {
        this.firstName = firstname;
        this.lastName = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public PhoneNumber getFax() {
        return fax;
    }

    public void setFax(PhoneNumber fax) {
        this.fax = fax;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone=" + phone +
                ", fax=" + fax +
                '}';
    }
}
