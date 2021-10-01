package br.com.octa.positron.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InuNpgDto {

	private long blocoDeProcessamento;
	private String cnpjEmissor;
	private String modeloNfe;
	private String serie;
	private long numeroInicial;
	private long numeroFinal;
	private String justificativa;
	private String uf;
	private String ano;
	
}
