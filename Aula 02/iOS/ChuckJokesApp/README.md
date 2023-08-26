#  Tutorial Consume do API
API Utilizada
https://api.chucknorris.io/
1) Incluir Alamofire

O Alamofire é uma biblioteca de código aberto para Swift que simplifica e agiliza o processo de fazer solicitações de rede em aplicativos iOS, macOS, watchOS e tvOS. Ele fornece uma abstração sobre as APIs de rede subjacentes, como URLSession, tornando mais fácil para os desenvolvedores realizar operações de rede de forma mais eficiente e legível.

Aqui estão algumas características e benefícios do Alamofire:

1. **Sintaxe Simplificada:** O Alamofire oferece uma sintaxe mais limpa e concisa em comparação com a implementação manual das APIs de rede nativas.

2. **Tratamento de Requisições:** O Alamofire oferece métodos para criar, enviar e processar solicitações HTTP, incluindo suporte a métodos HTTP como GET, POST, PUT, DELETE, etc.

3. **Parâmetros e Codificação:** A biblioteca permite que você adicione facilmente parâmetros e codificação de dados aos pedidos, simplificando a criação de solicitações complexas.

4. **Validação de Resposta:** O Alamofire inclui recursos para validar automaticamente respostas, como status de resposta, tipos de conteúdo e códigos de status.

5. **Manuseio de Erros:** A biblioteca facilita o tratamento de diferentes tipos de erros de rede, permitindo um fluxo de código mais claro e organizado.

6. **Download e Upload de Arquivos:** O Alamofire suporta o download e upload de arquivos, tornando-o útil para lidar com casos em que você precisa transmitir dados binários, como imagens, vídeos, etc.

7. **Serialização e Desserialização:** A biblioteca também pode converter automaticamente as respostas JSON em objetos Swift usando a serialização e desserialização incorporadas.

8. **Operações Assíncronas:** O Alamofire executa todas as operações de rede de forma assíncrona, evitando bloqueios na interface do usuário enquanto aguarda as respostas.


O passo a passo sobre como incluir o Alamofire em seu projeto usando o Swift Package Manager:

Abra seu projeto Xcode.
No menu superior, vá em "File" (Arquivo) > "Swift Packages" > "Add Package Dependency..." (Adicionar Dependência de Pacote...).
Na janela que aparecerá, você será solicitado a inserir a URL do repositório do pacote. Insira a URL do repositório do Alamofire: https://github.com/Alamofire/Alamofire.git e clique em "Next" (Próximo).
Na próxima tela, você pode escolher uma versão específica, um intervalo de versões ou a branch "main". Para este exemplo, vamos selecionar a versão mais recente. Clique em "Next".
Selecione o destino (target) ao qual deseja adicionar o pacote. Normalmente, isso seria o target principal do seu aplicativo. Clique em "Finish".
O Swift Package Manager baixará e integrará automaticamente o pacote Alamofire ao seu projeto.

3) Crie um struct para guardar os dados da piada
```swift
struct Joke: Codable {
    let value: String
}
```
O Codable é um protocolo introduzido na linguagem Swift que simplifica a tarefa de serializar (codificar) e desserializar (decodificar) dados entre representações em formato de objeto e formato de dados, como JSON ou Property List.

O protocolo Codable é uma combinação dos protocolos Encodable e Decodable. Aqui está uma explicação rápida de ambos:

Encodable: Um tipo que adere ao protocolo Encodable pode ser codificado em um formato de dados, como JSON. Isso é útil quando você deseja transformar uma estrutura Swift em uma representação que possa ser enviada pela rede ou salva em um arquivo.

Decodable: Um tipo que adere ao protocolo Decodable pode ser decodificado de um formato de dados, como JSON, para uma estrutura Swift. Isso é útil quando você deseja receber e processar dados recebidos pela rede ou lidos de um arquivo.

3) Crie o ViewModel
```swift
class JokeViewModel: ObservableObject {
    @Published var joke: String = ""
    
    func fetchJoke() {
        AF.request("https://api.chucknorris.io/jokes/random").responseDecodable(of: Joke.self) { response in
            switch response.result {
            case .success(let jokeResponse):
                self.joke = jokeResponse.value
            case .failure(let error):
                print(error)
            }
        }
    }
}
```

ObservableObject é um protocolo em SwiftUI, o framework de interface do usuário da Apple, que permite que uma classe seja usada como um objeto observável, também conhecido como ViewModel. Ele faz parte do mecanismo de gerenciamento de estado do SwiftUI, que permite que as vistas reajam automaticamente às mudanças nos dados subjacentes.

Quando uma classe adere ao protocolo ObservableObject, você pode usar as propriedades publicadas na classe para transmitir mudanças de estado para as visualizações que estão observando essas propriedades. Isso é fundamental para criar aplicativos dinâmicos, onde as alterações nos dados devem ser refletidas automaticamente na interface do usuário, sem a necessidade de atualizações manuais.


4) modifique seu ContentView para ter uma variavel que irá receber a piada, um botão para mudar a piada e um texto para mostrar a piada
```swift
struct ContentView: View {
    @ObservedObject var viewModel = JokeViewModel()
    
    var body: some View {
        VStack {
            Text("Chuck Norris Jokes")
                .font(.title)
                .padding()
            
            Text(viewModel.joke)
                .padding()
            
            Button("Get Joke") {
                viewModel.fetchJoke()
            }
            .padding()
        }
    }
}
```
