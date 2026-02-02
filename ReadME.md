
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
* *Localização:* Interface `Prototype` e classes `Anuncio` e `Imovel`.


* **Factory Method:** Define uma interface para criar objetos de imóveis, mas deixa as subclasses decidirem qual classe instanciar.
* *Localização:* Classes no pacote `padrao_FactoryMethod`.


* **Builder:** Separa a construção de anúncios complexos (Venda e Aluguel) de sua representação, permitindo diferentes tipos de anúncios com o mesmo processo de construção.
* *Localização:* Interface `AnuncioBuilder` e classes `AnuncioVendaBuilder`, `AnuncioaluguelBuilder` e `DiretorAnuncio`.


* **Decorator:** Adiciona filtros dinâmicos às buscas de anúncios (Preço, Localização, Tipo) sem alterar a classe base de busca.
* *Localização:* Classes no pacote `padrao_Decorator`.


* **Strategy:** Define uma família de algoritmos para o envio de notificações (SMS, Email, Telegram, WhatsApp), tornando-os intercambiáveis conforme a preferência do usuário.
* *Localização:* Interface `NotificacaoStrategy` e suas implementações.


* **Observer:** Permite que o sistema notifique usuários sobre atualizações em anúncios e registre logs de sistema automaticamente.
* *Localização:* Interface `AnuncioObserver` implementada por `Usuario` e `LoggerSistema`.


* **Chain of Responsibility:** Utilizado para a moderação de anúncios, onde uma cadeia de validadores verifica descrição, fotos e preços de forma sequencial.
* *Localização:* Pacote `padrao_chainOfResposability`.


* **State:** Gerencia os estados de um anúncio (Novo, Em Moderação, Aprovado, Vendido, etc.), alterando o comportamento do objeto conforme seu estado interno.
* *Localização:* Interface `EstadoAnuncio`.


* **Facade:** Oferece uma interface unificada para as funcionalidades complexas do sistema, como criar anúncios, filtrar e gerenciar usuários.
* *Localização:* Classe `fachada`.


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