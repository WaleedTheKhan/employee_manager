/* Employee Management System 2023
 * Created by: Waleed Khan
 * Last modified: April 7, 2023
 * This is the Model class for a JavaFX FXML project for managing employees.
 */
package employee_manager_Khan;

import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    
    private List<Employee> employees;

    public EmployeeModel() {
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public static class Employee {
        private int id;
        private String name;
        private String job;
        private boolean fulltime;
        private String gender;

        public Employee(int id, String name, String job, boolean fulltime, String gender) {
            this.id = id;
            this.name = name;
            this.fulltime = fulltime;
            this.gender = gender;
            this.job = job;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public boolean isFulltime() {
            return fulltime;
        }

        public void setFulltime(boolean fulltime) {
            this.fulltime = fulltime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return id + ", " + name + ", " + job + ", " + fulltime + ", " + gender;
        }

    }//end static class Employee

}//end class EmployeeModel