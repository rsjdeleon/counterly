package com.counterly.product.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7499390684589311728L;

    @Null
    private String id = null;

    @Null
    private Integer version = null;

    @Null
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    @JsonProperty("createdDate")
    private OffsetDateTime createdDate = null;

    @Null
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private OffsetDateTime modifiedDate = null;

    @NotBlank
    private String productId;

    private String name;
    private String manufacturerCode;
    private String description;

    @NotBlank
    private String sku;

    @NotBlank
    private String barcode;

    private String attributes;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    private boolean active = true;
}
