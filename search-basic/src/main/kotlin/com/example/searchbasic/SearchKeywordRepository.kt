package com.example.searchbasic

import org.springframework.data.jpa.repository.JpaRepository

interface SearchKeywordRepository : JpaRepository<SearchKeyword, Long> {
    fun findByKeyword(keyword: String?): SearchKeyword?
}
