package com.example.booksapp.models

class Game {
    var developer: String?
    var freetogame_profile_url: String?
    var game_url: String?
    var genre: String?
    var id: Long?
    var platform: String?
    var publisher: String?
    var release_date: String?
    var short_description: String?
    var thumbnail: String?
    var title: String?

    constructor(
        developer: String?,
        freetogame_profile_url: String?,
        game_url: String?,
        genre: String?,
        id: Long?,
        platform: String?,
        publisher: String?,
        release_date: String?,
        short_description: String?,
        thumbnail: String?,
        title: String?
    ) {
        this.developer = developer
        this.freetogame_profile_url = freetogame_profile_url
        this.game_url = game_url
        this.genre = genre
        this.id = id
        this.platform = platform
        this.publisher = publisher
        this.release_date = release_date
        this.short_description = short_description
        this.thumbnail = thumbnail
        this.title = title
    }
}