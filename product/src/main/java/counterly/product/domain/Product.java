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
public class Product {
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

	private String manufacturerCode;
	private String name;
	private String description;

	@ManyToOne
	private Category category;


}
