package edu.jonathangs.practice.taskmanager.model;

import java.text.DateFormat;
import java.util.Objects;

public class TarefaDecorator {
		
		private Tarefa mTarefa;
		
		public TarefaDecorator(Tarefa tarefa) {
			mTarefa = tarefa;
		}
		
		public int getId() {
			return mTarefa.getId();
		}
		
		public String getTitulo() {
			return mTarefa.getTitulo();
		}
		
		public String getDescricao() {
			return mTarefa.getDescricao();
		}
		
		public String getDescricaoStatus() {
			return TarefaHelper.getDescricaoStatus(mTarefa);
		}
		
		public String getInclusao() {
			if (Objects.nonNull(mTarefa.getDataInclusao())) {
				DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
				return formatter.format(mTarefa.getDataInclusao());
			}
			return "--";
		}
		
		public String getAlteracao() {
			if (Objects.nonNull(mTarefa.getDataAlteracao())) {
				DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
				return formatter.format(mTarefa.getDataAlteracao());
			}
			return "--";
		}
		
		public String getConclusao() {
			if (Objects.nonNull(mTarefa.getDataConclusao())) {
				DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
				return formatter.format(mTarefa.getDataConclusao());
			}
			return "--";
		}
		
		public boolean isConcluida() {
			return TarefaHelper.isConcluida(mTarefa);
		}
		
		public int getStatus() {
			return mTarefa.getStatus();
		}
		
	}