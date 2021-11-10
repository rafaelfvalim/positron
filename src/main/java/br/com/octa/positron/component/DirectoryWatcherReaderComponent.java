package br.com.octa.positron.component;

import br.com.octa.positron.service.PositionalFlatFileCSVService;
import br.com.octa.positron.service.TelegramBotService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class DirectoryWatcherReaderComponent implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    PositionalFlatFileCSVService positionalFlatFileCSV;
    @Value("${diretorio.entrada.csv}")
    private String diretorioEntradaArquivoCSV;
    Logger logger = LoggerFactory.getLogger(DirectoryWatcherReaderComponent.class);
    
    @Autowired
    TelegramBotService telegramBotService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Diretório monitorado " + diretorioEntradaArquivoCSV);
        telegramBotService.sendInformation("Inicio do Servidor");
        try {
            execute();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void execute() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(diretorioEntradaArquivoCSV);
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                logger.info("Novo arquivo adicionado: " + event.context() + ".");
                positionalFlatFileCSV.generateFile();
            }
            key.reset();
        }

    }


}
