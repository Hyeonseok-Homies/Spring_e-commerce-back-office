package com.backoffice.review.entity;

import com.backoffice.admin.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")

@SQLDelete(sql = "UPDATE review SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Review extends BaseEntity {//admin.entity.BaseEntity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderNumber;

    @Column(nullable = false)
    private Long productId;//고객이랑 상품 아이디를 활용한 정보를 가져와야하는지????????

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, length = 50)
    private String customerName;

    @Column(nullable = false, length = 50)
    private String productName;

    @Min(1) @Max(5)
    @Column(nullable = false, length = 50)
    private int grade;

    @Column(nullable = false, length = 500)
    private String reviewContent;

    @Column(nullable = false)
    private String customerEmail;

    private LocalDateTime deletedAt;
    private String deletedBy;

    @Builder
    public Review(
            Long id, Long orderNumber, String customerName,
            String productName, int grade, String reviewContent,
            String customerEmail, Long customerId,Long productId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.productName = productName;
        this.grade = grade;
        this.reviewContent = reviewContent;
        this.customerEmail = customerEmail;
        this.customerId = customerId;
        this.productId = productId;
    }

    //요구사항의 '마케팅 활용'을 위해 Soft Delete를 고려
    public void delete(String adminEmail) {
        if (this.deletedAt != null) {
            throw new IllegalStateException("이미 삭제 처리된 리뷰입니다.");
        }
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = adminEmail; //삭제한 관리자 기록
        this.deletedAt = LocalDateTime.now();
    }
}
