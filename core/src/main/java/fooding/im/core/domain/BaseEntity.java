package fooding.im.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @CreatedBy
    protected Long createdBy;

    @CreatedDate
    protected LocalDateTime createdAt;

    @LastModifiedBy
    protected Long updatedBy;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Column(nullable = false)
    protected boolean deleted;

    protected Long deletedBy;

    public void delete(long deletedBy) {
        this.deleted = true;
        this.deletedBy = deletedBy;
    }
}
