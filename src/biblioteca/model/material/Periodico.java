package biblioteca.model.material;

public class Periodico extends MaterialBibliografico{

    // atributos

    private String editora;
    private int numeroEdicao;
    private String dataPublicacao;
    private final TipoPeriodico tipoPeriodico;

    // construtor

    public Periodico(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, TipoPeriodico tipoPeriodico) {
        super(titulo, anoPublicacao);
        this.tipoPeriodico = tipoPeriodico;
        this.setEditora(editora.trim());
        this.setNumeroEdicao(numeroEdicao);
        this.setDataPublicacao(dataPublicacao.trim());
    }

    public Periodico(String titulo, int anoPublicacao, String editora, int numeroEdicao, String dataPublicacao, TipoPeriodico tipoPeriodico, int id) {
        super(titulo, anoPublicacao, id);
        this.tipoPeriodico = tipoPeriodico;
        this.setEditora(editora.trim());
        this.setNumeroEdicao(numeroEdicao);
        this.setDataPublicacao(dataPublicacao.trim());
    }

    // getters e setters

    public TipoPeriodico getTipoPeriodico() {
        return tipoPeriodico;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getNumeroEdicao() {
        return numeroEdicao;
    }

    public void setNumeroEdicao(int numeroEdicao) {
        this.numeroEdicao = numeroEdicao;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    // metodos

    @Override
    public String exibirDetalhes() {
        if (getTipoPeriodico() == TipoPeriodico.JORNAL) {
            return "Titulo do jornal: %s - Ano de publicação: %s - Numero de edição: %s - Editora: %s".formatted(this.getTitulo(), this.getAnoPublicacao(), this.getNumeroEdicao(), this.getEditora());
        }

        return "Titulo da revista: %s - Ano de publicação: %s - Numero de edição: %s - Editora: %s".formatted(this.getTitulo(), this.getAnoPublicacao(), this.getNumeroEdicao(), this.getEditora());
    }

}
