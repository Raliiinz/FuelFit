package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.impl.testutils.ExerciseTestData.makeExerciseSearchItem
import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchExercisesUseCaseImplTest {

    @MockK
    lateinit var repository: ExerciseRepository

    private lateinit var useCase: SearchExercisesUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SearchExercisesUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns same Success`() = runTest {
        val items = listOf(
            makeExerciseSearchItem(id = 1, baseId = 101, name = "Push Ups"),
            makeExerciseSearchItem(id = 2, baseId = 102, name = "Pull Ups")
        )
        coEvery { repository.searchExercises(term = "push", language = "en") } returns ResultWrapper.Success(items)

        val result = useCase.invoke(term = "push", language = "en")

        assertTrue(result is ResultWrapper.Success)
        assertEquals(items, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.searchExercises(term = "push", language = "en") }
    }

    @Test
    fun `when search term has no matches then returns Success with empty list`() = runTest {
        val emptyList = emptyList<ExerciseSearchItem>()
        val term = "nonexistent"
        val language = "en"
        coEvery {
            repository.searchExercises(term = term, language = language)
        } returns ResultWrapper.Success(emptyList)

        val result = useCase.invoke(term = term, language = language)

        assertTrue(result is ResultWrapper.Success)
        assertTrue((result as ResultWrapper.Success).data.isEmpty())
        coVerify(exactly = 1) { repository.searchExercises(term = term, language = language) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ResultWrapper.Error(ApiError(404, "Not Found"))
        coEvery { repository.searchExercises(term = "pull", language = "en") } returns error

        val result = useCase.invoke(term = "pull", language = "en")

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error.error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.searchExercises(term = "pull", language = "en") }
    }
}
