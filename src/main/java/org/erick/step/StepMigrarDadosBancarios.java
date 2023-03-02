package org.erick.step;



import org.erick.domain.DadosBancarios;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepMigrarDadosBancarios {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step migrarDadosBancarios(
			ItemReader<DadosBancarios> arquivoDadosBancariosReader, 
			ItemWriter<DadosBancarios> bancoDadosBancariosWriter) {
		return stepBuilderFactory
				.get("migrarDadosBancarios")
				.<DadosBancarios, DadosBancarios> chunk(10000)
				.reader(arquivoDadosBancariosReader)
				.writer(bancoDadosBancariosWriter)
				.build();
	}
}
