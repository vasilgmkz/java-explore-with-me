package ru.practicum.admin.compilations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.*;
import ru.practicum.admin.compilations.validate.Marker;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDtoFromConsole {
    private Set<Integer> events;
    @BooleanFlag
    @Builder.Default
    private Boolean pinned = false;
    @NotNull (groups = Marker.AddCompilation.class)
    @NotBlank (groups = Marker.AddCompilation.class)
    @Size(min = 1, max = 50)
    private String title;
}
