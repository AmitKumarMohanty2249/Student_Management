package Student_Management.STUDENT;



import java.util.List;
import java.util.Scanner;

public class StudentManagementMain {
    private StudentDAO studentDAO;
    private Scanner scanner;
    
    public StudentManagementMain() {
        this.studentDAO = new StudentDAO();
        this.scanner = new Scanner(System.in);
    }
    
    public void displayMenu() {
        System.out.println("\n====== Student Management System ======");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student by ID");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    
    public void addStudent() {
        System.out.println("\n--- Add New Student ---");
        
        try {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Error: Name cannot be empty!");
                return; 
            }
            
            if (containsNumbers(name)) {
                System.out.println("Error: Name cannot contain numeric values!");
                return; 
            }
            
            System.out.print("Enter age: ");
            String ageInput = scanner.nextLine();
            
            // Moved the validation BEFORE parsing
            if (!isValidInteger(ageInput)) {
                System.out.println("Error: Age must be a numeric value!");
                return; 
            }
            
            int age = Integer.parseInt(ageInput); // Use the validated input
            
            System.out.print("Enter course: ");  
            String course = scanner.nextLine();
            
            if (course == null || course.trim().isEmpty()) {
                System.out.println("Error: Course cannot be empty!");
                return;
            }
            
            if (containsOnlyNumbers(course)) {
                System.out.println("Error: Course cannot contain only numeric values!");
                return;
            }
            
            Student_Database student = new Student_Database(name, age, course);
            
            if (studentDAO.addStudent(student)) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("Failed to add student.");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
    
    public void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student_Database> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student_Database student : students) {
                System.out.println(student);
            }
        }
    }
    
    public void updateStudent() {
    	System.out.println("\n--- Update Student ---");
        
        System.out.print("Enter student ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Student_Database existingStudent = studentDAO.getStudentById(id);
        if (existingStudent == null) {
            System.out.println("Student not found with ID: " + id);
            return;
        }
        
        System.out.println("Current details: " + existingStudent);
        
        try {
            System.out.print("Enter new name (press enter to keep current): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                
                if (containsNumbers(name)) {
                    System.out.println("Error: Name cannot contain numeric values!");
                    return; 
                }
                existingStudent.setName(name);
            }
            
            
            System.out.print("Enter new age (press enter to keep current): ");
            String ageInput = scanner.nextLine();
            if (!ageInput.isEmpty()) {
                if (!isValidInteger(ageInput)) {
                    System.out.println("Error: Age must be a numeric value!");
                    return;
                }
                existingStudent.setAge(Integer.parseInt(ageInput));
            }
            
            System.out.print("Enter new course (press enter to keep current): ");
            String course = scanner.nextLine();
            if (!course.isEmpty()) {
            	if (containsOnlyNumbers(course)) {
                    System.out.println("Error: Course cannot contain only numeric values!");
                    return;
                }
                existingStudent.setCourse(course);
            }
            
            if (studentDAO.updateStudent(existingStudent)) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student.");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

  
    private boolean containsNumbers(String input) {
        if (input == null) return false;
        return input.matches(".*\\d.*");
    }    
    
    private boolean isValidInteger(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
	
    
    private boolean containsOnlyNumbers(String input) {
        if (input == null) return false;
        return input.matches("^[0-9\\s]+$"); 
    }
    public void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        
        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Student_Database student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found with ID: " + id);
            return;
        }
        
        System.out.println("Are you sure you want to delete: " + student + "? (y/n)");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("y")) {
            if (studentDAO.deleteStudent(id)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    public void searchStudent() {
        System.out.println("\n--- Search Student ---");
        
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Student_Database student = studentDAO.getStudentById(id);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found with ID: " + id);
        }
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using Student Management System!");
                    DatabaseConnection.closeConnection();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        
        scanner.close();
    }
    
    public static void main(String[] args) {
        StudentManagementMain system = new StudentManagementMain();
        system.run();
    }
}
