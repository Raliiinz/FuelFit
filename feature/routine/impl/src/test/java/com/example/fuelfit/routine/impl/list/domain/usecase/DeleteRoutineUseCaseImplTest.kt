package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteRoutineUseCaseImplTest {

    @MockK
    lateinit var repository: RoutineRepository

    private lateinit var useCase: DeleteRoutineUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteRoutineUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns Success`() = runTest {
        coEvery { repository.deleteRoutine(1) } returns ResultWrapper.Success(Unit)

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Success)
        coVerify(exactly = 1) { repository.deleteRoutine(1) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ApiError(404, "Not Found")
        coEvery { repository.deleteRoutine(99) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(99)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.deleteRoutine(99) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val error = ApiError(400, "Invalid ID")
        coEvery { repository.deleteRoutine(-1) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(-1)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.deleteRoutine(-1) }
    }
}
