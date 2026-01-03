package com.example.fuelfit.routine.impl.testutils

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.routine.api.details.model.RoutineDayType

object RoutineDayTestData {

    fun makeRoutineDayRequest(
        routineId: Int = 1,
        order: Int = 1,
        name: String = "Day 1",
        description: String? = "Test day",
        isRest: Boolean = false,
        needLogsToAdvance: Boolean = true,
        type: RoutineDayType = RoutineDayType.CUSTOM,
        config: String? = null
    ) = RoutineDayRequest(
        routineId = routineId,
        order = order,
        name = name,
        description = description,
        isRest = isRest,
        needLogsToAdvance = needLogsToAdvance,
        type = type,
        config = config
    )

    fun makeRoutineDay(
        id: Int = 1,
        routineId: Int = 1,
        order: Int = 1,
        name: String = "Day 1",
        description: String? = "Test day",
        isRest: Boolean = false,
        needLogsToAdvance: Boolean = true,
        type: RoutineDayType = RoutineDayType.CUSTOM,
        config: String? = null
    ) = RoutineDay(
        id = id,
        routineId = routineId,
        order = order,
        name = name,
        description = description,
        isRest = isRest,
        needLogsToAdvance = needLogsToAdvance,
        type = type,
        config = config
    )
}
