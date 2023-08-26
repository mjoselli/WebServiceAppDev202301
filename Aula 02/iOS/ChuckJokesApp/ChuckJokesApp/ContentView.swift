import SwiftUI


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
