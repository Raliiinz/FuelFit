package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.impl.testutils.ExerciseTestData.makeExerciseInfo
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
class GetExerciseByIdUseCaseImplTest {

    @MockK
    lateinit var repository: ExerciseRepository

    private lateinit var useCase: GetExerciseByIdUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetExerciseByIdUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns same Success`() = runTest {
        val id = 1
        val exercise = makeExerciseInfo(id = id)
        coEvery { repository.getExerciseById(id) } returns ResultWrapper.Success(exercise)

        val result = useCase(id)

        assertTrue(result is ResultWrapper.Success)
        assertEquals(exercise, (result as ResultWrapper.Success).data)
        coVerify(exactly = 1) { repository.getExerciseById(id) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val id = 1
        val error = ApiError(404, "Not Found")
        coEvery { repository.getExerciseById(id) } returns ResultWrapper.Error(error)

        val result = useCase(id)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getExerciseById(id) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val invalidId = -1
        val error = ApiError(400, "Invalid ID")
        coEvery { repository.getExerciseById(invalidId) } returns ResultWrapper.Error(error)

        val result = useCase(invalidId)

        assertTrue(result is ResultWrapper.Error)
        assertEquals(error, (result as ResultWrapper.Error).error)
        coVerify(exactly = 1) { repository.getExerciseById(invalidId) }
    }
}
