@Override
public String toString() {
    return "Revista '" + getTitulo() + "' " + edicao +
            ". Edição (" + getAno() + ") - Status: " + getStatus();
}

@Override
public String formatarParaEtiqueta() {
    return "ETIQUETA - REVISTA: " + getTitulo() +
            " | Edição: " + edicao +
            " | Ano: " + getAno();
}
}