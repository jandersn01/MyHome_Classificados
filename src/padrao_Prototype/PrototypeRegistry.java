package padrao_Prototype;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.imovel.Apartamento;
import model.imovel.Casa;
import model.imovel.Imovel;

/**
 * Prototype Registry - Armazena protótipos pré-configurados de imóveis.
 * RF02: Certos tipos de anúncios devem iniciar com configuração padrão.
 */
public class PrototypeRegistry {

    private Map<String, Imovel> prototipos;

    public PrototypeRegistry() {
        prototipos = new HashMap<>();
        carregarPrototiposPadrao();
    }

    /**
     * Carrega protótipos pré-configurados no registro.
     */
    private void carregarPrototiposPadrao() {
        // Apartamento padrão: unidade habitacional em condomínio, 2 quartos, 60m²
        Apartamento aptPadrao = new Apartamento(
            "Apartamento Padrao",
            250000.0,
            "Apartamento em condominio residencial",
            "Centro",
            60.0,
            1,      // andar
            true,   // elevador
            2       // quartos
        );
        registrar("apartamento_padrao", aptPadrao);

        // Apartamento compacto: studio, 1 quarto, 35m²
        Apartamento aptCompacto = new Apartamento(
            "Studio Compacto",
            150000.0,
            "Studio moderno e compacto",
            "Centro",
            35.0,
            5,      // andar
            true,   // elevador
            1       // quarto
        );
        registrar("apartamento_compacto", aptCompacto);

        // Casa padrão: 3 quartos, com quintal, 120m²
        Casa casaPadrao = new Casa(
            "Casa Padrao",
            350000.0,
            "Casa residencial com quintal",
            "Bairro Residencial",
            120.0,
            true,   // quintal
            3       // quartos
        );
        registrar("casa_padrao", casaPadrao);

        // Casa simples: 2 quartos, sem quintal, 80m²
        Casa casaSimples = new Casa(
            "Casa Simples",
            200000.0,
            "Casa compacta ideal para casal",
            "Bairro Residencial",
            80.0,
            false,  // sem quintal
            2       // quartos
        );
        registrar("casa_simples", casaSimples);
    }

    /**
     * Registra um novo protótipo no registro.
     */
    public void registrar(String chave, Imovel prototipo) {
        prototipos.put(chave, prototipo);
    }

    /**
     * Remove um protótipo do registro.
     */
    public void remover(String chave) {
        prototipos.remove(chave);
    }

    /**
     * Obtém uma cópia (clone) de um protótipo registrado.
     * Retorna null se a chave não existir.
     */
    public Imovel obterPrototipo(String chave) {
        Imovel prototipo = prototipos.get(chave);
        if (prototipo != null) {
            return prototipo.clone();
        }
        return null;
    }

    /**
     * Verifica se existe um protótipo com a chave especificada.
     */
    public boolean existePrototipo(String chave) {
        return prototipos.containsKey(chave);
    }

    /**
     * Retorna todas as chaves de protótipos registrados.
     */
    public Set<String> listarChaves() {
        return prototipos.keySet();
    }

    /**
     * Retorna o número de protótipos registrados.
     */
    public int getTotalPrototipos() {
        return prototipos.size();
    }

    /**
     * Retorna informações sobre os protótipos disponíveis.
     */
    public String getPrototiposInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Prototipos Disponiveis ===\n");
        for (Map.Entry<String, Imovel> entry : prototipos.entrySet()) {
            Imovel imovel = entry.getValue();
            sb.append("- ").append(entry.getKey())
              .append(": ").append(imovel.getTipoImovel())
              .append(" - ").append(imovel.getTitulo())
              .append(" (").append(imovel.getArea()).append("m2, R$ ")
              .append(imovel.getPreco()).append(")\n");
        }
        return sb.toString();
    }
}
