package counterly.product.domain;

import jakarta.persistence.Entity;
import lombok.*;

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
public class Category {
    private String name;
    private boolean active = true;
}
