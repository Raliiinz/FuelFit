package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
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
class GetExerciseCategoriesUseCaseImplTest {

    @MockK
    lateinit var repository: ExerciseRepository

    private lateinit var useCase: GetExerciseCategoriesUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetExerciseCategoriesUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns same Success`() = runTest {
        val categories = listOf(
            ExerciseCategory(1, "Strength"),
            ExerciseCategory(2, "Cardio")
        )
        coEvery { repository.getExerciseCategories(limit = 10, offset = 0, name = null, ordering = null) } returns ResultWrapper.Success(categories)

        val result = useCase.invoke(limit = 10, offset = 0, name = null, ordering = null)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(categories, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) {
            repository.getExerciseCategories(limit = 10, offset = 0, name = null, ordering = null)
        }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ResultWrapper.Error(ApiError(404, "Not Found"))
        coEvery { repository.getExerciseCategories(limit = null, offset = null, name = null, ordering = null) } returns error

        val result = useCase.invoke(limit = null, offset = null, name = null, ordering = null)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error.error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) {
            repository.getExerciseCategories(limit = null, offset = null, name = null, ordering = null)
        }
    }
}
