package com.example.vinicius.condominium.app

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes

/**
 * Created by Shrey on 12/3/2017.
 */

class Tweet {

    var name: String? = null
    var handle: String? = null
    var minutes: String? = null
    var content: String? = null
    var conImg: String? = null
    var prof: Int = 0
    var comment: Int = 0
    var rt: Int = 0
    var like: Int = 0

    constructor() {

    }

    constructor(name: String, handle: String, minutes: String, content: String, prof: Int, conImg: String, comment: Int, rt: Int, like: Int) {
        this.name = name
        this.handle = handle
        this.minutes = minutes
        this.content = content
        this.prof = prof
        this.conImg = conImg
        this.comment = comment
        this.rt = rt
        this.like = like
    }

    constructor(name: String, handle: String, minutes: String, content: String, prof: Int, conImg: String) {
        this.name = name
        this.handle = handle
        this.minutes = minutes
        this.content = content
        this.prof = prof
        this.conImg = conImg
    }
}
