package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.impl.testutils.ExerciseTestData.makeExerciseInfo
import com.example.fuelfit.exercise.impl.testutils.ExerciseTestData.makeExerciseList
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
class GetExercisesUseCaseImplTest {

    @MockK
    lateinit var repository: ExerciseRepository

    private lateinit var useCase: GetExercisesUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetExercisesUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns same Success`() = runTest {
        val exercise = makeExerciseInfo()
        val exerciseList = makeExerciseList(exercise)
        coEvery {
            repository.getExercises(limit = 10, offset = 0, categories = listOf(1))
        } returns ResultWrapper.Success(exerciseList)
        val result = useCase.invoke(limit = 10, offset = 0, categories = listOf(1))

        assertTrue(result is ResultWrapper.Success)
        assertEquals(exerciseList, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) {
            repository.getExercises(limit = 10, offset = 0, categories = listOf(1))
        }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ResultWrapper.Error(ApiError(404, "Not Found"))
        coEvery { repository.getExercises(limit = null, offset = null, categories = null) } returns error

        val result = useCase.invoke(limit = null, offset = null, categories = null)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error.error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) {
            repository.getExercises(limit = null, offset = null, categories = null)
        }
    }

    @Test
    fun `when repository returns empty ExerciseList then use case returns empty list`() = runTest {
        val emptyList = makeExerciseList()
        coEvery {
            repository.getExercises(limit = 5, offset = 0, categories = listOf(2))
        } returns ResultWrapper.Success(emptyList)

        val result = useCase.invoke(limit = 5, offset = 0, categories = listOf(2))

        assertTrue(result is ResultWrapper.Success)
        assertTrue((result as ResultWrapper.Success).data.exercises.isEmpty())
        coVerify(exactly = 1) {
            repository.getExercises(limit = 5, offset = 0, categories = listOf(2))
        }
    }

    @Test
    fun `when limit offset categories are null then use case still works`() = runTest {
        val exercise = makeExerciseInfo(id = 2, categoryId = 2, categoryName = "Cardio")
        val exerciseList = makeExerciseList(exercise)
        coEvery {
            repository.getExercises(limit = null, offset = null, categories = null)
        } returns ResultWrapper.Success(exerciseList)

        val result = useCase.invoke(limit = null, offset = null, categories = null)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(exerciseList, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) {
            repository.getExercises(limit = null, offset = null, categories = null)
        }
    }
}
