package br.com.octa.positron.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotServiceImpl implements TelegramBotService {
    Logger logger = LoggerFactory.getLogger(TelegramBotServiceImpl.class);

	@Value("${telegram.chatid}")
	private String CHAT_ID;

	@Value("${telegram.token}")
	private String TOKEN;


	private void sendMessage(String message) {
		HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5))
				.version(HttpClient.Version.HTTP_2).build();

		UriBuilder builder = UriBuilder.fromUri("https://api.telegram.org").path("/{token}/sendMessage")
				.queryParam("chat_id", CHAT_ID).queryParam("text", message);

		HttpRequest request = HttpRequest.newBuilder().GET().uri(builder.build("bot" + TOKEN))
				.timeout(Duration.ofSeconds(5)).build();

		HttpResponse<String> response;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
			logger.info(String.valueOf(response.statusCode()));
			logger.info(response.body());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(String message) {
		sendMessage("Erro: " + message);
	}

	@Override
	public void sendInformation(String message) {
		sendMessage("Info: " + message);
	}

	@Override
	public void sendWarning(String message) {
		sendMessage("Warnig: " + message);
	}

}
