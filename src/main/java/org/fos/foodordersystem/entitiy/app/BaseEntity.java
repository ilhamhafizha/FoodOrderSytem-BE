package org.fos.foodordersystem.entitiy.app;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BaseEntity {

    @Column(columnDefinition = "timestamp with time zone", nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(columnDefinition = "timestamp with time zone", nullable = false)
    private LocalDateTime modifiedDate = LocalDateTime.now();

    @Column(nullable = false, updatable = false)
    private String createdBy = "system";

    @Column
    private String updatedBy = "system";
}
