
---

# MyHome Classificados - Sistema de Anúncios Imobiliários

## 1. Identificação

* **Disciplina:** Padrões de Projeto de Software.
* **Período:** 2025.2.
* **Professor:** Alex Sandro.
* **Equipe:** Janderson e Maria Eduarda.

## 2. Descrição da Solução

O **MyHome Classificados** é uma plataforma desenvolvida em Java 21 para a gestão e publicação de anúncios de imóveis (casas, apartamentos, terrenos e salas comerciais). O sistema permite que usuários cadastrem anúncios, apliquem filtros de busca avançados e recebam notificações através de diferentes canais. A arquitetura foca na extensibilidade e na aplicação rigorosa de padrões de projeto para garantir a manutenção e evolução do software.

## 3. Padrões de Projeto Utilizados

O projeto aplica diversos padrões do GOF (Gang of Four) para resolver problemas específicos:

* **Prototype:** Utilizado para a clonagem de anúncios e tipos de imóveis, permitindo criar novos objetos a partir de instâncias existentes sem acoplamento com classes concretas.
    * **Decisão de Projeto:** O sistema utiliza o `PrototypeRegistry` como um repositório central de instâncias pré-configuradas. Atualmente, o registro foca na entrega de clones de **Imóveis** (Casa, Apartamento) para agilizar o preenchimento de anúncios. Embora a classe `Anuncio` implemente a interface e possua suporte para *Deep Copy* (clonagem profunda do imóvel interno), o fluxo principal utiliza o protótipo para gerar o "produto" que será anunciado.
* *Localização:* Interface `Prototype` e classes `Anuncio` e `Imovel`.

* **Factory Method:** Define uma interface para criar objetos de imóveis, mas deixa as subclasses decidirem qual classe instanciar.
    * **Decisão de Projeto:** A `Fachada` utiliza o método `obterFactory` para instanciar a fábrica correta em tempo de execução. Cada fábrica concreta (ex: `CasaFactory`) é responsável por injetar valores padrões de sistema (como número de quartos ou existência de quintal) antes de entregar o objeto ao cliente.
* *Localização:* Classes no pacote `padrao_FactoryMethod`.

* **Builder:** Separa a construção de anúncios complexos (Venda e Aluguel) de sua representação, permitindo diferentes tipos de anúncios com o mesmo processo de construção.
* *Localização:* Interface `AnuncioBuilder` e classes `AnuncioVendaBuilder`, `AnuncioAluguelBuilder` e `DiretorAnuncio`.

* **Decorator:** Adiciona filtros dinâmicos às buscas de anúncios (Preço, Localização, Tipo) sem alterar a classe base de busca.
* *Localização:* Classes no pacote `padrao_Decorator`.

* **Strategy:** Define uma família de algoritmos para o envio de notificações (SMS, Email, Telegram, WhatsApp), tornando-os intercambiáveis conforme a preferência do usuário.
    * **Decisão de Projeto (Moderação):** Além das notificações, o Strategy é aplicado na **Moderação**. A `Fachada` atua como o **Contexto**, possuindo uma referência para `ModeracaoStrategy`. Isso permite alternar dinamicamente entre `ModeracaoAutomatica` (que utiliza o **Chain of Responsibility**) e `ModeracaoManual` através do método `setEstrategiaModeracao`.
* *Localização:* Interface `NotificacaoStrategy` e suas implementações, e interface `ModeracaoStrategy`.

* **Observer:** Permite que o sistema notifique usuários sobre atualizações em anúncios e registre logs de sistema automaticamente.
* *Localização:* Interface `AnuncioObserver` implementada por `Usuario` e `LoggerSistema`.

* **Chain of Responsibility:** Utilizado para a moderação de anúncios, onde uma cadeia de validadores verifica descrição, fotos e preços de forma sequencial.
    * **Decisão de Projeto:** O `ModeradorAnuncios` configura a corrente de validadores (Preço -> Descrição -> Fotos -> Termos). A cadeia é disparada pela estratégia de moderação automática. Diferente de implementações que param no primeiro erro, nossa `ValidadorAnuncioBase` percorre toda a lista para gerar um relatório completo de pendências ao usuário.
* *Localização:* Pacote `padrao_chainOfResposability`.

* **State:** Gerencia os estados de um anúncio (Novo, Em Moderação, Aprovado, Vendido, etc.), alterando o comportamento do objeto conforme seu estado interno.
    * **Decisão de Projeto:** O padrão State foca exclusivamente na integridade do ciclo de vida. **Importante:** O objeto de estado (ex: `ModeracaoEstado`) não é quem executa a validação técnica; ele apenas sinaliza que o anúncio está apto para tal. A orquestração (chamar a moderação e validar a chain) é realizada por um método central na **Fachada**.
* *Localização:* Interface `EstadoAnuncio`.

* **Facade:** Oferece uma interface unificada para as funcionalidades complexas do sistema, como criar anúncios, filtrar e gerenciar usuários.
    * **Decisão de Projeto:** A `Fachada` centraliza a inteligência do sistema, orquestrando a criação de imóveis (Factory/Prototype), a montagem de anúncios (Builder) e o fluxo de aprovação (State/Strategy/Chain).
* *Localização:* Classe `Fachada`.

* **Singleton:** Garante que a configuração do sistema seja única em toda a execução.
* *Localização:* Classe `ConfiguracaoSistema`.


## 4. Resolução dos Requisitos

| Requisito | Descrição | Padrão/Solução |
| --- | --- | --- |
| **RF01** | Cadastro de Imóveis | Resolvido via **Factory Method** para instanciar tipos específicos de imóveis. |
| **RF02** | Clonagem de Anúncios | Implementado com o padrão **Prototype** nas classes `Anuncio` e `Imovel`. |
| **RF03** | Construção de Anúncios | O padrão **Builder** gerencia a criação passo a passo de anúncios complexos. |
| **RF04** | Notificação de Usuários | O padrão **Observer** notifica as mudanças, e o **Strategy** define o canal (Email, SMS, etc.). |
| **RF05** | Filtros de Busca | O padrão **Decorator** permite empilhar múltiplos filtros (ex: Localização + Preço). |
| **RF06** | Moderação de Anúncios | Implementada via **Chain of Responsibility** para validar requisitos do anúncio antes da publicação. |
| **RF07** | Gestão de Estados | O padrão **State** controla o ciclo de vida do anúncio (Ativo, Suspenso, Vendido). |

### **Builder (RF08)**
* **Descrição:** Gerencia a construção especializada de anúncios baseada na modalidade de negócio (Venda ou Aluguel).
* **Decisão de Projeto:** O sistema utiliza o padrão **Builder** para separar a construção de anúncios complexos de sua representação final. Enquanto o `AnuncioVendaBuilder` foca em atributos como aceitação de financiamento e permuta, o `AnuncioAluguelBuilder` lida com taxas de condomínio e tempo de contrato.
    * **Diretor:** A classe `DiretorAnuncio` orquestra o processo, garantindo que os passos fundamentais (Título, Imóvel, Preço) e os específicos (Dados Contratuais) sejam executados na ordem correta.
    * **Flexibilidade:** Esta abordagem permite que a `Fachada` crie anúncios completos de forma passo a passo, sem sobrecarregar o construtor da classe `Anuncio` com dezenas de parâmetros opcionais.
* *Localização:* Interface `AnuncioBuilder` e classes `AnuncioVendaBuilder`, `AnuncioAluguelBuilder` e `DiretorAnuncio`.

## 5. Como Executar o Projeto

1. **Pré-requisitos:** Certifique-se de ter o **Java JDK 21** instalado.
2. **Ambiente:** O projeto foi configurado utilizando o Eclipse IDE.
3. **Importação:**
* Abra o Eclipse.
* Vá em `File -> Import -> Existing Projects into Workspace`.
* Selecione a pasta raiz do projeto.


4. **Execução:**
* Localize a classe que contém o método `main` (geralmente uma classe de teste ou a própria `fachada` se implementada para demonstração).
* Clique com o botão direito e selecione `Run As -> Java Application`.


5. **Dados:** O sistema utiliza um carregador de dados CSV localizado em `repositorio/CSVDataLoader` para popular o sistema inicialmente.

---