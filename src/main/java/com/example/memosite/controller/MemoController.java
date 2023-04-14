package com.example.memosite.controller;

import com.example.memosite.dto.MemoSaveRequest;
import com.example.memosite.entity.Memo;
import com.example.memosite.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.parser.Entity;

@Controller
@RequestMapping("/")
public class MemoController {
    @Autowired
    MemoRepository memoRepository;



    @GetMapping
    public String list(Model model){
        model.addAttribute("memoList",memoRepository.findAll());
        return "index";
    }


    @PostMapping("main")
    public String save(MemoSaveRequest memoSaveRequest){

        memoRepository.save(memoSaveRequest.toEntity());
        return "index";
    }
}
