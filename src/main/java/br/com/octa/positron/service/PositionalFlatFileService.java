package br.com.octa.positron.service;

import br.com.octa.positron.model.dto.InuNpgDto;

import java.io.File;
import java.util.List;

public interface PositionalFlatFileService {
    public List<String> getCsvLines(File file);
    public List<InuNpgDto> fillData(List<String> linhas);
    public void  createFiles(List<InuNpgDto> inuNpgDtos, String destinationPath, String fileNamePrefix, String nameExtesion);
}
