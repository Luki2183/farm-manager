package pl.luki2183.farmManager.fieldInfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class FieldInfoListDto {
    private List<FieldInfoDto> dtoList;
    private int count;
}
