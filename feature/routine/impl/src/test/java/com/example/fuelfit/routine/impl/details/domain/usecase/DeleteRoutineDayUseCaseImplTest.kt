package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
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
class DeleteRoutineDayUseCaseImplTest {

    @MockK
    lateinit var repository: RoutineDayRepository

    private lateinit var useCase: DeleteRoutineDayUseCaseImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteRoutineDayUseCaseImpl(repository)
    }

    @Test
    fun `when repository returns Success then use case returns Success`() = runTest {
        coEvery { repository.deleteDay(1) } returns ResultWrapper.Success(Unit)

        val result = useCase.invoke(1)

        assertTrue(result is ResultWrapper.Success)
        coVerify(exactly = 1) { repository.deleteDay(1) }
    }

    @Test
    fun `when repository returns Error then use case returns same Error`() = runTest {
        val error = ApiError(404, "Day not found")
        coEvery { repository.deleteDay(99) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(99)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.deleteDay(99) }
    }

    @Test
    fun `when id is invalid then repository returns Error`() = runTest {
        val error = ApiError(400, "Invalid ID")
        coEvery { repository.deleteDay(-1) } returns ResultWrapper.Error(error)

        val result = useCase.invoke(-1)

        assertTrue(result is ResultWrapper.Error)
        coVerify(exactly = 1) { repository.deleteDay(-1) }
    }
}
