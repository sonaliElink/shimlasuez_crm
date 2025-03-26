package elink.suezShimla.water.crm.ConnectionRequest.Activity.TechnicalFeasibilityPlumberWork.Model;

public class SetEmployeeModel {

    String employeeCode;
    String employeeName;

    public SetEmployeeModel() {
    }

    public SetEmployeeModel(String employeeCode, String employeeName) {
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "SetEmployeeModel{" +
                "employeeCode='" + employeeCode + '\'' +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
