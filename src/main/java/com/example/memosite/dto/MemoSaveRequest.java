package com.example.memosite.dto;

import com.example.memosite.entity.Memo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoSaveRequest {
    private String memoText;
//    private LocalDateTime created_at;
//    private LocalDateTime updated_at;

    @Builder
    public MemoSaveRequest(String memoText) {
        this.memoText = memoText;
//        this.created_at = LocalDateTime.now();
//        this.updated_at = LocalDateTime.now();
    }

    //받은 객체를 entity화 해서 저장
    public Memo toEntity(){
        return Memo.builder().memoText(memoText).build();
    }
}
