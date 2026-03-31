package org.iimsa.common.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.iimsa.common.util.SecurityUtil;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

@Getter
@MappedSuperclass
@Access(AccessType.FIELD)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseUserEntity extends BaseEntity {
    @CreatedBy
    @Column(length=45, updatable = false)
    protected String createdBy;

    @LastModifiedBy
    @Column(length=45, insertable = false)
    protected String modifiedBy;

    @Column(length=45, insertable = false)
    protected String deletedBy;

    protected void delete(String deletedBy) {
        // 이미 삭제된 경우 중복 처리 방지
        if (this.deletedAt != null) {
            return;
        }

        this.deletedBy = StringUtils.hasText(deletedBy)
                ? deletedBy
                : SecurityUtil.getCurrentUsername().orElse("SYSTEM");

        this.deletedAt = LocalDateTime.now();
    }
}
