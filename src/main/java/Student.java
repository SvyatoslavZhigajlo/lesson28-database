public class Student implements Comparable<Student>{
    int id;
    String name;
    String lastName;
    int age;


    public Student(int id, String name, String lastName,int age) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Student:" +
                "id= " + id + "/-/" +
                "name: " + name + "/-/" +
                "lastName= " + lastName+ "/-/" +
                "age= " + age + "\n";
    }

    @Override
    public int compareTo(Student o) {
        return this.name.compareTo(o.name);
    }
}
