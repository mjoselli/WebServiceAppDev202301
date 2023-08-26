//
//  JokeViewModel.swift
//  ChuckJokesApp
//
//  Created by Mark Joselli on 24/08/23.
//

import Foundation
import Alamofire



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
