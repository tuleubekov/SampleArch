package com.kay.samplearch.domain.models

import com.kay.samplearch.data.api.articles.ArticlesDto
import com.kay.samplearch.data.models.ArticlesModel

class SupportInfoMapper {

    fun mapToArticlesModel(dto: ArticlesDto): ArticlesModel {
        return ArticlesModel()
    }
}