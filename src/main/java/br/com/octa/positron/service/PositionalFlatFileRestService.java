package br.com.octa.positron.service;

import br.com.octa.positron.model.dto.InuNpgDto;

import java.util.List;

public interface PositionalFlatFileRestService {

    public void generateFile(List<InuNpgDto> inuNpgDtoList);
}
