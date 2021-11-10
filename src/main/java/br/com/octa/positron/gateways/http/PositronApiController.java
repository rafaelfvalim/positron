package br.com.octa.positron.gateways.http;

import br.com.octa.positron.gateways.http.resource.FlatFileResource;
import br.com.octa.positron.model.dto.InuNpgDto;
import br.com.octa.positron.service.PositionalFlatFileRestService;
import br.com.octa.positron.service.TelegramBotService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/positron")
public class PositronApiController {
    @Autowired
    PositionalFlatFileRestService positionalFlatFileRestService;
    
    @Autowired
    TelegramBotService telegramBotService;

    @PostMapping("inutilizacao")
    @ApiOperation(value = "Serviço de geração de arquivos posicionais de inutilização de nota")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> geradorDeArquivos(@RequestBody @Valid final  FlatFileResource flatFileResource) {
		telegramBotService.sendInformation("Recebendo solicitação via API");

    	List<InuNpgDto> inuNpgDtos = new ArrayList<InuNpgDto>();
        inuNpgDtos.add(flatFileResource.toDTO());
        positionalFlatFileRestService.generateFile(inuNpgDtos);
        return ResponseEntity.ok("");
    }
}
