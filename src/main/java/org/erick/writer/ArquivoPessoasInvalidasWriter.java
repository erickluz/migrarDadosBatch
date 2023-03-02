package org.erick.writer;

import org.erick.domain.Pessoa;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ArquivoPessoasInvalidasWriter {

	@Bean
	public FlatFileItemWriter<Pessoa> pessoasInvalidasWriter() {
		return new FlatFileItemWriterBuilder<Pessoa>()
				.name("pessoasInvalidasWriter")
				.resource(new FileSystemResource("file/pessoas_invalidas.csv"))
				.delimited()
				.names("id")
				.build();
	}
}
