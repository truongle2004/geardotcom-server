package web_ecommerce.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web_ecommerce.core.utils.Constants;


import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {

    @ApiModelProperty(name = "Id")
    public Long id;

    @ApiModelProperty(notes = "Ngày tạo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    public LocalDateTime createdAt;

    @ApiModelProperty(notes = "Ngày cập nhật")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN)
    public LocalDateTime updatedAt;

    @ApiModelProperty(notes = "Người tạo")
    public String createdBy;

    @ApiModelProperty(notes = "Người cập nhật")
    public String updatedBy;
}
