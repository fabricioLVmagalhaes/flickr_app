package com.magalhaes.flickrapp

class Photo(val title: String, val author: String, val authorId: String, val tags: String, val image: String) {

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', tags='$tags', image='$image')"
    }
}