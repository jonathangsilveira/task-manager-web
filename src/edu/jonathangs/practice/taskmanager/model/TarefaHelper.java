package edu.jonathangs.practice.taskmanager.model;

public final class TarefaHelper {

	public static boolean isConcluida(Tarefa tarefa) {
		return tarefa.getStatus() == 1;
	}
	
	public static String getDescricaoStatus(Tarefa tarefa) {
		return isConcluida(tarefa) ? "Concluída" : "Em curso";
	}
	
}
