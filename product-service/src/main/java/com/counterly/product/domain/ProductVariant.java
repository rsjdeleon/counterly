package com.counterly.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

/**
 * @author Raymond De leon
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(length = 36, nullable = false, updatable = false)
	private String id;

	@Version
	private Long version;

	@CreationTimestamp
	@Column(updatable = false)
	private Timestamp createdDate;

	@UpdateTimestamp
	private Timestamp modifiedDate;

	private boolean active = true;

	@ManyToOne
	private Product product;

	private String sku;
	private String barcode;
	private String attributes;
	private double price;

}
