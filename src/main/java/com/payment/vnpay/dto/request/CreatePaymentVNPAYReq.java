package com.payment.vnpay.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentVNPAYReq {

    @NotBlank(message = "Loại đơn hàng bắt buộc nhập")
    private String orderType;

    @NotNull(message = "Số tiền bắt buộc nhập")
    @Min(value = 0, message = "Số tiền phải lớn hơn 0")
    private Long amount;

    @NotBlank(message = "Nội dung thanh toán không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9\\s:-]+$", message = "Nội dung thanh toán chỉ chấp nhận chữ cái, số, dấu cách, dấu ':' và '-' mà không có ký tự đặc biệt khác")
    private String orderInfo;

    private String bankCode;

    @Pattern(regexp = "^(vi|en)$", message = "Ngôn ngữ chỉ được chọn tiếng Việt 'vi' hoặc tiếng Anh 'en'")
    private String locale;
}
