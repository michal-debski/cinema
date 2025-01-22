package pl.agh.edu.mwo.analiza;

public abstract class Person {


    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    private final int age;

    public Person(String name, String surname, String email, String phone, int age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

}
