package com.doxa.oauth2.DTO.manageAdminMatrix;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class SubAdminPermissionDto {
    private String userUuid;
    private List<AdministrativesDto> administrativesDtoList;
}
