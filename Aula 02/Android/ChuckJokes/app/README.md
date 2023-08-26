#  Tutorial Consume do API
API Utilizada
https://api.chucknorris.io/

1) Incluir Retrofit

```kotlin
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

```
O Retrofit é uma biblioteca de cliente HTTP para Android e Java que simplifica a comunicação com APIs REST. Ele é especialmente útil para converter as chamadas HTTP em chamadas de método de interface, tornando o código mais limpo e organizado. No contexto do aplicativo que estamos discutindo, o Retrofit desempenha o papel de criar as chamadas de API, processar as respostas e mapeá-las para objetos de modelo.

Aqui estão os principais papéis e benefícios do Retrofit no aplicativo:

1. **Definição de API Baseada em Interfaces:** O Retrofit permite definir as chamadas de API por meio de interfaces. Você define métodos na interface que correspondem aos endpoints da API, juntamente com as anotações para especificar o tipo de solicitação (GET, POST, etc.) e os parâmetros da chamada.

2. **Conversão Automática:** O Retrofit converte automaticamente as respostas JSON (ou outros formatos) das chamadas de API em objetos Kotlin/Java. Isso elimina a necessidade de você analisar manualmente a resposta e mapear os campos JSON para objetos.

3. **Gestão de Erros:** O Retrofit trata automaticamente os códigos de status HTTP e oferece um meio conveniente de lidar com erros, como códigos de erro 404 ou 500. Você pode definir métodos de fallback ou exceções personalizadas para diferentes situações de erro.

4. **Integração com Bibliotecas de Conversão:** O Retrofit suporta várias bibliotecas de conversão, como Gson e Jackson, permitindo que você escolha a que melhor se adapta ao seu projeto para realizar a conversão entre JSON e objetos de modelo.

5. **Geração de URLs:** O Retrofit lida com a criação e a formatação das URLs para chamadas de API, incorporando de forma eficiente os parâmetros e segmentos de caminho necessários.

6. **Interceptor:** O Retrofit permite adicionar interceptores que podem modificar as requisições antes de serem enviadas. Isso é útil para adicionar cabeçalhos, autenticação, logging e outros comportamentos globais.

7. **Cliente Personalizado:** Embora o Retrofit forneça um cliente HTTP padrão baseado no OkHttp, você também pode usar um cliente HTTP personalizado, se necessário.

No projeto, o Retrofit é utilizado para criar a interface `ChuckNorrisApi`, que define o endpoint da API "jokes/random". O Retrofit também se encarrega de converter automaticamente a resposta JSON em objetos `Joke` por meio da biblioteca GsonConverterFactory. Isso simplifica significativamente a criação de chamadas de API e o mapeamento dos resultados para objetos de modelo.


2)Criar a classe data que irá guardar os dados da piada

```kotlin
data class Joke(val value: String)
```

3)criar a interface ChuckNorrisApi

A interface ChuckNorrisApi define um contrato para as chamadas de API que você pretende fazer para obter uma piada aleatória sobre Chuck Norris. Essa interface é usada em conjunto com a biblioteca Retrofit para criar e executar solicitações HTTP de forma estruturada e fácil de usar.

```kotlin
interface ChuckNorrisApi {
@GET("jokes/random")
suspend fun getRandomJoke(): Joke
}
```

4)criar o ViewModel

O JokeViewModel é uma classe que faz parte da arquitetura de componentes do Android e segue o padrão de arquitetura Model-View-ViewModel (MVVM). O ViewModel atua como intermediário entre a interface do usuário (View) e a fonte de dados (Model), gerenciando a lógica de negócios e o estado dos dados relacionados a essa parte específica da aplicação.
```kotlin
class JokeViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.chucknorris.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val chuckNorrisApi = retrofit.create(ChuckNorrisApi::class.java)

    var joke by mutableStateOf("Chuck Norris Jokes")

    fun fetchJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jokeResponse = chuckNorrisApi.getRandomJoke()
                joke = jokeResponse.value
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

```
O JokeViewModel atua como uma camada intermediária entre a interface do usuário (no caso, Compose) e a lógica de busca de piadas da API. Ele gerencia o estado da piada atual e fornece uma maneira de buscar novas piadas sem a necessidade de a interface do usuário lidar diretamente com as chamadas de rede ou a manipulação dos dados da API. Isso resulta em uma separação clara de responsabilidades e uma UI mais limpa e desacoplada da lógica de busca de dados.


5) Criar a interface visual

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: JokeViewModel = JokeViewModel()
        setContent {
            ChuckJokesAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContentView(viewModel)
                }
            }
        }
    }
}

@Composable
fun ContentView(viewModel: JokeViewModel) {
    Column {
        Text(text = viewModel.joke, modifier = Modifier.padding(16.dp))
        Button(onClick = { viewModel.fetchJoke() }, modifier = Modifier.padding(16.dp)) {
            Text(text = "Get Joke")
        }
    }
}
```

6) colocar a permissão de acesso a internet no manifest

```xml
    <uses-permission android:name="android.permission.INTERNET" />
```