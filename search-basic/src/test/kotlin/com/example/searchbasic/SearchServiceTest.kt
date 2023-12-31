package com.example.searchbasic

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(value = [MockitoExtension::class])
class SearchServiceTest {
    @InjectMocks
    private lateinit var searchService: SearchService

    @Mock
    private lateinit var searchKeywordRepository: SearchKeywordRepository

    @Test
    @DisplayName("없는 검색 키워드가 검색되는 케이스")
    fun not_exist_keyword() {
        val keyword = "없는 검색어"

        //given
        given(searchKeywordRepository.findByKeyword(keyword)).willReturn(null)
        given(searchKeywordRepository.save(any(SearchKeyword::class.java)))
            .willAnswer { invocation -> invocation.arguments[0] }

        //when
        val res = searchService.save(keyword)

        //then
        Assertions.assertThat(res.searchCnt).isEqualTo(1)
    }

    @Test
    @DisplayName("없는 검색 키워드가 검색되는 케이스")
    fun exist_keyword() {
        val keyword = "존재하는 검색어"
        val searchCnt = 22L

        //given
        given(searchKeywordRepository.findByKeyword(keyword))
            .willReturn(SearchKeyword(keyword = keyword, searchCnt = searchCnt))
        given(searchKeywordRepository.save(any(SearchKeyword::class.java)))
            .willAnswer { invocation -> invocation.arguments[0] }

        //when
        val res = searchService.save(keyword)

        //then
        Assertions.assertThat(res.searchCnt).isEqualTo(23)
    }
}
