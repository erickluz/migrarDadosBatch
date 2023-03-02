package org.erick.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class MigracaoDadosJob {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Job jobMigracaoDados(
			@Qualifier("migrarPessoa") Step migrarPessoa, 
			@Qualifier("migrarDadosBancarios") Step migrarDadosBancarios) {
		return jobBuilderFactory
				.get("migracaoDadosJob")
				.start(stepsParalelos(migrarPessoa, migrarDadosBancarios))
				.end()
				.incrementer(new RunIdIncrementer())
				.build();
	}
	
	private Flow stepsParalelos(Step migraPessoaStep, Step migrarDadosBancariosStep) {
		Flow migrarDadosBancariosFlow = new FlowBuilder<Flow>("migrarDadosBancariosFlow")
				.start(migrarDadosBancariosStep)
				.build();
		
		Flow stepsParalelos = new FlowBuilder<Flow>("stepsParalelosFlow")
				.start(migraPessoaStep)
				.split(new SimpleAsyncTaskExecutor())
				.add(migrarDadosBancariosFlow)
				.build();
		return stepsParalelos;
	}

}
