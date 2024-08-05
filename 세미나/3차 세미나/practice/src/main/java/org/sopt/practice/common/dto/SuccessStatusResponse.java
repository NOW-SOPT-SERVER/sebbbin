package org.sopt.practice.common.dto;

public record SuccessStatusResponse(
        int status, String message,  Object data
) {
    public static SuccessStatusResponse of(SuccessMessage successMessage){
        return new SuccessStatusResponse(successMessage.getStatus(), successMessage.getMessage(), null);
    }
    public static SuccessStatusResponse of(int status, String message, Object data) {
        return new SuccessStatusResponse(status, message, data);
    }

}
