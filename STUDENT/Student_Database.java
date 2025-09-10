package Student_Management.STUDENT;

public class Student_Database {
    private int id;
    private String name;
    private int age;
    private String course;
    
    
    public Student_Database() {}
    
    public Student_Database(String name, int age, String course) {
        setName(name); 
        this.age = age;
        this.course = course;
    }
    
    public Student_Database(int id, String name, int age, String course) {
        this.id = id;
        setName(name);
        this.age = age;
        this.course = course;
    }
    
   
    public int getId() { 
    	return id; 
    	}
    public String getName() { 
    	return name; 
    	}
    public int getAge() { 
    	return age; 
    	}
    public String getCourse() { 
    	return course; 
    	}
    
   
    public void setId(int id) { this.id = id; }
    
    public void setName(String name) {
    	if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (containsNumbers(name)) {
            throw new IllegalArgumentException("Name cannot contain numeric values: " + name);
        }
        this.name = name;
    }
    
    public void setAge(int age) { 
    	this.age = age;
    	}
    public void setCourse(String course) { 
    	if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException("Course cannot be null or empty");
        }
    	if (containsOnlyNumbers(course)) {
            throw new IllegalArgumentException("Course cannot contain only numeric values: " + course);
            }
    	this.course = course;
    	}
    
   
    private boolean containsOnlyNumbers(String input) {
    	  if (input == null) return false;
          return input.matches("^[0-9\\s]+$");
	}

	private boolean containsNumbers(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    
    
    private boolean containsNumbersRegex(String input) {
        return input != null && input.matches(".*\\d.*");
    }
    
    @Override
    public String toString() {
    	return "ID: " + id + ", " + name + ", " + age + " years, " + course;
    }
}