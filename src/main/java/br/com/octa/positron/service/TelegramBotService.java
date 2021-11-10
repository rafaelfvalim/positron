package br.com.octa.positron.service;

public interface TelegramBotService {
	
	public void sendError(String message);
	public void sendInformation(String message);
	public void sendWarning(String message);

}
