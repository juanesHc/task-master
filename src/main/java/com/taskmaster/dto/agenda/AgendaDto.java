package com.taskmaster.dto.agenda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDto {

    private List<AgendaTaskDto> agendaTaskDtoList;

}
