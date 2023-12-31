package com.example.searchbasic

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version

@Entity
@Table(name = "`search_keyword`")
class SearchKeyword(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_keyword_id")
    val id: Long = 0,

    @Column(name = "keyword", unique = true)
    val keyword: String?,

    @Column(name = "search_cnt")
    var searchCnt: Long,

    @Version
    private val version: Long = 0,
) {
    fun increaseSearchCnt() {
        searchCnt += 1
    }
}
