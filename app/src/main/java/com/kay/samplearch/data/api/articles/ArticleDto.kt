package com.kay.samplearch.data.api.articles

import com.google.gson.annotations.SerializedName

data class ArticleDto (
    @SerializedName("link") val link: String,
    @SerializedName("title") val titleDto: TitleDto,
    @SerializedName("cat_cover") val catCoverDto: CatCoverDto?
)

data class TitleDto(
    @SerializedName("rendered") val rendered: String
)

data class CatCoverDto(
    @SerializedName("sizes") val sizesDto: SizesDto?
)

data class SizesDto(
    @SerializedName("mobile") val mobile: String?
)