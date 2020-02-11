package com.kay.samplearch.domain.models

import com.kay.samplearch.data.api.articles.ArticleDto
import com.kay.samplearch.data.models.ArticleModel

class SupportInfoMapper {

    fun mapToArticlesModel(dtos: List<ArticleDto>): List<ArticleModel> {
        return dtos.map {
            ArticleModel(
                link = it.link,
                title = it.titleDto.rendered,
                catIcon = it.catCoverDto?.sizesDto?.mobile ?: ""
            )
        }
    }
}