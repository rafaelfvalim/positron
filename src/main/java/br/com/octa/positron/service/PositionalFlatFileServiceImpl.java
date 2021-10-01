package br.com.octa.positron.service;

import br.com.octa.positron.model.dto.InuNpgDto;
import br.com.octa.positron.utils.FileParseUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class PositionalFlatFileServiceImpl implements PositionalFlatFileService {
    Logger logger = LoggerFactory.getLogger(PositionalFlatFileServiceImpl.class);

    public List<String> getCsvLines(File file) {
        List<String> linhas = new ArrayList<String>();
        try {
            linhas = FileParseUtils.getLinhas(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    public List<InuNpgDto> fillData(List<String> linhas) {
        List<InuNpgDto> inuNpgDtos = new ArrayList<InuNpgDto>();
        String[] dados = null;
        for (String linha : linhas) {
            linha = linha.replaceAll("\uFEFF", "");
            dados = StringUtils.split(linha, ";");
            try {
                inuNpgDtos.add(new InuNpgDto(Long.valueOf(dados[0]),
                        dados[1],
                        dados[2],
                        dados[3],
                        Long.valueOf(dados[4]),
                        Long.valueOf(dados[5]),
                        dados[6],
                        dados[7],
                        dados[8]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return inuNpgDtos;
    }

    public void createFiles(List<InuNpgDto> inuNpgDtos, String destinationPath, String fileNamePrefix, String nameExtesion) {
        for (InuNpgDto inuNpgDto : inuNpgDtos) {
            long registros = 0L;
            long total = inuNpgDto.getNumeroFinal();
            long bloco = inuNpgDto.getBlocoDeProcessamento();
            while (registros < total) {
                Long registroInicial = registros;
                Long registroFinal = registros + bloco;
                if (registroFinal > total) {
                    registroFinal = total;
                }
                StringBuilder builder = new StringBuilder();
                String filename = destinationPath + "/" + fileNamePrefix + String.format("%09d", registroFinal) + nameExtesion;
                builder.append(inuNpgDto.getCnpjEmissor());
                builder.append("\n");
                builder.append(inuNpgDto.getModeloNfe());
                builder.append("\n");
                builder.append(inuNpgDto.getSerie());
                builder.append("\n");
                builder.append(registroInicial);
                builder.append("\n");
                builder.append(registroFinal);
                builder.append("\n");
                builder.append(inuNpgDto.getJustificativa());
                builder.append("\n");
                builder.append(inuNpgDto.getUf());
                builder.append("\n");
                builder.append(inuNpgDto.getAno());
                try {
                    logger.info("Generate file: " + filename);
                    FileUtils.writeStringToFile(new File(filename), builder.toString(), Charset.forName("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                registros = registros + bloco;
            }


        }
    }

}
