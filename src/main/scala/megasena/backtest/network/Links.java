package megasena.backtest.network;

public enum Links {
    TODOS_CONCURSOS("http://www1.caixa.gov.br/loterias/_arquivos/loterias/D_megase.zip");
    private final String url;

    Links(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
