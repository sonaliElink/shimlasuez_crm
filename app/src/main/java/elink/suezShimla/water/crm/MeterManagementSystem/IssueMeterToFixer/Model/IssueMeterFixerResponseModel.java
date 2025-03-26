package elink.suezShimla.water.crm.MeterManagementSystem.IssueMeterToFixer.Model;

import com.google.gson.annotations.SerializedName;

public class IssueMeterFixerResponseModel {
    @SerializedName("Success")
    private String Success;

    @SerializedName("Message")
    private String Message;

     @SerializedName("ErrorMessage")
    private String ErrorMessage;



    public IssueMeterFixerResponseModel() {
    }
    //{ 'Success': 'True','Message': '','ErrorMessage': '','ValidationMessage': '0'}


    public IssueMeterFixerResponseModel(String success, String message, String errorMessage) {
        Success = success;
        Message = message;
        ErrorMessage = errorMessage;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "IssueMeterFixerResponseModel{" +
                "Success='" + Success + '\'' +
                ", Message='" + Message + '\'' +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }
}
