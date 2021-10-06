package br.com.octa.positron.gateways.http.resource;


import br.com.octa.positron.model.dto.InuNpgDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatFileResource {

    @NotNull
    private long blocoDeProcessamento;
    @NotNull
    private String cnpjEmissor;
    @NotNull
    private String modeloNfe;
    @NotNull
    private String serie;
    @NotNull
    private long numeroInicial;
    @NotNull
    private long numeroFinal;
    @NotNull
    private String justificativa;
    @NotNull
    private String uf;
    @NotEmpty(message = "Informar o ano")
    private String ano;

    public InuNpgDto toDTO() {
        return new InuNpgDto(blocoDeProcessamento, cnpjEmissor, modeloNfe, serie, numeroInicial, numeroFinal, justificativa, uf, ano);
    }

    public List<InuNpgDto> toDTO(List<FlatFileResource> flatFileResources) {
        List<InuNpgDto> itens = new ArrayList<InuNpgDto>();
        flatFileResources.stream().forEach(ff -> itens.add(toDTO()));
        return itens;
    }

}
