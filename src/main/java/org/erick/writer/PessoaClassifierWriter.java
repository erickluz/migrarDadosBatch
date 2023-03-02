package org.erick.writer;

import org.erick.domain.Pessoa;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaClassifierWriter {

	@Bean
	public ClassifierCompositeItemWriter<Pessoa> classifierWriterPessoa(
			JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
			FlatFileItemWriter<Pessoa> pessoasInvalidasWriter) {
		return new ClassifierCompositeItemWriterBuilder<Pessoa>()
				.classifier(classifier(bancoPessoaWriter, pessoasInvalidasWriter))
				.build();
		
	}

	@SuppressWarnings("serial")
	private Classifier<Pessoa, ItemWriter<? super Pessoa>> classifier(JdbcBatchItemWriter<Pessoa> bancoPessoaWriter,
			FlatFileItemWriter<Pessoa> arquivoPessoasInvalidasWriter) {
		return new Classifier<Pessoa, ItemWriter<? super Pessoa>>() {

			@Override
			public ItemWriter<? super Pessoa> classify(Pessoa pessoa) {
				if (pessoa.isValida()) {
					return bancoPessoaWriter;
				} else {
					return arquivoPessoasInvalidasWriter;
				}
			
			}
		
		};
	}
}
