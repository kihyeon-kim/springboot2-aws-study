package com.example.sb2aws.study.web.domain.post

import com.example.sb2aws.study.web.domain.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Post(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null,

    @Column(length = 50, nullable = false)
    var title: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String,

    var author: String,
) : BaseTimeEntity() {
    fun update(
        title: String,
        content: String,
    ) {
        this.title = title
        this.content = content
    }
}