package padrao_FactoryMethod;

import model.imovel.Imovel;

/**
 * Factory Method - Classe abstrata que define o método de criação de imóveis.
 * RF01: O sistema deve ser flexível para adicionar novos tipos de imóveis
 * sem modificar código existente.
 */
public abstract class ImovelFactory {

    /**
     * Método factory abstrato que será implementado pelas subclasses.
     * Cada subclasse criará um tipo específico de imóvel.
     */
    public abstract Imovel criarImovel();

    /**
     * Método factory com parâmetros para criação personalizada.
     */
    public abstract Imovel criarImovel(String titulo, double preco, String descricao,
                                        String localizacao, double area);
}
