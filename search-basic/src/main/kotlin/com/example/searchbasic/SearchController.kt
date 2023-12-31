package com.example.searchbasic

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(
    private val searchService: SearchService,
) {
    @PostMapping("/search")
    fun search(keyword: String?): SearchKeywordDto {
        return searchService.save(keyword)
    }
}
