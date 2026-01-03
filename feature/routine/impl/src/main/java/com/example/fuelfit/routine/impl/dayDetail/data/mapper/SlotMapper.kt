package com.example.fuelfit.routine.impl.dayDetail.data.mapper

import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryType
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotEntryDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotEntryRequestDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotEntryTypeDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotRequestDto

internal class SlotMapper {

    fun fromDto(dto: SlotDto): Slot =
        Slot(
            id = dto.id,
            dayId = dto.day,
            order = dto.order,
            comment = dto.comment,
            config = dto.config
        )

    fun toRequestDto(request: SlotRequest): SlotRequestDto =
        SlotRequestDto(
            day = request.dayId,
            order = request.order,
            comment = request.comment,
            config = request.config
        )

    fun fromDto(dto: SlotEntryDto): SlotEntry =
        SlotEntry(
            id = dto.id,
            slotId = dto.slot,
            exerciseId = dto.exercise,
            type = dto.type.toDomain(),
            repetitionUnit = dto.repetitionUnit,
            repetitionRounding = dto.repetitionRounding,
            weightUnit = dto.weightUnit,
            weightRounding = dto.weightRounding,
            order = dto.order,
            comment = dto.comment,
            config = dto.config
        )

    fun toRequestDto(request: SlotEntryRequest): SlotEntryRequestDto =
        SlotEntryRequestDto(
            slot = request.slotId,
            exercise = request.exerciseId,
            type = request.type.toDto(),
            repetitionUnit = request.repetitionUnit,
            repetitionRounding = request.repetitionRounding,
            weightUnit = request.weightUnit,
            weightRounding = request.weightRounding,
            order = request.order,
            comment = request.comment,
            config = request.config
        )

    private fun SlotEntryTypeDto.toDomain(): SlotEntryType =
        SlotEntryType.valueOf(name)

    private fun SlotEntryType.toDto(): SlotEntryTypeDto = when (this) {
        SlotEntryType.NORMAL -> SlotEntryTypeDto.NORMAL
        SlotEntryType.DROPSET -> SlotEntryTypeDto.DROPSET
        SlotEntryType.MYO -> SlotEntryTypeDto.MYO
        SlotEntryType.PARTIAL -> SlotEntryTypeDto.PARTIAL
        SlotEntryType.FORCED -> SlotEntryTypeDto.FORCED
        SlotEntryType.TUT -> SlotEntryTypeDto.TUT
        SlotEntryType.ISO -> SlotEntryTypeDto.ISO
        SlotEntryType.JUMP -> SlotEntryTypeDto.JUMP
    }
}
