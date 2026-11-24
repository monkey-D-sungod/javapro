import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing an individual student and their academic metrics
class Student {
    private String name;
    private int rollNumber;
    private double[] marks;
    private double total;
    private double average;
    private char grade;

    public Student(String name, int rollNumber, double[] marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
        calculateMetrics();
    }

    // Calculate total, average, and assign grade
    private void calculateMetrics() {
        total = 0;
        for (double mark : marks) {
            total += mark;
        }
        average = total / marks.length;
        assignGrade();
    }

    private void assignGrade() {
        if (average >= 90) {
            grade = 'A';
        } else if (average >= 80) {
            grade = 'B';
        } else if (average >= 70) {
            grade = 'C';
        } else if (average >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public double getTotal() { return total; }
    public double getAverage() { return average; }
    public char getGrade() { return grade; }
    public boolean isPassed() { return average >= 60; } // Pass threshold: 60%

    public void displayReport() {
        System.out.printf("%-10d %-20s %-10.2f %-10.2f %-8c %-10s%n",
                rollNumber, name, total, average, grade, (isPassed() ? "PASS" : "FAIL"));
    }
}

public class StudentPerformanceTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        System.out.print("Enter the number of students: ");
        int numStudents = scanner.nextInt();

        System.out.print("Enter the number of subjects per student: ");
        int numSubjects = scanner.nextInt();

        // 1. Input Student Details and Marks
        for (int i = 0; i < numStudents; i++) {
            System.out.println("\n--- Enter details for Student " + (i + 1) + " ---");
            System.out.print("Roll Number: ");
            int roll = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline

            System.out.print("Student Name: ");
            String name = scanner.nextLine();

            double[] marks = new double[numSubjects];
            for (int j = 0; j < numSubjects; j++) {
                System.out.print("Marks for Subject " + (j + 1) + " (out of 100): ");
                marks[j] = scanner.nextDouble();
            }

            students.add(new Student(name, roll, marks));
        }

        // 2. Display Individual Student Report Card Table
        System.out.println("\n=========================================================================");
        System.out.println("                        STUDENT PERFORMANCE REPORT                       ");
        System.out.println("=========================================================================");
        System.out.printf("%-10s %-20s %-10s %-10s %-8s %-10s%n",
                "Roll No", "Name", "Total", "Average", "Grade", "Status");
        System.out.println("-------------------------------------------------------------------------");

        for (Student s : students) {
            s.displayReport();
        }

        // 3. Class-Wide Performance Analysis
        analyzeClassPerformance(students);
        scanner.close();
    }

    // Method to analyze overall class statistics
    private static void analyzeClassPerformance(List<Student> students) {
        if (students.isEmpty()) return;

        double classTotalAvg = 0;
        Student topStudent = students.get(0);
        Student lowestStudent = students.get(0);
        int passCount = 0;

        for (Student s : students) {
            classTotalAvg += s.getAverage();

            if (s.getAverage() > topStudent.getAverage()) {
                topStudent = s;
            }
            if (s.getAverage() < lowestStudent.getAverage()) {
                lowestStudent = s;
            }
            if (s.isPassed()) {
                passCount++;
            }
        }

        double overallClassAverage = classTotalAvg / students.size();
        int failCount = students.size() - passCount;

        System.out.println("\n=========================================================================");
        System.out.println("                       CLASS PERFORMANCE ANALYSIS                        ");
        System.out.println("=========================================================================");
        System.out.printf("Class Average Score : %.2f%%%n", overallClassAverage);
        System.out.printf("Top Performer       : %s (Avg: %.2f%%, Grade: %c)%n",
                topStudent.getName(), topStudent.getAverage(), topStudent.getGrade());
        System.out.printf("Needs Improvement   : %s (Avg: %.2f%%, Grade: %c)%n",
                lowestStudent.getName(), lowestStudent.getAverage(), lowestStudent.getGrade());
        System.out.printf("Pass / Fail Count   : %d Passed | %d Failed%n", passCount, failCount);
        System.out.println("=========================================================================");
    }
}
