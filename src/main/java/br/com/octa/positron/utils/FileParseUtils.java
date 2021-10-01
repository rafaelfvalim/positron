package br.com.octa.positron.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class FileParseUtils {
	
	
	public static List<String> getLinhas(File file) throws IOException {
		return FileUtils.readLines(file, StandardCharsets.UTF_8);
	}
	public static Set<String> listDirectoryFiles(String dir) {
		File diretory;
		if(!Files.isDirectory(Paths.get(dir))){
			return null;
		}else{
			diretory = new File(dir);
		}
		if(diretory.listFiles().length == 0){
			return null;
		}
		return Stream.of(diretory.listFiles())
				.filter(file -> !file.isDirectory())
				.map(File::getName)
				.collect(Collectors.toSet());
	}

	public static Set<String> filterByExtenesion(Set<String> files, String ext) {
		return files.stream().filter(file -> file.contains(ext)).collect(Collectors.toSet());
	}
}
