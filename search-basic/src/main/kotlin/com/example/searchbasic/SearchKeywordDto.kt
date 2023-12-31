package com.example.searchbasic

data class SearchKeywordDto(val keyword: String?, val searchCnt: Long) {
    constructor(searchKeyword: SearchKeyword) : this(searchKeyword.keyword, searchKeyword.searchCnt)
}
