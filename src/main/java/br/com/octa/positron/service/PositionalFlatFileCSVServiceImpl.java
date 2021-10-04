package br.com.octa.positron.service;

import br.com.octa.positron.model.dto.InuNpgDto;
import br.com.octa.positron.utils.FileParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Service
public class PositionalFlatFileCSVServiceImpl implements PositionalFlatFileCSVService {
    Logger logger = LoggerFactory.getLogger(PositionalFlatFileCSVServiceImpl.class);

    @Value("${diretorio.saida}")
    private String diretorioSaidaArquivo;
    @Value("${diretorio.saidabck}")
    private String diretorioSaidaArquivoBck;
    @Value("${diretorio.entrada.csv}")
    private String diretorioEntradaArquivoCSV;
    @Value("${diretorio.processado.csv}")
    private String diretorioArquivoProcessado;
    @Value("${flatfile.nameprefix}")
    private String flaFileNamePrefix;
    @Value("${flatfile.extension}")
    private String flatFileNameExtesion;
    @Value("${flatfile.sleeptimesec}")
    private int sleepSizeSecs;
    @Value("${flatfile.splitsize}")
    private int splitSize;
    @Autowired
    PositionalFlatFileService positionalFlatFileService;

    @Override
    public void generateFile() {
        checkDiretories();
        Set<String> inputFiles = FileParseUtils.listDirectoryFiles(diretorioEntradaArquivoCSV);
        if (inputFiles == null) return;
        Set<String> filesCSV = FileParseUtils.filterByExtenesion(inputFiles, ".csv");
        String fileNameOrign = diretorioEntradaArquivoCSV + "/";
        String fileDestination = diretorioArquivoProcessado + "/";
        for (String file : filesCSV) {
            logger.info("Arquivo: " + file);
            fileNameOrign = fileNameOrign.concat(file);
            fileDestination = fileDestination.concat(file);
            List<String> linhas = positionalFlatFileService.getCsvLines(new File(fileNameOrign));
            List<InuNpgDto> inuNpgDtos = positionalFlatFileService.fillData(linhas);
            positionalFlatFileService.createFiles(inuNpgDtos, diretorioSaidaArquivo, flaFileNamePrefix, flatFileNameExtesion, sleepSizeSecs, splitSize, diretorioSaidaArquivoBck);
        }

        try {
            Thread.sleep(2 * 1000);
            Files.deleteIfExists(Paths.get(fileDestination));
            Files.move(Paths.get(fileNameOrign), Paths.get(fileDestination));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkDiretories() {
        try {
            if (!Files.isDirectory(Paths.get(diretorioSaidaArquivo))) {
                Files.createDirectories(Paths.get(diretorioSaidaArquivo));
            }
            if (!Files.isDirectory(Paths.get(diretorioEntradaArquivoCSV))) {
                Files.createDirectories(Paths.get(diretorioEntradaArquivoCSV));
            }
            if (!Files.isDirectory(Paths.get(diretorioArquivoProcessado))) {
                Files.createDirectories(Paths.get(diretorioArquivoProcessado));
            }
            if (!Files.isDirectory(Paths.get(diretorioArquivoProcessado))) {
                Files.createDirectories(Paths.get(diretorioArquivoProcessado));
            }
            if (!Files.isDirectory(Paths.get(diretorioSaidaArquivoBck))) {
                Files.createDirectories(Paths.get(diretorioSaidaArquivoBck));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
