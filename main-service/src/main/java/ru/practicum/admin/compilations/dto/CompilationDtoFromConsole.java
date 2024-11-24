package ru.practicum.admin.compilations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.admin.compilations.validate.Marker;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDtoFromConsole {
    @NotNull(groups = Marker.AddCompilation.class)
    private Set<Integer> events;
    @NotNull(groups = Marker.AddCompilation.class)
    @BooleanFlag
    private Boolean pinned;
    @NotBlank(groups = {Marker.AddCompilation.class})
    @Size(min = 1, groups = Marker.UpdateCompilation.class)
    private String title;
}
