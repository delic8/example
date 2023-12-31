package com.example.searchbasic

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.orm.ObjectOptimisticLockingFailureException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicReference

@SpringBootTest
internal class SearchServiceIntegrationTest {
    @Autowired
    private lateinit var searchService: SearchService

    @Autowired
    private lateinit var searchKeywordRepository: SearchKeywordRepository

    private val existingKeyword = "존재하는 검색어"
    private val existingSearchCnt = 22L

    @BeforeEach
    fun setUp() {
        searchKeywordRepository.deleteAll()
        searchKeywordRepository.save(SearchKeyword(keyword = existingKeyword, searchCnt = existingSearchCnt))
    }

    @Test
    @DisplayName("존재하는 키워드가 검색되는 케이스")
    fun exist_keyword() {
        //when
        val (_, searchCnt) = searchService.save(existingKeyword)

        //then
        Assertions.assertThat(existingSearchCnt + 1).isEqualTo(searchCnt)
    }

    @Test
    @DisplayName("존재하는 키워드가 동시에 검색되는 케이스")
    fun exist_keyword_concurrently() {
        val e = AtomicReference<Throwable?>()

        //when
        CompletableFuture.allOf(
            CompletableFuture.runAsync { searchService.save(existingKeyword) },
            CompletableFuture.runAsync { searchService.save(existingKeyword) }
        ).exceptionally { throwable: Throwable ->
            e.set(throwable.cause)
            null
        }.join()

        //then
        Assertions.assertThat(e.get()).isNotNull()
        Assertions.assertThat(e.get()).isInstanceOf(ObjectOptimisticLockingFailureException::class.java)
        Assertions.assertThat(existingSearchCnt + 1)
            .isEqualTo(searchKeywordRepository.findByKeyword(existingKeyword)?.searchCnt)
    }
}
