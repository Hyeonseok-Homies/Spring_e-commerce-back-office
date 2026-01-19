package com.backoffice.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDeleteRequestDto {
    @NotNull(message = "삭제할 리뷰 ID가 입력되지 않았습니다.")
    private Long id;//삭제할 리뷰 id
}
