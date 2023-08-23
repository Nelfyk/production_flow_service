package ru.okibiteam.production_flow_service.entity.Exception;

public class ExceptionEntity extends Exception {
    private ErrorCode errorCode;

    public ExceptionEntity(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
    public enum ErrorCode {
        ERROR_CODE_1,
        ERROR_CODE_2,
        ERROR_CODE_3
    }
}
