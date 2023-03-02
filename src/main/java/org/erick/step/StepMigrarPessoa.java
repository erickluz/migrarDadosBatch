package org.erick.step;



import org.erick.domain.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepMigrarPessoa {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step migrarPessoa(
			ItemReader<Pessoa> arquivoPessoaReader, 
			ClassifierCompositeItemWriter<Pessoa> classifierWriterPessoa,
			FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter) {
		return stepBuilderFactory
				.get("migrarPessoa")
				.<Pessoa, Pessoa> chunk(10000)
				.reader(arquivoPessoaReader)
				.writer(classifierWriterPessoa)
				.stream(arquivoPessoasInvalidasWriter)
				.build();
	}
}
