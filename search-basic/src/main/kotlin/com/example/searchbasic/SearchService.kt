package com.example.searchbasic

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchService(private val searchKeywordRepository: SearchKeywordRepository) {
    @Transactional
    fun save(keyword: String?): SearchKeywordDto {
        val searchKeyword: SearchKeyword = searchKeywordRepository
            .findByKeyword(keyword)
            ?: SearchKeyword(keyword = keyword, searchCnt = 0L)
        searchKeyword.increaseSearchCnt()
        return SearchKeywordDto(searchKeywordRepository.save(searchKeyword))
    }
}
