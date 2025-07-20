package web_ecommerce.core.dto.response;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class Response<D> extends BaseResponse<D> {
    public Response() {
        this.setRequestId(UUID.randomUUID().toString());
    }

    public Response<D> withDataAndStatus(D data, @Nullable HttpStatus status) {
        this.setData(data);
        if (status == null) {
            this.setHttpStatus(HttpStatus.OK);
        } else {
            this.setHttpStatus(status);
        }
        return this;
    }


    public Response<D> withData(D data) {
        this.setData(data);
        return this;
    }
}
