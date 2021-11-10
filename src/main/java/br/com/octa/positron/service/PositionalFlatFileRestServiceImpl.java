package br.com.octa.positron.service;

import br.com.octa.positron.model.dto.InuNpgDto;
import br.com.octa.positron.utils.FileParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class PositionalFlatFileRestServiceImpl implements PositionalFlatFileRestService {

    Logger logger = LoggerFactory.getLogger(PositionalFlatFileRestServiceImpl.class);

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
    @Autowired
    PositionalFlatFileService positionalFlatFileService;
    @Autowired
	private TelegramBotService telegramBotService;
    
    @Override
    @Async
    public void generateFile(List<InuNpgDto> inuNpgDtoList) {
        checkDiretories();
        String fileNameOrign = diretorioEntradaArquivoCSV + "/";
        String fileDestination = diretorioArquivoProcessado + "/";
		telegramBotService.sendInformation("Inicio do processamento das mensagens");
        positionalFlatFileService.createFiles(inuNpgDtoList,
                diretorioSaidaArquivo,
                flaFileNamePrefix,
                flatFileNameExtesion,
                sleepSizeSecs,
                diretorioSaidaArquivoBck);
		telegramBotService.sendInformation("Final do processamento das mensagens");
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
