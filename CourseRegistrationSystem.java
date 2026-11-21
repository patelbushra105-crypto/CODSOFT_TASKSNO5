
import java.util.ArrayList;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    ArrayList<Integer> registeredStudents;

    Course(String courseCode, String title, String description,
           int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        registeredStudents = new ArrayList<>();
    }

    void displayCourse() {
        System.out.println("\nCourse Code: " + courseCode);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Schedule: " + schedule);
        System.out.println("Capacity: " + capacity);
        System.out.println("Available Slots: " +
                (capacity - registeredStudents.size()));
    }
}

class Student {
    int studentId;
    String name;
    ArrayList<String> registeredCourses;

    Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        registeredCourses = new ArrayList<>();
    }
}

public class CourseRegistrationSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();

    // Add sample courses
    static void addCourses() {
        courses.add(new Course("CS101", "Java Programming",
                "Introduction to Java Programming", 2,
                "Monday - 10:00 AM"));

        courses.add(new Course("CS102", "Python Programming",
                "Introduction to Python Programming", 3,
                "Tuesday - 11:00 AM"));

        courses.add(new Course("CS103", "Database Management",
                "Learn SQL and Database Concepts", 2,
                "Wednesday - 12:00 PM"));

        courses.add(new Course("CS104", "Web Development",
                "HTML, CSS and JavaScript", 3,
                "Thursday - 2:00 PM"));
    }

    // Find student using student ID
    static Student findStudent(int id) {
        for (Student s : students) {
            if (s.studentId == id) {
                return s;
            }
        }
        return null;
    }

    // Find course using course code
    static Course findCourse(String code) {
        for (Course c : courses) {
            if (c.courseCode.equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    // Register a new student
    static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        students.add(new Student(id, name));

        System.out.println("Student registered successfully!");
    }

    // Display all available courses
    static void displayCourses() {
        System.out.println("\n========== AVAILABLE COURSES ==========");

        for (Course c : courses) {
            c.displayCourse();
        }
    }

    // Register student for a course
    static void registerCourse() {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        displayCourses();

        System.out.print("\nEnter Course Code: ");
        String code = sc.nextLine();

        Course course = findCourse(code);

        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        if (student.registeredCourses.contains(course.courseCode)) {
            System.out.println("You are already registered for this course!");
            return;
        }

        if (course.registeredStudents.size() >= course.capacity) {
            System.out.println("Sorry! This course is full.");
            return;
        }

        student.registeredCourses.add(course.courseCode);
        course.registeredStudents.add(student.studentId);

        System.out.println("Course registration successful!");
    }

    // Drop a registered course
    static void dropCourse() {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        if (student.registeredCourses.isEmpty()) {
            System.out.println("You have not registered for any courses.");
            return;
        }

        System.out.println("Your Registered Courses: "
                + student.registeredCourses);

        System.out.print("Enter Course Code to Drop: ");
        String code = sc.nextLine();

        Course course = findCourse(code);

        if (course == null ||
                !student.registeredCourses.contains(code.toUpperCase())) {
            System.out.println("Course registration not found!");
            return;
        }

        student.registeredCourses.remove(course.courseCode);
        course.registeredStudents.remove(Integer.valueOf(student.studentId));

        System.out.println("Course dropped successfully!");
    }

    // Display student details and registered courses
    static void displayStudent() {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\n========== STUDENT DETAILS ==========");
        System.out.println("Student ID: " + student.studentId);
        System.out.println("Student Name: " + student.name);
        System.out.println("Registered Courses:");

        if (student.registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            for (String code : student.registeredCourses) {
                Course c = findCourse(code);
                System.out.println(code + " - " + c.title);
            }
        }
    }

    public static void main(String[] args) {

        addCourses();

        int choice;

        do {
            System.out.println("\n===== STUDENT COURSE REGISTRATION SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Available Courses");
            System.out.println("3. Register for a Course");
            System.out.println("4. Drop a Course");
            System.out.println("5. View Student Details");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addStudent();
                        break;

                    case 2:
                        displayCourses();
                        break;

                    case 3:
                        registerCourse();
                        break;

                    case 4:
                        dropCourse();
                        break;

                    case 5:
                        displayStudent();
                        break;

                    case 6:
                        System.out.println("Thank you for using the system!");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                choice = 0;
            }

        } while (choice != 6);

        sc.close();
    }
}
