package com.epam.advancedjvm.jmx;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeywordsController {

    private final KeywordsService keywordsService;

    public KeywordsController(KeywordsService keywordsService) {
        this.keywordsService = keywordsService;
    }

    @RequestMapping("keywords")
    public List<String> getAllKeyWords(@RequestBody String text) {
        return keywordsService.getAllKeyWords(text);
    }

}
