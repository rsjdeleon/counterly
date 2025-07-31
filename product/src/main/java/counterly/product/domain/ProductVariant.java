package counterly.product.domain;


import counterly.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Raymond De leon
 *
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(length = 36, columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

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
