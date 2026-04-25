package br.edu.ufrgs.model;

public class Aluno {
  private String nome;
  private double nota;

  public Aluno(String nome, double nota) {
    this.nome = nome;
    this.nota = nota;
  }

  public String verificarSituacao() {
    return (this.nota >= 6.0) ? "Aprovado(a)" : "Reprovado(a)";
  }

  public String getMensagemFinal() {
    return "O aluno " + nome + " está " + verificarSituacao() + " com nota " + nota;
  }
}
