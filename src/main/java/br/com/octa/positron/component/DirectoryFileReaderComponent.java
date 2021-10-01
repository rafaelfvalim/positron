package br.com.octa.positron.component;

import br.com.octa.positron.service.PositionalFlatFileCSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DirectoryFileReaderComponent implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger = LoggerFactory.getLogger(DirectoryFileReaderComponent.class);

    @Autowired
    PositionalFlatFileCSVService positionalFlatFileCSV;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Realizando leitura dos arquivos CSV");
        positionalFlatFileCSV.generateFile();
    }

}
