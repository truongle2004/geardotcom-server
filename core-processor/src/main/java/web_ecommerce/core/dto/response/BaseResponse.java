package web_ecommerce.core.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import web_ecommerce.core.utils.Constants;
import java.time.LocalDateTime;

public abstract class BaseResponse<T> {
    protected T data = null;
    private Boolean success = true;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    private LocalDateTime executeDate = LocalDateTime.now();

    @JsonProperty("requestId")
    private String requestId = null;

    // New field for HTTP status
    private int httpStatus = HttpStatus.OK.value();

    public void setData(T data) {
        this.data = data;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public LocalDateTime getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(LocalDateTime executeDate) {
        this.executeDate = executeDate;
    }

    public T getData() {
        return data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus.value();
    }
}
