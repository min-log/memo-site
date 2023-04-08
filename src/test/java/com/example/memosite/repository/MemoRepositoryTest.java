package com.example.memosite.repository;

import com.example.memosite.entity.Memo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTest {
    @Autowired
    MemoRepository memoRepository;

    @Test
    @DisplayName("1. 저장")
    public void test_1(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Memo memo = Memo.builder().memoText("Sample.."+ i).build();
            memoRepository.save(memo);
        });
    }
    @Test
    @DisplayName("2. 조회 - findById")
    public void test_2(){
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("==========================");
        if (result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }


    @Test
    @DisplayName("3. 조회 - getOne")
    @Transactional
    public void test_3(){
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);
        System.out.println("==========================");
        System.out.println(memo);

    }

    @Test
    @DisplayName("4. 수정 작업 테스트")
    public void test_4(){
        Memo memo = Memo.builder().mno(100L).memoText("Update test").build();
        System.out.println(memoRepository.save(memo));
    }

    @Test
    @DisplayName("5. 삭제 deleteById")
    public void test_5(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }


    // 페이징 처리 =====================
    @Test
    @DisplayName("6. 페이징 처리")
    public void test_6(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("========================");
        System.out.println("총 페이지 = " + result.getTotalPages()); //총 페이지
        System.out.println("전체 갯수 = " + result.getTotalElements());//전체 갯수
        System.out.println("현제 페이지 번호 = " + result.getNumber());//현제 페이지 번호
        System.out.println("페이지당 갯수 = " + result.getSize());//페이지당 갯수

        System.out.println("다음페이지 존재여부= " + result.hasNext());//다음페이지 존재여부
        System.out.println("시작 페이지 = " + result.isFirst());//시작 페이지

        System.out.println("========================");
        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }

    }


    @Test
    @DisplayName("7. 페이징 정렬 조건")
    public void test_7(){
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0,10,sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }


    @Test
    @DisplayName("8. 페이징 정렬 조건 여러개")
    public void test_8(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0,10,sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);
        for (Memo memo: result){
            System.out.println(memo);
        }

    }


    // Query Method 처리 =====================

    @Test
    @DisplayName("9. Query Method")
    public void test_9(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for (Memo memo: list){
            System.out.println(memo);
        }

    }

    @Test
    @DisplayName("10. Query Method와 pagerble 결합 - 조회")
    public void test_10(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    @DisplayName("11. Query Method와 pagerble 결합 - 삭제")
    @Transactional
    @Commit
    public void test_11(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }


    // Query 어노테이션 처리 =====================


}
